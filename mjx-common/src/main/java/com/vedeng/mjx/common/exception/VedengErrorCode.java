package com.vedeng.mjx.common.exception;

public enum VedengErrorCode {

    PARAM_NULL("MJX.PARAM_NULL", "参数不能为空"),

    //全局异常定义，各模块命名规范：模块_二级模块_错误信息
    UNKNOWN_EXCEPTION("MJX.UNKNOWN_EXCEPTION","系统内部错误"),

    FAIL_CODE("FAIL_CODE", "操作失败"),

    SUCCESS_CODE("SUCCESS_CODE", "成功"),



    /**************************************购物车的异常*******************************************/
    GOODSCAR_EMPTY("GOODSCAR_EMPTY","购物车内暂无商品"),
    PARAM_MISS("100016","参数缺失"),
    FAIL_GOODSCAR_COUNT("FAIL_GOODSCAR_COUNT","添加失败，购物车商品种类已达上限"),
    FAIL_GOODSCAR_COUNT_LIMIT("FAIL_GOODSCAR_COUNT","添加失败，购物车商品数量已达上限"),
    FAIL_GOODSCAR_CARID("FAIL_GOODSCAR_CARID","购物车唯一码不能为空"),
    FAIL_GOODSCAR_HAVEDOWNSALE_ONE("FAIL_GOODSCAR_HAVEDOWNSALE","商品已下架，添加失败"),
    FAIL_GOODSCAR_HAVEDOWNSALE_TWO("FAIL_GOODSCAR_HAVEDOWNSALE","您提交的商品中包含已失效的商品，请删除后再试"),
    FAIL_GOODSCAR_GOODS_REPAEAT("FAIL_GOODSCAR_GOODS_REPAEAT","您已经从购物车购买过此商品，请勿重复下单"),
    FAIL_GOODSCAR_NO_SELECT("FAIL_GOODSCAR_GOODS_REPAEAT","您还没选择商品"),
    FAIL_GOODSCAR_HAVE_ALREADY_DELETE("FAIL_GOODSCAR_HAVE_ALREADY_DELETE","您提交的商品中包含已删除的商品，请删除后再试"),

    /**************************************地址的异常*******************************************/
    FAIL_REGSION_ID("FAIL_REGSION_ID","市区Code不能为空"),
    EMPTY_OR_NULL_REGION_ID("100004","区域ID为空"),
    NOREGIONS_UNDER_REGION_ID("100005","该区域ID下无区域"),
    FAIL_REGSION_CODE("FAIL_REGSION_CODE","省市区的Code不能为空"),
    FAIL_RECEIVER_NAME("FAIL_RECEIVER_NAME","用户名称不能为空"),
    FAIL_PHONE("FAIL_PHONE","手机号码不能为空"),
    FAIL_PHONE_COUNT("FAIL_PHONE_COUNT","手机号最多可输入11位"),
    FAIL_PHONE_ISTRUE("FAIL_PHONE_ISTRUE","手机号码格式不正确"),
    FAIL_ADDRESS("FAIL_ADDRESS","详情地址不能为空"),
    FAIL_ADDRESS_COUNT("FAIL_ADDRESS_COUNT","详细地址最多可输入50个字"),
    FAIL_PROVINCE("FAIL_PROVINCE","省份不能为空"),
    FAIL_CITY("FAIL_CITY","城市不能为空"),
    FAIL_DISTRICT("FAIL_DISTRICT","区县不能为空"),
    FAIL_MORE_WORD("FAIL_MORE_WORD","收件人最多可输入20个字"),
    FAIL_LABEL("FAIL_LABEL","标签不能为空"),
    FAIL_LABEL_COUNT("FAIL_LABEL_COUNT","标签名称最多可输入10个字"),
    FAIL_ACCOUNT_ID("FAIL_ACCOUNT_ID","客户ID不能为空"),
    FAIL_ADDRESS_ID("FAIL_ADDRESS_ID","地址ID不能为空"),
    FAIL_ADDRESS_NOT_EXIST("FAIL_ADDRESS_NOT_EXIST","地址不存在"),
    ADDRESS_COUNT_OVER_TWENTY("100003","地址超过20个"),
    ADDRESS_NOT_BELONG_CUR_USER("100006","当前登陆用户和请求业务归属用户不同"),
    CUR_USER_NO_ADDRESSES("noAddress","当前登陆用户无地址列表信息"),
    ADD_ADDRESS_ERROR("100007","新增地址失败"),
    REGION_CODE_NAME_DISMATCH("100009","省市区code与名称不对应"),


