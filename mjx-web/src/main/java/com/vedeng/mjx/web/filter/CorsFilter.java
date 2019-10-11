package com.vedeng.mjx.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 跨域过滤器
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/7/2
 */
@Component
public class CorsFilter extends OncePerRequestFilter {

    private final Logger LOG = LoggerFactory.getLogger("login");
    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
   //  LOG.info("CorsFilter:::"+httpRequest.getRequestURI());
        // 允许的来源拦截
        String originHeader = httpRequest.getHeader("Origin");
        if (null != originHeader && (originHeader.contains(".ivedeng.com")
                || originHeader.contains(".vedeng.com") || originHeader.contains(".vedeng.com.cn"))) {
            response.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
            // 服务器端 Access-Control-Allow-Credentials = true时，参数Access-Control-Allow-Origin 的值不能为 '*'
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        else {
            response.setHeader("Access-Control-Allow-Origin", "*");
        }
//        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,version,gomanager,token,content-type,formtoken,source");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type,X-Requested-With,version,gomanager,token,formtoken,source");
        response.setHeader("Access-Control-Max-Age", "3600");
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(httpRequest, response);
        }


    }




    @Override
    public void destroy() {

    }
}
