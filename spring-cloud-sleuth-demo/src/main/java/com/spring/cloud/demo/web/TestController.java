package com.spring.cloud.demo.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jerry chan
 * @date 2021/6/21
 */
@RestController
public class TestController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @GetMapping("test")
    public String test() {
        return "jenkins 测试 dev tag v1.1";
    }

    @GetMapping("shutdown")
    public String shutdown() {
        ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
        ctx.close();
        return "context is shutdown";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
