package com.spring.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author jerry chan
 * @date 2021/6/10
 */
@EnableEurekaServer
@SpringBootApplication
public class SpringCloudEurekaServer {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaServer.class,args);
    }
}
