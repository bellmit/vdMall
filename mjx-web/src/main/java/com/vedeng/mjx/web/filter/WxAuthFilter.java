package com.vedeng.mjx.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.util.CookieUtils;
import com.vedeng.mjx.common.util.IpUtils;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.account.AccountService;
import com.vedeng.mjx.service.feign.passport.dto.ReqUserDto;
import com.vedeng.mjx.service.signInOrUp.SignInOrUpService;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.common.model.MapVo;
import com.vedeng.passport.api.login.dto.res.ResUserDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.vedeng.passport.api.wechat.dto.req.ReqOuthWeChatDTO;
import com.vedeng.passport.api.wechat.dto.res.ResOuthWeChatDTO;
import com.vedeng.passport.api.wechat.service.WeChatApiService;
import org.springframework.web.filter.OncePerRequestFilter;


public class WxAuthFilter  extends OncePerRequestFilter {
	/**
	 * 记录日志
	 */
	private final Logger LOG =  LoggerFactory.getLogger("login");

	@Autowired
	public WeChatApiService weChatApiService;


	@Value("${mjx.app_id}")
	private String appId;

	@Value("${mjx.app_secret}")
	private String appSecret;

	@Value("${mjx.session_timeout}")
	private String sessionTimeOut;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SignInOrUpService signInOrUpService;

	@Value("${mxj.redis_type}")
	private String redisType;


	@Value("${mjx.static_recouse}")
	private String staticUrl;

	// 标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
	String NO_LOGIN = "您还未登录";

	// 不需要登录就可以访问的路径(比如:注册登录等)
	String[] includeUrls = new String[] { "/login.html", "/register.html", "/doLogin.html" };

	/**
	 * 申请加入贝登
	 */
	String applyVedengJoinUrl = "/apply/vedengJoin";

	/**
	 * 传参openId 的 url
	 */
	String[] loginRegistUrlArr = new String[] { "/login.html" };




