package com.vedeng.mjx.web.controller.app.login;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.constant.CookieType;
import com.vedeng.mjx.common.constant.RedisKeyConstant;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.util.CookieEncode;
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
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Map;

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
@RequestMapping(value = {"/app"})
@Api(value = "贝登精选登录接口", tags = {"登录控制层"})
public class AppLoginController extends BaseController {

    @Autowired
    private SignInOrUpService signInOrUpService;

    @Autowired
    private VedengJoinService vedengJoinService;

    @Autowired
    private RedisOperateService redisOperateService;


    /**
     * @param reqUserDto
     * @return
     * @Description 获取验证码
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     */
    @ApiOperation(value = "获取验证码方法", notes = "mobile、smsType 必填", response = ResultInfo.class)
    @PostMapping("/signInOrUp/getCode")
    public ResultInfo getCode(@RequestBody ReqUserDto reqUserDto) {
        try {
            logger.info("获取验证码入参  getCode -----  reqUserDto：" + JSONObject.toJSON(reqUserDto));
            if (StringUtils.isBlank(reqUserDto.getMobile())) {//手机号判空验证
                return ResultInfo.fail(VedengErrorCode.MOBILE_EMPTY_ERROR_MSG);
            }
            if (checkWrongPhone(reqUserDto.getMobile())) {//手机号格式验证
                return ResultInfo.fail(VedengErrorCode.FAIL_PHONE_ISTRUE);
            }
            ResultInfo resultInfo = signInOrUpService.getCode(reqUserDto);
            logger.info("获取验证码结束 》》》》》》》》》》》》》》》》》》》resultInfo====》" + JSONObject.toJSON(resultInfo));
            return resultInfo;
        } catch (Exception e) {
            logger.error("获取验证码异常", e);
            return ResultInfo.fail(e);
        }
    }
    @GetMapping(value = "/logout")
    public ResultInfo outLogin(HttpServletRequest request,HttpServletResponse responce){
        ShiroSessionUtil.clearSessionTopAccountJx(responce);
        CookieUtils.deleteLoginCookie(request,responce, CookieType.APP_LOGIN_TOKEN.getKey());
        return ResultInfo.success(true);
    }

    /**
     * @param reqUserDto
     * @return
     * @Description 校验验证码
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     */
    @ApiOperation(value = "校验验证码方法", notes = "mobile、smsType 、smsCode 、isCheckImgCode 必填，当isCheckImgCode 为 1 时，imgCode 必填", response = ResultInfo.class)
    @PostMapping("/signInOrUp/checkCode")
    public ResultInfo checkCode(@RequestBody ReqUserDto reqUserDto, HttpServletRequest request, HttpServletResponse response) {
        try {

            if(ShiroSessionUtil.getSessionTopAccountJx()!=null){
                logger.info("app校验验证码开始:::"+request.getServerName()+",url="+request.getRequestURI() +"\t"+IpUtils.getRealIp(request)+",headsid="+ request.getHeader("sid")+"\t"+ShiroSessionUtil.getSessionTopAccountJx().getMobile());
            }else{
                logger.info("app校验验证码开始未登录:::"+request.getServerName()+",url="+request.getRequestURI() +"\t"+IpUtils.getRealIp(request)+",headsid="+ request.getHeader("sid") );

            }
            logger.info("校验验证码开始 》》》》》》》》》》》》》》》》》》》");
            logger.info("校验验证码入参  checkCode -----  reqUserDto：" + JSONObject.toJSON(reqUserDto));
            if (StringUtils.isBlank(reqUserDto.getMobile())) {//手机号判空验证
                return ResultInfo.fail(VedengErrorCode.MOBILE_EMPTY_ERROR_MSG);
            }
            if (checkWrongPhone(reqUserDto.getMobile())) {//手机号格式验证
                return ResultInfo.fail(VedengErrorCode.FAIL_PHONE_ISTRUE);
            }
            //处理passport需要的参数
            reqUserDto = this.getReqUserDto(reqUserDto, request);
            //校验验证码
            ResultInfo resultInfo = signInOrUpService.checkCode(reqUserDto);
            if (resultInfo != null && CommonConstant.SUCCESS_CODE.equals(resultInfo.getCode())) {
                TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
                if (jx != null) {
                    CookieUtils.addVedengEncodeIdHeadMonth(request, response, CookieType.APP_LOGIN_TOKEN.getKey(), jx.getSsoAccountId());
                    String tokenSid = CookieEncode.encodeIdTime(NumberUtils.toLong(jx.getSsoAccountId() + ""), System.currentTimeMillis());
                    Map<String, String> dataMap = Maps.newHashMap();
                    dataMap.put("sid", tokenSid);
                    resultInfo.setData(dataMap);
                }
            }
            logger.info("校验验证码结束 》》》》》》》》》》》》》》》》》》》resultInfo====》" + JSONObject.toJSON(resultInfo));
            return resultInfo;
        } catch (Exception e) {
            logger.error("校验验证码异常", e);
            return ResultInfo.fail(e);
        }
    }

