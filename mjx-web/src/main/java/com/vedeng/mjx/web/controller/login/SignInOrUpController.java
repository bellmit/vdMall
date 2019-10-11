package com.vedeng.mjx.web.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.constant.CookieType;
import com.vedeng.mjx.common.constant.RedisKeyConstant;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.util.CookieUtils;
import com.vedeng.mjx.common.util.IpUtils;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.feign.passport.dto.ReqUserDto;
import com.vedeng.mjx.service.redis.RedisOperateService;
import com.vedeng.mjx.service.signInOrUp.SignInOrUpService;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.service.vdengJoin.VedengJoinService;
import com.vedeng.mjx.web.controller.base.BaseController;
import com.vedeng.passport.api.login.constant.ReqSmsType;
import com.vedeng.passport.api.login.dto.req.user.ReqAccountDTO;
import com.vedeng.passport.api.login.dto.res.ResUserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.vedeng.passport.api.login.constant.PlatformType.PLATFORM_MJX;

/**
 * @ClassName SignInOrUpController
 * @Description 贝登精选登录接口控制器
 * @Author Cooper.xu
 * @Date 2019-06-25 13:15
 * @Version 1.0
 **/
@CrossOrigin
@RestController
@RequestMapping("/signInOrUp")
@Api(value = "贝登精选登录接口", tags = {"登录控制层"})
public class SignInOrUpController extends BaseController {

    @Autowired
    private SignInOrUpService signInOrUpService;

    @Autowired
    private VedengJoinService vedengJoinService;
    @Value("${force_cookie_domain:}")
    private String forceCookieDomain;
    @Value("${cookie.expire.seconds:2592000}")
    private Integer cookieExpireSeconds;
    @Value("${mjx.session_timeout}")
    private Long sessionTimeOut;

    @Autowired
    private RedisOperateService redisOperateService;
    /**
     * @Description 获取验证码
     * @param reqUserDto
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     * @return
     */
    @ApiOperation(value = "获取验证码方法", notes = "mobile、smsType 必填",response = ResultInfo.class)
    @PostMapping("/getCode")
    public ResultInfo getCode(@RequestBody ReqUserDto reqUserDto){
        try{
            logger.info("获取验证码开始 》》》》》》》》》》》》》》》》》》》");
            logger.info("获取验证码入参  getCode -----  reqUserDto："+JSONObject.toJSON(reqUserDto));
            if (StringUtils.isBlank(reqUserDto.getMobile())){//手机号判空验证
                return ResultInfo.fail(VedengErrorCode.MOBILE_EMPTY_ERROR_MSG);
            }
            if (checkWrongPhone(reqUserDto.getMobile())) {//手机号格式验证
                return ResultInfo.fail(VedengErrorCode.FAIL_PHONE_ISTRUE);
            }
            ResultInfo resultInfo = signInOrUpService.getCode(reqUserDto);
            logger.info("获取验证码结束 》》》》》》》》》》》》》》》》》》》resultInfo====》"+JSONObject.toJSON(resultInfo));
            return resultInfo;
        }catch (Exception e){
            logger.error("获取验证码异常",e);
            return ResultInfo.fail(e);
        }
    }

