package com.vedeng.mjx.web.interceptor;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 判断接口请求的accountID与当前用户的accountId是否相同
 */
@Component
public class AccountIdAuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		TopAccountJx userEntity =  ShiroSessionUtil.getSessionTopAccountJx();
		String accountId=request.getParameter("accountId");
		if(StringUtils.isBlank(accountId)){
			accountId=request.getHeader("accountId");
		}
		if (userEntity ==null || userEntity.getAccountId()==null || !StringUtils.equals(userEntity.getAccountId()+"",accountId)){
			response.setContentType("application/json; charset=utf-8");
			JSONObject jsonObj = JSONObject.fromObject(ResultInfo.fail(VedengErrorCode.AUTH_FAILED));
			PrintWriter writer = response.getWriter();
			writer.print(jsonObj);
			writer.close();
			response.flushBuffer();
			return false;
		}else{
			request.setAttribute("accountId",accountId);
		}
		return true;
	}

}