package com.spring.cloud.demo;

import com.spring.cloud.ribbon.config.PaymentRibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author jerry chan
 * @date 2021/6/10
 */
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
//ribbon 局部配置,优先级最低
@RibbonClient(value = "record-service",configuration = PaymentRibbonConfiguration.class)
public class SpringCloudUserService {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudUserService.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
