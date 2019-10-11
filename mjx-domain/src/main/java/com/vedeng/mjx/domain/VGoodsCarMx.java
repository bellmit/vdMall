package com.vedeng.mjx.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * V_GOODS_CAR_MX
 */
public class VGoodsCarMx extends VGoodsCarMxKey {
    /**
     * <pre>
     * 商品唯一标识
     * 表字段 : V_GOODS_CAR_MX.SKU_NO
     * </pre>
     * 
     */
    private String skuNo;

    /**
     * <pre>
     * 购物车主键
     * 表字段 : V_GOODS_CAR_MX.CAR_ID
     * </pre>
     * 
     */
    private Integer carId;

    /**
     * <pre>
     * 销售价
     * 表字段 : V_GOODS_CAR_MX.SALE_TOTAL
     * </pre>
     * 
     */
    private BigDecimal saleTotal;

    /**
     * <pre>
     * 数量
     * 表字段 : V_GOODS_CAR_MX.COUNT
     * </pre>
     * 
     */
    private Integer count;

    /**
     * <pre>
     * 是否提交（0未提交，1预提交，2已提交）
     * 表字段 : V_GOODS_CAR_MX.IS_COMMIT
     * </pre>
     * 
     */
    private String isCommit;

    /**
     * <pre>
     * 是否默认（0否1是）
     * 表字段 : V_GOODS_CAR_MX.IS_DEFAULT
     * </pre>
     * 
     */
    private String isDefault;

    /**
     * <pre>
     * 创建人ID
     * 表字段 : V_GOODS_CAR_MX.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 创建时间
     * 表字段 : V_GOODS_CAR_MX.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 修改人ID
     * 表字段 : V_GOODS_CAR_MX.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 修改时间
     * 表字段 : V_GOODS_CAR_MX.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 是否删除(0否，1是)
     * 表字段 : V_GOODS_CAR_MX.IS_DEL
     * </pre>
     * 
     */
    private String isDel;

    /**
     * 商品唯一标识
     * @return SKU_NO 商品唯一标识
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 商品唯一标识
     * @param skuNo 商品唯一标识
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo == null ? null : skuNo.trim();
    }

    /**
     * 购物车主键
     * @return CAR_ID 购物车主键
     */
    public Integer getCarId() {
        return carId;
    }

    /**
     * 购物车主键
     * @param carId 购物车主键
     */
    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    /**
     * 销售价
     * @return SALE_TOTAL 销售价
     */
    public BigDecimal getSaleTotal() {
        return saleTotal;
    }

    /**
     * 销售价
     * @param saleTotal 销售价
     */
    public void setSaleTotal(BigDecimal saleTotal) {
        this.saleTotal = saleTotal;
    }

    /**
     * 数量
     * @return COUNT 数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 数量
     * @param count 数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 是否提交（0未提交，1预提交，2已提交）
     * @return IS_COMMIT 是否提交（0未提交，1预提交，2已提交）
     */
    public String getIsCommit() {
        return isCommit;
    }

    /**
     * 是否提交（0未提交，1预提交，2已提交）
     * @param isCommit 是否提交（0未提交，1预提交，2已提交）
     */
    public void setIsCommit(String isCommit) {
        this.isCommit = isCommit == null ? null : isCommit.trim();
    }

    /**
     * 是否默认（0否1是）
     * @return IS_DEFAULT 是否默认（0否1是）
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * 是否默认（0否1是）
     * @param isDefault 是否默认（0否1是）
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    /**
     * 创建人ID
     * @return CREATOR 创建人ID
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 创建人ID
     * @param creator 创建人ID
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 创建时间
     * @return ADD_TIME 创建时间
     */
    public Date getAddTime() {
        return addTime;
    }
    public long getAddLongTime() {
        return addTime.getTime();
    }

    /**
     * 创建时间
     * @param addTime 创建时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 修改人ID
     * @return UPDATER 修改人ID
     */
    public Integer getUpdater() {
        return updater;
    }

    /**
     * 修改人ID
     * @param updater 修改人ID
     */
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    /**
     * 修改时间
     * @return MOD_TIME 修改时间
     */
    public Date getModTime() {
        return modTime;
    }

    /**
     * 修改时间
     * @param modTime 修改时间
     */
    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    /**
     * 是否删除(0否，1是)
     * @return IS_DEL 是否删除(0否，1是)
     */
    public String getIsDel() {
        return isDel;
    }

    /**
     * 是否删除(0否，1是)
     * @param isDel 是否删除(0否，1是)
     */
    public void setIsDel(String isDel) {
        this.isDel = isDel == null ? null : isDel.trim();
    }
}