package com.vedeng.mjx.web.controller.message.vo;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.springframework.web.client.RestTemplate;

public class TokenUtils {

	public static String getWxToken(String appid,String secret){
		// 获取access_token
		String getAccessUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid
				+ "&secret=" + secret;

		// 发送
		RestTemplate restTemplate = new RestTemplate();
		String accessTokenJson = restTemplate.getForObject(getAccessUrl, String.class);
		Map<String, Object> mapObject = JsonUtil.parseJSON2Map(accessTokenJson);
		System.out.println("mapObject:"+ JSON.toJSONString(mapObject));

		// 接口调用凭证
		String accessToken = (String) mapObject.get("access_token");
		
		return accessToken;
	}
	
}
