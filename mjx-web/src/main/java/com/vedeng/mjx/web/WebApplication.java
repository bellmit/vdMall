package com.vedeng.mjx.web;

import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.vedeng.mjx.mapper", "com.vedeng.mjx.mapper.ext"})
@ComponentScan(basePackages = {"com.vedeng.mjx"})
@EnableFeignClients(basePackages = {"com.vedeng.mjx.service.feign"})
@EnableDiscoveryClient
@SpringBootApplication
@EnableApolloConfig
@EnableCaching
public class WebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        return builder.sources(WebApplication.class);
    }
}
