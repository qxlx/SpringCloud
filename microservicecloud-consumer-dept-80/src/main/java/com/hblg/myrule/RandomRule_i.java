package com.hblg.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author i
 * @create 2020/2/19 17:33
 * @Description SpringCloud Ribbon随机访问算法实现
 *  依旧轮询策略，但是加上新需求，每个服务器要求被调用5次。也即
 * 以前是每台机器一次，现在是每台机器5次
 */
public class RandomRule_i extends AbstractLoadBalancerRule {

    //设置一个计数器 当前
    private Integer count = 0;
    //定义一个下标 currentIndex
    private Integer currentIndex = 0;
    //每个server节点访问的次数
    private static final Integer NUM =5;
    /**
     * Randomly choose from all living servers
     */
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
                /*
                 * No servers. End regardless of pass, because subsequent passes
                 * only get more restrictive.
                 */
                return null;
            }
        //--------------------------------------------
            if (count<NUM){
                server = upList.get(currentIndex);
                count++;//当前节点+1
            }else {
                //上一个节点访问次数达到5次
                currentIndex++;
                count = 0;
                //如果超出当前已有节点的数量 从0开始
                if (currentIndex>=upList.size()){
                    currentIndex = 0;
                }
            }
        // ----------------------------------------

            if (server == null) {
                /*
                 * The only time this should happen is if the server list were
                 * somehow trimmed. This is a transient condition. Retry after
                 * yielding.
                 */
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }

            // Shouldn't actually happen.. but must be transient or a bug.
            server = null;
            Thread.yield();
        }

        return server;

    }

    protected int chooseRandomInt(int serverCount) {
        return ThreadLocalRandom.current().nextInt(serverCount);
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}
