package com.vedeng.mjx.service.signInOrUp.impl;

import com.alibaba.fastjson.JSONObject;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.constant.RedisKeyConstant;
import com.vedeng.mjx.common.constant.ReqSmsTypeMap;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.util.CookieUtils;
import com.vedeng.mjx.common.util.IpUtils;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.mapper.TopAccountJxMapper;
import com.vedeng.mjx.mapper.ext.TopAccountJxExtMapper;
import com.vedeng.mjx.service.base.impl.BaseServiceImpl;
import com.vedeng.mjx.service.feign.passport.UserInfoServiceFeignClient;
import com.vedeng.mjx.service.feign.passport.dto.ReqUserDto;
import com.vedeng.mjx.service.redis.RedisOperateService;
import com.vedeng.mjx.service.signInOrUp.SignInOrUpService;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.service.vdengJoin.VedengJoinService;
import com.vedeng.passport.api.login.constant.PlatformType;
import com.vedeng.passport.api.login.constant.ReqSmsType;
import com.vedeng.passport.api.login.dto.req.sms.ReqSmsDTO;
import com.vedeng.passport.api.login.dto.req.user.ReqAccountDTO;
import com.vedeng.passport.api.login.dto.res.ResSmsDTO;
import com.vedeng.passport.api.login.dto.res.ResUserDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SingInOrUpServiceImpl
 * @Description TODO
 * @Author Cooper.xu
 * @Date 2019-06-27 15:43
 * @Version 1.0
 **/
@Service
public class SignInOrUpServiceImpl extends BaseServiceImpl implements SignInOrUpService {
    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger("login");

    /**
     * 注册登录api接口
     */
    @Resource
    private UserInfoServiceFeignClient passportApiService;

    @Resource
    private TopAccountJxMapper topAccountJxMapper;

    @Resource
    private TopAccountJxExtMapper topAccountJxExtMapper;

    @Resource
    private VedengJoinService vedengJoinService;
    @Resource
    private RedisOperateService redisOperateService;

    @Value("${mjx.session_timeout}")
    private String sessionTimeOut;

