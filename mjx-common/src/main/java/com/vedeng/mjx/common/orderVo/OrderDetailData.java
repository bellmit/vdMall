package com.vedeng.mjx.common.orderVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class OrderDetailData {

    private String orderNo;
    private Integer companyId;
    private String companyName;
    private Integer orderStatus;
    private Integer accountId;
    private String totalMoney;
    private String deliverMoney;
    private String couponMoney;
    private String marketMoney;
    private String salesMoney;
    private String orderRemark;
    private String deliveryUserName;
    private String deliveryUserAddress;
    private String deliveryUserPhone;
    private boolean isInvoice;
    private Integer invoiceType;
    private String invoiceMessage;
    private String invoiceUserName;
    private String invoiceUserAddress;
    private String invoiceUserPhone;
    private Integer productTypeNum;
    private Integer productNum;
    private Date addTime;
    private String orderTime;
    private Integer isSendInvoice;
    private String additionalClause;
    private String confirmContent;
    private Integer cancelFormStatus;
    private String cancelReason;
    private Date payTime;
    private List<OrderGoodsDetailData> goodList;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getDeliverMoney() {
        return deliverMoney;
    }

    public void setDeliverMoney(String deliverMoney) {
        this.deliverMoney = deliverMoney;
    }

    public String getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
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

    public boolean isInvoice() {
        return isInvoice;
    }

    public void setInvoice(boolean invoice) {
        isInvoice = invoice;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceMessage() {
        return invoiceMessage;
    }

    public void setInvoiceMessage(String invoiceMessage) {
        this.invoiceMessage = invoiceMessage;
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getIsSendInvoice() {
        return isSendInvoice;
    }

    public void setIsSendInvoice(Integer isSendInvoice) {
        this.isSendInvoice = isSendInvoice;
    }

    public String getAdditionalClause() {
        return additionalClause;
    }

    public void setAdditionalClause(String additionalClause) {
        this.additionalClause = additionalClause;
    }

    public String getConfirmContent() {
        return confirmContent;
    }

    public void setConfirmContent(String confirmContent) {
        this.confirmContent = confirmContent;
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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public List<OrderGoodsDetailData> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<OrderGoodsDetailData> goodList) {
        this.goodList = goodList;
    }
}
