package com.vedeng.mjx.service.signInOrUp;

import com.alibaba.fastjson.JSONObject;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.feign.passport.dto.ReqUserDto;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.passport.api.login.dto.req.user.ReqAccountDTO;
import com.vedeng.passport.api.login.dto.res.ResUserDTO;
import org.apache.commons.lang.StringUtils;

/**
 * @InterfaceName SignInOrUpService
 * @Description TODO
 * @Author Cooper.xu
 * @Date 2019-06-27 15:43
 * @Version 1.0
 **/
public interface SignInOrUpService {

    ResultInfo checkCode(ReqUserDto reqUserDto);

    ResultInfo pwdLogin(ReqUserDto reqUserDto);

    ResultInfo signUp(ReqUserDto reqUserDto, String userAgent);

    ResultInfo getCode(ReqUserDto reqUserDto);

    ResultInfo signUpOrSavePwd(ReqUserDto reqUserDto);

    Integer doAccountData(ReqUserDto reqUserDto, ResUserDTO resUserDTO);

     ResultInfo signUp(ReqUserDto reqUserDto);

     ResultInfo saveOrResetPwd(ReqUserDto reqUserDto);
}
