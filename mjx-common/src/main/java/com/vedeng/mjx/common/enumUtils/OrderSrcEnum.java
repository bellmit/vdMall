package com.vedeng.mjx.common.enumUtils;

public enum OrderSrcEnum {

    PC(1),//pc
    WECHAT(2),//微信
    APP(3);//app

    private Integer value;

    private OrderSrcEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
