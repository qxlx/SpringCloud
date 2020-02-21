package com.hblg.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author i
 * @create 2020/2/19 17:46
 * @Description  RandomRule Ribbon随机访问算法
 */
//继承第二阶梯的抽象类
public class RandomRule extends AbstractLoadBalancerRule {

    /**
     * Randomly choose from all living servers
     */
    public Server choose(ILoadBalancer lb, Object key) {
        //先判断负载均衡是否为空 为空直接返回
        if (lb == null) {
            return null;
        }
        Server server = null;

        //使用while 而不是用if 为的是在server==null 在进行依次判断 避免因多线程操作server数据的线程安全问题
        while (server == null) {
            //如果当前线程是中断状态 直接返回
            if (Thread.interrupted()) {
                return null;
            }
            //获取到在线微服务列表
            List<Server> upList = lb.getReachableServers();
            //获取到所有包含在线和可能因网络拥堵出现的异常服务列表
            List<Server> allList = lb.getAllServers();
            //获取到所有的服务的数量
            int serverCount = allList.size();
            //如果数量为0 直接返回null
            if (serverCount == 0) {
                /*
                 * No servers. End regardless of pass, because subsequent passes
                 * only get more restrictive.
                 */
                return null;
            }
            //根据当前服务列表数量 随机生成一个生产者节点下标
            int index = chooseRandomInt(serverCount);
            server = upList.get(index);

            //如果当前server节点为null 当前线程让步 继续下一次
            if (server == null) {
                /*
                 * The only time this should happen is if the server list were
                 * somehow trimmed. This is a transient condition. Retry after
                 * yielding.
                 */
                Thread.yield();
                continue;
            }

            //如果当前Server节点 是活跃状态直接返回 使用
            if (server.isAlive()) {
                return (server);
            }
            //为了避免其他情况的出现 设置当前Server节点为null 线程让步
            // Shouldn't actually happen.. but must be transient or a bug.
            server = null;
            Thread.yield();
        }

        return server;

    }

    /***
     * ThreadLocalRandom用内部生成的种子进行初始化，可能不会被修改。
     * 适用时，在并发程序中使用ThreadLocalRandom而不是共享的Random对象通常会遇到更少的开销和争用。
     * 当多个任务（例如，每个ForkJoinTask ）在线程池中并行使用随机数时，使用ThreadLocalRandom是特别合适的。
     * @param serverCount
     * @return
     */
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
