package com.vedeng.mjx.web.controller.login;


import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.constant.CookieType;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.util.CookieUtils;
import com.vedeng.mjx.common.util.IpUtils;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.account.AccountService;
import com.vedeng.mjx.service.feign.passport.dto.ReqUserDto;
import com.vedeng.mjx.service.signInOrUp.SignInOrUpService;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.web.controller.login.vo.LoginStatus;
import com.vedeng.passport.api.login.dto.res.ResUserDTO;
import com.vedeng.passport.api.wechat.dto.req.ReqOuthWeChatDTO;
import com.vedeng.passport.api.wechat.dto.res.ResOuthWeChatDTO;
import com.vedeng.passport.api.wechat.service.WeChatApiService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

    @Autowired
    public WeChatApiService weChatApiService;
    @Autowired
    private AccountService accountService;
    /**
     * 日志
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = {"login.html"})
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("login");
//		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
//		if (savedRequest != null && CommonConstant.APPLY_JOIN_VEDENG_URL.equals(savedRequest.getRequestUrl())){
//			view.addObject("isVedengJoin",CommonConstant.STATUS_1);
//		}
        view.setViewName("login/login");

        return view;
    }

    @GetMapping(value = {"/myip"})
    @ResponseBody
    public String myip(HttpServletRequest request) {
        //ModelAndView view = new ModelAndView("login");
//		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
//		if (savedRequest != null && CommonConstant.APPLY_JOIN_VEDENG_URL.equals(savedRequest.getRequestUrl())){
//			view.addObject("isVedengJoin",CommonConstant.STATUS_1);
//		}
        //	view.setViewName("login/login");

        return IpUtils.getRealIp(request);
    }

    @PostMapping("doLogin.html")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            SecurityUtils.getSubject().login(token);
        } catch (Exception e) {
            log.error("Error authenticating.", e);
            model.addAttribute("errorInvalidLogin", "The username or password was not correct.");
            return "login/login";
        }

        return "redirect:/index.html";
    }

    @GetMapping("logout.html")
    @Deprecated
    public ModelAndView logout() {
        ModelAndView view = new ModelAndView("logout");

        view.setViewName("login/logout");

        return view;
    }

    @GetMapping("/m/logout")
    @Deprecated
    public ModelAndView logoutForm() {
        ModelAndView view = new ModelAndView("logout");
        view.setViewName("redirect:/apibus/dispatch.html#/myindex");
        return view;
    }

    @GetMapping("userInfo.html")
    @ResponseBody
    public ResultInfo userInfo() {
        TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
        if (jx == null) {
            return ResultInfo.fail(VedengErrorCode.FAIL_CODE, "未登陆");
        }
        return ResultInfo.success(jx);
    }

    @Value("${mjx.app_id}")
    private String appId;

    @Value("${mjx.app_secret}")
    private String appSecret;

    @Value("${force_cookie_domain:}")
    private String forceCookieDomain;
    @Value("${cookie.expire.seconds:2592000}")
    private Integer cookieExpireSeconds;
    @Value("${mjx.session_timeout}")
    private Long sessionTimeOut;
    @Autowired
    private SignInOrUpService signInOrUpService;

    @RequestMapping(value = {"/m/loginStatus"})
    @ResponseBody
    public ResultInfo loginStatus(HttpServletRequest request, HttpServletResponse response) {

        LoginStatus status = new LoginStatus();
        String code = request.getParameter("code");
        String[] codeArray = request.getParameterValues("code");
        if (StringUtils.isBlank(code)) {//防止多传参数，这里只是可能
            if (!ArrayUtils.isEmpty(codeArray) && codeArray.length > 0) {
                code = codeArray[0];
            }
            if (!ArrayUtils.isEmpty(codeArray) && codeArray.length > 2) {
                log.warn("重定向次数过多" + code);
                return ResultInfo.success(status.loginout().noCode());
            }
        }
        log.info("开始获取登陆code：" + code);
        // ua
        String ua = request.getHeader(CommonConstant.USER_AGENT);
        // 是否存在 微信标识
        boolean isWxClient = StringUtils.indexOf(StringUtils.lowerCase(ua), CommonConstant.WX_MICR_FLAG) >= 0;
        TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
        //登陆状态
        if (jx != null) {
            return ResultInfo.success(status.login().noCode());
        }
        if (!isWxClient) {
            return ResultInfo.success(status.loginout().noCode());
        }
        //未登陆状态
        if (StringUtils.isBlank(code)) {
            //未登陆状态且session里面包含code
            if (ShiroSessionUtil.hasSessionWxCode()) {
                return ResultInfo.success(status.loginout().noCode().appId(appId));
            }
            return ResultInfo.success(status.loginout().code().appId(appId));
        } else {
            ShiroSessionUtil.setSessionWxCode();
            //没有登陆但是有code
            ReqOuthWeChatDTO reqOuthWeChatDTO = new ReqOuthWeChatDTO();
            reqOuthWeChatDTO.setCode(code);
            reqOuthWeChatDTO.setAppId(appId);
            reqOuthWeChatDTO.setAppSecret(appSecret);
            //通过code找到登陆信息

            ResOuthWeChatDTO outh = weChatApiService.outh(reqOuthWeChatDTO);
            if (outh == null) {
                log.info("code调用失败,告诉客户端需要重新传  code /m/loginStatus" + code + outh);
                if (outh != null) {
                    log.info("code调用失败,outh.getSsoAccountId() null 告诉客户端不需要code/m/loginStatus" + code);
                }
                return ResultInfo.success(status.loginout().code().appId(appId));
            } else if (outh.getSsoAccountId() == null) {
                //code调用失败,告诉客户端不需要code
                log.info("code调用成功,但是没有注册，告诉客户端不需要code/m/loginStatus" + code + outh);
                if (outh != null) {
                    log.info("code调用失败,outh.getSsoAccountId() null 告诉客户端不需要code/m/loginStatus" + code);
                }
                return ResultInfo.success(status.loginout().noCode());
            } else {
                log.info("code调用成功,告诉客户端不需要code/m/loginStatus  " + code + "\t" + outh.getOpenId());
                //设置session
                jx = accountService.selectBySsoAcountId(outh.getSsoAccountId());
                if (jx == null) {
                    ReqUserDto reqUserDto = new ReqUserDto();
                    reqUserDto.setOpenId(outh.getOpenId());
                    reqUserDto.setCompanyName(outh.getCorporation());
                    reqUserDto.setMobile(outh.getMobile());
                    reqUserDto.setFromType(CommonConstant.PASSPORT_FORM_TYPE_WECHAT);
                    ResUserDTO resUserDTO = new ResUserDTO();
                    resUserDTO.setSsoAccountId(outh.getSsoAccountId());
                    // 入库
                    try {
                        int modNum = signInOrUpService.doAccountData(reqUserDto, resUserDTO);
                        log.error("code 新增成功" + code + "\t" + outh.getMobile());
                    } catch (Exception e) {
                        log.error("code 新增失败" + code, e);
                    }
                }
                jx = accountService.selectBySsoAcountId(outh.getSsoAccountId());
                ShiroSessionUtil.setSession(jx);
                //延长Header时间
                CookieUtils.addVedengEncodeIdHeadMonth(request, response, CookieType.APP_LOGIN_TOKEN.getKey(), jx.getSsoAccountId());
                //延长cookie时间
                CookieUtils.addVedengEncodeIdCookieMonth(request, response, CookieType.APP_LOGIN_TOKEN.getKey(), jx.getSsoAccountId(), forceCookieDomain, cookieExpireSeconds);

            }
        }
        return ResultInfo.success(status.login().noCode());
    }

}
