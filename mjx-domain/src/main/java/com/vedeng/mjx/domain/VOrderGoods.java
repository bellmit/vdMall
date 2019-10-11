package com.vedeng.mjx.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * V_ORDER_GOODS
 */
public class VOrderGoods extends VOrderGoodsKey {
    /**
     * <pre>
     * 订单号
     * 表字段 : V_ORDER_GOODS.ORDER_NO
     * </pre>
     * 
     */
    private String orderNo;

    /**
     * <pre>
     * 商品ID
     * 表字段 : V_ORDER_GOODS.GOODS_ID
     * </pre>
     * 
     */
    private Integer goodsId;

    /**
     * <pre>
     * 商品数量
     * 表字段 : V_ORDER_GOODS.GOODS_NUM
     * </pre>
     * 
     */
    private Integer goodsNum;

    /**
     * <pre>
     * 订单商品单价
     * 表字段 : V_ORDER_GOODS.GOODS_PRICE
     * </pre>
     * 
     */
    private BigDecimal goodsPrice;

    /**
     * <pre>
     * 市场价
     * 表字段 : V_ORDER_GOODS.MARKET_MOMEY
     * </pre>
     * 
     */
    private BigDecimal marketMomey;

    /**
     * <pre>
     * 销售价
     * 表字段 : V_ORDER_GOODS.SALES_MONEY
     * </pre>
     * 
     */
    private BigDecimal salesMoney;

    /**
     * <pre>
     * 商品名称
     * 表字段 : V_ORDER_GOODS.GOODS_NAME
     * </pre>
     * 
     */
    private String goodsName;

    /**
     * <pre>
     * 订货号
     * 表字段 : V_ORDER_GOODS.GOODS_SKU
     * </pre>
     * 
     */
    private String goodsSku;

    /**
     * <pre>
     * 规格/型号 名称
     * 表字段 : V_ORDER_GOODS.GOODS_MODEL
     * </pre>
     * 
     */
    private String goodsModel;

    /**
     * <pre>
     * 规格/型号 属性值
     * 表字段 : V_ORDER_GOODS.SPECS
     * </pre>
     * 
     */
    private String specs;

    /**
     * <pre>
     * 商家备注
     * 表字段 : V_ORDER_GOODS.STORE_REMARK
     * </pre>
     * 
     */
    private String storeRemark;

    /**
     * <pre>
     * 
     * 表字段 : V_ORDER_GOODS.SPU_ID
     * </pre>
     * 
     */
    private Integer spuId;

    /**
     * <pre>
     * 商品品牌名称
     * 表字段 : V_ORDER_GOODS.GOODS_BRAND_NAME
     * </pre>
     * 
     */
    private String goodsBrandName;

    /**
     * <pre>
     * 订单商品优惠后单价
     * 表字段 : V_ORDER_GOODS.COUPON_AMOUNT
     * </pre>
     * 
     */
    private BigDecimal couponAmount;

    /**
     * <pre>
     * 商品缩略图域名
     * 表字段 : V_ORDER_GOODS.GOODS_DOMAIN
     * </pre>
     * 
     */
    private String goodsDomain;

    /**
     * <pre>
     * 缩略图地址
     * 表字段 : V_ORDER_GOODS.GOODS_URI
     * </pre>
     * 
     */
    private String goodsUri;

    /**
     * <pre>
     * 发货数量
     * 表字段 : V_ORDER_GOODS.DELIVERY_NUM
     * </pre>
     * 
     */
    private Integer deliveryNum;

    /**
     * <pre>
     * 发货状态：0待发货、1部分发货、2全部发货
     * 表字段 : V_ORDER_GOODS.DELIVERY_STATUS
     * </pre>
     * 
     */
    private Boolean deliveryStatus;

    /**
     * <pre>
     * 最后一次发货时间
     * 表字段 : V_ORDER_GOODS.DELIVERY_TIME
     * </pre>
     * 
     */
    private Date deliveryTime;

    /**
     * <pre>
     * 收货数量
     * 表字段 : V_ORDER_GOODS.ARRIVAL_NUM
     * </pre>
     * 
     */
    private Integer arrivalNum;

    /**
     * <pre>
     * 收货状态：0待收货、1部分收货、2全部收货
     * 表字段 : V_ORDER_GOODS.ARRIVAL_STATUS
     * </pre>
     * 
     */
    private Boolean arrivalStatus;

