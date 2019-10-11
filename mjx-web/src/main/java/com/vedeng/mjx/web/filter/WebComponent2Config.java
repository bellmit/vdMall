package com.vedeng.mjx.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @Description:
 * @author: Wyatt
 * @Date: 2019-07-02 17:54
 */
@Configuration
public class WebComponent2Config {

    @Bean
    public Filter corsFilter() {
        return new CorsFilter();
    }

    @Bean
    public Filter appAuthFilter() {
        return new AppAuthFilter();
    }

    @Bean
    public Filter wxFilter() {
        return new WxAuthFilter();
    }



}

