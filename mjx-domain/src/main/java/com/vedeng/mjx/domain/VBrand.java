package com.vedeng.mjx.domain;

import java.util.Date;

/**
 * V_BRAND
 */
public class VBrand extends VBrandKey {
    /**
     * <pre>
     * 品牌名称
     * 表字段 : V_BRAND.BRAND_NAME
     * </pre>
     * 
     */
    private String brandName;

    /**
     * <pre>
     * ERP品牌id(暂定是否使用)
     * 表字段 : V_BRAND.ERP_BRAND_ID
     * </pre>
     * 
     */
    private Integer erpBrandId;

    /**
     * <pre>
     * SEO标题
     * 表字段 : V_BRAND.SEO_TITLE
     * </pre>
     * 
     */
    private String seoTitle;

    /**
     * <pre>
     * SEO关键词
     * 表字段 : V_BRAND.SEO_ANTISTOP
     * </pre>
     * 
     */
    private String seoAntistop;

    /**
     * <pre>
     * 是否推荐（0 否 1 是）
     * 表字段 : V_BRAND.IS_RECOMMEND
     * </pre>
     * 
     */
    private Boolean isRecommend;

    /**
     * <pre>
     * 展示状态（0 展示 1 不展示）
     * 表字段 : V_BRAND.SHOW_STATUS
     * </pre>
     * 
     */
    private Boolean showStatus;

    /**
     * <pre>
     * 平台id(1 贝登 2医械购 )
     * 表字段 : V_BRAND.PLATFROM_ID
     * </pre>
     * 
     */
    private Integer platfromId;

    /**
     * <pre>
     * 排序
     * 表字段 : V_BRAND.SORT
     * </pre>
     * 
     */
    private Integer sort;

    /**
     * <pre>
     * 添加人
     * 表字段 : V_BRAND.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 添加时间
     * 表字段 : V_BRAND.CREATE_TIME
     * </pre>
     * 
     */
    private Date createTime;

    /**
     * <pre>
     * 修改人
     * 表字段 : V_BRAND.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 修改时间
     * 表字段 : V_BRAND.UPDATE_TIME
     * </pre>
     * 
     */
    private Date updateTime;

    /**
     * <pre>
     * 是否删除（0 否 1 是）
     * 表字段 : V_BRAND.IS_DEL
     * </pre>
     * 
     */
    private Boolean isDel;

    /**
     * 品牌名称
     * @return BRAND_NAME 品牌名称
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 品牌名称
     * @param brandName 品牌名称
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    /**
     * ERP品牌id(暂定是否使用)
     * @return ERP_BRAND_ID ERP品牌id(暂定是否使用)
     */
    public Integer getErpBrandId() {
        return erpBrandId;
    }

    /**
     * ERP品牌id(暂定是否使用)
     * @param erpBrandId ERP品牌id(暂定是否使用)
     */
    public void setErpBrandId(Integer erpBrandId) {
        this.erpBrandId = erpBrandId;
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
     * SEO关键词
     * @return SEO_ANTISTOP SEO关键词
     */
    public String getSeoAntistop() {
        return seoAntistop;
    }

    /**
     * SEO关键词
     * @param seoAntistop SEO关键词
     */
    public void setSeoAntistop(String seoAntistop) {
        this.seoAntistop = seoAntistop == null ? null : seoAntistop.trim();
    }

    /**
     * 是否推荐（0 否 1 是）
     * @return IS_RECOMMEND 是否推荐（0 否 1 是）
     */
    public Boolean getIsRecommend() {
        return isRecommend;
    }

    /**
     * 是否推荐（0 否 1 是）
     * @param isRecommend 是否推荐（0 否 1 是）
     */
    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    /**
     * 展示状态（0 展示 1 不展示）
     * @return SHOW_STATUS 展示状态（0 展示 1 不展示）
     */
    public Boolean getShowStatus() {
        return showStatus;
    }

    /**
     * 展示状态（0 展示 1 不展示）
     * @param showStatus 展示状态（0 展示 1 不展示）
     */
    public void setShowStatus(Boolean showStatus) {
        this.showStatus = showStatus;
    }

    /**
     * 平台id(1 贝登 2医械购 )
     * @return PLATFROM_ID 平台id(1 贝登 2医械购 )
     */
    public Integer getPlatfromId() {
        return platfromId;
    }

    /**
     * 平台id(1 贝登 2医械购 )
     * @param platfromId 平台id(1 贝登 2医械购 )
     */
    public void setPlatfromId(Integer platfromId) {
        this.platfromId = platfromId;
    }

    /**
     * 排序
     * @return SORT 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 添加人
     * @return CREATOR 添加人
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 添加人
     * @param creator 添加人
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 添加时间
     * @return CREATE_TIME 添加时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 添加时间
     * @param createTime 添加时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改人
     * @return UPDATER 修改人
     */
    public Integer getUpdater() {
        return updater;
    }

    /**
     * 修改人
     * @param updater 修改人
     */
    public void setUpdater(Integer updater) {
        this.updater = updater;
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
     * 是否删除（0 否 1 是）
     * @return IS_DEL 是否删除（0 否 1 是）
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 是否删除（0 否 1 是）
     * @param isDel 是否删除（0 否 1 是）
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}