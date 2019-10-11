package com.vedeng.mjx.domain;

import java.util.Date;

/**
 * V_ACCOUNT_ADDRESS
 */
public class VAccountAddress extends VAccountAddressKey {

    /**
     * 参数标识，1:省份
     */
    private String flagId;

    /**
     * <pre>
     * 区域（T_REGION主键）
     * 表字段 : V_ACCOUNT_ADDRESS.REGION_ID
     * </pre>
     * 
     */
    private Integer regionId;

    /**
     * <pre>
     * 多级地址id，逗号拼接（冗余字段）
     * 表字段 : V_ACCOUNT_ADDRESS.REGION_IDS
     * </pre>
     * 
     */
    private String regionIds;

    /**
     * <pre>
     * 多级地址拼接（冗余字段）
     * 表字段 : V_ACCOUNT_ADDRESS.REGION_NAME
     * </pre>
     * 
     */
    private String regionName;

    /**
     * <pre>
     * 登陆人id
     * 表字段 : V_ACCOUNT_ADDRESS.ACCOUNT_ID
     * </pre>
     * 
     */
    private Integer accountId;

    /**
     * <pre>
     * 收货（票）人名称
     * 表字段 : V_ACCOUNT_ADDRESS.ACCOUNT_NAME
     * </pre>
     * 
     */
    private String accountName;

    /**
     * <pre>
     * 地址详情
     * 表字段 : V_ACCOUNT_ADDRESS.ADDRESS_INFO
     * </pre>
     * 
     */
    private String addressInfo;

    /**
     * <pre>
     * 手机
     * 表字段 : V_ACCOUNT_ADDRESS.PHONE
     * </pre>
     * 
     */
    private String phone;

    /**
     * <pre>
     * 收货地址，是否默认 0-否 | 1-默认
     * 表字段 : V_ACCOUNT_ADDRESS.IS_DEFAULT_ADDRESS
     * </pre>
     * 
     */
    private Boolean isDefaultAddress;

    /**
     * <pre>
     * 收票地址，是否默认 0-否 | 1-默认
     * 表字段 : V_ACCOUNT_ADDRESS.IS_DEFAULT_TICKET
     * </pre>
     * 
     */
    private Boolean isDefaultTicket;

    /**
     * <pre>
     * 邮政编码
     * 表字段 : V_ACCOUNT_ADDRESS.POSTCODE
     * </pre>
     * 
     */
    private String postcode;

    /**
     * <pre>
     * 地址排序
     * 表字段 : V_ACCOUNT_ADDRESS.SORT
     * </pre>
     * 
     */
    private Integer sort;

    /**
     * <pre>
     * 添加时间
     * 表字段 : V_ACCOUNT_ADDRESS.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 更新时间
     * 表字段 : V_ACCOUNT_ADDRESS.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 创建人
     * 表字段 : V_ACCOUNT_ADDRESS.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 更新人
     * 表字段 : V_ACCOUNT_ADDRESS.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 标签（如：南京仓库）
     * 表字段 : V_ACCOUNT_ADDRESS.LABEL
     * </pre>
     * 
     */
    private String label;

    /**
     * <pre>
     * 删除标志 0-未删除 | 1-已删除
     * 表字段 : V_ACCOUNT_ADDRESS.IS_DEL
     * </pre>
     * 
     */
    private Boolean isDel;

    /**
     * 区域（T_REGION主键）
     * @return REGION_ID 区域（T_REGION主键）
     */
    public Integer getRegionId() {
        return regionId;
    }

    /**
     * 区域（T_REGION主键）
     * @param regionId 区域（T_REGION主键）
     */
    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    /**
     * 多级地址id，逗号拼接（冗余字段）
     * @return REGION_IDS 多级地址id，逗号拼接（冗余字段）
     */
    public String getRegionIds() {
        return regionIds;
    }

    /**
     * 多级地址id，逗号拼接（冗余字段）
     * @param regionIds 多级地址id，逗号拼接（冗余字段）
     */
    public void setRegionIds(String regionIds) {
        this.regionIds = regionIds == null ? null : regionIds.trim();
    }

    /**
     * 多级地址拼接（冗余字段）
     * @return REGION_NAME 多级地址拼接（冗余字段）
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * 多级地址拼接（冗余字段）
     * @param regionName 多级地址拼接（冗余字段）
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    /**
     * 登陆人id
     * @return ACCOUNT_ID 登陆人id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 登陆人id
     * @param accountId 登陆人id
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 收货（票）人名称
     * @return ACCOUNT_NAME 收货（票）人名称
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 收货（票）人名称
     * @param accountName 收货（票）人名称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    /**
     * 地址详情
     * @return ADDRESS_INFO 地址详情
     */
    public String getAddressInfo() {
        return addressInfo;
    }

    /**
     * 地址详情
     * @param addressInfo 地址详情
     */
    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo == null ? null : addressInfo.trim();
    }

    /**
     * 手机
     * @return PHONE 手机
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 手机
     * @param phone 手机
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 收货地址，是否默认 0-否 | 1-默认
     * @return IS_DEFAULT_ADDRESS 收货地址，是否默认 0-否 | 1-默认
     */
    public Boolean getIsDefaultAddress() {
        return isDefaultAddress;
    }

    /**
     * 收货地址，是否默认 0-否 | 1-默认
     * @param isDefaultAddress 收货地址，是否默认 0-否 | 1-默认
     */
    public void setIsDefaultAddress(Boolean isDefaultAddress) {
        this.isDefaultAddress = isDefaultAddress;
    }

    /**
     * 收票地址，是否默认 0-否 | 1-默认
     * @return IS_DEFAULT_TICKET 收票地址，是否默认 0-否 | 1-默认
     */
    public Boolean getIsDefaultTicket() {
        return isDefaultTicket;
    }

    /**
     * 收票地址，是否默认 0-否 | 1-默认
     * @param isDefaultTicket 收票地址，是否默认 0-否 | 1-默认
     */
    public void setIsDefaultTicket(Boolean isDefaultTicket) {
        this.isDefaultTicket = isDefaultTicket;
    }

    /**
     * 邮政编码
     * @return POSTCODE 邮政编码
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * 邮政编码
     * @param postcode 邮政编码
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    /**
     * 地址排序
     * @return SORT 地址排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 地址排序
     * @param sort 地址排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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
     * 更新时间
     * @return MOD_TIME 更新时间
     */
    public Date getModTime() {
        return modTime;
    }

    /**
     * 更新时间
     * @param modTime 更新时间
     */
    public void setModTime(Date modTime) {
        this.modTime = modTime;
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
     * 标签（如：南京仓库）
     * @return LABEL 标签（如：南京仓库）
     */
    public String getLabel() {
        return label;
    }

    /**
     * 标签（如：南京仓库）
     * @param label 标签（如：南京仓库）
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * 删除标志 0-未删除 | 1-已删除
     * @return IS_DEL 删除标志 0-未删除 | 1-已删除
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 删除标志 0-未删除 | 1-已删除
     * @param isDel 删除标志 0-未删除 | 1-已删除
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public String getFlagId() {
        return flagId;
    }

    public void setFlagId(String flagId) {
        this.flagId = flagId;
    }
}