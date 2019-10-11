package com.vedeng.mjx.common.enumUtils;

public enum OrderEnum {

    ALL(-1),//全部
    EXAMINE(0),//审核
    WAIT_CONFIRM(1),//待确认
    CONFIRM(2),//已确认
    PAY(3),//已付款
    SEND(4),//待收货
    FINISH(5),//已完成
    CHANEL(6);//已取消

    private Integer value;

    private OrderEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
