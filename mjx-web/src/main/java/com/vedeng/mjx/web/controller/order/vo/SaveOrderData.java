package com.vedeng.mjx.web.controller.order.vo;

import java.util.List;

public class SaveOrderData {

    /**
     * 地址id
     */
    private Integer accountAddressId;
    /**
     * 购物车id
     */
    private List<String> placeNo;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 订单来源
     */
    private Integer orderSrc;

    public Integer getAccountAddressId() {
        return accountAddressId;
    }

    public void setAccountAddressId(Integer accountAddressId) {
        this.accountAddressId = accountAddressId;
    }

    public List<String> getPlaceNo() {
        return placeNo;
    }

    public void setPlaceNo(List<String> placeNo) {
        this.placeNo = placeNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getOrderSrc() {
        return orderSrc;
    }

    public void setOrderSrc(Integer orderSrc) {
        this.orderSrc = orderSrc;
    }
}
