package com.spring.cloud.demo.facade.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RecordFacadeTest {

    @Autowired
    private RecordFacade recordFacade;

    @Test
    public void getRecord() {
        System.out.println(recordFacade.getRecord("1"));
    }

    @Test
    public void getRecords() {
    }
}