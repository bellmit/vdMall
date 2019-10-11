/*
 * Copyright 2018 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.vedeng.mjx.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedeng.mjx.common.constant.CommonConstant;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/**
 * <b>Description: 接口请求工具类</b><br>
 * <b>Author: Franlin.wu</b> 
 * @fileName HttpClientUtil.java
 * <br><b>Date: 2018-09-25 下午6:34:20 </b> 
 *
 */
public class HttpClientUtil {
    
	public static final String APPLICATION_JSON = "application/json";
    
	public static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    /**
     * get请求
     */
    public static final String REQ_TYPE_GET = "get";
    
    /**
     * post请求
     */
    public static final String REQ_TYPE_POST = "post";
    
    /**
     * put请求
     */
    public static final String REQ_TYPE_PUT = "put";
    
    /**
     * delete请求
     */
    public static final String REQ_TYPE_DELETE = "delete";
    
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);
    
    /**
     * 
     * <b>Description: rest统一接口返回Json字符串</b><br> 
     * @param uri      请求uri
     * @param method   请求方法GET,POST,PUT,DELETE
     * @param headers  请求头参数
     * @param paramsObj   接口入参
     * @return
     * <b>Author: Franlin</b>  
     * <br><b>Date: 2018年9月25日 上午9:06:42 </b>
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static String httpForJsonByMethod(String uri, String method, Map<String, String> headers, Object paramsObj) throws ClientProtocolException, IOException
    {
    	if(null == method || null == uri)
    	{
    		return "{\"code\":404,\"message\":\"请求请求参数异常\"}";
    	}
    	// 请求url
    	URI reqUrl = null;
    	try
		{
    		URL url = new URL(uri);
    		// 请求类型 http/https
    		String scheme = url.getProtocol();
    		// 域名
    		String host = url.getHost();
    		// 请求路径
    		String path = url.getPath();
    		// query
    		String query = url.getQuery();
    		// 端口号
    		int port = url.getPort();
    		LOG.info("scheme:{}, host:{}, port:{}, path:{}, query:{}", scheme, host, port, path, query);
			reqUrl = new URI(scheme, null, host, port, path, query, null);
		} catch (URISyntaxException e) {
			LOG.error("设置url发生异常", e);
		} catch (MalformedURLException e) {
			LOG.error("请求uri异常", e);
		}
    	if(null == reqUrl) {
    		return "{\"code\":404,\"message\":\"请求url错误\"}";
    	}
    	// 创建连接
        HttpClient client = HttpClients.createDefault();
        
        ObjectMapper mapper = new ObjectMapper(); 
        // 对象转json 转换策略: Include.NON_EMPTY 空字符串忽略转换  / Include.NON_NULL null忽略转换
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String reqStr = null;
		try
		{
			reqStr = mapper.writeValueAsString(paramsObj);
		} catch (JsonProcessingException e) {
			LOG.error("json解析请求请求参数异常", e);
		}
		
        LOG.info("请求类型：{}，请求参数：{}", method, reqStr);
        
        StringEntity stringEntity = new StringEntity(reqStr, CommonConstant.CHARSET_UTF8);
        stringEntity.setContentType(CONTENT_TYPE_TEXT_JSON);
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        // 连接响应
        HttpResponse response = null;
        
        if(REQ_TYPE_GET.equalsIgnoreCase(method))
        {
            HttpGet request = new HttpGet(reqUrl);
            
            // 封装请求头
            httpQequestHead(request, headers);
            
            response = client.execute(request);
        }
        else if(REQ_TYPE_POST.equalsIgnoreCase(method))
        {
            HttpPost request = new HttpPost(reqUrl);
            
            // 封装请求头
            httpQequestHead(request, headers);
            
            request.setEntity(stringEntity);
            response = client.execute(request);
        }
        else if(REQ_TYPE_PUT.equalsIgnoreCase(method))
        {
            HttpPut request = new HttpPut(reqUrl);
            
            // 封装请求头
            httpQequestHead(request, headers);
            
            request.setEntity(stringEntity);
            response = client.execute(request);
        }
        else if(REQ_TYPE_DELETE.equalsIgnoreCase(method))
        {
            HttpDelete request = new HttpDelete(reqUrl);
            
            // 封装请求头
            httpQequestHead(request, headers);
            
            response = client.execute(request);
        }
        
        if(null == response)
        {
        	return "{\"code\":405,\"message\":\"响应异常\"}";
        }
        
        return EntityUtils.toString(response.getEntity(), Consts.UTF_8);
    }
    
    /**
     * 
     * <b>Description: 封装请求头</b><br> 
     * @param request
     * @param headers
     * <b>Author: Franlin.wu</b>  
     * <br><b>Date: 2018年12月11日 下午3:25:47 </b>
     */
    private static void httpQequestHead(HttpRequestBase request, Map<String, String> headers) {
    	request.setHeader("Content-Type", "application/json");
    	if(null != headers) {
    		for(Map.Entry<String, String> entry : headers.entrySet())
    		{
    			if(null == entry || null == entry.getKey()) {
    				continue;
    			}
    			String key = entry.getKey();
    			
    			if(null == key) {
    				continue;
    			}
    			
    			String value = entry.getValue();
    			value = null == value ? "" : value;
    			// 避免被覆盖
    			if("Accept".equals(key)) {
    				value = "application/json;" + value;
    			} else if("Content-Type".equals(key)) {
    				value = "application/json;" + value;
    			}
    			
    			request.setHeader(key, value);
    		}
    	}
    }

    /**
     *
     * <b>Description: get请求</b><br>
     * @param url
     * @param headers
     * <b>Author: Franlin.wu</b>
     * <br><b>Date: 2019年06月29日 下午3:25:47 </b>
     */
    public static String get(String url, Map<String, String> headers) {

        String result = null;

        try {
            result = httpForJsonByMethod(url, REQ_TYPE_GET, headers, null);
        } catch (Exception e) {
            LOG.error("get 请求发生异常 | error ", e);
        }

        return result;
    }


    /**
     *
     * <b>Description: get请求</b><br>
     * @param url
     * <b>Author: Franlin.wu</b>
     * <br><b>Date: 2019年06月29日 下午3:25:47 </b>
     */
    public static String get(String url) {

        String result = null;

        try {
            result = httpForJsonByMethod(url, REQ_TYPE_GET, null, null);
        } catch (Exception e) {
            LOG.error("get 请求发生异常 | error ", e);
        }

        return result;
    }

    /**
     *
     * <b>Description: post请求</b><br>
     * @param url
     * @param headers
     * @param paramsObj
     * <b>Author: Franlin.wu</b>
     * <br><b>Date: 2019年06月29日 下午3:25:47 </b>
     */
    public static String post(String url, Map<String, String> headers, Object paramsObj) {
        String result = null;

        try {
            result = httpForJsonByMethod(url, REQ_TYPE_POST, headers, null);
        } catch (Exception e) {
            LOG.error("post 请求发生异常 | error ", e);
        }

        return result;
    }

    /**
     *
     * <b>Description: post请求</b><br>
     * @param url
     * @param paramsObj
     * <b>Author: Franlin.wu</b>
     * <br><b>Date: 2019年06月29日 下午3:25:47 </b>
     */
    public static String post(String url, Object paramsObj) {
        String result = null;

        try {
            result = httpForJsonByMethod(url, REQ_TYPE_POST, null, paramsObj);
        } catch (Exception e) {
            LOG.error("post 请求发生异常 | error ", e);
        }

        return result;
    }

    /**
     *
     * <b>Description: post请求</b><br>
     * @param url
     * @param paramsObj
     * <b>Author: Franlin.wu</b>
     * <br><b>Date: 2019年06月29日 下午3:25:47 </b>
     */
    public static String postExceptionThrow(String url, Object paramsObj) throws  Exception {
        String   result = httpForJsonByMethod(url, REQ_TYPE_POST, null, paramsObj);

        return result;
    }
   

}
