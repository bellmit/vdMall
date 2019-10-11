package com.vedeng.mjx.common.enumUtils;

public enum PlaceEnum {

    INDEX("index"),
    INDEX_SPARE("index_spare"),
    JX_INDEX("jx_index"),
    JX_INDEX_SPARE("jx_index_spare");

    private String value;

    private PlaceEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
