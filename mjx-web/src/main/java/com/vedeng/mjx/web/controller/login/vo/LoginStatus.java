package com.vedeng.mjx.web.controller.login.vo;

public class LoginStatus {
    private Integer isLoginFlag=0;
    private Integer needCodeFlag=1;
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getIsLoginFlag() {
        return isLoginFlag;
    }

    public void setIsLoginFlag(Integer isLoginFlag) {
        this.isLoginFlag = isLoginFlag;
    }

    public Integer getNeedCodeFlag() {
        return needCodeFlag;
    }

    public void setNeedCodeFlag(Integer needCodeFlag) {
        this.needCodeFlag = needCodeFlag;
    }
    public LoginStatus login(){
        isLoginFlag=1;
        return this;
    }
    public LoginStatus loginout(){
        isLoginFlag=0;
        return this;
    }
    public LoginStatus code(){
        needCodeFlag=1;
        return this;
    }
    public LoginStatus noCode(){
        needCodeFlag=0;
        return this;
    }
    public LoginStatus appId(String appId1){
        this.appId=appId1;
        return this;
    }
}
