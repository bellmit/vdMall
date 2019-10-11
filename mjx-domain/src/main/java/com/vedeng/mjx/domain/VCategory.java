package com.vedeng.mjx.domain;

import java.util.Date;

/**
 * V_CATEGORY
 */
public class VCategory extends VCategoryKey {
    /**
     * <pre>
     * 父级id
     * 表字段 : V_CATEGORY.PARENT_ID
     * </pre>
     * 
     */
    private Integer parentId;

    /**
     * <pre>
     * 分类名称
     * 表字段 : V_CATEGORY.CATEGORY_NAME
     * </pre>
     * 
     */
    private String categoryName;

    /**
     * <pre>
     * 状态（0:停用；1：启用）
     * 表字段 : V_CATEGORY.STATUS
     * </pre>
     * 
     */
    private Integer status;

    /**
     * <pre>
     * 当前级别（1开始）
     * 表字段 : V_CATEGORY.LEVEL
     * </pre>
     * 
     */
    private Integer level;

    /**
     * <pre>
     * 0未推荐 1推荐
     * 表字段 : V_CATEGORY.IS_RECOMMEND
     * </pre>
     * 
     */
    private Integer isRecommend;

    /**
     * <pre>
     * 添加时间
     * 表字段 : V_CATEGORY.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 0 否  1 是
     * 表字段 : V_CATEGORY.IS_DEL
     * </pre>
     * 
     */
    private Integer isDel;

    /**
     * <pre>
     * 添加人
     * 表字段 : V_CATEGORY.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 修改时间
     * 表字段 : V_CATEGORY.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 修改人
     * 表字段 : V_CATEGORY.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 三级id
     * 表字段 : V_CATEGORY.CATEGORY_ID
     * </pre>
     * 
     */
    private Integer categoryId;

    /**
     * <pre>
     * 平台id
     * 表字段 : V_CATEGORY.PLATFROM_ID
     * </pre>
     * 
     */
    private Integer platfromId;

    /**
     * 父级id
     * @return PARENT_ID 父级id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 父级id
     * @param parentId 父级id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 分类名称
     * @return CATEGORY_NAME 分类名称
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 分类名称
     * @param categoryName 分类名称
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    /**
     * 状态（0:停用；1：启用）
     * @return STATUS 状态（0:停用；1：启用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态（0:停用；1：启用）
     * @param status 状态（0:停用；1：启用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 当前级别（1开始）
     * @return LEVEL 当前级别（1开始）
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 当前级别（1开始）
     * @param level 当前级别（1开始）
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 0未推荐 1推荐
     * @return IS_RECOMMEND 0未推荐 1推荐
     */
    public Integer getIsRecommend() {
        return isRecommend;
    }

    /**
     * 0未推荐 1推荐
     * @param isRecommend 0未推荐 1推荐
     */
    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    /**
     * 添加时间
     * @return ADD_TIME 添加时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     * @param addTime 添加时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 0 否  1 是
     * @return IS_DEL 0 否  1 是
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 0 否  1 是
     * @param isDel 0 否  1 是
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
     * 三级id
     * @return CATEGORY_ID 三级id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 三级id
     * @param categoryId 三级id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 平台id
     * @return PLATFROM_ID 平台id
     */
    public Integer getPlatfromId() {
        return platfromId;
    }

    /**
     * 平台id
     * @param platfromId 平台id
     */
    public void setPlatfromId(Integer platfromId) {
        this.platfromId = platfromId;
    }
}