    MOBILE_EMPTY_ERROR_MSG("PASSPORT.MOBILE_EMPTY_ERROR_MSG", "手机号为空"),

    REG_ERROR_MSG("PASSPORT.REG_ERROR_MSG", "注册失败,请稍后重试"),

    REG_SUCCESS_MSG("PASSPORT.REG_SUCCESS_MSG", "注册成功"),

    /****************************密码登录异常编码**********************************************/

    PASSWORD_FAIL_LOGIN("PASSPORT.PASSWORD_FAIL_LOGIN", "密码错误或手机号错误，请重新输入"),

    PASSWORD_FAIL_LOGIN_LOCK("PASSPORT.PASSWORD_FAIL_LOGIN_LOCK", "密码输错次数过多，请使用短信验证码登录"),

    PASSWORD_FAIL_LOGIN_LIMIT("PASSPORT.PASSWORD_FAIL_LOGIN_LIMIT", "密码输错次数过多，需弹窗输入图形验证码"),

    PASSWORD_FAIL_IMAGECODE("PASSPORT.PASSWORD_FAIL_IMAGECODE", "图形验证码错误，请重新输入"),
    //未注册
    PASSWORD_FAIL_NOT_REG("PASSPORT.PASSWORD_FAIL_NOT_REG", "手机号或密码不正确，请重新输入"),

    /****************************用户登录校验异常编码**********************************************/

    USER_NOT_LOGIN_YET("noLogin", "登陆已失效，请重新登陆"),

    /****************************校验验证码异常编码**********************************************/

    //SUCCESS_LOGIN("PASSPORT.SUCCESS_LOGIN", "登录正常"),

    FAIL_OUT_LIMIT("PASSPORT.FAIL_OUT_LIMIT", "短信验证码已失效，请重新获取"),

    FAIL_OUT_LIMIT_NEED_IMGCODE("PASSPORT.FAIL_OUT_LIMIT_NEED_IMGCODE", "错误次数较多，需弹框输入图形验证码"),

    FAIL_ERROR_CODE("PASSPORT.FAIL_ERROR_CODE", "短信验证码错误，请重新输入"),

    FAIL_ERROR_CODE_LIMIT("PASSPORT.FAIL_ERROR_CODE_LIMIT", "短信验证码错误次数过多，请重新获取"),

    FAIL_CODE_LOGIN_NOT_REGISTER("PASSPORT.FAIL_CODE_LOGIN_NOT_REGISTER", "用户验证码登陆未注册,默认注册,跳转页面"),

    FAIL_IMAGE_CODE("PASSPORT.FAIL_IMAGE_CODE", "图形验证码错误，请重新输入"),

    PASSPORT_FAIL_NOT_REGISTER("PASSPORT.PASSPORT_FAIL_NOT_REGISTER", "忘记密码时未注册，需要弹窗提示去注册"),
    //PARAM_ERROR_CODE("PASSPORT.PARAM_ERROR_CODE", "请求参数错误"),

    //SYSTEM_ERROR_CODE("PASSPORT.SYSTEM_ERROR_CODE", "系统异常"),

    /****************************获取验证码异常编码**********************************************/

    SMS_SEND_FAILED("PASSPORT.SMS_SEND_FAILED", "短信验证码发送失败，请稍后重试"),

    SMS_CHECKED_FAILED("PASSPORT.SMS_CHECKED_FAILED", "短信验证码验证失败，请稍后重试"),

