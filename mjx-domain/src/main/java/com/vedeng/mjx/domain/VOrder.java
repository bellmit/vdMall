package com.vedeng.mjx.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * V_ORDER
 */
public class VOrder extends VOrderKey {
    /**
     * <pre>
     * 订单号
     * 表字段 : V_ORDER.ORDER_NO
     * </pre>
     * 
     */
    private String orderNo;

    /**
     * <pre>
     * 微信端订单号
     * 表字段 : V_ORDER.ORDER_NO_WX
     * </pre>
     * 
     */
    private String orderNoWx;

    /**
     * <pre>
     * 公司ID
     * 表字段 : V_ORDER.COMPANY_ID
     * </pre>
     * 
     */
    private Integer companyId;

    /**
     * <pre>
     * 公司名称
     * 表字段 : V_ORDER.COMPANY_NAME
     * </pre>
     * 
     */
    private String companyName;

    /**
     * <pre>
     * 订单来源：1PC、2微信、3APP
     * 表字段 : V_ORDER.ORDER_SRC
     * </pre>
     * 
     */
    private Integer orderSrc;

    /**
     * <pre>
     * 订单状态：0审核中(已提交)、1待确认(审核通过)、2待付款(已确认)、3待发货(已付款)、4待收货(已发货)、5、已完成 6、已取消
     * 表字段 : V_ORDER.ORDER_STATUS
     * </pre>
     * 
     */
    private Integer orderStatus;

    /**
     * <pre>
     * 创建订单人员ID
     * 表字段 : V_ORDER.ACCOUNT_ID
     * </pre>
     * 
     */
    private Integer accountId;

    /**
     * <pre>
     * 订单总金额
     * 表字段 : V_ORDER.TOTAL_MONEY
     * </pre>
     * 
     */
    private BigDecimal totalMoney;

    /**
     * <pre>
     * 运费
     * 表字段 : V_ORDER.DELIVER_MONEY
     * </pre>
     * 
     */
    private BigDecimal deliverMoney;

    /**
     * <pre>
     * 优惠券总金额
     * 表字段 : V_ORDER.COUPON_MONEY
     * </pre>
     * 
     */
    private BigDecimal couponMoney;

    /**
     * <pre>
     * 订单实际金额
     * 表字段 : V_ORDER.REAL_TOTAL_MONEY
     * </pre>
     * 
     */
    private BigDecimal realTotalMoney;

    /**
     * <pre>
     * 市场价
     * 表字段 : V_ORDER.MARKET_MOMNEY
     * </pre>
     * 
     */
    private BigDecimal marketMomney;

    /**
     * <pre>
     * 销售价
     * 表字段 : V_ORDER.SALES_MONEY
     * </pre>
     * 
     */
    private BigDecimal salesMoney;

    /**
     * <pre>
     * 是否支付：0未支付、1部分支付、2已支付
     * 表字段 : V_ORDER.IS_PAY
     * </pre>
     * 
     */
    private Boolean isPay;

    /**
     * <pre>
     * 审核时间
     * 表字段 : V_ORDER.PAY_TIME
     * </pre>
     * 
     */
    private Date payTime;

    /**
     * <pre>
     * 支付方式：0线上、1线下
     * 表字段 : V_ORDER.PAYMENT_MODE
     * </pre>
     * 
     */
    private Boolean paymentMode;

    /**
     * <pre>
     * 支付方式：1支付宝、2微信、3银行
     * 表字段 : V_ORDER.PAY_TYPE
     * </pre>
     * 
     */
    private Boolean payType;

    /**
     * <pre>
     * 在线交易流水号
     * 表字段 : V_ORDER.TRADE_NO
     * </pre>
     * 
     */
    private String tradeNo;

    /**
     * <pre>
     * 订单有效标志：0有效、1删除
     * 表字段 : V_ORDER.ORDER_FLAG
     * </pre>
     * 
     */
    private Boolean orderFlag;

    /**
     * <pre>
     * 订单备注
     * 表字段 : V_ORDER.ORDER_REMARK
     * </pre>
     * 
     */
    private String orderRemark;

