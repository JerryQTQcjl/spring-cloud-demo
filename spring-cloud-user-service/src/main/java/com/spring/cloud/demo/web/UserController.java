package com.spring.cloud.demo.web;

import com.spring.cloud.demo.facade.rest.RecordFacade;
import com.spring.cloud.demo.facade.rest.RecordFacadeOri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @author jerry chan
 * @date 2021/6/10
 */
@RestController
public class UserController {

    @Autowired
    private RecordFacade recordFacade;

    @Autowired
    private RecordFacadeOri recordFacadeOri;

    @GetMapping("buy")
    public String buy(){

        return "";
    }

    @GetMapping("sale")
    public String sale(){

        return "";
    }

    @GetMapping("user/record/{id}")
    public String getRecord(@PathVariable String id){
//        return recordFacade.getRecord(id);
        return recordFacadeOri.getRecord(id);
    }

    @GetMapping("user/record/collapser/{id}")
    public String getRecordCollapser(@PathVariable String id) throws ExecutionException, InterruptedException {
        return recordFacade.getRecordCollapser(id).get();
    }
}
