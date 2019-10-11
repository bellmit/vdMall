package com.vedeng.mjx.service.redis;


import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.javassist.ClassPool;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect //该注解标示该类为切面类
@Component //注入依赖
public class QueueAspect {



    private RedisOperateService redisOperateService;
    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体
    @Around("@annotation(redisQueueService)")
    public Object addqueue(ProceedingJoinPoint jp, RedisQueueService redisQueueService) {



        Object[] parames = jp.getArgs();//获取目标方法体参数

      /*  Config config = new Config();
        config.useSingleServer().setAddress("redis://129.211.121.171:6379").setDatabase(2);
        RedissonClient redissonClient = Redisson.create(config);*/
        System.out.println(jp.getSignature().toString( ));

        /*ClassPool classPool=new ClassPool();
        for (Object argItem : parames) {
            if (argItem instanceof QueueFlag) {
                QueueFlag queueFlag = (QueueFlag) argItem;
                Boolean queueflag = queueFlag.getQueueflag();
                String queueName = jp.getTarget().getClass().toString();
                RBlockingQueue<Object> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
                RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
                if (queueflag) {
                    Object infoQueue = queueFlag.getInfoQueue();
                    *//*延时一分钟才能取*//*
                    delayedQueue.offer(infoQueue, 1, TimeUnit.MINUTES);
                    break;
                }else {
                    try {
                        jp.proceed();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        }*/

        System.out.println(jp.getSignature().getName());
        String className = jp.getTarget().getClass().toString();//获取目标类名
        System.out.println("className:" + className);
        className = className.substring(className.indexOf("com"));
        String signature = jp.getSignature().toString();//获取目标方法签名
        System.out.println("signature:" + signature);
        return "2222";
    }


/*    @AfterReturning("@annotation(rl)")
    public Object takequeue(JoinPoint jp, RedisQueueService rl) {
        Object[] parames = jp.getArgs();//获取目标方法体参数

        Config config = new Config();
        config.useSingleServer().setAddress("redis://129.211.121.171:6379").setDatabase(2);
        RedissonClient redissonClient = Redisson.create(config);
        for (Object argItem : parames) {
            if (argItem instanceof QueueFlag) {
                QueueFlag queueFlag = (QueueFlag) argItem;
                Boolean queueflag = queueFlag.getQueueflag();
                String queueName = queueFlag.getQueueName();
                RBlockingQueue<Object> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
                RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
                if (!queueflag) {
                    return delayedQueue;
                }else {
                    return delayedQueue.peek();
                }
            }
        }
        return null;
    }*/
}