    SMS_CODE_LOST_EFFECTIVE("PASSPORT.SMS_CODE_LOST_EFFECTIVE", "短信验证码已失效，请重新获取"),

    REG_INFO_PERFECT_SUCCESS("PASSPORT.REG_INFO_PERFECT_SUCCESS", "注册信息完善成功，请跳转页面"),

    PASSWORD_SAVE_SUCCESS("PASSPORT.PASSWORD_SAVE_SUCCESS", "密码重置成功"),

    SMS_SEND_LITMIT_TIME_AND_NUBMER("PASSPORT.SMS_SEND_LITMIT_TIME_AND_NUBMER", "60s之内只能获取一次"),

    SMS_SEND_LITMIT_MAX_ERROR_NUBMER_TIME("PASSPORT.SMS_SEND_LITMIT_MAX_ERROR_NUBMER_TIME", "验证码获取次数达到上限，请15分钟后再试"),

    SMS_SEND_LITMIT_MAX_ERROR_NUBMER_DAY("PASSPORT.SMS_SEND_LITMIT_MAX_ERROR_NUBMER_DAY", "该手机号获取验证码次数已达上限，如有疑问，请联系客服 4006-999-569。"),

    SMS_SEND_VERIFY_CODE_FAIL("PASSPORT.SMS_SEND_VERIFY_CODE_FAIL", "发送短信验证码失败"),

    SMS_CREATE_VERIFY_CODE_FAIL("PASSPORT.SMS_CREATE_VERIFY_CODE_FAIL", "生成短信验证码失败"),

    SMS_REQ_ERROR_MSG("PASSPORT.SMS_REQ_ERROR_MSG", "短信验证码请求类型错误"),

    USER_NOT_REGISTER_MSG("PASSPORT.USER_NOT_REGISTER_MSG", "用户未注册"),

    VERIFY_CODE_VALID_MSG("PASSPORT.VERIFY_CODE_VALID_MSG", "验证码错误"),

    MOBILE_EMPTY_ERROR_FORMAT_MSG("PASSPORT.MOBILE_EMPTY_ERROR_FORMAT_MSG", "手机号格式错误"),

    CAPTCHACODE_VERIFY_ERROR_MSG("PASSPORT.CAPTCHACODE_VERIFY_ERROR_MSG", "图形验证码获取失败"),

    /****************************立即加入贝登精选异常编码**********************************************/

    VEDENG_JOIN_ACCOUNT_FORBIDDEN("MJX.VEDENG_JOIN_ERROR_FORBIDDEN", "您的账号为禁用状态，无法申请"),

    VEDENG_JOIN_IS_IN_REVIEW("MJX.VEDENG_JOIN_IS_IN_REVIEW", "您已申请加入贝登精选，请等待系统审核"),

    VEDENG_JOIN_IS_JOINED("MJX.VEDENG_JOIN_IS_JOINED", "您已成功加入贝登精选，可查看商品价格"),

    /****************************订单**********************************************/
    INTERFACE_SINGULAR("INTERFACE_SINGULAR","服务接口异常"),

    USER_ADDRESS_ERR("USER_ADDRESS_ERR","查询用户收货地址失败"),

    USER_ADDRESS_EXIST("USER_ADDRESS_EXIST","用户地址不存在或已删除"),

    GOODS_CAR_ERR("GOODS_CAR_ERR","查询购物车失败"),

    SELECT_GOODS_CAR_ERR("SELECT_GOODS_CAR_ERR","查询购物车中商品数量不一致"),

 //   GOODS_CAR_EXIST("GOODS_CAR_EXIST","购物车中有已删除的商品，请重新提交"),
    GOODS_CAR_EXIST("GOODS_CAR_EXIST","购物车中存在已删除的商品，请重新选购"),

    GOODS_ERR("GOODS_ERR","订单内存在已删除商品，请重新选购"),

    GOODS_EXIST("GOODS_EXIST","商品信息不存在或已删除"),

