package com.vedeng.mjx.domain;

import java.util.Date;

/**
 * V_CANCEL
 */
public class VCancel extends VCancelKey {
    /**
     * <pre>
     * 原因
     * 表字段 : V_CANCEL.content
     * </pre>
     * 
     */
    private String content;

    /**
     * <pre>
     * 
     * 表字段 : V_CANCEL.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 
     * 表字段 : V_CANCEL.CREATOR
     * </pre>
     * 
     */
    private String creator;

    /**
     * <pre>
     * 
     * 表字段 : V_CANCEL.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 
     * 表字段 : V_CANCEL.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 
     * 表字段 : V_CANCEL.IS_DEL
     * </pre>
     * 
     */
    private String isDel;

    /**
     * 原因
     * @return content 原因
     */
    public String getContent() {
        return content;
    }

    /**
     * 原因
     * @param content 原因
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
    public String getCreator() {
        return creator;
    }

    /**
     * 
     * @param creator 
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
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
     * 
     * @return IS_DEL 
     */
    public String getIsDel() {
        return isDel;
    }

    /**
     * 
     * @param isDel 
     */
    public void setIsDel(String isDel) {
        this.isDel = isDel == null ? null : isDel.trim();
    }
}