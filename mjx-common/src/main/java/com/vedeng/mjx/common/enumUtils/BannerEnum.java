package com.vedeng.mjx.common.enumUtils;

public enum BannerEnum {

    MJX(9),
    APP(14);

    private Integer value;

    private BannerEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
