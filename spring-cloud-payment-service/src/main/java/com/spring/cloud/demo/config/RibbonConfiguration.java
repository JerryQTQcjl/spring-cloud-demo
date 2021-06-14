package com.spring.cloud.demo.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jerry chan
 * @date 2021/6/12
 * @describtion ribbon 全局配置，优先级最高
 */
@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule iRule(){
        return new RoundRobinRule();
    }
}
