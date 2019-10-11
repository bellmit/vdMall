package com.vedeng.mjx.common.enumUtils;

public enum IntentType {

    H5("0"),//跳转H5,pageTitle 可选，无此字段就默认“贝登医疗”
    GOODS_DETAIL("1"),//跳转商品详情,skuNo 必填，否则跳到首页
    GOODS_CAR_PERSON("2"),//跳转独立购物车,需登录
    ORDER_LIST("3"),//跳转订单列表,orderType  订单类型，默认是全部订单类型
    ORDER_DETAIL("4"),//跳转订单详情,orderNumber 订单号，必传，否则跳到订单列表
    ADDRESS_LIST("5"),//跳转地址列表,需登录
    PERSON("6"),//跳转个人中心,需登录
    SOLR_RESULT("7");//跳转搜索结果,searchType 3种搜索类型，分类和品牌类型需要 searchId

    private String value;

    private IntentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
