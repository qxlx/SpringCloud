package com.hblg.springcloud.cfgbeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;

/**
 * @author i
 * @create 2020/2/18 16:26
 * @Description
 */
@Configuration
public class ConfigBean {   //类比spring中的applicationContext.xml

    //使用bean

    /***
     * RestTmplate提供了多种便捷访问远程HTTP服务的方法、
     * 是一种简单便捷的访问Restful服务模板类  是spring 提供的用于访问Rest服务的客户端模板工具集
     * @return
     */
    @Bean
    @LoadBalanced
    //Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端  负载均衡的工具。
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @Bean
    public IRule myRule(){
        //return new RandomRule();   //随机访问替代轮询访问
        return new RetryRule();//默认轮询访问每个节点 当有一个节点多次访问出现失败情况 会将该节点剔除
    }

}