    /**
     * <pre>
     * 收货地址ID
     * 表字段 : V_ORDER.DELIVERY_ADDRESS_ID
     * </pre>
     * 
     */
    private Integer deliveryAddressId;

    /**
     * <pre>
     * 收货人名称
     * 表字段 : V_ORDER.DELIVERY_USER_NAME
     * </pre>
     * 
     */
    private String deliveryUserName;

    /**
     * <pre>
     * 收货地址：省 市 区
     * 表字段 : V_ORDER.DELIVERY_USER_AREA
     * </pre>
     * 
     */
    private String deliveryUserArea;

    /**
     * <pre>
     * 收件人地址
     * 表字段 : V_ORDER.DELIVERY_USER_ADDRESS
     * </pre>
     * 
     */
    private String deliveryUserAddress;

    /**
     * <pre>
     * 收件人电话
     * 表字段 : V_ORDER.DELIVERY_USER_TEL
     * </pre>
     * 
     */
    private String deliveryUserTel;

    /**
     * <pre>
     * 收件人手机
     * 表字段 : V_ORDER.DELIVERY_USER_PHONE
     * </pre>
     * 
     */
    private String deliveryUserPhone;

    /**
     * <pre>
     * 物流状态：0未发货、1部分发货、2全部发货
     * 表字段 : V_ORDER.DELIVERY_STATUS
     * </pre>
     * 
     */
    private Boolean deliveryStatus;

    /**
     * <pre>
     * 发货时间（首次）
     * 表字段 : V_ORDER.FIRST_DELIVERY_TIME
     * </pre>
     * 
     */
    private Date firstDeliveryTime;

    /**
     * <pre>
     * 最后一次发货时间
     * 表字段 : V_ORDER.LAST_DELIVERY_TIME
     * </pre>
     * 
     */
    private Date lastDeliveryTime;

    /**
     * <pre>
     * 收货状态：0未收货、1部分收货、2全部收货
     * 表字段 : V_ORDER.ARRIVAL_STATUS
     * </pre>
     * 
     */
    private Boolean arrivalStatus;

    /**
     * <pre>
     * 收货时间（首次）
     * 表字段 : V_ORDER.FIRST_ARRIVAL_TIME
     * </pre>
     * 
     */
    private Date firstArrivalTime;

    /**
     * <pre>
     * 最后一次收货时间
     * 表字段 : V_ORDER.LAST_ARRIVAL_TIME
     * </pre>
     * 
     */
    private Date lastArrivalTime;

    /**
     * <pre>
     * 附加条款
     * 表字段 : V_ORDER.ADDITIONAL_CLAUSE
     * </pre>
     * 
     */
    private String additionalClause;

    /**
     * <pre>
     * 是否需要发票：0不需要、1需要
     * 表字段 : V_ORDER.IS_INVOICE
     * </pre>
     * 
     */
    private Boolean isInvoice;

    /**
     * <pre>
     * 是否寄送发票  0否 1是
     * 表字段 : V_ORDER.IS_SEND_INVOICE
     * </pre>
     * 
     */
    private Integer isSendInvoice;

    /**
     * <pre>
     * 开票方式：1纸质发票、2电子发票
     * 表字段 : V_ORDER.OPEN_INVOICE_MODE
     * </pre>
     * 
     */
    private Boolean openInvoiceMode;

    /**
     * <pre>
     * 1、增值税发票  2、普通发票等（字典库33）
     * 表字段 : V_ORDER.INVOICE_TYPE
     * </pre>
     * 
     */
    private Integer invoiceType;

    /**
     * <pre>
     * 发票抬头
     * 表字段 : V_ORDER.INVOICE_CLIENT
     * </pre>
     * 
     */
    private String invoiceClient;

    /**
     * <pre>
     * 纳税人识别号
     * 表字段 : V_ORDER.TAX_NUM
     * </pre>
     * 
     */
    private String taxNum;

    /**
     * <pre>
     * 收票地址ID
     * 表字段 : V_ORDER.INVOICE_ADDRESS_ID
     * </pre>
     * 
     */
    private Integer invoiceAddressId;

