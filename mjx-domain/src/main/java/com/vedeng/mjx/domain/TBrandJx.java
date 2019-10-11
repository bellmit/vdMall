package com.vedeng.mjx.domain;

/**
 * T_BRAND_JX
 */
public class TBrandJx extends TBrandJxKey {
    /**
     * <pre>
     * 品牌性质 1国产 2进口
     * 表字段 : T_BRAND_JX.BRAND_NATURE
     * </pre>
     * 
     */
    private Boolean brandNature;

    /**
     * <pre>
     * 品牌名称
     * 表字段 : T_BRAND_JX.BRAND_NAME
     * </pre>
     * 
     */
    private String brandName;

    /**
     * <pre>
     * 品牌链接
     * 表字段 : T_BRAND_JX.BRAND_WEBSITE
     * </pre>
     * 
     */
    private String brandWebsite;

    /**
     * <pre>
     * 品牌商
     * 表字段 : T_BRAND_JX.OWNER
     * </pre>
     * 
     */
    private String owner;

    /**
     * <pre>
     * 是否展示:0 不展示，1 展示
     * 表字段 : T_BRAND_JX.IS_SHOW
     * </pre>
     * 
     */
    private Integer isShow;

    /**
     * <pre>
     * 是否推荐:0 不推荐，1 推荐
     * 表字段 : T_BRAND_JX.IS_RECOMMEND
     * </pre>
     * 
     */
    private Integer isRecommend;

    /**
     * <pre>
     * 商标图片域名
     * 表字段 : T_BRAND_JX.LOGO_DOMAIN
     * </pre>
     * 
     */
    private String logoDomain;

    /**
     * <pre>
     * 商标图片地址
     * 表字段 : T_BRAND_JX.LOGO_URI
     * </pre>
     * 
     */
    private String logoUri;

    /**
     * <pre>
     * 排序
     * 表字段 : T_BRAND_JX.SORT
     * </pre>
     * 
     */
    private Integer sort;

    /**
     * <pre>
     * 中文首字母
     * 表字段 : T_BRAND_JX.INITIAL_CN
     * </pre>
     * 
     */
    private String initialCn;

    /**
     * <pre>
     * 英文首字母
     * 表字段 : T_BRAND_JX.INITIAL_EN
     * </pre>
     * 
     */
    private String initialEn;

    /**
     * <pre>
     * 描述
     * 表字段 : T_BRAND_JX.DESCRIPTION
     * </pre>
     * 
     */
    private String description;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_BRAND_JX.ADD_TIME
     * </pre>
     * 
     */
    private Long addTime;

    /**
     * <pre>
     * 创建人
     * 表字段 : T_BRAND_JX.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 更新时间
     * 表字段 : T_BRAND_JX.MOD_TIME
     * </pre>
     * 
     */
    private Long modTime;

    /**
     * <pre>
     * 更新人
     * 表字段 : T_BRAND_JX.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 品牌商ID即生产厂商ID
     * 表字段 : T_BRAND_JX.PRODUCT_COMPANY_ID
     * </pre>
     * 
     */
    private Integer productCompanyId;

    /**
     * <pre>
     * 分类ID
     * 表字段 : T_BRAND_JX.CATEGORY_ID
     * </pre>
     * 
     */
    private Integer categoryId;

    /**
     * <pre>
     * 授权证明域名
     * 表字段 : T_BRAND_JX.AUTHORITY_DOMAIN
     * </pre>
     * 
     */
    private String authorityDomain;

    /**
     * <pre>
     * 授权证明地址
     * 表字段 : T_BRAND_JX.AUTHORITY_URI
     * </pre>
     * 
     */
    private String authorityUri;

    /**
     * <pre>
     * 是否删除标志 0-未删除 | 1-已删除
     * 表字段 : T_BRAND_JX.IS_DELETE
     * </pre>
     * 
     */
    private Integer isDelete;

    /**
     * <pre>
     * 生产商品类型 1-医疗器械 |  2-非医疗器械 | 3-包含两者
     * 表字段 : T_BRAND_JX.BRAND_TYPE
     * </pre>
     * 
     */
    private Integer brandType;

    /**
     * <pre>
     * 删除原因
     * 表字段 : T_BRAND_JX.COMMENTS
     * </pre>
     * 
     */
    private String comments;

    /**
     * 品牌性质 1国产 2进口
     * @return BRAND_NATURE 品牌性质 1国产 2进口
     */
    public Boolean getBrandNature() {
        return brandNature;
    }

    /**
     * 品牌性质 1国产 2进口
     * @param brandNature 品牌性质 1国产 2进口
     */
    public void setBrandNature(Boolean brandNature) {
        this.brandNature = brandNature;
    }

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
     * 品牌链接
     * @return BRAND_WEBSITE 品牌链接
     */
    public String getBrandWebsite() {
        return brandWebsite;
    }

