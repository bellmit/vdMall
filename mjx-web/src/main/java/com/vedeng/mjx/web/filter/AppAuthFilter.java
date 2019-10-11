package com.vedeng.mjx.web.filter;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.constant.CookieType;
import com.vedeng.mjx.common.model.MapVo;
import com.vedeng.mjx.common.util.CookieUtils;
import com.vedeng.mjx.common.util.IpUtils;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.account.AccountService;
import com.vedeng.mjx.service.feign.passport.dto.ReqUserDto;
import com.vedeng.mjx.service.signInOrUp.SignInOrUpService;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.passport.api.login.dto.res.ResUserDTO;
import com.vedeng.passport.api.wechat.dto.req.ReqOuthWeChatDTO;
import com.vedeng.passport.api.wechat.dto.res.ResOuthWeChatDTO;
import com.vedeng.passport.api.wechat.service.WeChatApiService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class AppAuthFilter  extends OncePerRequestFilter {
    private final Logger LOG = LoggerFactory.getLogger("login");
    // 不需要登录就可以访问的路径(比如:注册登录等)
    private String exclude = ".*(\\.(js|css|jpg|png|txt|gif|jpeg|ico|bmp|json));.*(/signInOrUp/).*;";
    private List<Pattern> excludePattern = new ArrayList<Pattern>(1);


    @Value("${mxj.redis_type}")
    private String redisType;
    @Value("${mjx.static_recouse}")
    private String staticUrl;
    @Value("${force_cookie_domain:}")
    private String forceCookieDomain;
    @Value("${cookie.expire.seconds:2592000}")
    private Integer cookieExpireSeconds;

    @Value("${mjx.app_id}")
    private String appId;

    @Value("${mjx.app_secret}")
    private String appSecret;

    @Value("${mjx.session_timeout}")
    private Long sessionTimeOut;

    @Autowired
    public WeChatApiService weChatApiService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private SignInOrUpService signInOrUpService;

    @Override
    public void initFilterBean() throws ServletException {
        excludePattern.addAll(StringUtil.loadPattern(exclude).keySet());
    }

 
    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws ServletException, IOException {

        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originatingUrl = urlPathHelper.getOriginatingRequestUri(httpRequest);
        //其他请求不走此filter
        if (isStaticUrl(originatingUrl, httpRequest)) {
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }

        String ossAccountId2 = CookieUtils.getEncodeIdCookieOrHead(httpRequest, CookieType.APP_LOGIN_TOKEN.getKey(),cookieExpireSeconds);
        //cookie转session
       if (ShiroSessionUtil.getSessionTopAccountJx() == null) {

           String ossAccountId = CookieUtils.getEncodeIdCookieOrHead(httpRequest, CookieType.APP_LOGIN_TOKEN.getKey(),cookieExpireSeconds);
           LOG.info("用户未登陆开始查询cookie"+ossAccountId);
           if (StringUtils.isNotBlank(ossAccountId) && NumberUtils.toInt(ossAccountId) > 0) {
               TopAccountJx jx = accountService.selectBySsoAcountId(NumberUtils.toInt(ossAccountId));
               if (jx != null) {
                   LOG.info("用户未登陆开始 通过cookie获取导用户信息"+jx.getMobile());
                   ShiroSessionUtil.setSession(jx);
                   //延长Header时间
                   CookieUtils.addVedengEncodeIdHeadMonth(httpRequest, httpResponse, CookieType.APP_LOGIN_TOKEN.getKey(), jx.getSsoAccountId());
                   //延长cookie时间
                   CookieUtils.addVedengEncodeIdCookieMonth(httpRequest, httpResponse, CookieType.APP_LOGIN_TOKEN.getKey(), NumberUtils.toInt(ossAccountId), forceCookieDomain,cookieExpireSeconds);
               }
           }else{
               CookieUtils.deleteLoginCookie(httpRequest,httpResponse,CookieType.APP_LOGIN_TOKEN.getKey());
           }
        }else{
           //重新获取最新用户信息
         //  TopAccountJx jx = accountService.selectBySsoAcountId(ShiroSessionUtil.getSessionTopAccountJx().getSsoAccountId());
         //  ShiroSessionUtil.setSession(jx)  ;
       }
        if(ShiroSessionUtil.getSessionTopAccountJx()!=null){
            LOG.info("AppAuthFilter:::"+httpRequest.getServerName()+",url="+httpRequest.getRequestURI()+" ,  sid="+ossAccountId2+"\t"+IpUtils.getRealIp(httpRequest)+",headsid="+ httpRequest.getHeader("sid")+"\t"+ShiroSessionUtil.getSessionTopAccountJx().getMobile());
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }

    private boolean isStaticUrl(String originatingUrl, HttpServletRequest httpRequest) {
        return StringUtil.match(excludePattern, originatingUrl);
    }

    @Override
    public void destroy() {
    }




}