    /**
     * <pre>
     * 收票人id
     * 表字段 : V_ORDER.INVOICE_USER_ID
     * </pre>
     * 
     */
    private Integer invoiceUserId;

    /**
     * <pre>
     * 收票人名称
     * 表字段 : V_ORDER.INVOICE_USER_NAME
     * </pre>
     * 
     */
    private String invoiceUserName;

    /**
     * <pre>
     * 收票地址：省 市 区
     * 表字段 : V_ORDER.INVOICE_USER_AREA
     * </pre>
     * 
     */
    private String invoiceUserArea;

    /**
     * <pre>
     * 收票人地址
     * 表字段 : V_ORDER.INVOICE_USER_ADDRESS
     * </pre>
     * 
     */
    private String invoiceUserAddress;

    /**
     * <pre>
     * 收票人电话
     * 表字段 : V_ORDER.INVOICE_USER_TEL
     * </pre>
     * 
     */
    private String invoiceUserTel;

    /**
     * <pre>
     * 收票人手机
     * 表字段 : V_ORDER.INVOICE_USER_PHONE
     * </pre>
     * 
     */
    private String invoiceUserPhone;

    /**
     * <pre>
     * 收票邮箱（电子发票）
     * 表字段 : V_ORDER.INVOICE_USER_MAILBOX
     * </pre>
     * 
     */
    private String invoiceUserMailbox;

    /**
     * <pre>
     * 商品种类数量
     * 表字段 : V_ORDER.PRODUCT_TYPE_NUM
     * </pre>
     * 
     */
    private Integer productTypeNum;

    /**
     * <pre>
     * 商品数量
     * 表字段 : V_ORDER.PRODUCT_NUM
     * </pre>
     * 
     */
    private Integer productNum;

    /**
     * <pre>
     * 取消原来订单状态
     * 表字段 : V_ORDER.CANCEL_FORM_STATUS
     * </pre>
     * 
     */
    private Integer cancelFormStatus;

    /**
     * <pre>
     * 取消原因
     * 表字段 : V_ORDER.CANCEL_REASON
     * </pre>
     * 
     */
    private String cancelReason;

    /**
     * <pre>
     * 订单推送ERP：0未推送、1已推送
     * 表字段 : V_ORDER.IS_PUSH
     * </pre>
     * 
     */
    private Boolean isPush;

    /**
     * <pre>
     * 是否有进行中的售后（退货&退款）：0否、1是
     * 表字段 : V_ORDER.IS_REFUND
     * </pre>
     * 
     */
    private Boolean isRefund;

    /**
     * <pre>
     * 售后总金额（退换货&退款）
     * 表字段 : V_ORDER.BACK_MONEY
     * </pre>
     * 
     */
    private BigDecimal backMoney;

    /**
     * <pre>
     * 创建时间
     * 表字段 : V_ORDER.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 创建人
     * 表字段 : V_ORDER.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 最近一次更新时间
     * 表字段 : V_ORDER.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 更新人
     * 表字段 : V_ORDER.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 是否删除 0、未删除 1、已删除
     * 表字段 : V_ORDER.IS_DEL
     * </pre>
     * 
     */
    private Integer isDel;

    private List<VOrderGoods> goodsList;

    /**
     * 订单号
     * @return ORDER_NO 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 订单号
     * @param orderNo 订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * 微信端订单号
     * @return ORDER_NO_WX 微信端订单号
     */
    public String getOrderNoWx() {
        return orderNoWx;
    }

    /**
     * 微信端订单号
     * @param orderNoWx 微信端订单号
     */
    public void setOrderNoWx(String orderNoWx) {
        this.orderNoWx = orderNoWx == null ? null : orderNoWx.trim();
    }

    /**
     * 公司ID
     * @return COMPANY_ID 公司ID
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 公司ID
     * @param companyId 公司ID
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 公司名称
     * @return COMPANY_NAME 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 公司名称
     * @param companyName 公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * 订单来源：1PC、2微信、3APP
     * @return ORDER_SRC 订单来源：1PC、2微信、3APP
     */
    public Integer getOrderSrc() {
        return orderSrc;
    }

