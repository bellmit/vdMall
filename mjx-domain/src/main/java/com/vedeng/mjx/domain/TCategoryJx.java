package com.vedeng.mjx.domain;

/**
 * T_CATEGORY_JX
 */
public class TCategoryJx extends TCategoryJxKey {
    /**
     * <pre>
     * 公司ID
     * 表字段 : T_CATEGORY_JX.COMPANY_ID
     * </pre>
     * 
     */
    private Integer companyId;

    /**
     * <pre>
     * 分类父节点ID
     * 表字段 : T_CATEGORY_JX.PARENT_ID
     * </pre>
     * 
     */
    private Integer parentId;

    /**
     * <pre>
     * 分类名称
     * 表字段 : T_CATEGORY_JX.CATEGORY_NAME
     * </pre>
     * 
     */
    private String categoryName;

    /**
     * <pre>
     * 当前节点之前的所有节点，逗号拼接
     * 表字段 : T_CATEGORY_JX.TREENODES
     * </pre>
     * 
     */
    private String treenodes;

    /**
     * <pre>
     * 分类编码
     * 表字段 : T_CATEGORY_JX.CATEGORY_CODE
     * </pre>
     * 
     */
    private Integer categoryCode;

    /**
     * <pre>
     * 父级分类编码
     * 表字段 : T_CATEGORY_JX.PARENT_CATEGORY_CODE
     * </pre>
     * 
     */
    private Integer parentCategoryCode;

    /**
     * <pre>
     * 原分类编码
     * 表字段 : T_CATEGORY_JX.OLD_CATEGORY_CODE
     * </pre>
     * 
     */
    private Integer oldCategoryCode;

    /**
     * <pre>
     * 关联分类编码（暂做冗余）
     * 表字段 : T_CATEGORY_JX.LINK_CATEGORY_CODE
     * </pre>
     * 
     */
    private Integer linkCategoryCode;

    /**
     * <pre>
     * 状态（0:停用；1：启用）
     * 表字段 : T_CATEGORY_JX.STATUS
     * </pre>
     * 
     */
    private Integer status;

    /**
     * <pre>
     * 是否删除 0否 1是
     * 表字段 : T_CATEGORY_JX.IS_DELETED
     * </pre>
     * 
     */
    private Integer isDeleted;

    /**
     * <pre>
     * 当前级别（1开始依次递增）
     * 表字段 : T_CATEGORY_JX.LEVEL
     * </pre>
     * 
     */
    private Integer level;

    /**
     * <pre>
     * 排序
     * 表字段 : T_CATEGORY_JX.SORT
     * </pre>
     * 
     */
    private Integer sort;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_CATEGORY_JX.ADD_TIME
     * </pre>
     * 
     */
    private Long addTime;

    /**
     * <pre>
     * 创建人ID
     * 表字段 : T_CATEGORY_JX.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 更新时间
     * 表字段 : T_CATEGORY_JX.MOD_TIME
     * </pre>
     * 
     */
    private Long modTime;

    /**
     * <pre>
     * 更新人ID
     * 表字段 : T_CATEGORY_JX.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

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
     * 分类父节点ID
     * @return PARENT_ID 分类父节点ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 分类父节点ID
     * @param parentId 分类父节点ID
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
     * 当前节点之前的所有节点，逗号拼接
     * @return TREENODES 当前节点之前的所有节点，逗号拼接
     */
    public String getTreenodes() {
        return treenodes;
    }

    /**
     * 当前节点之前的所有节点，逗号拼接
     * @param treenodes 当前节点之前的所有节点，逗号拼接
     */
    public void setTreenodes(String treenodes) {
        this.treenodes = treenodes == null ? null : treenodes.trim();
    }

    /**
     * 分类编码
     * @return CATEGORY_CODE 分类编码
     */
    public Integer getCategoryCode() {
        return categoryCode;
    }

    /**
     * 分类编码
     * @param categoryCode 分类编码
     */
    public void setCategoryCode(Integer categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * 父级分类编码
     * @return PARENT_CATEGORY_CODE 父级分类编码
     */
    public Integer getParentCategoryCode() {
        return parentCategoryCode;
    }

    /**
     * 父级分类编码
     * @param parentCategoryCode 父级分类编码
     */
    public void setParentCategoryCode(Integer parentCategoryCode) {
        this.parentCategoryCode = parentCategoryCode;
    }

    /**
     * 原分类编码
     * @return OLD_CATEGORY_CODE 原分类编码
     */
    public Integer getOldCategoryCode() {
        return oldCategoryCode;
    }

    /**
     * 原分类编码
     * @param oldCategoryCode 原分类编码
     */
    public void setOldCategoryCode(Integer oldCategoryCode) {
        this.oldCategoryCode = oldCategoryCode;
    }

    /**
     * 关联分类编码（暂做冗余）
     * @return LINK_CATEGORY_CODE 关联分类编码（暂做冗余）
     */
    public Integer getLinkCategoryCode() {
        return linkCategoryCode;
    }

    /**
     * 关联分类编码（暂做冗余）
     * @param linkCategoryCode 关联分类编码（暂做冗余）
     */
    public void setLinkCategoryCode(Integer linkCategoryCode) {
        this.linkCategoryCode = linkCategoryCode;
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
     * 是否删除 0否 1是
     * @return IS_DELETED 是否删除 0否 1是
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除 0否 1是
     * @param isDeleted 是否删除 0否 1是
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 当前级别（1开始依次递增）
     * @return LEVEL 当前级别（1开始依次递增）
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 当前级别（1开始依次递增）
     * @param level 当前级别（1开始依次递增）
     */
    public void setLevel(Integer level) {
        this.level = level;
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
     * 创建时间
     * @return ADD_TIME 创建时间
     */
    public Long getAddTime() {
        return addTime;
    }

    /**
     * 创建时间
     * @param addTime 创建时间
     */
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
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
     * 更新时间
     * @return MOD_TIME 更新时间
     */
    public Long getModTime() {
        return modTime;
    }

    /**
     * 更新时间
     * @param modTime 更新时间
     */
    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    /**
     * 更新人ID
     * @return UPDATER 更新人ID
     */
    public Integer getUpdater() {
        return updater;
    }

    /**
     * 更新人ID
     * @param updater 更新人ID
     */
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }


}