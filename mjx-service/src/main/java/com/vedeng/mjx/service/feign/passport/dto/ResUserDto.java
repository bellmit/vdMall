package com.vedeng.mjx.service.feign.passport.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ClassName ResUserDto
 * @Description 登录注册登录成功后返回的参数
 * @Author Cooper.xu
 * @Date 2019-06-27 14:18
 * @Version 1.0
 **/
public class ResUserDto implements Serializable {

    private String code = "success";

}