    ADD_ORDER_ERR("ADD_ORDER_ERR","订单提交失败"),

    ADD_ERPORDER_ERR("ADD_ERPORDER_ERR","订单推送到erp失败"),

    UPDATE_ERPORDER_ERR("ADD_ERPORDER_ERR","erp订单修改失败"),

    UPDATE_GOODS_CAR_ERR("UPDATE_GOODS_CAR_ERR","修改购物车状态失败"),

    ACCOUNT_NOTNULL("ACCOUNT_NOTNULL","创建人不能为空"),

    ORDERNO_NOTNULL("ORDERNO_NOTNULL","订单号不能为空"),

    SALES_MONEY_NOTNULL("SALES_MONEY_NOTNULL","销售价不能为空"),

    ORDER_STATUS_ERR("ORDER_STATUS_ERR","订单状态已发生变更，操作失败"),

    ORDER_ERR("ORDER_ERR","订单查询失败"),

    UPDATE_ORDER_ERR("UPDATE_ORDER_ERR","修改订单信息失败"),

    CONFIRM_ORDER_ERR("CONFIRM_ORDER_ERR","确认订单失败"),

    SEVEN("SEVEN","七天无理由退换货"),

    GOODS_ON_SALE("GOODS_ON_SALE","订单内存在下架商品，请重新选购"),

    SKU_ERR("SKU_ERR","订单内存在下架商品，请重新选购"),

    ORDER_GOODS_NOTNULL("ORDER_GOODS_NOTNULL","订单商品不能为空"),

    CANCEL_ORDER_ERR("CANCEL_ORDER_ERR","取消订单失败"),

    DELETE_ORDER_ERR("DELETE_ORDER_ERR","删除订单失败"),

    ORDER_STATUS_NOTNULL("ORDER_STATUS_NOTNULL","订单状态不能为空"),

    UPDATE_ORDER_STATUS_ERR("UPDATE_ORDER_STATUS_ERR","修改订单收货地址失败"),

    NO_ORDER_DETAIL("NO_ORDER_DETAIL","该订单不存在"),

    NO_POWER("4396","当前登陆用户和请求业务归属用户不同"),

    UPDATE_ORDER_GOODS_ERR("UPDATE_ORDER_GOODS_ERR","修改订单商品表失败"),

    ORDER_GOODS_EXIST("ORDER_GOODS_EXIST","订单内存在已删除商品，请重新选购"),

    ADD_ORDER_GOODS_ERR("ADD_ORDER_GOODS_ERR","添加订单商品表失败"),

    ORDER_FORM_NONULL("ORDER_FORM_NONULL","订单原来状态不能为空"),

    INFORM_ERP_ERR("INFORM_ERP_ERR","通知erp关闭订单失败"),

    FAIL_EXPIRE_CODE("FAIL_EXPIRE_CODE", "短信验证码验证正确状态已经过期，请重新获取验证码"),

    /****************************商品详情返回码**********************************************/
    SKU_NO_NOT_EXISTS("100001","skuNo缺失"),
    SKU_ATTR_GOODSAPI_ERROR("100002","获取兄弟sku属性失败"),
    NO_PRICE_AUTH("100010","没有价格权限"),
    AUTH_FAILED("4396", "权限验证失败"),
//    SKU_NOT_EXISTS_IN_OP("100002","skuNo在运营平台不存在");


    /****************************分享页面**********************************************/
    SHAREINFO_NO_SHARETYPE("100011","shareType缺失"),


    /****************************FORM表单提交防重异常**********************************************/
    FORM_TOKEN_IS_BLANK("100012","formToken为空"),
    FORM_TOKEN_DISMATCH_REDIS_FORMTOKEN("100013","reqFormToken与reidsFormToken不匹配"),
    FORM_TOKEN_IN_COOKIES_EXPIRED("100014","页面已过期，请刷新后再试"),
    FORM_TOKEN_EXISTS_IN_REDIS("100015","请勿重复提交");


    private String code;
    private String message;


    private VedengErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
