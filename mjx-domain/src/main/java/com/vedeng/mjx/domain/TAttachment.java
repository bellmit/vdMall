package com.vedeng.mjx.domain;

/**
 * T_ATTACHMENT(销售攻略图片)
 */
public class TAttachment extends TAttachmentKey {
    /**
     * <pre>
     * 附件类型
     * 表字段 : T_ATTACHMENT.ATTACHMENT_TYPE（400类型时，是攻略图）
     * </pre>
     * 
     */
    private Integer attachmentType;

    /**
     * <pre>
     * 附件模块
     * 表字段 : T_ATTACHMENT.ATTACHMENT_FUNCTION
     * </pre>
     * 
     */
    private Integer attachmentFunction;

    /**
     * <pre>
     * 关联表ID
     * 表字段 : T_ATTACHMENT.RELATED_ID
     * </pre>
     * 
     */
    private Integer relatedId;

    /**
     * <pre>
     * 附件名称
     * 表字段 : T_ATTACHMENT.NAME
     * </pre>
     * 
     */
    private String name;

    /**
     * <pre>
     * 域名
     * 表字段 : T_ATTACHMENT.DOMAIN
     * </pre>
     * 
     */
    private String domain;

    /**
     * <pre>
     * 地址
     * 表字段 : T_ATTACHMENT.URI
     * </pre>
     * 
     */
    private String uri;

    /**
     * <pre>
     * 描述
     * 表字段 : T_ATTACHMENT.DESCRIPTION
     * </pre>
     * 
     */
    private String description;

    /**
     * <pre>
     * 排序,用于同模块同类型上传多张图片展示
     * 表字段 : T_ATTACHMENT.SORT
     * </pre>
     * 
     */
    private Integer sort;

    /**
     * <pre>
     * 是否默认：0 否/ 1 是
     * 表字段 : T_ATTACHMENT.IS_DEFAULT
     * </pre>
     * 
     */
    private Boolean isDefault;

    /**
     * <pre>
     * 添加时间
     * 表字段 : T_ATTACHMENT.ADD_TIME
     * </pre>
     * 
     */
    private Long addTime;

    /**
     * <pre>
     * 添加人
     * 表字段 : T_ATTACHMENT.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * 附件类型
     * @return ATTACHMENT_TYPE 附件类型
     */
    public Integer getAttachmentType() {
        return attachmentType;
    }

    /**
     * 附件类型
     * @param attachmentType 附件类型
     */
    public void setAttachmentType(Integer attachmentType) {
        this.attachmentType = attachmentType;
    }

    /**
     * 附件模块
     * @return ATTACHMENT_FUNCTION 附件模块
     */
    public Integer getAttachmentFunction() {
        return attachmentFunction;
    }

    /**
     * 附件模块
     * @param attachmentFunction 附件模块
     */
    public void setAttachmentFunction(Integer attachmentFunction) {
        this.attachmentFunction = attachmentFunction;
    }

    /**
     * 关联表ID
     * @return RELATED_ID 关联表ID
     */
    public Integer getRelatedId() {
        return relatedId;
    }

    /**
     * 关联表ID
     * @param relatedId 关联表ID
     */
    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    /**
     * 附件名称
     * @return NAME 附件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 附件名称
     * @param name 附件名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 域名
     * @return DOMAIN 域名
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 域名
     * @param domain 域名
     */
    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    /**
     * 地址
     * @return URI 地址
     */
    public String getUri() {
        return uri;
    }

    /**
     * 地址
     * @param uri 地址
     */
    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
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
     * 排序,用于同模块同类型上传多张图片展示
     * @return SORT 排序,用于同模块同类型上传多张图片展示
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序,用于同模块同类型上传多张图片展示
     * @param sort 排序,用于同模块同类型上传多张图片展示
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 是否默认：0 否/ 1 是
     * @return IS_DEFAULT 是否默认：0 否/ 1 是
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 是否默认：0 否/ 1 是
     * @param isDefault 是否默认：0 否/ 1 是
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 添加时间
     * @return ADD_TIME 添加时间
     */
    public Long getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     * @param addTime 添加时间
     */
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
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
}