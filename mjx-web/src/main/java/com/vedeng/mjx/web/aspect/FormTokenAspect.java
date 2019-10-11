package com.vedeng.mjx.web.aspect;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.util.CookieUtils;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.service.redis.RedisOperateService;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Aspect
@Component
public class FormTokenAspect {

    private final Logger logger = LoggerFactory.getLogger(FormTokenAspect.class);

    @Resource
    private RedisOperateService redisOperateService;

    @Value("${force_cookie_domain}")
    private String forceCookieDomain;


    @AfterReturning(value = "@annotation(com.vedeng.mjx.web.annotation.FormTokenPre)",returning = "result",argNames = "result")
    public void doAfterReturningAdvicePointCutFormToKenPre(ResultInfo result){
        logger.info("---------- AfterReturning start ----------");
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        HttpServletRequest request = attributes.getRequest();
        String uri=request.getRequestURI();
        if(result.isSuccess()){
            logger.info("result is success uri is {}",uri);
            //成功时添加cookie 并存入redis
            String uuid = UUID.randomUUID().toString() + System.currentTimeMillis();
            String accountId = String.valueOf(ShiroSessionUtil.getSessionTopAccountJx().getAccountId());
            //存入key为uuid value为用户名加请求uri 过期时间24h
            redisOperateService.setKeyValueWithExpire(uuid, accountId+uri,86400*1000L);
            logger.info("setKeyValueWithExpire key is {] value is {}",uuid,accountId+uri);
            CookieUtils.addCookiewithPathAndExpire(response, CommonConstant.REDIS_FORM_TOKEN_KEY,uuid,-1,"/",forceCookieDomain);
        }
        logger.info("---------- AfterReturning end ----------");

    }
    @Around(value = "@annotation(com.vedeng.mjx.web.annotation.FormTokenPost)")
    public Object doAfterReturningAdvicePointCutFormToKenPost(ProceedingJoinPoint proceedingJoinPoint){
        logger.info("---------- Around start ----------");
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String clientToken = CookieUtils.getCookieValueByKey(request, CommonConstant.REDIS_FORM_TOKEN_KEY);
        logger.info("RepeatSubmit preHandle start URI is {}",request.getRequestURI());
        if (StringUtil.isBlank(clientToken)) {
            //前端传入clientToken 为空 拦截请求 提示页面已过期，请刷新后再试
            logger.info("redisFormToken in cookie isBlank interceptor return false");
            return ResultInfo.fail(VedengErrorCode.FORM_TOKEN_IN_COOKIES_EXPIRED);
        }
        String value = redisOperateService.getKeyValue(clientToken);
        if (StringUtil.isBlank(value)) {
            //redis中不存在该uuid 拦截请求
            logger.info("redisFormToken in redis isBlank interceptor return false");
            return ResultInfo.fail(VedengErrorCode.FORM_TOKEN_EXISTS_IN_REDIS);
        } else {
            //redis里面存在uuid 不拦截请求
            //删除对应uuid
            logger.info("redisFormToken exists in redis key is {} and value is{} interceptor return true",clientToken,value);
            redisOperateService.deleteStringRedisKey(clientToken);
            ResultInfo resultInfo=new ResultInfo(VedengErrorCode.UNKNOWN_EXCEPTION);
            try {
                resultInfo= (ResultInfo) proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                logger.error("proceedingJoinPoint.proceed error",throwable);

            }
            logger.info("---------- Around end with proceed----------");
            return resultInfo;
        }
    }
}