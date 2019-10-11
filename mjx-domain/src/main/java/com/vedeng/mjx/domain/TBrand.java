package com.vedeng.mjx.domain;

/**
 * T_BRAND
 */
public class TBrand extends TBrandKey {
    /**
     * <pre>
     * ERP公司ID(T_COMPANY)
     * 表字段 : T_BRAND.COMPANY_ID
     * </pre>
     * 
     */
    private Integer companyId;

    /**
     * <pre>
     * 品牌性质 1国产 2进口
     * 表字段 : T_BRAND.BRAND_NATURE
     * </pre>
     * 
     */
    private Boolean brandNature;

    /**
     * <pre>
     * 品牌名称
     * 表字段 : T_BRAND.BRAND_NAME
     * </pre>
     * 
     */
    private String brandName;

    /**
     * <pre>
     * 品牌英文名
     * 表字段 : T_BRAND.BRAND_NAME_EN
     * </pre>
     * 
     */
    private String brandNameEn;

    /**
     * <pre>
     * 生产企业
     * 表字段 : T_BRAND.MANUFACTURER
     * </pre>
     * 
     */
    private String manufacturer;

    /**
     * <pre>
     * 品牌链接
     * 表字段 : T_BRAND.BRAND_WEBSITE
     * </pre>
     * 
     */
    private String brandWebsite;

    /**
     * <pre>
     * 品牌商
     * 表字段 : T_BRAND.OWNER
     * </pre>
     * 
     */
    private String owner;

    /**
     * <pre>
     * 商标图片域名
     * 表字段 : T_BRAND.LOGO_DOMAIN
     * </pre>
     * 
     */
    private String logoDomain;

    /**
     * <pre>
     * 商标图片地址
     * 表字段 : T_BRAND.LOGO_URI
     * </pre>
     * 
     */
    private String logoUri;

    /**
     * <pre>
     * 排序
     * 表字段 : T_BRAND.SORT
     * </pre>
     * 
     */
    private Integer sort;

    /**
     * <pre>
     * 中文首字母
     * 表字段 : T_BRAND.INITIAL_CN
     * </pre>
     * 
     */
    private String initialCn;

    /**
     * <pre>
     * 英文首字母
     * 表字段 : T_BRAND.INITIAL_EN
     * </pre>
     * 
     */
    private String initialEn;

    /**
     * <pre>
     * 0 ERP 1耗材商城
     * 表字段 : T_BRAND.SOURCE
     * </pre>
     * 
     */
    private Boolean source;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_BRAND.ADD_TIME
     * </pre>
     * 
     */
    private Long addTime;

    /**
     * <pre>
     * 创建人
     * 表字段 : T_BRAND.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 更新时间
     * 表字段 : T_BRAND.MOD_TIME
     * </pre>
     * 
     */
    private Long modTime;

    /**
     * <pre>
     * 更新人
     * 表字段 : T_BRAND.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 是否是贝登精选
     * 表字段 : T_BRAND.IS_MJX
     * </pre>
     * 
     */
    private Integer isMjx;

    /**
     * <pre>
     * 是否删除标志 0-未删除 | 1-已删除
     * 表字段 : T_BRAND.IS_DELETE
     * </pre>
     * 
     */
    private Boolean isDelete;

    /**
     * <pre>
     * 删除原因
     * 表字段 : T_BRAND.COMMENTS
     * </pre>
     * 
     */
    private String comments;

    /**
     * <pre>
     * 公司名称
     * 表字段 : T_BRAND.COMPANY_NAME
     * </pre>
     * 
     */
    private String companyName;

    /**
     * ERP公司ID(T_COMPANY)
     * @return COMPANY_ID ERP公司ID(T_COMPANY)
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * ERP公司ID(T_COMPANY)
     * @param companyId ERP公司ID(T_COMPANY)
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

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
     * 品牌英文名
     * @return BRAND_NAME_EN 品牌英文名
     */
    public String getBrandNameEn() {
        return brandNameEn;
    }

    /**
     * 品牌英文名
     * @param brandNameEn 品牌英文名
     */
    public void setBrandNameEn(String brandNameEn) {
        this.brandNameEn = brandNameEn == null ? null : brandNameEn.trim();
    }

    /**
     * 生产企业
     * @return MANUFACTURER 生产企业
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * 生产企业
     * @param manufacturer 生产企业
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
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
     * 0 ERP 1耗材商城
     * @return SOURCE 0 ERP 1耗材商城
     */
    public Boolean getSource() {
        return source;
    }

    /**
     * 0 ERP 1耗材商城
     * @param source 0 ERP 1耗材商城
     */
    public void setSource(Boolean source) {
        this.source = source;
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
     * 是否是贝登精选
     * @return IS_MJX 是否是贝登精选
     */
    public Integer getIsMjx() {
        return isMjx;
    }

    /**
     * 是否是贝登精选
     * @param isMjx 是否是贝登精选
     */
    public void setIsMjx(Integer isMjx) {
        this.isMjx = isMjx;
    }

    /**
     * 是否删除标志 0-未删除 | 1-已删除
     * @return IS_DELETE 是否删除标志 0-未删除 | 1-已删除
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 是否删除标志 0-未删除 | 1-已删除
     * @param isDelete 是否删除标志 0-未删除 | 1-已删除
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
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
}