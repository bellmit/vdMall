package com.vedeng.mjx.web.filter;

import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.model.MapVo;
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
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Deprecated
public class NewWxAuthFilter  extends OncePerRequestFilter {
	/**
	 * 记录日志
	 */
	private final Logger LOG =  LoggerFactory.getLogger("login");

	private String exclude = ".*(\\.(js|css|jpg|png|txt|gif|jpeg|ico|bmp|json));.*(/signInOrUp/).*;";
	private List<Pattern> excludePattern = new ArrayList<Pattern>(1);
	@Autowired
	private AccountService accountService;


	@Override
	public void initFilterBean() throws ServletException {
		excludePattern.addAll(StringUtil.loadPattern(exclude).keySet());
	}

	@Override
	public void destroy() {
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws ServletException, IOException {

		Subject currentUser = SecurityUtils.getSubject();
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String originatingUrl = urlPathHelper.getOriginatingRequestUri(httpRequest);
		//其他请求不走此filter
		if (isStaticUrl(originatingUrl, httpRequest)) {
			filterChain.doFilter(httpRequest, httpResponse);
			return;
		}
		LOG.info(httpRequest.getRequestURI());

		filterChain.doFilter(httpRequest, httpResponse);
	}
	private boolean isStaticUrl(String originatingUrl, HttpServletRequest httpRequest) {
		return StringUtil.match(excludePattern, originatingUrl);
	}
}
