package com.spring.cloud.demo.facade.rest;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author jerry chan
 * @date 2021/6/13
 * @describtion ribbon + hystrix 原生代码
 */
@Component
public class RecordFacadeOri {

    private final static String SERVICE_URL = "http://record-service/";

    @Autowired
    private RestTemplate restTemplate;


    public String getRecord(final String id) {

        final HystrixCommand.Setter setter =
                HystrixCommand.Setter.withGroupKey(
                        HystrixCommandGroupKey.Factory.asKey("record-service")
                ).andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutEnabled(true)
                                .withExecutionTimeoutInMilliseconds(1000)
                );

        final RecordHystrixCommand hystrixCommand = new RecordHystrixCommand(setter, id);

        //同步执行
        //return hystrixCommand.execute();

        //异步执行,不会立即发送请求
        final Future<String> future = hystrixCommand.queue();

        try {
            //此处才会真正的发送请求
            return future.get();
        } catch (Exception e) {
            return hystrixCommand.getFallback();
        }

    }


    class RecordHystrixCommand extends HystrixCommand<String> {

        private String id;

        protected RecordHystrixCommand(Setter setter, String id) {
            super(setter);
            this.id = id;
        }

        @Override
        protected String run() throws Exception {
//            ThreadUtil.sleep(3000);
            String url = StrUtil.join("", SERVICE_URL, "record/", id);
            return restTemplate.getForObject(url, String.class);
        }

        @Override
        protected String getFallback() {
            return "服务降级";
        }
    }

}