    /**
     * @Description 校验验证码
     * @param reqUserDto
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     * @return
     */
    @ApiOperation(value = "校验验证码方法", notes = "mobile、smsType 、smsCode 、isCheckImgCode 必填，当isCheckImgCode 为 1 时，imgCode 必填",response = ResultInfo.class)
    @PostMapping("/checkCode")
    public ResultInfo checkCode(@RequestBody ReqUserDto reqUserDto, HttpServletRequest request, HttpServletResponse response){
        try{
            String ossAccountId2 = CookieUtils.getEncodeIdCookieOrHead(request, CookieType.APP_LOGIN_TOKEN.getKey(),cookieExpireSeconds);
            if(ShiroSessionUtil.getSessionTopAccountJx()!=null){
                logger.info("校验验证码开始:::"+request.getServerName()+",url="+request.getRequestURI()+" ,  sid="+ossAccountId2+"\t"+IpUtils.getRealIp(request)+",headsid="+ request.getHeader("sid")+"\t"+ShiroSessionUtil.getSessionTopAccountJx().getMobile());
            }else{
                logger.info("校验验证码开始未登录:::"+request.getServerName()+",url="+request.getRequestURI()+" ,  sid="+ossAccountId2+"\t"+IpUtils.getRealIp(request)+",headsid="+ request.getHeader("sid") );

            }
            logger.info("校验验证码开始 》》》》》》》》》》》》》》》》》》》");
            logger.info("校验验证码入参  checkCode -----  reqUserDto："+JSONObject.toJSON(reqUserDto));
            if (StringUtils.isBlank(reqUserDto.getMobile())){//手机号判空验证
                return ResultInfo.fail(VedengErrorCode.MOBILE_EMPTY_ERROR_MSG);
            }

            if (checkWrongPhone(reqUserDto.getMobile())) {//手机号格式验证
                return ResultInfo.fail(VedengErrorCode.FAIL_PHONE_ISTRUE);
            }
            //处理passport需要的参数
            reqUserDto = this.getReqUserDto(reqUserDto,request);
            //校验验证码
            ResultInfo resultInfo = signInOrUpService.checkCode(reqUserDto);
            if (resultInfo != null && CommonConstant.SUCCESS_CODE.equals(resultInfo.getCode())){
                TopAccountJx jx= ShiroSessionUtil.getSessionTopAccountJx();
                if(jx!=null){
                    CookieUtils.addVedengEncodeIdHeadMonth(request,response, CookieType.APP_LOGIN_TOKEN.getKey(),jx.getSsoAccountId());
                }
                logger.info("贝登精选backurl------"+reqUserDto.getBackurl());
                //如果是贝登精选立即申请进来的，需要推送到ERP
                if (CommonConstant.APPLY_JOIN_BACKURL.equals(reqUserDto.getBackurl())){
                    //推送数据
                    resultInfo.setData(this.applyJoinVedeng(reqUserDto.getMobile()));
                }
            }
            logger.info("校验验证码结束 》》》》》》》》》》》》》》》》》》》resultInfo====》"+JSONObject.toJSON(resultInfo));
            return resultInfo;
        }catch (Exception e){
            logger.error("校验验证码异常",e);
            return ResultInfo.fail(e);
        }
    }

    /**
     * @Description 密码登录
     * @param reqUserDto
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     * @return
     */
    @ApiOperation(value = "密码登录方法", notes = "mobile 、password 、isCheckImgCode 必填，当isCheckImgCode 为 1 时，imgCode 必填",response = ResultInfo.class)
    @PostMapping("/signInWithPwd")
    public ResultInfo signInWithPwd(@RequestBody ReqUserDto reqUserDto, HttpServletRequest request, HttpServletResponse response){
        try{
            logger.info("密码登录开始 》》》》》》》》》》》》》》》》》》》》》》》》》");
            logger.info("密码登录入参  signInWithPwd -----  reqUserDto："+JSONObject.toJSON(reqUserDto));
            if (StringUtils.isBlank(reqUserDto.getMobile())){//手机号判空验证
                return ResultInfo.fail(VedengErrorCode.MOBILE_EMPTY_ERROR_MSG);
            }
            if (checkWrongPhone(reqUserDto.getMobile())) {//手机号格式验证
                return ResultInfo.fail(VedengErrorCode.FAIL_PHONE_ISTRUE);
            }
            //处理passport需要的参数
            reqUserDto = this.getReqUserDto(reqUserDto,request);
            ResultInfo resultInfo = signInOrUpService.pwdLogin(reqUserDto);
            if (resultInfo != null && CommonConstant.SUCCESS_CODE.equals(resultInfo.getCode())){
                TopAccountJx jx= ShiroSessionUtil.getSessionTopAccountJx();
                if(jx!=null){
                    //登陆成功之后设置cookie 内openId为空
                    CookieUtils.addWxAuthOpenIdCookie(response, CommonConstant.MJX_WECHAT_OPENID,null,0);
                    CookieUtils.addVedengEncodeIdCookieMonth(request,response, CookieType.APP_LOGIN_TOKEN.getKey(),jx.getSsoAccountId(),forceCookieDomain,cookieExpireSeconds);
                }
                logger.info("贝登精选backurl------"+reqUserDto.getBackurl());
                //如果是贝登精选立即申请进来的，需要推送到ERP
                if (CommonConstant.APPLY_JOIN_BACKURL.equals(reqUserDto.getBackurl())){
                    //推送数据
                    resultInfo.setData(this.applyJoinVedeng(reqUserDto.getMobile()));
                }
            }
            logger.info("密码登录结束 》》》》》》》》》》》》》》》》》》》》》》》》》ResultInfo====》"+JSONObject.toJSON(resultInfo));
            return resultInfo;
        }catch (Exception e){
            logger.error("密码登录异常",e);
            return ResultInfo.fail(e);
        }
    }

