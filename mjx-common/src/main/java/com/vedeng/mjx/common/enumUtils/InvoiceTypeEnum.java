package com.vedeng.mjx.common.enumUtils;

public enum InvoiceTypeEnum {

    TYPE_1(1,"13%增值税专用发票"),
    TYPE_2(510,"13%增值税专用发票"),
    TYPE_3(972,"13%增值税专用发票"),
    TYPE_4(2,"13%增值税普通发票"),
    TYPE_5(511,"13%增值税普通发票"),
    TYPE_6(971,"13%增值税普通发票"),
    TYPE_7(0,"");

    private Integer type;
    private String message;

    private InvoiceTypeEnum(Integer type,String message) {
        this.type = type;
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
