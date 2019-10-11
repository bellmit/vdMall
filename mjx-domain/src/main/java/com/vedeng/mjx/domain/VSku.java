package com.vedeng.mjx.domain;

import java.util.Date;

/**
 * V_SKU
 */
public class VSku extends VSkuKey {
    /**
     * <pre>
     * 订货号
     * 表字段 : V_SKU.SKU_NO
     * </pre>
     * 
     */
    private String skuNo;

    /**
     * <pre>
     * 
     * 表字段 : V_SKU.SPU_ID
     * </pre>
     * 
     */
    private Long spuId;

    /**
     * <pre>
     * erp商品名称
     * 表字段 : V_SKU.SKU_NAME
     * </pre>
     * 
     */
    private String skuName;

    /**
     * <pre>
     * 展示名称
     * 表字段 : V_SKU.SHOW_NAME
     * </pre>
     * 
     */
    private String showName;

    /**
     * <pre>
     * 审核状态 1待完善 2返回修改 3审核通过 4删除
     * 表字段 : V_SKU.CHECK_STATUS
     * </pre>
     * 
     */
    private Integer checkStatus;

    /**
     * <pre>
     * 制造商型号
     * 表字段 : V_SKU.MODEL
     * </pre>
     * 
     */
    private String model;

    /**
     * <pre>
     * 规格
     * 表字段 : V_SKU.SPEC
     * </pre>
     * 
     */
    private String spec;

    /**
     * <pre>
     * 1启用 0禁用
     * 表字段 : V_SKU.STATUS
     * </pre>
     * 
     */
    private Integer status;

    /**
     * <pre>
     * 是否新品
     * 表字段 : V_SKU.IS_NEW
     * </pre>
     * 
     */
    private Integer isNew;

    /**
     * <pre>
     * 是否热销
     * 表字段 : V_SKU.IS_HOT
     * </pre>
     * 
     */
    private Integer isHot;

    /**
     * <pre>
     * 0：否；1:是（七天无理由）
     * 表字段 : V_SKU.IS_SEVEN
     * </pre>
     * 
     */
    private Integer isSeven;

    /**
     * <pre>
     * 是否上下架 1:是，0否
     * 表字段 : V_SKU.IS_ON_SALE
     * </pre>
     * 
     */
    private Integer isOnSale;

    /**
     * <pre>
     * 是否显示攻略：0不显示，1显示
     * 表字段 : V_SKU.IS_OP_SHOW
     * </pre>
     * 
     */
    private Boolean isOpShow;

    /**
     * <pre>
     * 适用机构
     * 表字段 : V_SKU.OP_INSTITUTION
     * </pre>
     * 
     */
    private String opInstitution;

    /**
     * <pre>
     * 卖点
     * 表字段 : V_SKU.OP_SALESPOINT
     * </pre>
     * 
     */
    private String opSalespoint;

    /**
     * <pre>
     * SEO描述
     * 表字段 : V_SKU.SEO_DESCRIPT
     * </pre>
     * 
     */
    private String seoDescript;

    /**
     * <pre>
     * SEO关键词
     * 表字段 : V_SKU.SEO_KEYWORDS
     * </pre>
     * 
     */
    private String seoKeywords;

    /**
     * <pre>
     * SEO标题
     * 表字段 : V_SKU.SEO_TITLE
     * </pre>
     * 
     */
    private String seoTitle;

    /**
     * <pre>
     * 创建人
     * 表字段 : V_SKU.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 创建时间
     * 表字段 : V_SKU.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 更新人
     * 表字段 : V_SKU.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 修改时间
     * 表字段 : V_SKU.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 删除标记（0否1是）
     * 表字段 : V_SKU.IS_DEL
     * </pre>
     * 
     */
    private Integer isDel;

    /**
     * 订货号
     * @return SKU_NO 订货号
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 订货号
     * @param skuNo 订货号
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo == null ? null : skuNo.trim();
    }

    /**
     * 
     * @return SPU_ID 
     */
    public Long getSpuId() {
        return spuId;
    }

    /**
     * 
     * @param spuId 
     */
    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    /**
     * erp商品名称
     * @return SKU_NAME erp商品名称
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * erp商品名称
     * @param skuName erp商品名称
     */
    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    /**
     * 展示名称
     * @return SHOW_NAME 展示名称
     */
    public String getShowName() {
        return showName;
    }

    /**
     * 展示名称
     * @param showName 展示名称
     */
    public void setShowName(String showName) {
        this.showName = showName == null ? null : showName.trim();
    }

    /**
     * 审核状态 1待完善 2返回修改 3审核通过 4删除
     * @return CHECK_STATUS 审核状态 1待完善 2返回修改 3审核通过 4删除
     */
    public Integer getCheckStatus() {
        return checkStatus;
    }

    /**
     * 审核状态 1待完善 2返回修改 3审核通过 4删除
     * @param checkStatus 审核状态 1待完善 2返回修改 3审核通过 4删除
     */
    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    /**
     * 制造商型号
     * @return MODEL 制造商型号
     */
    public String getModel() {
        return model;
    }

    /**
     * 制造商型号
     * @param model 制造商型号
     */
    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    /**
     * 规格
     * @return SPEC 规格
     */
    public String getSpec() {
        return spec;
    }

    /**
     * 规格
     * @param spec 规格
     */
    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    /**
     * 1启用 0禁用
     * @return STATUS 1启用 0禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1启用 0禁用
     * @param status 1启用 0禁用
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
     * 适用机构
     * @return OP_INSTITUTION 适用机构
     */
    public String getOpInstitution() {
        return opInstitution;
    }

    /**
     * 适用机构
     * @param opInstitution 适用机构
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
     * SEO描述
     * @return SEO_DESCRIPT SEO描述
     */
    public String getSeoDescript() {
        return seoDescript;
    }

    /**
     * SEO描述
     * @param seoDescript SEO描述
     */
    public void setSeoDescript(String seoDescript) {
        this.seoDescript = seoDescript == null ? null : seoDescript.trim();
    }

    /**
     * SEO关键词
     * @return SEO_KEYWORDS SEO关键词
     */
    public String getSeoKeywords() {
        return seoKeywords;
    }

    /**
     * SEO关键词
     * @param seoKeywords SEO关键词
     */
    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords == null ? null : seoKeywords.trim();
    }

    /**
     * SEO标题
     * @return SEO_TITLE SEO标题
     */
    public String getSeoTitle() {
        return seoTitle;
    }

    /**
     * SEO标题
     * @param seoTitle SEO标题
     */
    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle == null ? null : seoTitle.trim();
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