    /**
     * @param reqUserDto
     * @return
     * @Description 密码登录
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     */
    @ApiOperation(value = "密码登录方法", notes = "mobile 、password 、isCheckImgCode 必填，当isCheckImgCode 为 1 时，imgCode 必填", response = ResultInfo.class)
    @PostMapping("/signInOrUp/signInWithPwd")
    public ResultInfo signInWithPwd(@RequestBody ReqUserDto reqUserDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info("密码登录开始 》》》》》》》》》》》》》》》》》》》》》》》》》");
            logger.info("密码登录入参  signInWithPwd -----  reqUserDto：" + JSONObject.toJSON(reqUserDto));
            if (StringUtils.isBlank(reqUserDto.getMobile())) {//手机号判空验证
                return ResultInfo.fail(VedengErrorCode.MOBILE_EMPTY_ERROR_MSG);
            }
            if (checkWrongPhone(reqUserDto.getMobile())) {//手机号格式验证
                return ResultInfo.fail(VedengErrorCode.FAIL_PHONE_ISTRUE);
            }

            //处理passport需要的参数
            reqUserDto = this.getReqUserDto(reqUserDto, request);
            ResultInfo resultInfo = signInOrUpService.pwdLogin(reqUserDto);
            if (resultInfo != null && CommonConstant.SUCCESS_CODE.equals(resultInfo.getCode())) {

                TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
                if (jx != null) {
                    CookieUtils.addVedengEncodeIdHeadMonth(request, response, CookieType.APP_LOGIN_TOKEN.getKey(), jx.getSsoAccountId());
                    String tokenSid = CookieEncode.encodeIdTime(NumberUtils.toLong(jx.getSsoAccountId() + ""), System.currentTimeMillis());
                    Map<String, String> dataMap = Maps.newHashMap();
                    dataMap.put("sid", tokenSid);
                    resultInfo.setData(dataMap);
                }
            }
            logger.info("密码登录结束 》》》》》》》》》》》》》》》》》》》》》》》》》ResultInfo====》" + JSONObject.toJSON(resultInfo));
            return resultInfo;
        } catch (Exception e) {
            logger.error("密码登录异常", e);
            return ResultInfo.fail(e);
        }
    }


    /**
     * @param reqUserDto
     * @return
     * @Description /重置密码/修改密码方法
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     */
    @PostMapping("/signInOrUp/saveOrResetPwd")
    public ResultInfo resetPwd(@RequestBody ReqUserDto reqUserDto, HttpServletRequest request,HttpServletResponse response) {
        try {
            if(ShiroSessionUtil.getSessionTopAccountJx()!=null){
                logger.info("app重置密码开始:::"+request.getServerName()+",url="+request.getRequestURI() +"\t"+IpUtils.getRealIp(request)+",headsid="+ request.getHeader("sid")+"\t"+ShiroSessionUtil.getSessionTopAccountJx().getMobile());
            }else{
                logger.info("app重置密码开始未登录:::"+request.getServerName()+",url="+request.getRequestURI() +"\t"+IpUtils.getRealIp(request)+",headsid="+ request.getHeader("sid") );

            }
            String params = JSONObject.toJSONString(reqUserDto);
            logger.info(" 重置密码/修改密码入参  signUpOrResetPwd -----  reqUserDto：" + params);
            if (StringUtils.isBlank(reqUserDto.getMobile())) {//手机号判空验证
                return ResultInfo.fail(VedengErrorCode.MOBILE_EMPTY_ERROR_MSG);
            }
            if (checkWrongPhone(reqUserDto.getMobile())) {//手机号格式验证
                return ResultInfo.fail(VedengErrorCode.FAIL_PHONE_ISTRUE);
            }

            String resultRestPwd = redisOperateService.getKeyValue(RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS + reqUserDto.getMobile() + ReqSmsType.REQ_SMS_FORGET_PASSWORD.getSmsType()+ IpUtils.getRealIp(request));
            String resultSavePwd = redisOperateService.getKeyValue(RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS + reqUserDto.getMobile() + ReqSmsType.REQ_SMS_MODIFY_PASSWORD.getSmsType()+ IpUtils.getRealIp(request));
            if (StringUtils.isBlank(resultRestPwd) && StringUtils.isBlank(resultSavePwd)) {

                return ResultInfo.fail(VedengErrorCode.FAIL_EXPIRE_CODE);
            }

            reqUserDto = this.getReqUserDto(reqUserDto, request);
            ResultInfo resultInfo = signInOrUpService.saveOrResetPwd(reqUserDto);
            TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
            Map<String, String> dataMap = Maps.newHashMap();
            CookieUtils.addVedengEncodeIdHeadMonth(request, response, CookieType.APP_LOGIN_TOKEN.getKey(), jx.getSsoAccountId());
            String tokenSid = CookieEncode.encodeIdTime(NumberUtils.toLong(jx.getSsoAccountId() + ""), System.currentTimeMillis());
            dataMap.put("sid", tokenSid);
            resultInfo.setData(dataMap);
            logger.info(" 重置密码/修改密码结束 》》》》》》》》》》》》》》》》》》》ResultInfo====》" + params);
            return resultInfo;
        } catch (Exception e) {
            logger.error("注册/重置密码/修改密码异常", e);
            return ResultInfo.fail(e);
        }
    }

    /**
     * @param reqUserDto
     * @return
     * @Description 注册
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     */
    @PostMapping("/signInOrUp/signUp")
    public ResultInfo signUp(@RequestBody ReqUserDto reqUserDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            String params = JSONObject.toJSONString(reqUserDto);
            logger.info("注册   signUp -----  reqUserDto：" + params);
            if (StringUtils.isBlank(reqUserDto.getMobile())) {//手机号判空验证
                return ResultInfo.fail(VedengErrorCode.MOBILE_EMPTY_ERROR_MSG);
            }
            if (checkWrongPhone(reqUserDto.getMobile())) {//手机号格式验证
                return ResultInfo.fail(VedengErrorCode.FAIL_PHONE_ISTRUE);
            }
            String resultSavePwd = redisOperateService.getKeyValue(RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS + reqUserDto.getMobile() + ReqSmsType.REQ_SMS_REG_OR_LOGIN.getSmsType()+ IpUtils.getRealIp(request));
            String resultRestPwd = redisOperateService.getKeyValue(RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS + reqUserDto.getMobile() + ReqSmsType.REQ_SMS_FORGET_PASSWORD.getSmsType()+ IpUtils.getRealIp(request));

            if (StringUtils.isBlank(resultSavePwd)&&StringUtils.isBlank(resultRestPwd)) {
                return ResultInfo.fail(VedengErrorCode.FAIL_EXPIRE_CODE);
            }
            //处理passport需要的参数
            reqUserDto = this.getReqUserDto(reqUserDto, request);
            ResultInfo resultInfo = signInOrUpService.signUp(reqUserDto);
            if (resultInfo != null && (VedengErrorCode.REG_SUCCESS_MSG.getCode().equals(resultInfo.getCode())
                    || VedengErrorCode.PASSWORD_SAVE_SUCCESS.getCode().equals(resultInfo.getCode()))) {
                TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
                if (jx != null) {
                    CookieUtils.addVedengEncodeIdHeadMonth(request, response, CookieType.APP_LOGIN_TOKEN.getKey(), jx.getSsoAccountId());
                    String tokenSid = CookieEncode.encodeIdTime(NumberUtils.toLong(jx.getSsoAccountId() + ""), System.currentTimeMillis());
                    Map<String, String> dataMap = Maps.newHashMap();
                    dataMap.put("sid", tokenSid);
                    resultInfo.setData(dataMap);
                }
            }
            logger.info("注册/结束 》》》》》》》》》》》》》》》》》》》ResultInfo====》" + params);
            return resultInfo;
        } catch (Exception e) {
            logger.error("注册/异常", e);
            return ResultInfo.fail(e);
        }
    }

    /**
     * @param reqUserDto
     * @param response
     * @return
     * @Description 获取图形验证码
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     */
    @ApiOperation(value = "获取图形验证码方法", notes = "mobile 必填")
    @PostMapping(value = "/signInOrUp/getCaptchaCode")
    public ResultInfo getCaptchaCode(@RequestBody ReqUserDto reqUserDto, HttpServletResponse response) {
        String mobile=reqUserDto.getMobile();
        if (StringUtils.isBlank(reqUserDto.getMobile())) {//手机号判空验证
            return ResultInfo.fail(VedengErrorCode.MOBILE_EMPTY_ERROR_MSG);
        }
        if (checkWrongPhone(reqUserDto.getMobile())) {//手机号格式验证
            return ResultInfo.fail(VedengErrorCode.FAIL_PHONE_ISTRUE);
        }
        logger.info("获取图形验证码入参  mobile -----  mobile：" + mobile);
        try {
            ReqAccountDTO reqAccountDTO = new ReqAccountDTO();
            reqAccountDTO.setMobile(mobile.substring(0, mobile.indexOf("?")>0?mobile.indexOf("?"):mobile.length()));
            reqAccountDTO.setCaptchaCodeLen(CommonConstant.CAPTCHA_CODE_LENGTH);
            //获取图形验证码
            ResUserDTO resUserDTO = passportApiService.captchaCode(reqAccountDTO);
            byte[] imageCode = resUserDTO.getCaptchaCode();
            logger.info("获取图形验证码结束 》》》》》》》》》》》》》》》》》》》》》》》");
            Map<String,String> map= Maps.newHashMap();
            map.put("url",new String(Base64.getEncoder().encode(imageCode)));
            return ResultInfo.success(map);
        } catch (Exception e) {
            logger.error("获取图形验证码异常", e);
            return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
        } finally {

        }
    }

    /**
     * @param reqUserDto
     * @return
     * @Description 处理passport需要的参数
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     */
    public ReqUserDto getReqUserDto(ReqUserDto reqUserDto, HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        //封装请求的客户端参数
        reqUserDto.setUserAgent(userAgent);
        reqUserDto.setIpAddress(getIp(request));
        reqUserDto.setFromType(getFromType(request));
        reqUserDto.setPlatformSource(PLATFORM_MJX.getSource());
        return reqUserDto;
    }



}
