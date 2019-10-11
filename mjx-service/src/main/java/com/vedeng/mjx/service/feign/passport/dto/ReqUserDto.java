package com.vedeng.mjx.service.feign.passport.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ClassName ResUserDto
 * @Description 登录注册相关参数封装对象
 * @Author Cooper.xu
 * @Date 2019-06-27 14:18
 * @Version 1.0
 **/
@ApiModel(value="ReqUserDto",description="登录注册相关参数封装对象")
public class ReqUserDto implements Serializable {


    /**
     * 手机号码
     */
    @ApiModelProperty(value="手机号码",name="mobile",example="13312341234")
    private String mobile;

    /**
     * smsType：1 注册/登录 2 注册 3 登录 4 忘记密码 5 修改密码
     */
    @ApiModelProperty(value="smsType：1 注册/登录 2 注册 3 登录 4 忘记密码 5 修改密码",name="smsType",example="1")
    private String smsType;

    /**
     * 短信验证码
     */
    @ApiModelProperty(value="短信验证码",name="smsCode",example="12345678")
    private String smsCode;

    /**
     * isCheckImgCode是否需要验证图形验证码：1 是  0 否
     */
    @ApiModelProperty(value="isCheckImgCode是否需要验证图形验证码：1 是  0 否",name="isCheckImgCode",example="1")
    private String isCheckImgCode;

    /**
     * 图形验证码
     */
    @ApiModelProperty(value="图形验证码",name="imgCode",example="1234")
    private String imgCode;

    /**
     * 登录密码
     */
    @ApiModelProperty(value="登录密码",name="password",example="123456")
    private String password;

    /**
     * 企业名称
     */
    @ApiModelProperty(value="企业名称",name="companyName",example="南京贝登医疗股份有限公司")
    private String companyName;

    /**
     * 调用测试数据的标志 1-是 0-否
     */
    @ApiModelProperty(value="调用测试数据的标志 1-是 0-否",name="isTest",example="1")
    private String isTest;

    /**
     * openId
     */
    @ApiModelProperty(value="微信openId",name="openId",example="xadasdaxxadasdasxazxasda12312")
    private String openId;

    /**
     * isVedengJoin 是否申请加入贝登精选
     */
    @ApiModelProperty(value="isVedengJoin 是否申请加入贝登精选 1-是  0-否",name="isVedengJoin",example="1")
    private Integer isVedengJoin;

    /**
     * backUrl 返回页链接
     */
    @ApiModelProperty(value="backurl 返回页链接",name="backurl",example="www.baidu.com")
    private String backurl;

    /**
     * loginData 登录数据，冗余
     */
    @ApiModelProperty(value="loginData 登录数据，冗余",name="loginData",example="")
    private String loginData;

    /**
     * ipAddress 客户端真实IP（PASSPORT参数）
     */
    @ApiModelProperty(value="ipAddress（PASSPORT参数） 客户端真实IP，冗余",name="ipAddress",example="")
    private String ipAddress;

    /**
     * userAgent 设备标识（PASSPORT参数）
     */
    @ApiModelProperty(value="userAgent（PASSPORT参数） 设备标识，冗余",name="userAgent",example="")
    private String userAgent;

    /**
     * platformSource 平台（PASSPORT参数）
     */
    @ApiModelProperty(value="platformSource（PASSPORT参数） 平台 0-商城 1-商城论坛 2-其他 3-贝登精选 4-医械购，冗余",name="platformSource",example="")
    private Integer platformSource;

    /**
     * fromType 来源（PASSPORT参数）
     */
    @ApiModelProperty(value="fromType（PASSPORT参数） 1-WEB端 PC   2-微信   3-IOS_APP  4-安卓APP   5-其他   6-ERP  7-m站，冗余",name="fromType",example="")
    private Integer fromType;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private String ip;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getIsCheckImgCode() {
        return isCheckImgCode;
    }

    public void setIsCheckImgCode(String isCheckImgCode) {
        this.isCheckImgCode = isCheckImgCode;
    }

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIsTest() {
        return isTest;
    }

    public void setIsTest(String isTest) {
        this.isTest = isTest;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getIsVedengJoin() {
        return isVedengJoin;
    }

    public void setIsVedengJoin(Integer isVedengJoin) {
        this.isVedengJoin = isVedengJoin;
    }

    public String getLoginData() {
        return loginData;
    }

    public void setLoginData(String loginData) {
        this.loginData = loginData;
    }

    public String getBackurl() {
        return backurl;
    }

    public void setBackurl(String backurl) {
        this.backurl = backurl;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getPlatformSource() {
        return platformSource;
    }

    public void setPlatformSource(Integer platformSource) {
        this.platformSource = platformSource;
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }
}
