package com.vedeng.mjx.domain;

import java.util.Date;

/**
 * V_VERSION
 */
public class VVersion extends VVersionKey {
    /**
     * <pre>
     * 平台id 1、贝登APP 2、医械购APP
     * 表字段 : V_VERSION.PLATFORM_ID
     * </pre>
     * 
     */
    private Integer platformId;

    /**
     * <pre>
     * 客户端 1、安卓 2、ios
     * 表字段 : V_VERSION.CLIENT
     * </pre>
     * 
     */
    private Integer client;

    /**
     * <pre>
     * 版本号
     * 表字段 : V_VERSION.VERSION_NO
     * </pre>
     * 
     */
    private String versionNo;

    /**
     * <pre>
     * 更新方式 0、非强制 1、强制
     * 表字段 : V_VERSION.UPDATE_TYPE
     * </pre>
     * 
     */
    private Integer updateType;

    /**
     * <pre>
     * 是否弹窗 0、不弹窗 1、弹窗
     * 表字段 : V_VERSION.IS_CPM
     * </pre>
     * 
     */
    private Integer isCpm;

    /**
     * <pre>
     * 下载地址
     * 表字段 : V_VERSION.APK_URL
     * </pre>
     * 
     */
    private String apkUrl;

    /**
     * <pre>
     * 更新说明
     * 表字段 : V_VERSION.CONTENT
     * </pre>
     * 
     */
    private String content;

    /**
     * <pre>
     * 是否启用此版本 0、否 1、是
     * 表字段 : V_VERSION.IS_OPEN
     * </pre>
     * 
     */
    private Integer isOpen;

    /**
     * <pre>
     * 是否删除 0、否 1、是
     * 表字段 : V_VERSION.IS_DEL
     * </pre>
     * 
     */
    private Integer isDel;

    /**
     * <pre>
     * 创建时间
     * 表字段 : V_VERSION.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 创建人
     * 表字段 : V_VERSION.CREATOR
     * </pre>
     * 
     */
    private String creator;

    /**
     * <pre>
     * 修改时间
     * 表字段 : V_VERSION.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 修改人
     * 表字段 : V_VERSION.UPDATER
     * </pre>
     * 
     */
    private String updater;

    /**
     * <pre>
     * 启用时间
     * 表字段 : V_VERSION.START_TIME
     * </pre>
     * 
     */
    private Date startTime;

    /**
     * 平台id 1、贝登APP 2、医械购APP
     * @return PLATFORM_ID 平台id 1、贝登APP 2、医械购APP
     */
    public Integer getPlatformId() {
        return platformId;
    }

    /**
     * 平台id 1、贝登APP 2、医械购APP
     * @param platformId 平台id 1、贝登APP 2、医械购APP
     */
    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    /**
     * 客户端 1、安卓 2、ios
     * @return CLIENT 客户端 1、安卓 2、ios
     */
    public Integer getClient() {
        return client;
    }

    /**
     * 客户端 1、安卓 2、ios
     * @param client 客户端 1、安卓 2、ios
     */
    public void setClient(Integer client) {
        this.client = client;
    }

    /**
     * 版本号
     * @return VERSION_NO 版本号
     */
    public String getVersionNo() {
        return versionNo;
    }

    /**
     * 版本号
     * @param versionNo 版本号
     */
    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }

    /**
     * 更新方式 0、非强制 1、强制
     * @return UPDATE_TYPE 更新方式 0、非强制 1、强制
     */
    public Integer getUpdateType() {
        return updateType;
    }

    /**
     * 更新方式 0、非强制 1、强制
     * @param updateType 更新方式 0、非强制 1、强制
     */
    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    /**
     * 是否弹窗 0、不弹窗 1、弹窗
     * @return IS_CPM 是否弹窗 0、不弹窗 1、弹窗
     */
    public Integer getIsCpm() {
        return isCpm;
    }

    /**
     * 是否弹窗 0、不弹窗 1、弹窗
     * @param isCpm 是否弹窗 0、不弹窗 1、弹窗
     */
    public void setIsCpm(Integer isCpm) {
        this.isCpm = isCpm;
    }

    /**
     * 下载地址
     * @return APK_URL 下载地址
     */
    public String getApkUrl() {
        return apkUrl;
    }

    /**
     * 下载地址
     * @param apkUrl 下载地址
     */
    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl == null ? null : apkUrl.trim();
    }

    /**
     * 更新说明
     * @return CONTENT 更新说明
     */
    public String getContent() {
        return content;
    }

    /**
     * 更新说明
     * @param content 更新说明
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 是否启用此版本 0、否 1、是
     * @return IS_OPEN 是否启用此版本 0、否 1、是
     */
    public Integer getIsOpen() {
        return isOpen;
    }

    /**
     * 是否启用此版本 0、否 1、是
     * @param isOpen 是否启用此版本 0、否 1、是
     */
    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * 是否删除 0、否 1、是
     * @return IS_DEL 是否删除 0、否 1、是
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 是否删除 0、否 1、是
     * @param isDel 是否删除 0、否 1、是
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
     * 创建人
     * @return CREATOR 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建人
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
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
    public String getUpdater() {
        return updater;
    }

    /**
     * 修改人
     * @param updater 修改人
     */
    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    /**
     * 启用时间
     * @return START_TIME 启用时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 启用时间
     * @param startTime 启用时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}