package com.spring.cloud.ribbon.config;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jerry chan
 * @date 2021/6/12
 * @describtion ribbon 局部配置，优先级最低
 */
@Configuration
public class PaymentRibbonConfiguration {

    @Bean
    public IRule iRule(){
        //选择第一个可用的服务实例
        return new BestAvailableRule();
    }
}
