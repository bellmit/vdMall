package com.vedeng.mjx.common.enumUtils;

public enum ClientEnum {

    ANDROID(1),//安卓
    IOS(2);//ios

    private Integer value;

    private ClientEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