    /**
     * 订单来源：1PC、2微信、3APP
     * @param orderSrc 订单来源：1PC、2微信、3APP
     */
    public void setOrderSrc(Integer orderSrc) {
        this.orderSrc = orderSrc;
    }

    /**
     * 订单状态：0审核中(已提交)、1待确认(审核通过)、2待付款(已确认)、3待发货(已付款)、4待收货(已发货)、5、已完成 6、已取消
     * @return ORDER_STATUS 订单状态：0审核中(已提交)、1待确认(审核通过)、2待付款(已确认)、3待发货(已付款)、4待收货(已发货)、5、已完成 6、已取消
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 订单状态：0审核中(已提交)、1待确认(审核通过)、2待付款(已确认)、3待发货(已付款)、4待收货(已发货)、5、已完成 6、已取消
     * @param orderStatus 订单状态：0审核中(已提交)、1待确认(审核通过)、2待付款(已确认)、3待发货(已付款)、4待收货(已发货)、5、已完成 6、已取消
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 创建订单人员ID
     * @return ACCOUNT_ID 创建订单人员ID
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 创建订单人员ID
     * @param accountId 创建订单人员ID
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 订单总金额
     * @return TOTAL_MONEY 订单总金额
     */
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    /**
     * 订单总金额
     * @param totalMoney 订单总金额
     */
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    /**
     * 运费
     * @return DELIVER_MONEY 运费
     */
    public BigDecimal getDeliverMoney() {
        return deliverMoney;
    }

    /**
     * 运费
     * @param deliverMoney 运费
     */
    public void setDeliverMoney(BigDecimal deliverMoney) {
        this.deliverMoney = deliverMoney;
    }

    /**
     * 优惠券总金额
     * @return COUPON_MONEY 优惠券总金额
     */
    public BigDecimal getCouponMoney() {
        return couponMoney;
    }

    /**
     * 优惠券总金额
     * @param couponMoney 优惠券总金额
     */
    public void setCouponMoney(BigDecimal couponMoney) {
        this.couponMoney = couponMoney;
    }

    /**
     * 订单实际金额
     * @return REAL_TOTAL_MONEY 订单实际金额
     */
    public BigDecimal getRealTotalMoney() {
        return realTotalMoney;
    }

    /**
     * 订单实际金额
     * @param realTotalMoney 订单实际金额
     */
    public void setRealTotalMoney(BigDecimal realTotalMoney) {
        this.realTotalMoney = realTotalMoney;
    }

    /**
     * 市场价
     * @return MARKET_MOMNEY 市场价
     */
    public BigDecimal getMarketMomney() {
        return marketMomney;
    }

    /**
     * 市场价
     * @param marketMomney 市场价
     */
    public void setMarketMomney(BigDecimal marketMomney) {
        this.marketMomney = marketMomney;
    }

    /**
     * 销售价
     * @return SALES_MONEY 销售价
     */
    public BigDecimal getSalesMoney() {
        return salesMoney;
    }

    /**
     * 销售价
     * @param salesMoney 销售价
     */
    public void setSalesMoney(BigDecimal salesMoney) {
        this.salesMoney = salesMoney;
    }

    /**
     * 是否支付：0未支付、1部分支付、2已支付
     * @return IS_PAY 是否支付：0未支付、1部分支付、2已支付
     */
    public Boolean getIsPay() {
        return isPay;
    }

