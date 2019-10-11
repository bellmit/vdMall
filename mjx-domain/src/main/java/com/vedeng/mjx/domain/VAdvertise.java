package com.vedeng.mjx.domain;

import java.util.Date;

/**
 * V_ADVERTISE
 */
public class VAdvertise extends VAdvertiseKey {
    /**
     * <pre>
     * 平台id
     * 表字段 : V_ADVERTISE.PLATFROM_ID
     * </pre>
     * 
     */
    private Integer platfromId;

    /**
     * <pre>
     * 广告端口
     * 表字段 : V_ADVERTISE.T_PLATFROM_PORT_ID
     * </pre>
     * 
     */
    private Integer tPlatfromPortId;

    /**
     * <pre>
     * 广告ID
     * 表字段 : V_ADVERTISE.ADVERTISE_NO
     * </pre>
     * 
     */
    private String advertiseNo;

    /**
     * <pre>
     * 广告主题
     * 表字段 : V_ADVERTISE.THEME
     * </pre>
     * 
     */
    private String theme;

    /**
     * <pre>
     * 广告位置
     * 表字段 : V_ADVERTISE.PLACE
     * </pre>
     * 
     */
    private Integer place;

    /**
     * <pre>
     * 开始时间点
     * 表字段 : V_ADVERTISE.START_TIME
     * </pre>
     * 
     */
    private Integer startTime;

    /**
     * <pre>
     * 结束时间点
     * 表字段 : V_ADVERTISE.END_TIME
     * </pre>
     * 
     */
    private Integer endTime;

    /**
     * <pre>
     * 结束时间
     * 表字段 : V_ADVERTISE.END_DATE
     * </pre>
     * 
     */
    private Date endDate;

    /**
     * <pre>
     * 开始时间
     * 表字段 : V_ADVERTISE.START_DATE
     * </pre>
     * 
     */
    private Date startDate;

    /**
     * <pre>
     * 状态（0 启用 1 禁用）
     * 表字段 : V_ADVERTISE.STATUS
     * </pre>
     * 
     */
    private Integer status;

    /**
     * <pre>
     * 展示类型
     * 表字段 : V_ADVERTISE.SHOW_TYPE
     * </pre>
     * 
     */
    private Integer showType;

    /**
     * <pre>
     * 专题页链接
     * 表字段 : V_ADVERTISE.PAGE_LINKS
     * </pre>
     * 
     */
    private String pageLinks;

    /**
     * <pre>
     * 是否删除（0 否 1是）
     * 表字段 : V_ADVERTISE.IS_DEL
     * </pre>
     * 
     */
    private Boolean isDel;

    /**
     * <pre>
     * 创建时间
     * 表字段 : V_ADVERTISE.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 修改时间
     * 表字段 : V_ADVERTISE.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 添加人
     * 表字段 : V_ADVERTISE.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 修改人
     * 表字段 : V_ADVERTISE.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

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

    /**
     * 广告端口
     * @return T_PLATFROM_PORT_ID 广告端口
     */
    public Integer gettPlatfromPortId() {
        return tPlatfromPortId;
    }

    /**
     * 广告端口
     * @param tPlatfromPortId 广告端口
     */
    public void settPlatfromPortId(Integer tPlatfromPortId) {
        this.tPlatfromPortId = tPlatfromPortId;
    }

    /**
     * 广告ID
     * @return ADVERTISE_NO 广告ID
     */
    public String getAdvertiseNo() {
        return advertiseNo;
    }

    /**
     * 广告ID
     * @param advertiseNo 广告ID
     */
    public void setAdvertiseNo(String advertiseNo) {
        this.advertiseNo = advertiseNo == null ? null : advertiseNo.trim();
    }

    /**
     * 广告主题
     * @return THEME 广告主题
     */
    public String getTheme() {
        return theme;
    }

    /**
     * 广告主题
     * @param theme 广告主题
     */
    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    /**
     * 广告位置
     * @return PLACE 广告位置
     */
    public Integer getPlace() {
        return place;
    }

    /**
     * 广告位置
     * @param place 广告位置
     */
    public void setPlace(Integer place) {
        this.place = place;
    }

    /**
     * 开始时间点
     * @return START_TIME 开始时间点
     */
    public Integer getStartTime() {
        return startTime;
    }

    /**
     * 开始时间点
     * @param startTime 开始时间点
     */
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    /**
     * 结束时间点
     * @return END_TIME 结束时间点
     */
    public Integer getEndTime() {
        return endTime;
    }

    /**
     * 结束时间点
     * @param endTime 结束时间点
     */
    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    /**
     * 结束时间
     * @return END_DATE 结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 结束时间
     * @param endDate 结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 开始时间
     * @return START_DATE 开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 开始时间
     * @param startDate 开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 状态（0 启用 1 禁用）
     * @return STATUS 状态（0 启用 1 禁用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态（0 启用 1 禁用）
     * @param status 状态（0 启用 1 禁用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 展示类型
     * @return SHOW_TYPE 展示类型
     */
    public Integer getShowType() {
        return showType;
    }

    /**
     * 展示类型
     * @param showType 展示类型
     */
    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    /**
     * 专题页链接
     * @return PAGE_LINKS 专题页链接
     */
    public String getPageLinks() {
        return pageLinks;
    }

    /**
     * 专题页链接
     * @param pageLinks 专题页链接
     */
    public void setPageLinks(String pageLinks) {
        this.pageLinks = pageLinks == null ? null : pageLinks.trim();
    }

    /**
     * 是否删除（0 否 1是）
     * @return IS_DEL 是否删除（0 否 1是）
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 是否删除（0 否 1是）
     * @param isDel 是否删除（0 否 1是）
     */
    public void setIsDel(Boolean isDel) {
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
}