    /**
     * @Description 注册/重置密码/修改密码方法
     * @param reqUserDto
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     * @return
     */
    @ApiOperation(value = "注册/重置密码/修改密码方法", notes = "忘记密码 修改密码场景mobile、password 必填，注册信息完善时 mobile、password、companyName 必填",response = ResultInfo.class)
    @PostMapping("/signUpOrResetPwd")
    public ResultInfo signUpOrResetPwd(@RequestBody ReqUserDto reqUserDto, HttpServletRequest request, HttpServletResponse response){
       try{
           String ossAccountId2 = CookieUtils.getEncodeIdCookieOrHead(request, CookieType.APP_LOGIN_TOKEN.getKey(),cookieExpireSeconds);
           if(ShiroSessionUtil.getSessionTopAccountJx()!=null){
               logger.info("重置密码开始开始:::"+request.getServerName()+",url="+request.getRequestURI()+" ,  sid="+ossAccountId2+"\t"+IpUtils.getRealIp(request)+",headsid="+ request.getHeader("sid")+"\t"+ShiroSessionUtil.getSessionTopAccountJx().getMobile());
           }else{
               logger.info("重置密码开始未登录:::"+request.getServerName()+",url="+request.getRequestURI()+" ,  sid="+ossAccountId2+"\t"+IpUtils.getRealIp(request)+",headsid="+ request.getHeader("sid") );

           }
           logger.info("注册/重置密码/修改密码开始 》》》》》》》》》》》》》》》》》》》");
           logger.info("注册/重置密码/修改密码入参  signUpOrResetPwd -----  reqUserDto："+JSONObject.toJSON(reqUserDto));
           if (StringUtils.isBlank(reqUserDto.getMobile())){//手机号判空验证
               return ResultInfo.fail(VedengErrorCode.MOBILE_EMPTY_ERROR_MSG);
           }
           if (checkWrongPhone(reqUserDto.getMobile())) {//手机号格式验证
               return ResultInfo.fail(VedengErrorCode.FAIL_PHONE_ISTRUE);
           }
           if (StringUtils.isNotBlank(reqUserDto.getCompanyName())){
               //注册
               String signUp = redisOperateService.getKeyValue(RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS + reqUserDto.getMobile() + ReqSmsType.REQ_SMS_REG_OR_LOGIN.getSmsType()
               + IpUtils.getRealIp(request));
               if (  StringUtils.isBlank(signUp)) {
                   return ResultInfo.fail(VedengErrorCode.AUTH_FAILED);
               }
           }else{
               //重置密码或修改密码
               String resultRestPwd = redisOperateService.getKeyValue(RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS + reqUserDto.getMobile() + ReqSmsType.REQ_SMS_FORGET_PASSWORD.getSmsType()+ IpUtils.getRealIp(request));
               String resultSavePwd = redisOperateService.getKeyValue(RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS + reqUserDto.getMobile() + ReqSmsType.REQ_SMS_MODIFY_PASSWORD.getSmsType()+ IpUtils.getRealIp(request));
               if (StringUtils.isBlank(resultRestPwd) && StringUtils.isBlank(resultSavePwd)) {
                   return ResultInfo.fail(VedengErrorCode.AUTH_FAILED);
               }

           }
           //处理passport需要的参数
           reqUserDto = this.getReqUserDto(reqUserDto,request);
           ResultInfo resultInfo = signInOrUpService.signUpOrSavePwd(reqUserDto);
           if (resultInfo != null && (VedengErrorCode.REG_SUCCESS_MSG.getCode().equals(resultInfo.getCode())
                   || VedengErrorCode.PASSWORD_SAVE_SUCCESS.getCode().equals(resultInfo.getCode()))){
               logger.info("贝登精选backurl------"+reqUserDto.getBackurl());
               TopAccountJx jx= ShiroSessionUtil.getSessionTopAccountJx();
               if(jx!=null){
                   CookieUtils.addVedengEncodeIdCookieMonth(request,response, CookieType.APP_LOGIN_TOKEN.getKey(),jx.getSsoAccountId(),forceCookieDomain,cookieExpireSeconds);
                   //登陆成功之后设置cookie 内openId为空
                   CookieUtils.addWxAuthOpenIdCookie(response, CommonConstant.MJX_WECHAT_OPENID,null,0);
               }
               //完善信息时
               //如果是贝登精选立即申请进来的，需要推送到ERP
               if (CommonConstant.APPLY_JOIN_BACKURL.equals(reqUserDto.getBackurl())){
                   //推送数据
                   resultInfo.setData(this.applyJoinVedeng(reqUserDto.getMobile()));
               }
           }
           logger.info("注册/重置密码/修改密码结束 》》》》》》》》》》》》》》》》》》》ResultInfo====》"+JSONObject.toJSON(resultInfo));
           return resultInfo;
        }catch (Exception e){
            logger.error("注册/重置密码/修改密码异常",e);
            return ResultInfo.fail(e);
        }
    }

