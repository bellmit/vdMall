package com.vedeng.mjx.common.constant;

/**
 * @Description: 公共常量类
 * @Author: Cooper.xu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/24
 */
public interface CommonConstant {

    /**
     * 字符集
     */
    String CHARSET_UTF8 = "UTF-8";

    /**
     * 60 秒时分秒的转换单位
     */
    Integer COMMON_TIME_UNIT_BY_MIN_SEC = 60;

    /**
     * 24 1 day 24 hours
     */
    Integer COMMON_TIME_UNIT_BY_DAY = 24;

    /**
     * 1000 1s -> 1000 ms
     */
    Integer COMMON_TIME_UNIT_MILLIS = 1000;

    /**
     * 图形验证码长度
     */
    Integer CAPTCHA_CODE_LENGTH = 4;

    /**
     * 正常[用户状态正常]
     */
    String SUCCESS_LOGIN = "1";

    /**
     *  失败[用户名密码登陆失败]
     */
    String PASSWORD_FAIL_LOGIN = "2";

    /**
     * 锁定[密码输错次数超过阈值]
     */
    String PASSWORD_FAIL_LOGIN_LOCK = "3";

    /**
     * 未注册[忘记密码时]
     */
    String PASSPORT_FAIL_NOT_REGISTER = "4";

    /**
     * 验证码已失效
     */
    String FAIL_OUT_LIMIT = "5";

    /**
     * 验证码错误次数过多且要展示图形验证码
     */
    String PASSWORD_FAIL_LOGIN_LIMIT_AND_NEED_CODE = "6";

    /**
     * 验证码错误
     */
    String FAIL_ERROR_CODE = "7";

    /**
     * 未注册[用户验证码登陆时]
     */
    String PASSPORT_FAIL_ERROR_CODE_LIMIT = "8";

    /**
     * 失败，图形验证码错误
     */
    String PASSPORT_FAIL_CODE_LOGIN_NOT_REGISTER = "9";

    /**
     * 需要展示图形验证码[用户名密码登陆失败]
     */
    String PASSPORT_FAIL_IMAGE_CODE = "10";

    /**
     * 验证码错误次数过多,请重新获取
     */
    String PASSWORD_FAIL_LOGIN_LIMIT_AND_LOST_EFFCTIVE = "11";

    /**
     * 请求参数错误
     */
    String PASSPORT_PARAM_ERROR_CODE = "88";

    /**
     * 系统异常
     */
    String PASSPORT_SYSTEM_ERROR_CODE = "99";

    /**
     * 成功标志
     */
    String SUCCESS_CODE = "SUCCESS_CODE";

    String IS_TEST_TURE = "1";

    /**
     * 用户登陆状态
     * 1: 正常[用户状态正常]
     * 2: 失败[用户名密码登陆失败]
     * 3: 锁定[密码输错次数超过阈值]
     * 4: 未注册[用户名忘记密码时]
     * 5: 验证码已失效
     * 6: 验证码错误次数过多且要展示图形验证码
     * 7: 验证码错误
     * 8: 未注册[用户验证码登陆时]
     * 9:  失败，图形验证码错误
     * 10: 需要展示图形验证码[用户名密码登陆失败]
     * 88: 失败，请求参数缺失
     * 99: 失败，系统异常
     */
    Integer STATUS_1 = 1;

    Integer STATUS_0 = 0;

    /**
     * 客户种类 0 贝登精选
     */
    Integer ACCOUNT_TYPE_0 = 0;

    /**
     * 客户来源 1 WEB
     */
    Integer ACCOUNT_FROM_1 = 1;

    /**
     * 客户来源 2 微信
     */
    Integer ACCOUNT_FROM_2 = 2;

    /**
     * 客户来源 5 qita
     */
    Integer ACCOUNT_FROM_5 = 5;

    /**
     * 是否申请加入贝登精选
     */
    Integer IS_VEDENG_JOIN_1 = 1;
    Integer ZEOR = 0;

    Integer ONE = 1;
    Integer TWO = 2;
    Integer THREE = 3;


    /**
     * session 
     */
    String SESSION_KEY = "SESSION_USER";
    //用于判断未登陆用户，已经获取过code
    String SESSION_WXCODE_KEY = "SESSION_WXCODE_KEY";
    String SESSION_LOGOUT_KEY = "SESSION_LOGOUT_KEY";

    String ERP_IS_ENABLE_0_JX = "0";

    String ERP_IS_VEDENG_JX_1_JX = "1";

    String ERP_IS_VEDENG_JOIN_1_JX = "1";

    /**
     * 微信openId
     */
    String WECHAT_OPENID = "openId";

    /**
     * 微信openId
     */
    String MJX_WECHAT_OPENID = "mjxopenId";


