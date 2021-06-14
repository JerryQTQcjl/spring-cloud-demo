package com.spring.cloud.demo.facade.rest;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author jerry chan
 * @date 2021/6/13
 * @describtion ribbon + hystrix注解实现
 * 这里请求缓存个人觉得有点鸡肋，感兴趣可以执行查阅资料
 */
@Component
public class RecordFacade {

    private final static String SERVICE_URL = "http://record-service/";

    @Autowired
    private RestTemplate restTemplate;

    /*
        这种方式使用的都是同步发送
     */
    @HystrixCommand(
            //分组key，线程池默认按照分组key进行分组（线程池名称）
            groupKey = "record-service",
            commandKey = "getRecord",
            //线程池key（线程池名称），在没有线程池key时，按groupkey区分线程池
            threadPoolKey = "record-service-pool-01",
            commandProperties = {
                    //请求超时时间
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "1000"),
                    //执行策略，默认线程池
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "THREAD")
            },
            threadPoolProperties = {

                    @HystrixProperty(name = HystrixPropertiesManager.CORE_SIZE, value = "10"),
                    @HystrixProperty(name = HystrixPropertiesManager.MAX_QUEUE_SIZE, value = "-1")
            },
            fallbackMethod = "getRecordFallback"
    )
    public String getRecord(String id) {
        ThreadUtil.sleep(3000);
        String url = StrUtil.join("", SERVICE_URL, "record/", id);
        return restTemplate.getForObject(url, String.class);
    }

    /**
        这种方式使用的都是异步发送
     */
    @HystrixCommand(groupKey = "record-service")
    public Future<String> getRecordFuture(final String id){
        return new AsyncResult<String>(){

            @Override
            public String invoke() {
                ThreadUtil.sleep(3000);
                String url = StrUtil.join("", SERVICE_URL, "record/", id);
                return restTemplate.getForObject(url, String.class);
            }
        };
    }

    /*
        请求合并
     */
    @HystrixCollapser(
            collapserKey = "getRecordCollapser",
            //作用域，global全局  request单次请求
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            //合并方法
            batchMethod = "getRecords",
            collapserProperties = {
                    //最大合并请求数
                    @HystrixProperty(name = HystrixPropertiesManager.MAX_REQUESTS_IN_BATCH, value = "3"),
                    //最大等待时间，达到这个时间即使没有达到最大合并请求书也会发送请求
                    @HystrixProperty(name = HystrixPropertiesManager.TIMER_DELAY_IN_MILLISECONDS, value = "10000")
            }
    )
    public Future<String> getRecordCollapser(String id) {
        //这里不需要实现
        return null;
    }

    @HystrixCommand(
            groupKey = "record-service",
            threadPoolKey = "record-service-pool-01"
    )
    public List<String> getRecords(List<String> ids) {
        System.out.println(StrUtil.join(",","合并请求",ids));
        String url = StrUtil.join("", SERVICE_URL, "records/", StrUtil.join(",",ids));

        return restTemplate.getForObject(url,List.class);
    }


    public String getRecordFallback(String id) {
        return "服务降级";
    }

}
