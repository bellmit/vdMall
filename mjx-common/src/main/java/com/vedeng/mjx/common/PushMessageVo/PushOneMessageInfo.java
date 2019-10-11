package com.vedeng.mjx.common.PushMessageVo;

public class    PushOneMessageInfo {
    //友盟注册信息
    private String appKey;
    //友盟注册信息
    private String appMasterSecret;
    //手机特征码 每个手机唯一
    private String deviceToken;
    //订单推送的类型
    private Integer type;

    //消息编号
    private Integer messageId;

    //别名表示用于单用户绑定多设备时使用（经讨论，目前采用手机号唯一表示 ）
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppMasterSecret() {
        return appMasterSecret;
    }

    public void setAppMasterSecret(String appMasterSecret) {
        this.appMasterSecret = appMasterSecret;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