	@Override
	public void destroy() {
	}

    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws ServletException, IOException {

		Subject currentUser = SecurityUtils.getSubject();
		// 获取url地址
		String nowReqUri = httpRequest.getRequestURI();
      //  System.out.println("WxAuthFilter::"+nowReqUri);
		//app不走此filter
	 	if(StringUtils.startsWith(nowReqUri,"/m/loginStatus")||StringUtils.startsWith(nowReqUri,"/app")||StringUtils.equals(httpRequest.getServerName(),"app.vedeng.com")
				||StringUtils.equals(httpRequest.getServerName(),"121.43.178.1")
				||StringUtils.equals(httpRequest.getServerName(),"localhost")){

			filterChain.doFilter(httpRequest, httpResponse);
			return;
		}
		LOG.info("WxAuthFilter.doFilter | begin  relative path : "+httpRequest.getRequestURI() +" IP : " +IpUtils.getRealIp(httpRequest));
		LOG.info("WxAuthFilter.doFilter | begin  path : "+httpRequest.getRequestURL() +" IP : " +IpUtils.getRealIp(httpRequest));

		Session session = currentUser.getSession();

		LOG.info("WxAuthFilter.doFilter | sessionId:{} currentUser:{} ",session != null ? session.getId() : "",
				JSON.toJSONString(ShiroSessionUtil.getSessionTopAccountJx()));

		if (currentUser.getPrincipal() != null) {
			//已登陆情况下，清一次cookie内的openId，保障当前数据正确性
			//并记录登陆状态也为已登陆
			CookieUtils.addWxAuthOpenIdCookie(httpResponse, CommonConstant.MJX_WECHAT_OPENID,null,0);
			for(String url : loginRegistUrlArr) {
				if(url.equals(nowReqUri)) {
					httpRequest.getRequestDispatcher("/index.html").forward(httpRequest, httpResponse);
					return;
				}
			}
			filterChain.doFilter(httpRequest, httpResponse);
			return;
		}

		// 放开她
		for(String staticUri : staticUrl.split(",")) {
			if(StringUtils.isBlank(staticUri)) {
				continue;
			}
			if(nowReqUri.toLowerCase().endsWith(staticUri.toLowerCase())) {
				filterChain.doFilter(httpRequest, httpResponse);
				return;
			}
		}

		String wxOpenId = getOpenIdByCookie(httpRequest);
		// ua
		String ua = httpRequest.getHeader(CommonConstant.USER_AGENT);
		ua = null == ua ? "" : ua.toLowerCase();
		// 是否存在 微信标识
		int index = ua.indexOf(CommonConstant.WX_MICR_FLAG);
		// STATE
		String stateUrl = httpRequest.getParameter(CommonConstant.STATE);
		// 微信code
		String wxCode = httpRequest.getParameter(CommonConstant.WECHAT_CODE);

		LOG.info("WxAuthFilter.doFilter | ------------------------------------");
		LOG.info("WxAuthFilter.doFilter | ua :{}", ua);
		LOG.info("WxAuthFilter.doFilter | micromessenger.index :{}", index);
		LOG.info("WxAuthFilter.doFilter | nowReqUri:{}", nowReqUri);
		LOG.info("WxAuthFilter.doFilter | stateUrl:{}", stateUrl);
		LOG.info("WxAuthFilter.doFilter | wxCode:{}", wxCode);
		LOG.info("WxAuthFilter.doFilter | wxOpenId :{}", wxOpenId);

		// 微信端 && 存在微信code && openId为空
		if (-1 != index && StringUtils.isNotBlank(wxCode) && StringUtil.isJsBlank(wxOpenId)) {
			ReqOuthWeChatDTO reqOuthWeChatDTO = new ReqOuthWeChatDTO();
			reqOuthWeChatDTO.setCode(wxCode);
			reqOuthWeChatDTO.setAppId(appId);
			reqOuthWeChatDTO.setAppSecret(appSecret);
			// 微信端 code 参数去除
			MapVo<String, Boolean> redirectMap = getQueryStringRemoveWxParamer(httpRequest);
			// 重定向标识
			boolean redirectFlag = redirectMap.getValue();

			String redirectUrl = httpRequest.getRequestURL() + "?" + redirectMap.getKey();
			LOG.info("WxAuthFilter.doFilter | ---- | redirectFlag:{}", redirectFlag);
			LOG.info("WxAuthFilter.doFilter | ---- | redirectUrl:{}", redirectUrl);
			// 根据code获取openId
			ResOuthWeChatDTO outh = weChatApiService.outh(reqOuthWeChatDTO);
			LOG.info("WxAuthFilter.doFilter | outh info :{}",JSON.toJSONString(outh));
			if(null == outh) {
				// 不存在 openId 设置cookie
				CookieUtils.addWxAuthOpenIdCookie(httpResponse, CommonConstant.MJX_WECHAT_OPENID,null,0);
				// 重定向
				if(redirectFlag) {
					httpResponse.sendRedirect(redirectUrl);
				} else {
					filterChain.doFilter(httpRequest, httpResponse);
				}
				return;
			}
			// openId
			wxOpenId = outh.getOpenId();
			boolean rediUrlFlag = false;
			// openId
			for(String url : loginRegistUrlArr) {
				if(url.equals(nowReqUri)) {
					// 设置
					rediUrlFlag = true;
					break;
				}
			}
			// 用户唯一标识
			Integer ossAccountId = outh.getSsoAccountId();
			// 不存在
			if(null == ossAccountId || CommonConstant.ZEOR.equals(ossAccountId)) {
				// 由于用户信息不存在导致免登失败，设置cookie openId，以备后续登陆传递
				CookieUtils.addWxAuthOpenIdCookie(httpResponse, CommonConstant.MJX_WECHAT_OPENID,wxOpenId,null);
				// 重定向
				if(redirectFlag) {
					httpResponse.sendRedirect(redirectUrl);
				} else {
					filterChain.doFilter(httpRequest, httpResponse);
				}
				return;
			}

			// 根据ossAccountId查询用户信息
			TopAccountJx jx = accountService.selectBySsoAcountId(ossAccountId);
			LOG.info("WxAuthFilter.doFilter | TopAccountJx:{}", JSON.toJSONString(jx));
			if(null == jx) {
				ReqUserDto reqUserDto = new ReqUserDto();
				reqUserDto.setOpenId(wxOpenId);
				reqUserDto.setCompanyName(outh.getCorporation());
				reqUserDto.setMobile(outh.getMobile());
				reqUserDto.setFromType(CommonConstant.PASSPORT_FORM_TYPE_WECHAT);
				ResUserDTO resUserDTO = new ResUserDTO();
				resUserDTO.setSsoAccountId(ossAccountId);
				// 入库
				int modNum = signInOrUpService.doAccountData(reqUserDto, resUserDTO);
			//	LOG.info("WxAuthFilter.doFilter | singInOrUpService.doAcountData num :{}", modNum);
				// 没有入库成功
				if(modNum == 0) {
					filterChain.doFilter(httpRequest, httpResponse);
					return;
				}
				// 查询用户信息
				jx = accountService.selectBySsoAcountId(ossAccountId);
			}
			LOG.info("WxAuthFilter.doFilter | ShiroSessionUtil.setSession.jx :{}", JSON.toJSONString(jx));
			// 设置session
			ShiroSessionUtil.setSession(jx);
			//免登成功，清空cookie内openId
			CookieUtils.addWxAuthOpenIdCookie(httpResponse, CommonConstant.MJX_WECHAT_OPENID,null,0);
//			LOG.info("重定向url标志:{}", rediUrlFlag);
			if(rediUrlFlag) {
				// 解码
				stateUrl = URLDecoder.decode(null == stateUrl ? "" : stateUrl, CommonConstant.CHARSET_UTF8);
//				LOG.info("重定向 stateUrl1:{}", stateUrl);
				if(StringUtil.isJsBlank(stateUrl) || stateUrl.indexOf(CommonConstant.PAR_HTML) == -1) {
					httpRequest.getRequestDispatcher("/index.html").forward(httpRequest, httpResponse);
				} else {
//					LOG.info("重定向 stateUrl2:{}", stateUrl);
					// URLEncoder.encode(stateUrl, CommonConstant.CHARSET_UTF8)
					httpResponse.sendRedirect(stateUrl);
				}

			}
		}

		filterChain.doFilter(httpRequest, httpResponse);

//		LOG.info("WxAuthFilter.doFilter | end ...");
	}