    /**
     * 是否支付：0未支付、1部分支付、2已支付
     * @param isPay 是否支付：0未支付、1部分支付、2已支付
     */
    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }

    /**
     * 审核时间
     * @return PAY_TIME 审核时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 审核时间
     * @param payTime 审核时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 支付方式：0线上、1线下
     * @return PAYMENT_MODE 支付方式：0线上、1线下
     */
    public Boolean getPaymentMode() {
        return paymentMode;
    }

    /**
     * 支付方式：0线上、1线下
     * @param paymentMode 支付方式：0线上、1线下
     */
    public void setPaymentMode(Boolean paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     * 支付方式：1支付宝、2微信、3银行
     * @return PAY_TYPE 支付方式：1支付宝、2微信、3银行
     */
    public Boolean getPayType() {
        return payType;
    }

    /**
     * 支付方式：1支付宝、2微信、3银行
     * @param payType 支付方式：1支付宝、2微信、3银行
     */
    public void setPayType(Boolean payType) {
        this.payType = payType;
    }

    /**
     * 在线交易流水号
     * @return TRADE_NO 在线交易流水号
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * 在线交易流水号
     * @param tradeNo 在线交易流水号
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    /**
     * 订单有效标志：0有效、1删除
     * @return ORDER_FLAG 订单有效标志：0有效、1删除
     */
    public Boolean getOrderFlag() {
        return orderFlag;
    }

    /**
     * 订单有效标志：0有效、1删除
     * @param orderFlag 订单有效标志：0有效、1删除
     */
    public void setOrderFlag(Boolean orderFlag) {
        this.orderFlag = orderFlag;
    }

    /**
     * 订单备注
     * @return ORDER_REMARK 订单备注
     */
    public String getOrderRemark() {
        return orderRemark;
    }

    /**
     * 订单备注
     * @param orderRemark 订单备注
     */
    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark == null ? null : orderRemark.trim();
    }

    /**
     * 收货地址ID
     * @return DELIVERY_ADDRESS_ID 收货地址ID
     */
    public Integer getDeliveryAddressId() {
        return deliveryAddressId;
    }

    /**
     * 收货地址ID
     * @param deliveryAddressId 收货地址ID
     */
    public void setDeliveryAddressId(Integer deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    /**
     * 收货人名称
     * @return DELIVERY_USER_NAME 收货人名称
     */
    public String getDeliveryUserName() {
        return deliveryUserName;
    }

    /**
     * 收货人名称
     * @param deliveryUserName 收货人名称
     */
    public void setDeliveryUserName(String deliveryUserName) {
        this.deliveryUserName = deliveryUserName == null ? null : deliveryUserName.trim();
    }

    /**
     * 收货地址：省 市 区
     * @return DELIVERY_USER_AREA 收货地址：省 市 区
     */
    public String getDeliveryUserArea() {
        return deliveryUserArea;
    }

    /**
     * 收货地址：省 市 区
     * @param deliveryUserArea 收货地址：省 市 区
     */
    public void setDeliveryUserArea(String deliveryUserArea) {
        this.deliveryUserArea = deliveryUserArea == null ? null : deliveryUserArea.trim();
    }

    /**
     * 收件人地址
     * @return DELIVERY_USER_ADDRESS 收件人地址
     */
    public String getDeliveryUserAddress() {
        return deliveryUserAddress;
    }

    /**
     * 收件人地址
     * @param deliveryUserAddress 收件人地址
     */
    public void setDeliveryUserAddress(String deliveryUserAddress) {
        this.deliveryUserAddress = deliveryUserAddress == null ? null : deliveryUserAddress.trim();
    }

    /**
     * 收件人电话
     * @return DELIVERY_USER_TEL 收件人电话
     */
    public String getDeliveryUserTel() {
        return deliveryUserTel;
    }

    /**
     * 收件人电话
     * @param deliveryUserTel 收件人电话
     */
    public void setDeliveryUserTel(String deliveryUserTel) {
        this.deliveryUserTel = deliveryUserTel == null ? null : deliveryUserTel.trim();
    }

    /**
     * 收件人手机
     * @return DELIVERY_USER_PHONE 收件人手机
     */
    public String getDeliveryUserPhone() {
        return deliveryUserPhone;
    }

    /**
     * 收件人手机
     * @param deliveryUserPhone 收件人手机
     */
    public void setDeliveryUserPhone(String deliveryUserPhone) {
        this.deliveryUserPhone = deliveryUserPhone == null ? null : deliveryUserPhone.trim();
    }

    /**
     * 物流状态：0未发货、1部分发货、2全部发货
     * @return DELIVERY_STATUS 物流状态：0未发货、1部分发货、2全部发货
     */
    public Boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    /**
     * 物流状态：0未发货、1部分发货、2全部发货
     * @param deliveryStatus 物流状态：0未发货、1部分发货、2全部发货
     */
    public void setDeliveryStatus(Boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * 发货时间（首次）
     * @return FIRST_DELIVERY_TIME 发货时间（首次）
     */
    public Date getFirstDeliveryTime() {
        return firstDeliveryTime;
    }

    /**
     * 发货时间（首次）
     * @param firstDeliveryTime 发货时间（首次）
     */
    public void setFirstDeliveryTime(Date firstDeliveryTime) {
        this.firstDeliveryTime = firstDeliveryTime;
    }

    /**
     * 最后一次发货时间
     * @return LAST_DELIVERY_TIME 最后一次发货时间
     */
    public Date getLastDeliveryTime() {
        return lastDeliveryTime;
    }

    /**
     * 最后一次发货时间
     * @param lastDeliveryTime 最后一次发货时间
     */
    public void setLastDeliveryTime(Date lastDeliveryTime) {
        this.lastDeliveryTime = lastDeliveryTime;
    }

    /**
     * 收货状态：0未收货、1部分收货、2全部收货
     * @return ARRIVAL_STATUS 收货状态：0未收货、1部分收货、2全部收货
     */
    public Boolean getArrivalStatus() {
        return arrivalStatus;
    }

    /**
     * 收货状态：0未收货、1部分收货、2全部收货
     * @param arrivalStatus 收货状态：0未收货、1部分收货、2全部收货
     */
    public void setArrivalStatus(Boolean arrivalStatus) {
        this.arrivalStatus = arrivalStatus;
    }

    /**
     * 收货时间（首次）
     * @return FIRST_ARRIVAL_TIME 收货时间（首次）
     */
    public Date getFirstArrivalTime() {
        return firstArrivalTime;
    }

    /**
     * 收货时间（首次）
     * @param firstArrivalTime 收货时间（首次）
     */
    public void setFirstArrivalTime(Date firstArrivalTime) {
        this.firstArrivalTime = firstArrivalTime;
    }

    /**
     * 最后一次收货时间
     * @return LAST_ARRIVAL_TIME 最后一次收货时间
     */
    public Date getLastArrivalTime() {
        return lastArrivalTime;
    }

    /**
     * 最后一次收货时间
     * @param lastArrivalTime 最后一次收货时间
     */
    public void setLastArrivalTime(Date lastArrivalTime) {
        this.lastArrivalTime = lastArrivalTime;
    }

    /**
     * 附加条款
     * @return ADDITIONAL_CLAUSE 附加条款
     */
    public String getAdditionalClause() {
        return additionalClause;
    }

    /**
     * 附加条款
     * @param additionalClause 附加条款
     */
    public void setAdditionalClause(String additionalClause) {
        this.additionalClause = additionalClause == null ? null : additionalClause.trim();
    }

    /**
     * 是否需要发票：0不需要、1需要
     * @return IS_INVOICE 是否需要发票：0不需要、1需要
     */
    public Boolean getIsInvoice() {
        return isInvoice;
    }

    /**
     * 是否需要发票：0不需要、1需要
     * @param isInvoice 是否需要发票：0不需要、1需要
     */
    public void setIsInvoice(Boolean isInvoice) {
        this.isInvoice = isInvoice;
    }

    /**
     * 是否寄送发票  0否 1是
     * @return IS_SEND_INVOICE 是否寄送发票  0否 1是
     */
    public Integer getIsSendInvoice() {
        return isSendInvoice;
    }

    /**
     * 是否寄送发票  0否 1是
     * @param isSendInvoice 是否寄送发票  0否 1是
     */
    public void setIsSendInvoice(Integer isSendInvoice) {
        this.isSendInvoice = isSendInvoice;
    }

    /**
     * 开票方式：1纸质发票、2电子发票
     * @return OPEN_INVOICE_MODE 开票方式：1纸质发票、2电子发票
     */
    public Boolean getOpenInvoiceMode() {
        return openInvoiceMode;
    }

    /**
     * 开票方式：1纸质发票、2电子发票
     * @param openInvoiceMode 开票方式：1纸质发票、2电子发票
     */
    public void setOpenInvoiceMode(Boolean openInvoiceMode) {
        this.openInvoiceMode = openInvoiceMode;
    }

    /**
     * 1、增值税发票  2、普通发票等（字典库33）
     * @return INVOICE_TYPE 1、增值税发票  2、普通发票等（字典库33）
     */
    public Integer getInvoiceType() {
        return invoiceType;
    }

    /**
     * 1、增值税发票  2、普通发票等（字典库33）
     * @param invoiceType 1、增值税发票  2、普通发票等（字典库33）
     */
    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 发票抬头
     * @return INVOICE_CLIENT 发票抬头
     */
    public String getInvoiceClient() {
        return invoiceClient;
    }

    /**
     * 发票抬头
     * @param invoiceClient 发票抬头
     */
    public void setInvoiceClient(String invoiceClient) {
        this.invoiceClient = invoiceClient == null ? null : invoiceClient.trim();
    }

    /**
     * 纳税人识别号
     * @return TAX_NUM 纳税人识别号
     */
    public String getTaxNum() {
        return taxNum;
    }

    /**
     * 纳税人识别号
     * @param taxNum 纳税人识别号
     */
    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum == null ? null : taxNum.trim();
    }

    /**
     * 收票地址ID
     * @return INVOICE_ADDRESS_ID 收票地址ID
     */
    public Integer getInvoiceAddressId() {
        return invoiceAddressId;
    }

    /**
     * 收票地址ID
     * @param invoiceAddressId 收票地址ID
     */
    public void setInvoiceAddressId(Integer invoiceAddressId) {
        this.invoiceAddressId = invoiceAddressId;
    }

    /**
     * 收票人id
     * @return INVOICE_USER_ID 收票人id
     */
    public Integer getInvoiceUserId() {
        return invoiceUserId;
    }

    /**
     * 收票人id
     * @param invoiceUserId 收票人id
     */
    public void setInvoiceUserId(Integer invoiceUserId) {
        this.invoiceUserId = invoiceUserId;
    }

    /**
     * 收票人名称
     * @return INVOICE_USER_NAME 收票人名称
     */
    public String getInvoiceUserName() {
        return invoiceUserName;
    }

    /**
     * 收票人名称
     * @param invoiceUserName 收票人名称
     */
    public void setInvoiceUserName(String invoiceUserName) {
        this.invoiceUserName = invoiceUserName == null ? null : invoiceUserName.trim();
    }

    /**
     * 收票地址：省 市 区
     * @return INVOICE_USER_AREA 收票地址：省 市 区
     */
    public String getInvoiceUserArea() {
        return invoiceUserArea;
    }

    /**
     * 收票地址：省 市 区
     * @param invoiceUserArea 收票地址：省 市 区
     */
    public void setInvoiceUserArea(String invoiceUserArea) {
        this.invoiceUserArea = invoiceUserArea == null ? null : invoiceUserArea.trim();
    }

    /**
     * 收票人地址
     * @return INVOICE_USER_ADDRESS 收票人地址
     */
    public String getInvoiceUserAddress() {
        return invoiceUserAddress;
    }

    /**
     * 收票人地址
     * @param invoiceUserAddress 收票人地址
     */
    public void setInvoiceUserAddress(String invoiceUserAddress) {
        this.invoiceUserAddress = invoiceUserAddress == null ? null : invoiceUserAddress.trim();
    }

    /**
     * 收票人电话
     * @return INVOICE_USER_TEL 收票人电话
     */
    public String getInvoiceUserTel() {
        return invoiceUserTel;
    }

    /**
     * 收票人电话
     * @param invoiceUserTel 收票人电话
     */
    public void setInvoiceUserTel(String invoiceUserTel) {
        this.invoiceUserTel = invoiceUserTel == null ? null : invoiceUserTel.trim();
    }

    /**
     * 收票人手机
     * @return INVOICE_USER_PHONE 收票人手机
     */
    public String getInvoiceUserPhone() {
        return invoiceUserPhone;
    }

    /**
     * 收票人手机
     * @param invoiceUserPhone 收票人手机
     */
    public void setInvoiceUserPhone(String invoiceUserPhone) {
        this.invoiceUserPhone = invoiceUserPhone == null ? null : invoiceUserPhone.trim();
    }

    /**
     * 收票邮箱（电子发票）
     * @return INVOICE_USER_MAILBOX 收票邮箱（电子发票）
     */
    public String getInvoiceUserMailbox() {
        return invoiceUserMailbox;
    }

    /**
     * 收票邮箱（电子发票）
     * @param invoiceUserMailbox 收票邮箱（电子发票）
     */
    public void setInvoiceUserMailbox(String invoiceUserMailbox) {
        this.invoiceUserMailbox = invoiceUserMailbox == null ? null : invoiceUserMailbox.trim();
    }

    /**
     * 商品种类数量
     * @return PRODUCT_TYPE_NUM 商品种类数量
     */
    public Integer getProductTypeNum() {
        return productTypeNum;
    }

    /**
     * 商品种类数量
     * @param productTypeNum 商品种类数量
     */
    public void setProductTypeNum(Integer productTypeNum) {
        this.productTypeNum = productTypeNum;
    }

    /**
     * 商品数量
     * @return PRODUCT_NUM 商品数量
     */
    public Integer getProductNum() {
        return productNum;
    }

    /**
     * 商品数量
     * @param productNum 商品数量
     */
    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    /**
     * 取消原来订单状态
     * @return CANCEL_FORM_STATUS 取消原来订单状态
     */
    public Integer getCancelFormStatus() {
        return cancelFormStatus;
    }

    /**
     * 取消原来订单状态
     * @param cancelFormStatus 取消原来订单状态
     */
    public void setCancelFormStatus(Integer cancelFormStatus) {
        this.cancelFormStatus = cancelFormStatus;
    }

    /**
     * 取消原因
     * @return CANCEL_REASON 取消原因
     */
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     * 取消原因
     * @param cancelReason 取消原因
     */
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason == null ? null : cancelReason.trim();
    }

    /**
     * 订单推送ERP：0未推送、1已推送
     * @return IS_PUSH 订单推送ERP：0未推送、1已推送
     */
    public Boolean getIsPush() {
        return isPush;
    }

    /**
     * 订单推送ERP：0未推送、1已推送
     * @param isPush 订单推送ERP：0未推送、1已推送
     */
    public void setIsPush(Boolean isPush) {
        this.isPush = isPush;
    }

    /**
     * 是否有进行中的售后（退货&退款）：0否、1是
     * @return IS_REFUND 是否有进行中的售后（退货&退款）：0否、1是
     */
    public Boolean getIsRefund() {
        return isRefund;
    }

    /**
     * 是否有进行中的售后（退货&退款）：0否、1是
     * @param isRefund 是否有进行中的售后（退货&退款）：0否、1是
     */
    public void setIsRefund(Boolean isRefund) {
        this.isRefund = isRefund;
    }

    /**
     * 售后总金额（退换货&退款）
     * @return BACK_MONEY 售后总金额（退换货&退款）
     */
    public BigDecimal getBackMoney() {
        return backMoney;
    }

    /**
     * 售后总金额（退换货&退款）
     * @param backMoney 售后总金额（退换货&退款）
     */
    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }

    /**
     * 创建时间
     * @return ADD_TIME 创建时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 创建时间
     * @param addTime 创建时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 创建人
     * @return CREATOR 创建人
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 创建人
     * @param creator 创建人
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 最近一次更新时间
     * @return MOD_TIME 最近一次更新时间
     */
    public Date getModTime() {
        return modTime;
    }

    /**
     * 最近一次更新时间
     * @param modTime 最近一次更新时间
     */
    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    /**
     * 更新人
     * @return UPDATER 更新人
     */
    public Integer getUpdater() {
        return updater;
    }

    /**
     * 更新人
     * @param updater 更新人
     */
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    /**
     * 是否删除 0、未删除 1、已删除
     * @return IS_DEL 是否删除 0、未删除 1、已删除
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 是否删除 0、未删除 1、已删除
     * @param isDel 是否删除 0、未删除 1、已删除
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public List<VOrderGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<VOrderGoods> goodsList) {
        this.goodsList = goodsList;
    }

}