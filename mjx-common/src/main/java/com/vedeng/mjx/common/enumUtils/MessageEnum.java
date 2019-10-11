package com.vedeng.mjx.common.enumUtils;

public enum MessageEnum {
    SYSTEM(1, "system"), ORDER(2, "order"), SERVICE(3, "service");
    // 成员变量
    private Integer index;
    private String name;

    // 构造方法
    private MessageEnum(Integer index, String name) {
        this.name = name;
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}