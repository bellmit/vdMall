package com.vedeng.mjx.web.interceptor;


import com.vedeng.mjx.common.util.CookieEncode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 拦截器配置
 * @author: Wyatt
 * @Date: 2019-06-12 19:17
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebConfiguration.class);

    @Autowired
    private GlobalViewVariablesHandlerInterceptor globalViewVariablesHandlerInterceptor;
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private AccountIdAuthInterceptor accountIdAuthInterceptor;
    @Value("${cookie.encode.password}")
    private String cookiePassord;

    /**
     * cookie加密
     ** @return
     */
    @Bean
    public CookieEncode encode(){
        CookieEncode encode=new CookieEncode();
        encode.init(cookiePassord);
        logger.info("cookie密码为："+cookiePassord);
        return encode;
    }
    @Bean
    public WebMvcConfigurer WebMvcConfigurer() {

        return new WebMvcConfigurer() {
            public void addInterceptors(InterceptorRegistry registry) {

                //需要登陆验证
                List<String> loginList = new ArrayList<>();
                loginList.add("/order/*");
                loginList.add("/app/order/*");
                loginList.add("/mjx/goodsCar/*");
                loginList.add("/app/address/*");
                loginList.add("/mjx/address/*");
                loginList.add("/user/*");
                loginList.add("/app/user/*");
                loginList.add("/m/message/*");
                loginList.add("/app/message/*");
                loginList.add("/app/goodsCar/*");
                loginList.add("/m/goodsCar/*");

                //不需要登陆验证
                List<String> noLoginList = new ArrayList<>();
                noLoginList.add("/order/verifyOrder");
                noLoginList.add("/order/updateOrderConfirmFinish");
                noLoginList.add("/order/updateErpOrder");
                noLoginList.add("/user/updateCompanyName");

                //需要accountId权限验证
                List<String> accountIdAuthList = new ArrayList<>();
                accountIdAuthList.add("/order/selectOrderDetail");

                //不需要accountId权限验证
                List<String> accountIdNoAuthList = new ArrayList<>();
                accountIdNoAuthList.add("/order/verifyOrder");
                accountIdNoAuthList.add("/order/updateOrderConfirmFinish");
                accountIdNoAuthList.add("/order/updateErpOrder");

                registry.addInterceptor(globalViewVariablesHandlerInterceptor);
                //判断用户是否登陆
                InterceptorRegistration loginRegistration = registry.addInterceptor(loginInterceptor);
                for(String str : loginList){
                    loginRegistration.addPathPatterns(str);
                }
                for(String str : noLoginList){
                    loginRegistration.excludePathPatterns(str);
                }
                //判断session里面的accountId是否与requestId是否相同
                InterceptorRegistration accountRegistration = registry.addInterceptor(accountIdAuthInterceptor);
                for(String str : accountIdAuthList){
                    accountRegistration.addPathPatterns(str);
                }
                for(String str : accountIdNoAuthList){
                    accountRegistration.excludePathPatterns(str);
                }
            }
        };
    }


}

