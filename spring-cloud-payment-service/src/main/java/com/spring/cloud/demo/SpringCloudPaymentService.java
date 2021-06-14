package com.spring.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author jerry chan
 * @date 2021/6/10
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudPaymentService {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudPaymentService.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
