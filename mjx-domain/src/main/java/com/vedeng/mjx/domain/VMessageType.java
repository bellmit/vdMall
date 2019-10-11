package com.vedeng.mjx.domain;

import java.util.Date;

/**
 * V_MESSAGE_TYPE
 */
public class VMessageType extends VMessageTypeKey {
    /**
     * <pre>
     * 消息名称
     * 表字段 : V_MESSAGE_TYPE.MESSAGE_NAME
     * </pre>
     * 
     */
    private String messageName;

    /**
     * <pre>
     * 
     * 表字段 : V_MESSAGE_TYPE.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 
     * 表字段 : V_MESSAGE_TYPE.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 
     * 表字段 : V_MESSAGE_TYPE.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 
     * 表字段 : V_MESSAGE_TYPE.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 是否删除
     * 表字段 : V_MESSAGE_TYPE.IS_DEL
     * </pre>
     * 
     */
    private Integer isDel;

    /**
     * <pre>
     * 是否启用
     * 表字段 : V_MESSAGE_TYPE.STATUS
     * </pre>
     * 
     */
    private Integer status;

    /**
     * 消息名称
     * @return MESSAGE_NAME 消息名称
     */
    public String getMessageName() {
        return messageName;
    }

    /**
     * 消息名称
     * @param messageName 消息名称
     */
    public void setMessageName(String messageName) {
        this.messageName = messageName == null ? null : messageName.trim();
    }

    /**
     * 
     * @return ADD_TIME 
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 
     * @param addTime 
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 
     * @return CREATOR 
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 
     * @param creator 
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 
     * @return MOD_TIME 
     */
    public Date getModTime() {
        return modTime;
    }

    /**
     * 
     * @param modTime 
     */
    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    /**
     * 
     * @return UPDATER 
     */
    public Integer getUpdater() {
        return updater;
    }

    /**
     * 
     * @param updater 
     */
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    /**
     * 是否删除
     * @return IS_DEL 是否删除
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 是否删除
     * @param isDel 是否删除
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * 是否启用
     * @return STATUS 是否启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 是否启用
     * @param status 是否启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}