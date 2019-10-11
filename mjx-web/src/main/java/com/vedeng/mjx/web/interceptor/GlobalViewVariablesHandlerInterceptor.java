package com.vedeng.mjx.web.interceptor;

import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.util.CookieUtils;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 模板全局变量设置
 * @author: Wyatt
 * @Date: 2019-06-12 19:05
 */
@Component
public class GlobalViewVariablesHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(GlobalViewVariablesHandlerInterceptor.class);

    /**
     * 前后端分离静态资源版本号
     */
    @Value("${staticResourcesVersion}")
    private String staticResourcesVersion;

    /**
     * 静态资源的随机戳
     */
    @Value("${mjx.static_timestamp}")
    private String staticTimeStamp;

    /**
     * context url
     */
    @Value("${context_url}")
    private String contextUrl;

    /**
     * context url
     */
    @Value("${m_url}")
    private String mUrl;

    /**
     * 静态服务器前缀
     */
    @Value("${static_domain}")
    private String staticDomain;


    @Value("${mjx.app_id}")
    private String appId;

    /**
     * 记录日志
     */
    private final Logger LOG =  LoggerFactory.getLogger(this.getClass());

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

     //   LOG.info("GlobalViewVariablesHandlerInterceptor.postHandle | ---------------------------------------------------------------------------");
      //  LOG.info("GlobalViewVariablesHandlerInterceptor.postHandle | handler :{}, getRequestURI :{}", handler.getClass(), request.getRequestURI());

        request.setAttribute("staticResourcesVersion", staticResourcesVersion);



        boolean isWxRedirect=contextUrl.indexOf("mjx.vedeng.com")>=0||contextUrl.indexOf("wxtest.vedeng.com")>=0;

        if("121.43.178.1".equals(request.getServerName())){
            request.setAttribute("contextUrl", request.getServerName());
        }else{
            request.setAttribute("contextUrl", contextUrl);
        }

        request.setAttribute("mUrl", mUrl);

        request.setAttribute("forceWxRedirect", isWxRedirect);

        request.setAttribute("staticDomain", staticDomain);

        //
        request.setAttribute("staticTimeStamp", staticTimeStamp);

        request.setAttribute(CommonConstant.APP_ID, appId);

        // 获取用户信息
        TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
        request.setAttribute("loginUser",jx);
        // 记录用户登录状态
        if(null == jx) {
           // LOG.info("GlobalViewVariablesHandlerInterceptor.postHandle | ACCOUNT_STATUS ：{}", CommonConstant.ACCOUNT_LOGOUT_STATUS);
            request.setAttribute(CommonConstant.ACCOUNT_STATUS, CommonConstant.ACCOUNT_LOGOUT_STATUS);
        } else {
           // LOG.info("GlobalViewVariablesHandlerInterceptor.postHandle | ACCOUNT_STATUS ：{}", CommonConstant.ACCOUNT_LOGIN_STATUS);
            request.setAttribute(CommonConstant.ACCOUNT_STATUS, CommonConstant.ACCOUNT_LOGIN_STATUS);
        }
        // LOG.info("GlobalViewVariablesHandlerInterceptor.postHandle | ---------------------------------------------------------------------------");
    }


}

