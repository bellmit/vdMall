package com.vedeng.mjx.common.enumUtils;

public enum PlatFormEnum {

    VEDENG_APP(1),//贝登app
    GOMANAGER_APP(2);//医械购app

    private Integer value;

    private PlatFormEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
