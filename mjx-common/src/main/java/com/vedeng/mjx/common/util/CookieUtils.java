package com.vedeng.mjx.common.util;


import org.apache.commons.lang.ArrayUtils;
import com.vedeng.mjx.common.constant.CommonConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
    private static final Logger LOG =  LoggerFactory.getLogger(CookieUtils.class);


    public static void addEncodeIdCookie(HttpServletResponse response,String key, Integer value, String domain, Integer expire){
        Cookie cookie=new Cookie(key  , CookieEncode.encodeIdTime(NumberUtils.toLong(value+""),System.currentTimeMillis()));
        LOG.info("COOKIE encode：key is {},value is {},domain is {}",key,cookie.getValue(),domain);
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(expire);
        response.addCookie(cookie);
    }
    public static void addVedengEncodeIdCookieMonth(HttpServletRequest request,HttpServletResponse response,String key, Integer value,String customDomain,int cookieExpireDays){
        if(StringUtils.isBlank(customDomain)){
            customDomain= request.getServerName();
        }
        addEncodeIdCookie(response,key,value,customDomain,cookieExpireDays);
    }

    public static void addWxAuthOpenIdCookie(HttpServletResponse response,String key,String value,Integer expire){
        Cookie c1 = new Cookie(key, value);
        c1.setPath("/");
        if(expire != null) {
            c1.setMaxAge(expire);
        }
        response.addCookie(c1);
    }

    public static void deleteLoginCookie(HttpServletRequest request,HttpServletResponse response,String key ){

        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static String getEncodeIdCookieOrHead( HttpServletRequest request,String key,int cookieExpireDays){

        String value="";
        Cookie[] cookies=request.getCookies();
        String org="cookie";
        if(!ArrayUtils.isEmpty(cookies)){
            for(Cookie cookie:cookies){
                if(StringUtils.equals(key,cookie.getName())){
                    value=cookie.getValue();
                }
            }
        }
        if(StringUtils.isBlank(value)){
            value=request.getHeader(key);
            org="head";
        }
        if(StringUtils.isBlank(value)||StringUtils.equalsIgnoreCase(value,"null")
                ||StringUtils.equalsIgnoreCase(value,"[object Object]")){
            LOG.info("cookie 为空 访问ID："+IpUtils.getRealIp(request)+"\t访问URL："+request.getRequestURI()+"\t访问域名："+request.getServerName()+"\tcookie来源地："+org +"\tvalue="+value);

            return "";
        }
        try{
        return  CookieEncode.decodeWithExpire(value,cookieExpireDays*1000L);
        }catch(Exception e){
            LOG.warn("访问ID："+IpUtils.getRealIp(request)+"\t访问URL："+request.getRequestURI()+"\t访问域名："+request.getServerName()+"\tcookie来源地："+org+"\t无法解析的cookie值："+value,e);

            return "";
        }
    }


    public static void main(String[] args) {
        System.out.println(2592000*1000l);
    }

    public static void addVedengEncodeIdHeadMonth(HttpServletRequest request, HttpServletResponse response, String key, Integer ssoAccountId) {
        String encode= CookieEncode.encodeIdTime(NumberUtils.toLong(ssoAccountId+""),System.currentTimeMillis());
        response.addHeader(key,encode);
    }

    public static String getCookieValueByKey(HttpServletRequest request,String name){
        String value="";
        Cookie[] cookies=request.getCookies();
        if(!ArrayUtils.isEmpty(cookies)){
            for(Cookie cookie:cookies){
                if(StringUtils.equals(name,cookie.getName())){
                    value=cookie.getValue();
                }
            }
        }
        return value;
    }

    public static void addCookiewithPathAndExpire(HttpServletResponse response, String key,String value,int expire,String path,String domain){
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(domain);
        if(expire>=0){
            //大于0时设置cookie 并持久化到浏览器 否则生命周期为关闭浏览器时删除cookie
            cookie.setMaxAge(expire);
        }
        cookie.setPath(path);
        response.addCookie(cookie);
        LOG.info("key is {},cookie.getValue is {},expire is {},path is {},domain is {}",key,cookie.getValue(),expire,path,domain);
    }

}
