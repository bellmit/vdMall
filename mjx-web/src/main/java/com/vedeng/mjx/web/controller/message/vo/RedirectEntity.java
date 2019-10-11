package com.vedeng.mjx.web.controller.message.vo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 获取跳转链接
 * 
 * @create 2017-08-16 15:48
 */
@Component
public class RedirectEntity {
	private static final Logger log = LogManager.getLogger("TUserService.Controller");

	/**
	 * @desc 获取跳转链接
	 * @email zc94616@163.com
	 * @create 2017/8/16 15:55
	 **/
	public String getRedirectUrl(String urlAddress) {

		String redirectUrl = "http://wxtest.vedeng.com/apibus/wx/wxchat/getCode";
		String appid = "wxefcefde8edf57103";

		log.error("redirectUrl:"+redirectUrl);
		String redirect = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";
		log.error("redirect:"+redirect);
		String url = "";
		try {
			url = String.format(redirect,appid, URLEncoder.encode(redirectUrl, "utf-8"), urlAddress);
		} catch (UnsupportedEncodingException e) {
			log.error("Exception:"+e.getLocalizedMessage());
		}
		return url;
	}

	public static void main(String[] args) {

		RedirectEntity redirectEntity = new RedirectEntity();
		System.out.println(redirectEntity.getRedirectUrl("123"));

	}
	
}