    /**
     * <pre>
     * 最后一次收货时间
     * 表字段 : V_ORDER_GOODS.ARRIVAL_TIME
     * </pre>
     * 
     */
    private Date arrivalTime;

    /**
     * <pre>
     * 是否删除：0使用、1删除
     * 表字段 : V_ORDER_GOODS.IS_DEL
     * </pre>
     * 
     */
    private Boolean isDel;

    /**
     * <pre>
     * 创建时间
     * 表字段 : V_ORDER_GOODS.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 创建人
     * 表字段 : V_ORDER_GOODS.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 最近一次更新时间
     * 表字段 : V_ORDER_GOODS.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 更新人
     * 表字段 : V_ORDER_GOODS.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * sku商品实际总额(包含优惠，退货)
     * 表字段 : V_ORDER_GOODS.SKU_AMOUNT
     * </pre>
     * 
     */
    private BigDecimal skuAmount;

    /**
     * <pre>
     * 是否可以退货 0、否 1、是
     * 表字段 : V_ORDER_GOODS.IS_SEVEN
     * </pre>
     * 
     */
    private Integer isSeven;

    /**
     * <pre>
     * 是否可以退货
     * 表字段 : V_ORDER_GOODS.SEVEN
     * </pre>
     * 
     */
    private String seven;

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
     * 商品ID
     * @return GOODS_ID 商品ID
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * 商品ID
     * @param goodsId 商品ID
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 商品数量
     * @return GOODS_NUM 商品数量
     */
    public Integer getGoodsNum() {
        return goodsNum;
    }

    /**
     * 商品数量
     * @param goodsNum 商品数量
     */
    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    /**
     * 订单商品单价
     * @return GOODS_PRICE 订单商品单价
     */
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * 订单商品单价
     * @param goodsPrice 订单商品单价
     */
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * 市场价
     * @return MARKET_MOMEY 市场价
     */
    public BigDecimal getMarketMomey() {
        return marketMomey;
    }

    /**
     * 市场价
     * @param marketMomey 市场价
     */
    public void setMarketMomey(BigDecimal marketMomey) {
        this.marketMomey = marketMomey;
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
     * 商品名称
     * @return GOODS_NAME 商品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 商品名称
     * @param goodsName 商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    /**
     * 订货号
     * @return GOODS_SKU 订货号
     */
    public String getGoodsSku() {
        return goodsSku;
    }

    /**
     * 订货号
     * @param goodsSku 订货号
     */
    public void setGoodsSku(String goodsSku) {
        this.goodsSku = goodsSku == null ? null : goodsSku.trim();
    }

    /**
     * 规格/型号 名称
     * @return GOODS_MODEL 规格/型号 名称
     */
    public String getGoodsModel() {
        return goodsModel;
    }

    /**
     * 规格/型号 名称
     * @param goodsModel 规格/型号 名称
     */
    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel == null ? null : goodsModel.trim();
    }

    /**
     * 规格/型号 属性值
     * @return SPECS 规格/型号 属性值
     */
    public String getSpecs() {
        return specs;
    }

    /**
     * 规格/型号 属性值
     * @param specs 规格/型号 属性值
     */
    public void setSpecs(String specs) {
        this.specs = specs == null ? null : specs.trim();
    }

    /**
     * 商家备注
     * @return STORE_REMARK 商家备注
     */
    public String getStoreRemark() {
        return storeRemark;
    }

    /**
     * 商家备注
     * @param storeRemark 商家备注
     */
    public void setStoreRemark(String storeRemark) {
        this.storeRemark = storeRemark == null ? null : storeRemark.trim();
    }

    /**
     * 
     * @return SPU_ID 
     */
    public Integer getSpuId() {
        return spuId;
    }

    /**
     * 
     * @param spuId 
     */
    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    /**
     * 商品品牌名称
     * @return GOODS_BRAND_NAME 商品品牌名称
     */
    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    /**
     * 商品品牌名称
     * @param goodsBrandName 商品品牌名称
     */
    public void setGoodsBrandName(String goodsBrandName) {
        this.goodsBrandName = goodsBrandName == null ? null : goodsBrandName.trim();
    }

