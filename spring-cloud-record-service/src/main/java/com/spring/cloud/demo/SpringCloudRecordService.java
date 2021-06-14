package com.spring.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jerry chan
 * @date 2021/6/10
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudRecordService {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudRecordService.class, args);
    }
}
