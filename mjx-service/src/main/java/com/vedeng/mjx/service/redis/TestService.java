package com.vedeng.mjx.service.redis;

import org.springframework.stereotype.Service;

@Service
public class TestService implements TestServiceI{




    @RedisQueueService
    public Object test(QueueFlag queueFlag,Integer id){
            if(!queueFlag.getQueueflag()){
              return "";
            }
        System.out.println("test方法执行中");
        return "111";
    }


}
