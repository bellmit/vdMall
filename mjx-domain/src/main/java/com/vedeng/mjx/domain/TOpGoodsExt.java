package com.vedeng.mjx.domain;

import java.util.Date;

/**
 * T_OP_GOODS_EXT
 */
public class TOpGoodsExt extends TOpGoodsExtKey {
    /**
     * <pre>
     * 订货号
     * 表字段 : T_OP_GOODS_EXT.SPU_NO
     * </pre>
     * 
     */
    private String spuNo;

    /**
     * <pre>
     * erp商品名称
     * 表字段 : T_OP_GOODS_EXT.SPU_NAME
     * </pre>
     * 
     */
    private String spuName;

    /**
     * <pre>
     * 是否显示攻略：0不显示，1显示
     * 表字段 : T_OP_GOODS_EXT.IS_OP_SHOW
     * </pre>
     * 
     */
    private Boolean isOpShow;

    /**
     * <pre>
     * 所属机构
     * 表字段 : T_OP_GOODS_EXT.OP_INSTITUTION
     * </pre>
     * 
     */
    private String opInstitution;

    /**
     * <pre>
     * 卖点
     * 表字段 : T_OP_GOODS_EXT.OP_SALESPOINT
     * </pre>
     * 
     */
    private String opSalespoint;

    /**
     * <pre>
     * 
     * 表字段 : T_OP_GOODS_EXT.BRAND_ID
     * </pre>
     * 
     */
    private Integer brandId;

    /**
     * <pre>
     * 
     * 表字段 : T_OP_GOODS_EXT.CATEGORY_ID
     * </pre>
     * 
     */
    private Integer categoryId;

    /**
     * <pre>
     * 状态
     * 表字段 : T_OP_GOODS_EXT.STATUS
     * </pre>
     * 
     */
    private Integer status;

    /**
     * <pre>
     * 是否新品
     * 表字段 : T_OP_GOODS_EXT.IS_NEW
     * </pre>
     * 
     */
    private Integer isNew;

    /**
     * <pre>
     * 是否热销
     * 表字段 : T_OP_GOODS_EXT.IS_HOT
     * </pre>
     * 
     */
    private Integer isHot;

    /**
     * <pre>
     * 0：否；1:是（七天无理由）
     * 表字段 : T_OP_GOODS_EXT.IS_SEVEN
     * </pre>
     * 
     */
    private Integer isSeven;

    /**
     * <pre>
     * 是否上下架 1:是，0否
     * 表字段 : T_OP_GOODS_EXT.IS_ON_SALE
     * </pre>
     * 
     */
    private Integer isOnSale;

    /**
     * <pre>
     * 商品来源
     * 表字段 : T_OP_GOODS_EXT.OP_GOODS_RESOURCE
     * </pre>
     * 
     */
    private String opGoodsResource;

    /**
     * <pre>
     * 创建人
     * 表字段 : T_OP_GOODS_EXT.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 修改时间
     * 表字段 : T_OP_GOODS_EXT.UPDATE_TIME
     * </pre>
     * 
     */
    private Date updateTime;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_OP_GOODS_EXT.CREATE_TIME
     * </pre>
     * 
     */
    private Date createTime;

    /**
     * <pre>
     * 更新人
     * 表字段 : T_OP_GOODS_EXT.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 删除标记（0否1是）
     * 表字段 : T_OP_GOODS_EXT.IS_DEL
     * </pre>
     * 
     */
    private Integer isDel;

    /**
     * 订货号
     * @return SPU_NO 订货号
     */
    public String getSpuNo() {
        return spuNo;
    }

    /**
     * 订货号
     * @param spuNo 订货号
     */
    public void setSpuNo(String spuNo) {
        this.spuNo = spuNo == null ? null : spuNo.trim();
    }

    /**
     * erp商品名称
     * @return SPU_NAME erp商品名称
     */
    public String getSpuName() {
        return spuName;
    }

    /**
     * erp商品名称
     * @param spuName erp商品名称
     */
    public void setSpuName(String spuName) {
        this.spuName = spuName == null ? null : spuName.trim();
    }

    /**
     * 是否显示攻略：0不显示，1显示
     * @return IS_OP_SHOW 是否显示攻略：0不显示，1显示
     */
    public Boolean getIsOpShow() {
        return isOpShow;
    }

    /**
     * 是否显示攻略：0不显示，1显示
     * @param isOpShow 是否显示攻略：0不显示，1显示
     */
    public void setIsOpShow(Boolean isOpShow) {
        this.isOpShow = isOpShow;
    }

    /**
     * 所属机构
     * @return OP_INSTITUTION 所属机构
     */
    public String getOpInstitution() {
        return opInstitution;
    }

    /**
     * 所属机构
     * @param opInstitution 所属机构
     */
    public void setOpInstitution(String opInstitution) {
        this.opInstitution = opInstitution == null ? null : opInstitution.trim();
    }

    /**
     * 卖点
     * @return OP_SALESPOINT 卖点
     */
    public String getOpSalespoint() {
        return opSalespoint;
    }

    /**
     * 卖点
     * @param opSalespoint 卖点
     */
    public void setOpSalespoint(String opSalespoint) {
        this.opSalespoint = opSalespoint == null ? null : opSalespoint.trim();
    }

    /**
     * 
     * @return BRAND_ID 
     */
    public Integer getBrandId() {
        return brandId;
    }

    /**
     * 
     * @param brandId 
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    /**
     * 
     * @return CATEGORY_ID 
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 
     * @param categoryId 
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 状态
     * @return STATUS 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 是否新品
     * @return IS_NEW 是否新品
     */
    public Integer getIsNew() {
        return isNew;
    }

    /**
     * 是否新品
     * @param isNew 是否新品
     */
    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    /**
     * 是否热销
     * @return IS_HOT 是否热销
     */
    public Integer getIsHot() {
        return isHot;
    }

    /**
     * 是否热销
     * @param isHot 是否热销
     */
    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    /**
     * 0：否；1:是（七天无理由）
     * @return IS_SEVEN 0：否；1:是（七天无理由）
     */
    public Integer getIsSeven() {
        return isSeven;
    }

    /**
     * 0：否；1:是（七天无理由）
     * @param isSeven 0：否；1:是（七天无理由）
     */
    public void setIsSeven(Integer isSeven) {
        this.isSeven = isSeven;
    }

    /**
     * 是否上下架 1:是，0否
     * @return IS_ON_SALE 是否上下架 1:是，0否
     */
    public Integer getIsOnSale() {
        return isOnSale;
    }

    /**
     * 是否上下架 1:是，0否
     * @param isOnSale 是否上下架 1:是，0否
     */
    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }

    /**
     * 商品来源
     * @return OP_GOODS_RESOURCE 商品来源
     */
    public String getOpGoodsResource() {
        return opGoodsResource;
    }

    /**
     * 商品来源
     * @param opGoodsResource 商品来源
     */
    public void setOpGoodsResource(String opGoodsResource) {
        this.opGoodsResource = opGoodsResource == null ? null : opGoodsResource.trim();
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
     * 修改时间
     * @return UPDATE_TIME 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 创建时间
     * @return CREATE_TIME 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * 删除标记（0否1是）
     * @return IS_DEL 删除标记（0否1是）
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 删除标记（0否1是）
     * @param isDel 删除标记（0否1是）
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}