    /**
     * 品牌链接
     * @param brandWebsite 品牌链接
     */
    public void setBrandWebsite(String brandWebsite) {
        this.brandWebsite = brandWebsite == null ? null : brandWebsite.trim();
    }

    /**
     * 品牌商
     * @return OWNER 品牌商
     */
    public String getOwner() {
        return owner;
    }

    /**
     * 品牌商
     * @param owner 品牌商
     */
    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    /**
     * 是否展示:0 不展示，1 展示
     * @return IS_SHOW 是否展示:0 不展示，1 展示
     */
    public Integer getIsShow() {
        return isShow;
    }

    /**
     * 是否展示:0 不展示，1 展示
     * @param isShow 是否展示:0 不展示，1 展示
     */
    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    /**
     * 是否推荐:0 不推荐，1 推荐
     * @return IS_RECOMMEND 是否推荐:0 不推荐，1 推荐
     */
    public Integer getIsRecommend() {
        return isRecommend;
    }

    /**
     * 是否推荐:0 不推荐，1 推荐
     * @param isRecommend 是否推荐:0 不推荐，1 推荐
     */
    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    /**
     * 商标图片域名
     * @return LOGO_DOMAIN 商标图片域名
     */
    public String getLogoDomain() {
        return logoDomain;
    }

    /**
     * 商标图片域名
     * @param logoDomain 商标图片域名
     */
    public void setLogoDomain(String logoDomain) {
        this.logoDomain = logoDomain == null ? null : logoDomain.trim();
    }

    /**
     * 商标图片地址
     * @return LOGO_URI 商标图片地址
     */
    public String getLogoUri() {
        return logoUri;
    }

    /**
     * 商标图片地址
     * @param logoUri 商标图片地址
     */
    public void setLogoUri(String logoUri) {
        this.logoUri = logoUri == null ? null : logoUri.trim();
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
     * 中文首字母
     * @return INITIAL_CN 中文首字母
     */
    public String getInitialCn() {
        return initialCn;
    }

    /**
     * 中文首字母
     * @param initialCn 中文首字母
     */
    public void setInitialCn(String initialCn) {
        this.initialCn = initialCn == null ? null : initialCn.trim();
    }

    /**
     * 英文首字母
     * @return INITIAL_EN 英文首字母
     */
    public String getInitialEn() {
        return initialEn;
    }

    /**
     * 英文首字母
     * @param initialEn 英文首字母
     */
    public void setInitialEn(String initialEn) {
        this.initialEn = initialEn == null ? null : initialEn.trim();
    }

    /**
     * 描述
     * @return DESCRIPTION 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
     * 品牌商ID即生产厂商ID
     * @return PRODUCT_COMPANY_ID 品牌商ID即生产厂商ID
     */
    public Integer getProductCompanyId() {
        return productCompanyId;
    }

    /**
     * 品牌商ID即生产厂商ID
     * @param productCompanyId 品牌商ID即生产厂商ID
     */
    public void setProductCompanyId(Integer productCompanyId) {
        this.productCompanyId = productCompanyId;
    }

    /**
     * 分类ID
     * @return CATEGORY_ID 分类ID
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 分类ID
     * @param categoryId 分类ID
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 授权证明域名
     * @return AUTHORITY_DOMAIN 授权证明域名
     */
    public String getAuthorityDomain() {
        return authorityDomain;
    }

    /**
     * 授权证明域名
     * @param authorityDomain 授权证明域名
     */
    public void setAuthorityDomain(String authorityDomain) {
        this.authorityDomain = authorityDomain == null ? null : authorityDomain.trim();
    }

    /**
     * 授权证明地址
     * @return AUTHORITY_URI 授权证明地址
     */
    public String getAuthorityUri() {
        return authorityUri;
    }

    /**
     * 授权证明地址
     * @param authorityUri 授权证明地址
     */
    public void setAuthorityUri(String authorityUri) {
        this.authorityUri = authorityUri == null ? null : authorityUri.trim();
    }

    /**
     * 是否删除标志 0-未删除 | 1-已删除
     * @return IS_DELETE 是否删除标志 0-未删除 | 1-已删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 是否删除标志 0-未删除 | 1-已删除
     * @param isDelete 是否删除标志 0-未删除 | 1-已删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 生产商品类型 1-医疗器械 |  2-非医疗器械 | 3-包含两者
     * @return BRAND_TYPE 生产商品类型 1-医疗器械 |  2-非医疗器械 | 3-包含两者
     */
    public Integer getBrandType() {
        return brandType;
    }

    /**
     * 生产商品类型 1-医疗器械 |  2-非医疗器械 | 3-包含两者
     * @param brandType 生产商品类型 1-医疗器械 |  2-非医疗器械 | 3-包含两者
     */
    public void setBrandType(Integer brandType) {
        this.brandType = brandType;
    }

    /**
     * 删除原因
     * @return COMMENTS 删除原因
     */
    public String getComments() {
        return comments;
    }

    /**
     * 删除原因
     * @param comments 删除原因
     */
    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }
}