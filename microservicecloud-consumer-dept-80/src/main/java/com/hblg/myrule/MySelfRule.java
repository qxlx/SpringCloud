package com.hblg.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author i
 * @create 2020/2/19 17:23
 * @Description
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        //return new RandomRule();//Ribbon默认是轮询的，自定义为随机的。
        return new RandomRule_i();
    }
}