    @Override
    public ResultInfo checkCode(ReqUserDto reqUserDto) {
        //封装接口入参
        ReqAccountDTO reqAccountDTO = this.getReqAccountDTO(reqUserDto);
        ResUserDTO resUserDTO;
        //声明返回对象
        switch (ReqSmsType.valueOf(ReqSmsType.class,
                ReqSmsTypeMap.map.get(reqUserDto.getSmsType()))){
            // 1 注册/登录
            case REQ_SMS_REG_OR_LOGIN :
                logger.info("注册/登录方式-----");
                resUserDTO = passportApiService.verifyCodeLogin(reqAccountDTO);
                logger.info("注册/登录方式-----resUserDTO=="+JSONObject.toJSON(resUserDTO));
                break;
            // 2 注册，暂不提供服务
            case REQ_SMS_REG :
                logger.info("注册，暂不提供服务-----");
                resUserDTO = null;
                break;
            // 3 登录，暂不提供服务
            case REQ_SMS_LOGIN :
                logger.info("登录，暂不提供服务-----");
                resUserDTO = null;
                break;
            // 4 忘记密码
            case REQ_SMS_FORGET_PASSWORD :
                logger.info("忘记密码-----");
                resUserDTO = passportApiService.forgetPassword(reqAccountDTO);
                logger.info("忘记密码-----resUserDTO=="+JSONObject.toJSON(resUserDTO));
                break;
            // 5 修改密码
            case REQ_SMS_MODIFY_PASSWORD :
                logger.info("修改密码-----");
                resUserDTO = passportApiService.updatePassword(reqAccountDTO);
                logger.info("修改密码-----resUserDTO=="+JSONObject.toJSON(resUserDTO));
                break;
            default:
                resUserDTO = null;
                break;
        }
        if (resUserDTO == null){//校验失败，请稍后重试
            logger.info("1////校验失败，请稍后重试----- resUserDTO 为空");
            return ResultInfo.fail(VedengErrorCode.SMS_CHECKED_FAILED);
        }
        if (CommonConstant.FAIL_ERROR_CODE.equals(resUserDTO.getUserStatus())){
            logger.info("2////短信验证码错误，请重新输入-----");
            //短信验证码错误，请重新输入
            return ResultInfo.fail(VedengErrorCode.FAIL_ERROR_CODE);
        }else if (CommonConstant.FAIL_OUT_LIMIT.equals(resUserDTO.getUserStatus())){
            //短信验证码已失效，请重新获取
            logger.info("3////短信验证码已失效，请重新获取-----");
            return ResultInfo.fail(VedengErrorCode.FAIL_OUT_LIMIT);
        }else if (CommonConstant.PASSWORD_FAIL_LOGIN_LIMIT_AND_LOST_EFFCTIVE.equals(resUserDTO.getUserStatus())){
            //短信验证码错误次数过多，请重新获取
            logger.info("4////短信验证码错误次数过多，请重新获取-----");
            return ResultInfo.fail(VedengErrorCode.FAIL_ERROR_CODE_LIMIT);
        }else if (CommonConstant.PASSWORD_FAIL_LOGIN_LIMIT_AND_NEED_CODE.equals(resUserDTO.getUserStatus())){
            //错误次数较多，需弹框输入图形验证码
            logger.info("5////错误次数较多，需弹框输入图形验证码-----");
            return ResultInfo.fail(VedengErrorCode.FAIL_OUT_LIMIT_NEED_IMGCODE);
        }else if (CommonConstant.PASSPORT_FAIL_CODE_LOGIN_NOT_REGISTER.equals(resUserDTO.getUserStatus())){
            //图形验证码错误，请重新输入
            logger.info("6////图形验证码错误，请重新输入-----");
            return ResultInfo.fail(VedengErrorCode.FAIL_IMAGE_CODE);
        }else if (CommonConstant.PASSPORT_FAIL_ERROR_CODE_LIMIT.equals(resUserDTO.getUserStatus())){
            //未注册的返回信息
            logger.info("7////用户验证码登陆未注册,默认注册,跳转页面-----");
            logger.info("7////用户验证码登陆未注册,默认注册,本地插入数据开始-----");
            //this.doAcountData(reqUserDto,resUserDTO,userAgent);
            redisOperateService.setKeyValueWithExpire(RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS+reqUserDto.getMobile()+reqUserDto.getSmsType()+reqUserDto.getIpAddress()
                    ,"1",RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS_EXPIRE);
            logger.info("7////用户验证码登陆未注册,默认注册,本地插入数据结束-----");
            return ResultInfo.fail(VedengErrorCode.FAIL_CODE_LOGIN_NOT_REGISTER);
        }else if (CommonConstant.SUCCESS_LOGIN.equals(resUserDTO.getUserStatus())){
            logger.info("8////登录成功-----");
            //
            redisOperateService.setKeyValueWithExpire(RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS+reqUserDto.getMobile()+reqUserDto.getSmsType()+reqUserDto.getIpAddress()
                    ,"1",RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS_EXPIRE);

            this.doAccountData(reqUserDto,resUserDTO);
            // 根据ossAccountId获取用户信息
            TopAccountJx jx = topAccountJxExtMapper.selectBySsoAcountId(resUserDTO.getSsoAccountId());
            logger.info("验证码登录----jx="+JSONObject.toJSON(jx));
            // 设置用户session
            ShiroSessionUtil.setSession(jx);
            //推送数据到ERP
            //vedengJoinService.pushAcountDataToERP(reqUserDto.getMobile());
            //登录成功，跳转页面
            return ResultInfo.success(VedengErrorCode.SUCCESS_CODE);
        }else if (CommonConstant.PASSPORT_FAIL_NOT_REGISTER.equals(resUserDTO.getUserStatus())){
            //忘记密码时未注册，需要弹窗提示去注册
            logger.info("9////忘记密码时未注册，需要弹窗提示去注册-----");
            redisOperateService.setKeyValueWithExpire(RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS+reqUserDto.getMobile()+ReqSmsType.REQ_SMS_REG_OR_LOGIN.getSmsType()+reqUserDto.getIpAddress()
                    ,"1",RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS_EXPIRE);
            redisOperateService.setKeyValueWithExpire(RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS+reqUserDto.getMobile()+reqUserDto.getSmsType()+reqUserDto.getIpAddress()
                    ,"1",RedisKeyConstant.MOBILE_CODE_VERIFY_SUCCESS_EXPIRE);
            return ResultInfo.fail(VedengErrorCode.PASSPORT_FAIL_NOT_REGISTER);
        }else {
            logger.info("8////登录失败-----");
            return ResultInfo.fail(VedengErrorCode.SMS_CHECKED_FAILED);
        }
    }

