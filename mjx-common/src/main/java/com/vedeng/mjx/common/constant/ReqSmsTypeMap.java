package com.vedeng.mjx.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 短信请求类型常量枚举
 * @Author: Cooper.xu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/24
 */
public class ReqSmsTypeMap {

    public final static Map<String,String> map = new HashMap<>();
    static {
        map.put("1", "REQ_SMS_REG_OR_LOGIN");//注册/登录
        map.put("2", "REQ_SMS_REG");//注册
        map.put("3", "REQ_SMS_LOGIN");//登录
        map.put("4", "REQ_SMS_FORGET_PASSWORD");//忘记密码
        map.put("5", "REQ_SMS_MODIFY_PASSWORD");//修改密码
    }
}

