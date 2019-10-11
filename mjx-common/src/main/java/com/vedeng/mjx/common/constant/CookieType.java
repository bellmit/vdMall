package com.vedeng.mjx.common.constant;

/**
 * @Description: cookie的类型
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/7/1
 */
public enum  CookieType {

    LOGIN_STATUS("accountStatus","用户登录状态"),

    LOGIN_TOKEN("accountToken","用户登录token"),

    APP_LOGIN_TOKEN("sid","app用户登录token");

    private String key;

    private String desc;

    private CookieType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