    @Override
    public ResultInfo pwdLogin(ReqUserDto reqUserDto) {
        //封装接口入参
        ReqAccountDTO reqAccountDTO = this.getReqAccountDTO(reqUserDto);
        //进行密码登录
        ResUserDTO resUserDTO = passportApiService.passwordLogin(reqAccountDTO);
        if (null == resUserDTO){
            logger.info("登录失败----- resUserDTO 为空");
            return ResultInfo.fail(VedengErrorCode.PASSWORD_FAIL_LOGIN);
        }
        logger.info("返回参数-----resUserDTO---"+ JSONObject.toJSON(resUserDTO));
        if(CommonConstant.PASSWORD_FAIL_LOGIN.equals(resUserDTO.getUserStatus())){
            //手机号或密码不正确，请重新输入
            logger.info("手机号或密码不正确，请重新输入");
            return ResultInfo.fail(VedengErrorCode.PASSWORD_FAIL_LOGIN);
        }else if(CommonConstant.PASSWORD_FAIL_LOGIN_LOCK.equals(resUserDTO.getUserStatus())){
            //密码输错次数过多，请使用短信验证码登录
            logger.info("密码输错次数过多，请使用短信验证码登录");
            return ResultInfo.fail(VedengErrorCode.PASSWORD_FAIL_LOGIN_LOCK);
        }else if(CommonConstant.PASSPORT_FAIL_IMAGE_CODE.equals(resUserDTO.getUserStatus())){
            //需弹窗输入图形验证码
            logger.info("需弹窗输入图形验证码");
            return ResultInfo.fail(VedengErrorCode.PASSWORD_FAIL_LOGIN_LIMIT);
        }else if(CommonConstant.PASSPORT_FAIL_CODE_LOGIN_NOT_REGISTER.equals(resUserDTO.getUserStatus())){
            //图形验证码错误，请重新输入
            logger.info("图形验证码错误，请重新输入");
            return ResultInfo.fail(VedengErrorCode.PASSWORD_FAIL_IMAGECODE);
        }else if(CommonConstant.SUCCESS_LOGIN.equals(resUserDTO.getUserStatus())){
            logger.info("登录成功，重置 session");
            this.doAccountData(reqUserDto,resUserDTO);
            //登录成功，重置 session
            // 根据ossAccountId获取用户信息
            TopAccountJx jx = topAccountJxExtMapper.selectBySsoAcountId(resUserDTO.getSsoAccountId());
            logger.info("密码登录----jx="+JSONObject.toJSON(jx));
            // 设置用户session
            logger.info("登录成功，重置 session成功");
            ShiroSessionUtil.setSession(jx);
            return ResultInfo.success(VedengErrorCode.SUCCESS_CODE);
        }else {
            //登录失败
            logger.info("登录失败");
            return ResultInfo.fail(VedengErrorCode.PASSWORD_FAIL_LOGIN);
        }
    }

    @Override
    public ResultInfo signUp(ReqUserDto reqUserDto, String userAgent) {
        ReqAccountDTO reqAccountDTO = new ReqAccountDTO();
        reqAccountDTO.setMobile(reqUserDto.getMobile());
        logger.info("openId========"+reqUserDto.getOpenId());
        // 设置openId
        reqAccountDTO.setOpenId(reqUserDto.getOpenId());
        //进行注册
        //ResUserDTO resUserDTO = passportApiService.register(reqAccountDTO);
        ResUserDTO resUserDTO = new ResUserDTO();
        if (! CommonConstant.SUCCESS_LOGIN.equals(resUserDTO.getUserStatus())){
            return ResultInfo.fail(VedengErrorCode.REG_ERROR_MSG);
        }
        //同时需要在库中插入新用户信息
        this.doAccountData(reqUserDto,resUserDTO);
        //推送数据到ERP
        vedengJoinService.pushAcountDataToERP(reqUserDto.getMobile());
        return ResultInfo.success(VedengErrorCode.REG_SUCCESS_MSG);
    }

