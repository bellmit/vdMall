package com.vedeng.mjx.web.controller.msearch.search;

import com.vedeng.mjx.service.redis.QueueFlag;
import com.vedeng.mjx.service.redis.TestServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @Autowired
    private TestServiceI testServiceI;


    @RequestMapping("/testRedis")
    public Object testRedis(){


        QueueFlag queueFlag=new QueueFlag();
        queueFlag.setQueueflag(true);
//        queueFlag.setQueueName("orderqueue");


        System.out.println("hello");
        return testServiceI.test(queueFlag,2);


    }
}