    /**
     * @Description 获取图形验证码
     * @param mobile
     * @param response
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     * @return
     */
    @ApiOperation(value = "获取图形验证码方法", notes = "mobile 必填")
    @GetMapping(value="/getCaptchaCode")
    public void getCaptchaCode(String mobile, HttpServletResponse response){
        ServletOutputStream responseOutputStream = null;
        logger.info("获取图形验证码开始 》》》》》》》》》》》》》》》》》》》》》》》");
        logger.info("获取图形验证码入参  mobile -----  mobile："+mobile);
        try{
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            ReqAccountDTO reqAccountDTO = new ReqAccountDTO();
            reqAccountDTO.setMobile(mobile.substring(0,mobile.indexOf("?")>0?mobile.indexOf("?"):mobile.length()));
            reqAccountDTO.setCaptchaCodeLen(CommonConstant.CAPTCHA_CODE_LENGTH);
            //获取图形验证码
            ResUserDTO resUserDTO = passportApiService.captchaCode(reqAccountDTO);
            byte[] imageCode = resUserDTO.getCaptchaCode();
            logger.info("获取图形验证码结束 》》》》》》》》》》》》》》》》》》》》》》》");
            responseOutputStream = response.getOutputStream();
            responseOutputStream.write(imageCode);
            responseOutputStream.flush();
            responseOutputStream.close();
        }catch (Exception e){
            logger.error("获取图形验证码异常",e);
        }finally {
            if (responseOutputStream != null){
                try {
                    responseOutputStream.close();
                } catch (IOException e) {
                    logger.error("获取图形验证码关闭responseOutputStream异常",e);
                }
            }
        }

    }

    /**
     * @Description 处理passport需要的参数
     * @param reqUserDto
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     * @return
     */
    public ReqUserDto getReqUserDto(ReqUserDto reqUserDto,HttpServletRequest request){
        String userAgent = request.getHeader("user-agent").toLowerCase();
        //封装请求的客户端参数
        reqUserDto.setUserAgent(userAgent);
        reqUserDto.setIpAddress(getIp(request));
        reqUserDto.setFromType(getFromType(request));
        reqUserDto.setPlatformSource(PLATFORM_MJX.getSource());
        return reqUserDto;
    }

    /**
     * @Description 申请加入贝登精选推送
     * @param mobile
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     * @return
     */
    public ResultInfo applyJoinVedeng (String mobile){
        logger.info("贝登精选立即申请推送到ERP开始----------------------------");
        ResultInfo resultInfo = vedengJoinService.applyJoinVedeng(mobile);
        logger.info("贝登精选立即申请推送到ERP结束-----------------------ResultInfo====》"+JSONObject.toJSON(resultInfo));
        return resultInfo;
    }
}