    @Override
    public ResultInfo getCode(ReqUserDto reqUserDto) {
        //封装参数对象
        ReqSmsDTO reqSmsDTO = new ReqSmsDTO();
        reqSmsDTO.setMobile(reqUserDto.getMobile());
        // 平台类型
        reqSmsDTO.setPlatformSource(PlatformType.PLATFORM_MJX.getSource());
        reqSmsDTO.setSmsType(Integer.valueOf(reqUserDto.getSmsType()));
        Map<String,Integer> map=new HashMap<    >();

        String lastSuccessTime=redisOperateService.getKeyValue(RedisKeyConstant.MOBILE_CODE_LEFT_SECOND+reqUserDto.getMobile());
        if(StringUtils.isNotBlank(lastSuccessTime)){
            long minseconds=System.currentTimeMillis()- NumberUtils.toLong(lastSuccessTime);
            long seconds=minseconds/1000;
            long leftSeconds=60-seconds;
            map.put("leftSecond",NumberUtils.toInt(leftSeconds+""));
            return ResultInfo.fail(VedengErrorCode.SMS_SEND_LITMIT_TIME_AND_NUBMER).data(map);
        }

        //调用发送短信的接口
        ResSmsDTO resSmsDTO = passportApiService.sendVerifyCode(reqSmsDTO);
        // 发送失败
        if (null == resSmsDTO){
            return ResultInfo.fail(VedengErrorCode.SMS_SEND_FAILED);
        }
        if (!CommonConstant.SUCCESS_CODE.equals(resSmsDTO.getCode())){
            return ResultInfo.fail(resSmsDTO.getCode(),resSmsDTO.getMessage());
        }
        //上一次发送成功的时间
        redisOperateService.setKeyValueWithExpire(RedisKeyConstant.MOBILE_CODE_LEFT_SECOND+reqUserDto.getMobile(),System.currentTimeMillis()+"",60*1000);
        map.put("leftSecond",60);
        return ResultInfo.success(VedengErrorCode.SUCCESS_CODE).data(map);
    }

    @Override
    public ResultInfo signUpOrSavePwd(ReqUserDto reqUserDto) {
        //如果是注册信息完善时，需要更新企业名称，同时更新完后会默认登录
        if (StringUtils.isNotBlank(reqUserDto.getCompanyName())){
           return signUp(reqUserDto);
        }else{
        //重置密码或修改密码
            return saveOrResetPwd(reqUserDto);
        }
    }

    @Override
    public ResultInfo signUp(ReqUserDto reqUserDto) {
        //封装接口入参
        ReqAccountDTO reqAccountDTO = this.getReqAccountDTO(reqUserDto);
        //保存密码
        ResUserDTO resUserDTO;
        //如果是注册信息完善时，需要更新企业名称，同时更新完后会默认登录

            reqAccountDTO.setCorporation(reqUserDto.getCompanyName());
            resUserDTO = passportApiService.updateRegistrationInfo(reqAccountDTO);
            if (resUserDTO == null){
                return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
            }else {
                if (CommonConstant.SUCCESS_LOGIN.equals(resUserDTO.getUserStatus())){
                    //更新到库中
                    this.doAccountData(reqUserDto,resUserDTO);
                    //登录成功，重置 session
                    // 根据ossAccountId获取用户信息
                    TopAccountJx jx = topAccountJxExtMapper.selectBySsoAcountId(resUserDTO.getSsoAccountId());
                    logger.info("注册信息完善----jx="+JSONObject.toJSON(jx));
                    // 设置用户session
                    ShiroSessionUtil.setSession(jx);
                    //推送数据到ERP
                    vedengJoinService.pushAcountDataToERP(reqUserDto.getMobile());
                    return ResultInfo.success(VedengErrorCode.REG_SUCCESS_MSG);
                }else {
                    return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
                }
            }

    }

