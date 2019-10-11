package com.vedeng.mjx.common.constand;

/**
 * @ClassName CommonUrl
 * @Description 接口地址常量
 * @Author Cooper.xu
 * @Date 2019-07-01 17:36
 * @Version 1.0
 **/
public interface CommonUrl {

    /**
     * 将立即加入贝登精选信息推送到ERP的地址
     */
    String VEDENG_JOIN_TO_ERP_URL = "/system/user/updateIsVedengState.do";

    /**
     * 注册信息推送至ERP
     */
    String ACOUNT_DATA_TO_ERP_URL = "/system/user/doJxAcountData.do";
}
