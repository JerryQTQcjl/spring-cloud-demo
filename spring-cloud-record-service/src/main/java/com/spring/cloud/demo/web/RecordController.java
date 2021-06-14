package com.spring.cloud.demo.web;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jerry chan
 * @date 2021/6/10
 */
@RestController
public class RecordController {

    @Value(value = "${server.port}")
    private String port;

    @GetMapping("record/{type}/{user}/{count}")
    public String saveRecord(@PathVariable("type") String type, @PathVariable("user") String user, @PathVariable("count") int count) {
        if (StrUtil.equals("1", type)) {
            System.out.println(StrUtil.join("","您向",user,"转账",count,"元"));
        }else{
            System.out.println(StrUtil.join("",user,"向您","转账",count,"元"));
        }
        return StrUtil.join("","记录服务[",port,"] 记录交易记录成功！");
    }

    @GetMapping("record/{id}")
    public String getRecord(@PathVariable String id){
        return StrUtil.join("","记录服务[",port,"] 获取id为",id,"的交易记录！");
    }

    @GetMapping("records/{ids}")
    public List<String> getRecords(@PathVariable("ids") String[] ids){
        final ArrayList<String> result = new ArrayList<>();
        Arrays.asList(ids).forEach(id -> {
            result.add(StrUtil.join("","记录服务[",port,"] 获取id为",id,"的交易记录！"));
        });
        return result;
    }
}