    /**
	 * 获取cookie
	 * @auther franlin.wu
	 * @param request
	 * @return
	 */
	private String getOpenIdByCookie(HttpServletRequest request) {

		if(null == request) {
			return "";
		}
		Cookie[] cookies = request.getCookies();
		if(null == cookies) {
			return "";
		}

		for(Cookie ck : cookies) {
			if(null == ck) {
				continue;
			}
			if(CommonConstant.MJX_WECHAT_OPENID.equals(ck.getName())) {
				return ck.getValue();
			}
		}

		return "";
	}

	/**
	 * 微信鉴权后处理 微信带的code的问题
	 * @auther franlin.wu
	 * @param request
	 * @return MapVo key: 重定向url, value： 记录state的次数>=2次的时候 true  0,1次 是 false
	 */
	private MapVo<String, Boolean> getQueryStringRemoveWxParamer(HttpServletRequest request) {
		//
		MapVo<String, Boolean> mapVo = new MapVo();
		String queryStr = request.getQueryString();
		LOG.info("WxAuthFilter.getQueryStringRemoveWxParamer | getQueryString:{}, begin ...", queryStr);
		StringBuffer buf = new StringBuffer("");
		// 记录参数 参数state的次数
		int cntState = 0;
		if(StringUtils.isNotBlank(queryStr)) {
			String[] parArr = queryStr.split("&");
			if(null != parArr) {
				int parSize = parArr.length;
				for(int i = 0; i < parSize; i++) {
					String par = parArr[i];
					// 存在合法\
					if(StringUtils.isNotBlank(par)) {
						String[] pArr = par.split("=");
						// 合法参数 && 参数名称不可为code
						if(null != pArr && pArr.length == 2 && !CommonConstant.WECHAT_CODE.equals(pArr[0])) {
							// 记录
							if(CommonConstant.STATE.equals(pArr[0])) {
								cntState++;
							}
							// 最后一个
							if(i == parSize - 1) {
								buf.append(pArr[0]).append("=").append(pArr[1]);
							} else {
								buf.append(pArr[0]).append("=").append(pArr[1]).append("&");
							}
						}
					}

				}

			}
		}

		if(cntState >= 2) {
			mapVo.setValue(true);
		} else {
			mapVo.setValue(false);
		}
		LOG.info("WxAuthFilter.getQueryStringRemoveWxParamer | getQueryString:{}, end ...", buf.toString());
		mapVo.setKey(buf.toString());

		return mapVo;
	}

}
