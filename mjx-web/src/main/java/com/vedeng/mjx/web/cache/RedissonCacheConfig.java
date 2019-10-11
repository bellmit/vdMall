package com.vedeng.mjx.web.cache;

import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @author: Wyatt
 * @Date: 2019-06-03 15:44
 */
@Configuration
@ComponentScan
@EnableCaching
public class RedissonCacheConfig {

    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private String port;

    private int connectionMinimumIdleSize = 10;
    private int idleConnectionTimeout=10000;
    private int connectTimeout=10000;
    private int timeout=3000;
    private int retryAttempts=3;
    private int retryInterval=1500;

    @Value("${redis.pass:}")
    private String password;
    private int subscriptionsPerConnection=5;
    private String clientName=null;
    private int subscriptionConnectionMinimumIdleSize = 1;
    private int subscriptionConnectionPoolSize = 50;
    private int connectionPoolSize = 64;
    private int database = 0;
    private int dnsMonitoringInterval = 5000;

    private int thread = 8; //当前处理核数量 * 2

    @Bean(destroyMethod="shutdown")
    RedissonClient client() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+host+":"+port)
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setConnectionPoolSize(connectionPoolSize)
                .setDatabase(database)
                .setDnsMonitoringInterval(dnsMonitoringInterval)
                .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
                .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
                .setSubscriptionsPerConnection(subscriptionsPerConnection)
                .setClientName(clientName)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
                .setTimeout(timeout)
                .setConnectTimeout(connectTimeout)
                .setIdleConnectionTimeout(idleConnectionTimeout);

        if (StringUtils.isNotBlank(password)) {
            config.useSingleServer().setPassword(password);
        }
        config.setCodec(new JsonJacksonCodec());
        config.setThreads(thread);
        config.setEventLoopGroup(new NioEventLoopGroup());
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        return new RedissonSpringCacheManager(redissonClient);
    }
    @Bean(name = "redissonConnectionFactory")
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }
}

