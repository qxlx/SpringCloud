package com.hblg.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author i
 * @create 2020/2/22 11:15
 * @Description  EurekaServer服务器端启动类 接受其他微服务注册进来
 */
@SpringBootApplication
@EnableEurekaServer
public class Config_Git_EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Config_Git_EurekaServerApplication.class,args);
    }

}
