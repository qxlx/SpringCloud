package com.hblg.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient//Eureka客户端
@EnableFeignClients(basePackages = {"com.hblg.springcloud"})
//@ComponentScan("com.hblg.springcloud")
public class DeptConsumer80_Feign_App {

    /***
     * Feign通过接口的方法调用Rest服务 之前是Ribbon+RestTemplate
     * 该请求发送给Eureka服务器
     * 通过Feign直接找到服务接口 由于在进行服务调用的时候融合了Ribbon技术 所以也支持负载均衡作用
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DeptConsumer80_Feign_App.class, args);
    }
}