    /**
     * 微信openId
     */
    String MJX_WECHAT_OPENID_ONLINE = "online";


    /**
     * 微信 UA
     */
    String WECHAT_UA = "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36 wechatdevtools/1.02.1905151 MicroMessenger/6.7.3 webview/15619846993357479 webdebugger port/43982";

    /**
     * user-agent
     */
    String USER_AGENT = "user-agent";

    /**
     * micromessenger
     */
    String WX_MICR_FLAG = "micromessenger";

    /**
     * 微信code
     */
    String WECHAT_CODE = "code";

    /**
     * 微信使用过的标识
     */
    String WECHAT_CODE_FLAG = "codeUseFlag";

    /**
     * 用户登录状态 key
     */
    String ACCOUNT_STATUS = "accountStatus";

    /**
     * 登录状态
     */
    Integer ACCOUNT_LOGIN_STATUS = 1;

    /**
     * 未登录状态
     */
    Integer ACCOUNT_LOGOUT_STATUS = 0;

    /**
     * appId
     */
    String APP_ID = "appId";
    /**
     * 是否有查看价格的权限
     */
    String SESSION_CHECK_PRICE_AUTH = "SESSION_CHECK_PRICE_AUTH";

    /**
     * state
     */
    String STATE = "state";

    /**
     * .html
     */
    String PAR_HTML = ".html";

    /**
     * 功能描述: 文件类型-父级ID
     * @auther: duke.li
     * @date: 2019/6/27 11:30
     */
    Integer FILE_TYPE_SYS_ID = 23;

    /**
     * 功能描述: 商品销售攻略:文件类型
     * @auther: duke.li
     * @date: 2019/6/27 11:30
     */
    Integer OP_GOODS_SYS_ID = 400;

    /**
     * 立即申请跳转登录页的标志
     */
    String APPLY_JOIN_BACKURL = "joinToIndex";

    /**
     * 来源 m站/手机浏览器 (PASSPORT参数)
     */
    Integer PASSPORT_FROM_TYPE_M = 7;

    /**
     * 来源 微信(PASSPORT参数)
     */
    Integer PASSPORT_FORM_TYPE_WECHAT = 2;

    /**
     * 来源 pc(PASSPORT参数)
     */
    Integer PASSPORT_FORM_TYPE_WEB = 1;

    /**
     * 来源 其他(PASSPORT参数)
     */
    Integer PASSPORT_FORM_TYPE_OHTER = 5;

    /**
     * 来源 安卓(PASSPORT参数)
     */
    Integer PASSPORT_FORM_TYPE_ANDROID = 4;

    /**
     * 来源 IOS(PASSPORT参数)
     */
    Integer PASSPORT_FORM_TYPE_IOS = 3;

    /**
     * 来源 IPAD(PASSPORT参数)
     */
    Integer PASSPORT_FORM_TYPE_IPAD = 8;

    /**
     * 平台 贝登精选(PASSPORT参数)
     */
    Integer PASSPORT_PLATFORM_SOURCE = 3;
    /**
     * 直辖市 北京
     */
    String ZHIXIASHI_BEIJING = "北京";
    /**
     * 直辖市 天津
     */
    String ZHIXIASHI_TIANJING = "天津";
    /**
     * 直辖市 上海
     */
    String ZHIXIASHI_SHANGHAI = "上海";
    /**
     * 直辖市 重庆
     */
    String ZHIXIASHI_CHONGQING = "重庆";

    /**
     * 地址最多条数
     */
    Integer MAX_ADDRESS_COUNT = 20;
    /**
     *
     */
    String REDIS_FORM_TOKEN_KEY = "r_t_n";
    /**
     *字符串 "0"
     */
    String STRING_ZERO = "0";
    /**
     *字符串 "1"
     */
    String STRING_ONE = "1";
    /**
     *字符串 "11"
     */
    String STRING_ONE_ONE = "11";
    /**
     *字符串 "10"
     */
    String STRING_ONE_ZERO = "10";
    /**
     *字符串 "12"
     */
    String STRING_ONE_TWO = "12";
    /**
     *字符串 "12"
     */
    Integer INTEGER_ELEVEN = 11;
    /**
     *字符串 "12"
     */
    String STRING_SUCCESS = "success";
    /**
     * 贝登APP-IOS的User-Agent
     */
    String USERAGENT_VEDENG_APP_IOS="vedeng-ios";
    /**
     * 贝登APP-Android的User-Agent
     */
    String USERAGENT_VEDENG_APP_ANDROID="vedeng-android";
    /**
     * 规格
     */
    String GOODS_TYPE_SPEC="规格";
    /**
     *
     */
    Integer GOODSCAR_CATEGORY_MAX_COUNT=99;
    Integer GOODSCAR_NUM_MAX_COUNT=9999;


}
