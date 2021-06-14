package com.spring.cloud.demo.web;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author jerry chan
 * @date 2021/6/10
 */
@RestController
public class PaymentController {

    @Value(value = "${server.port}")
    private String port;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("payment/{user}/{count}")
    public String payment(@PathVariable("user") String user, @PathVariable("count") String count) {
        String url = StrUtil.join("", "http://record-service/record/{type}/{user}/{count}");

        final String result = restTemplate.getForObject(
                url,
                String.class,
                MapUtil.builder(new HashMap<String, Object>(3))
                        .put("type", "1")
                        .put("user", user)
                        .put("count", count)
                        .build()
        );
        return StrUtil.join("",result,"\n","支付服务[",port,"]处理，付款成功");
    }

    @GetMapping("receive/{user}/{count}")
    public String receive(@PathVariable("user") String user, @PathVariable("count") String count) {
        String url = StrUtil.join("", "http://record-service/record/{type}/{user}/{count}");

        final String result = restTemplate.getForObject(
                url,
                String.class,
                MapUtil.builder(new HashMap<String, Object>(3))
                        .put("type", "2")
                        .put("user", user)
                        .put("count", count)
                        .build()
        );
        return StrUtil.join("",result,"\n","支付服务[",port,"]处理，收款成功");
    }
}