    /**
     * 订单商品优惠后单价
     * @return COUPON_AMOUNT 订单商品优惠后单价
     */
    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    /**
     * 订单商品优惠后单价
     * @param couponAmount 订单商品优惠后单价
     */
    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    /**
     * 商品缩略图域名
     * @return GOODS_DOMAIN 商品缩略图域名
     */
    public String getGoodsDomain() {
        return goodsDomain;
    }

    /**
     * 商品缩略图域名
     * @param goodsDomain 商品缩略图域名
     */
    public void setGoodsDomain(String goodsDomain) {
        this.goodsDomain = goodsDomain == null ? null : goodsDomain.trim();
    }

    /**
     * 缩略图地址
     * @return GOODS_URI 缩略图地址
     */
    public String getGoodsUri() {
        return goodsUri;
    }

    /**
     * 缩略图地址
     * @param goodsUri 缩略图地址
     */
    public void setGoodsUri(String goodsUri) {
        this.goodsUri = goodsUri == null ? null : goodsUri.trim();
    }

    /**
     * 发货数量
     * @return DELIVERY_NUM 发货数量
     */
    public Integer getDeliveryNum() {
        return deliveryNum;
    }

    /**
     * 发货数量
     * @param deliveryNum 发货数量
     */
    public void setDeliveryNum(Integer deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    /**
     * 发货状态：0待发货、1部分发货、2全部发货
     * @return DELIVERY_STATUS 发货状态：0待发货、1部分发货、2全部发货
     */
    public Boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    /**
     * 发货状态：0待发货、1部分发货、2全部发货
     * @param deliveryStatus 发货状态：0待发货、1部分发货、2全部发货
     */
    public void setDeliveryStatus(Boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * 最后一次发货时间
     * @return DELIVERY_TIME 最后一次发货时间
     */
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * 最后一次发货时间
     * @param deliveryTime 最后一次发货时间
     */
    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * 收货数量
     * @return ARRIVAL_NUM 收货数量
     */
    public Integer getArrivalNum() {
        return arrivalNum;
    }

    /**
     * 收货数量
     * @param arrivalNum 收货数量
     */
    public void setArrivalNum(Integer arrivalNum) {
        this.arrivalNum = arrivalNum;
    }

    /**
     * 收货状态：0待收货、1部分收货、2全部收货
     * @return ARRIVAL_STATUS 收货状态：0待收货、1部分收货、2全部收货
     */
    public Boolean getArrivalStatus() {
        return arrivalStatus;
    }

    /**
     * 收货状态：0待收货、1部分收货、2全部收货
     * @param arrivalStatus 收货状态：0待收货、1部分收货、2全部收货
     */
    public void setArrivalStatus(Boolean arrivalStatus) {
        this.arrivalStatus = arrivalStatus;
    }

    /**
     * 最后一次收货时间
     * @return ARRIVAL_TIME 最后一次收货时间
     */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /**
     * 最后一次收货时间
     * @param arrivalTime 最后一次收货时间
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * 是否删除：0使用、1删除
     * @return IS_DEL 是否删除：0使用、1删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 是否删除：0使用、1删除
     * @param isDel 是否删除：0使用、1删除
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
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
     * sku商品实际总额(包含优惠，退货)
     * @return SKU_AMOUNT sku商品实际总额(包含优惠，退货)
     */
    public BigDecimal getSkuAmount() {
        return skuAmount;
    }

    /**
     * sku商品实际总额(包含优惠，退货)
     * @param skuAmount sku商品实际总额(包含优惠，退货)
     */
    public void setSkuAmount(BigDecimal skuAmount) {
        this.skuAmount = skuAmount;
    }

    /**
     * 是否可以退货 0、否 1、是
     * @return IS_SEVEN 是否可以退货 0、否 1、是
     */
    public Integer getIsSeven() {
        return isSeven;
    }

    /**
     * 是否可以退货 0、否 1、是
     * @param isSeven 是否可以退货 0、否 1、是
     */
    public void setIsSeven(Integer isSeven) {
        this.isSeven = isSeven;
    }

    /**
     * 是否可以退货
     * @return SEVEN 是否可以退货
     */
    public String getSeven() {
        return seven;
    }

    /**
     * 是否可以退货
     * @param seven 是否可以退货
     */
    public void setSeven(String seven) {
        this.seven = seven == null ? null : seven.trim();
    }
}