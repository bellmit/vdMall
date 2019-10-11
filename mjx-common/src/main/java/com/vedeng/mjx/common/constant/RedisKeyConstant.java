package com.vedeng.mjx.common.constant;

/**
 * @Description: redis
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/7/1
 */
public interface RedisKeyConstant {

    /**
     * 微信code问题url
     */
    String WECHAT_REQ_CODE_URL = "wechat.code.url";

    String mjxallkey ="mjx_catorycache";

    String mjxcat3key="mjx_catory3";
    String MOBILE_CODE_LEFT_SECOND="m_c_l_s";

    String MJX_INDEX = "index_json04";

    String MJX_INDEX_SPARE = "index_json_spare04";

//    String MJX_INDEX = "index_json_test";
//
//    String MJX_INDEX_SPARE = "index_json_spare_test";

//    String MJX_INDEX = "index_json";
//
//    String MJX_INDEX_SPARE = "index_json_spare";


    String MOBILE_CHECK_AUTH="account:m_c_a";

    String PRODUCT_EXIST = "searchSkuResult";

    String PRODUCT_EXIST_WIND = "skuList";

    String MOBILE_CODE_VERIFY_SUCCESS="m_c_v_s";

    Long MOBILE_CODE_VERIFY_SUCCESS_EXPIRE=5*60*1000L;


}
