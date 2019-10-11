package com.vedeng.mjx.web.interceptor;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Value("${mjx.app_id}")
	private String appId;
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
		if (userEntity ==null || userEntity.getAccountId()==null){
			response.setContentType("application/json; charset=utf-8");
			JSONObject jsonObj = JSONObject.fromObject(ResultInfo.fail(VedengErrorCode.USER_NOT_LOGIN_YET));

			//如果是微信，则传一个appId过去
			String ua = request.getHeader(CommonConstant.USER_AGENT);
			boolean isWxClient = StringUtils.indexOf(StringUtils.lowerCase(ua), CommonConstant.WX_MICR_FLAG) >= 0;
			if(isWxClient){
				jsonObj.put("appId",appId);
			}


			PrintWriter writer = response.getWriter();
			writer.print(jsonObj);
			writer.close();
			response.flushBuffer();
			return false;
		}
		return true;
	}

}