    @Override
    public ResultInfo saveOrResetPwd(ReqUserDto reqUserDto) {
        //封装接口入参
        ReqAccountDTO reqAccountDTO = this.getReqAccountDTO(reqUserDto);
        //保存密码
        ResUserDTO resUserDTO;
         //重置密码或修改密码
            resUserDTO = passportApiService.resetPassword(reqAccountDTO);
            if (resUserDTO == null){
                return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
            }else {
                if (CommonConstant.SUCCESS_LOGIN.equals(resUserDTO.getUserStatus())){
                    this.doAccountData(reqUserDto,resUserDTO);
                    //登录成功，重置 session
                    // 根据ossAccountId获取用户信息
                    TopAccountJx jx = topAccountJxExtMapper.selectBySsoAcountId(resUserDTO.getSsoAccountId());
                    logger.info("注册信息完善----jx="+JSONObject.toJSON(jx));
                    // 设置用户session
                    ShiroSessionUtil.setSession(jx);
                    return ResultInfo.success(VedengErrorCode.PASSWORD_SAVE_SUCCESS);
                }else {
                    return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
                }
            }
    }

    @Override
    public Integer doAccountData(ReqUserDto reqUserDto, ResUserDTO resUserDTO) {
        TopAccountJx topAccount = topAccountJxExtMapper.selectByMobile(reqUserDto.getMobile());
        Integer result = 0;
        TopAccountJx topAccountJx = new TopAccountJx();
        topAccountJx.setMobile(reqUserDto.getMobile());
        topAccountJx.setAccountType(CommonConstant.ACCOUNT_TYPE_0);
        topAccountJx.setSsoAccountId(resUserDTO.getSsoAccountId());
        topAccountJx.setWeixinOpenid(reqUserDto.getOpenId());
        if (reqUserDto.getFromType() != null){
            if (CommonConstant.PASSPORT_FORM_TYPE_WECHAT.equals(reqUserDto.getFromType())){//微信客户端
                topAccountJx.setFrom(CommonConstant.ACCOUNT_FROM_2);
            }else if (CommonConstant.PASSPORT_FROM_TYPE_M.equals(reqUserDto.getFromType())){//手机浏览器
                topAccountJx.setFrom(CommonConstant.ACCOUNT_FROM_1);
            }else{//其他
                topAccountJx.setFrom(CommonConstant.ACCOUNT_FROM_5);
            }
        }
        if (topAccount != null){
            //更新到库中
            result = topAccountJxExtMapper.updateByMobileSelective(topAccountJx);
        }else {
            //插入到库中
            topAccountJx.setCompanyName(reqUserDto.getCompanyName()==null?resUserDTO.getCorporation():reqUserDto.getCompanyName());
            result =topAccountJxMapper.insertSelective(topAccountJx);
        }
        //同时推送数据到ERP
        //vedengJoinService.pushAcountDataToERP(reqUserDto.getMobile());
        return result;
    }

    /**
     * @Description passport接口入参处理
     * @param reqUserDto
     * @Author cooper.xu
     * @Date 2019-06-25 13:15
     * @return
     */
    public ReqAccountDTO getReqAccountDTO(ReqUserDto reqUserDto){
        ReqAccountDTO reqAccountDTO = new ReqAccountDTO();
        //封装接口入参
        if (reqUserDto != null){
            reqAccountDTO.setMobile(reqUserDto.getMobile());
            reqAccountDTO.setSmsType(reqUserDto.getSmsType() == null ? null : Integer.valueOf(reqUserDto.getSmsType()));
            reqAccountDTO.setVerifyCode(reqUserDto.getSmsCode());
            reqAccountDTO.setNeedVerifyCaptchaCode(reqUserDto.getIsCheckImgCode());
            reqAccountDTO.setOpenId(reqUserDto.getOpenId());
            reqAccountDTO.setUserAgent(reqUserDto.getUserAgent());
            reqAccountDTO.setIpAddress(reqUserDto.getIpAddress());
            reqAccountDTO.setFromType(reqUserDto.getFromType());
            reqAccountDTO.setPlatformSource(reqUserDto.getPlatformSource());
            reqAccountDTO.setPassword(reqUserDto.getPassword());
            if (CommonConstant.STATUS_1.equals(reqUserDto.getIsCheckImgCode() == null ? null : Integer.valueOf(reqUserDto.getIsCheckImgCode()))){
                reqAccountDTO.setCaptchaCode(reqUserDto.getImgCode());
            }
        }
        return reqAccountDTO;
    }
}
