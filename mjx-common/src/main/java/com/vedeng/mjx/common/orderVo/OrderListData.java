package com.vedeng.mjx.common.orderVo;

import java.math.BigDecimal;
import java.util.List;

public class OrderListData {

    private String orderNo;
    private Integer orderStatus;
    private Integer accountId;
    private String marketMoney;
    private String salesMoney;
    private String orderRemark;
    private Integer deliveryAddressId;
    private String deliveryUserName;
    private String deliveryUserAddress;
    private String deliveryUserPhone;
    private Integer invoiceAddressId;
    private String invoiceUserName;
    private String invoiceUserAddress;
    private String invoiceUserPhone;
    private Integer productTypeNum;
    private Integer productNum;
    private Integer cancelFormStatus;
    private String cancelReason;
    private List<OrderGoodsListData> goodsList;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getMarketMoney() {
        return marketMoney;
    }

    public void setMarketMoney(String marketMoney) {
        this.marketMoney = marketMoney;
    }

    public String getSalesMoney() {
        return salesMoney;
    }

    public void setSalesMoney(String salesMoney) {
        this.salesMoney = salesMoney;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public Integer getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(Integer deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public String getDeliveryUserName() {
        return deliveryUserName;
    }

    public void setDeliveryUserName(String deliveryUserName) {
        this.deliveryUserName = deliveryUserName;
    }

    public String getDeliveryUserAddress() {
        return deliveryUserAddress;
    }

    public void setDeliveryUserAddress(String deliveryUserAddress) {
        this.deliveryUserAddress = deliveryUserAddress;
    }

    public String getDeliveryUserPhone() {
        return deliveryUserPhone;
    }

    public void setDeliveryUserPhone(String deliveryUserPhone) {
        this.deliveryUserPhone = deliveryUserPhone;
    }

    public Integer getInvoiceAddressId() {
        return invoiceAddressId;
    }

    public void setInvoiceAddressId(Integer invoiceAddressId) {
        this.invoiceAddressId = invoiceAddressId;
    }

    public String getInvoiceUserName() {
        return invoiceUserName;
    }

    public void setInvoiceUserName(String invoiceUserName) {
        this.invoiceUserName = invoiceUserName;
    }

    public String getInvoiceUserAddress() {
        return invoiceUserAddress;
    }

    public void setInvoiceUserAddress(String invoiceUserAddress) {
        this.invoiceUserAddress = invoiceUserAddress;
    }

    public String getInvoiceUserPhone() {
        return invoiceUserPhone;
    }

    public void setInvoiceUserPhone(String invoiceUserPhone) {
        this.invoiceUserPhone = invoiceUserPhone;
    }

    public Integer getProductTypeNum() {
        return productTypeNum;
    }

    public void setProductTypeNum(Integer productTypeNum) {
        this.productTypeNum = productTypeNum;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public List<OrderGoodsListData> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderGoodsListData> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getCancelFormStatus() {
        return cancelFormStatus;
    }

    public void setCancelFormStatus(Integer cancelFormStatus) {
        this.cancelFormStatus = cancelFormStatus;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
