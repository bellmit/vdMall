package com.vedeng.mjx.domain;

import java.util.Date;

/**
 * V_WX_PHONE
 */
public class VWxPhone extends VWxPhoneKey {
    /**
     * <pre>
     * 
     * 表字段 : V_WX_PHONE.PHONE
     * </pre>
     * 
     */
    private String phone;

    /**
     * <pre>
     * 
     * 表字段 : V_WX_PHONE.OPENID
     * </pre>
     * 
     */
    private String openid;

    /**
     * <pre>
     * 
     * 表字段 : V_WX_PHONE.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 
     * 表字段 : V_WX_PHONE.CREATOR
     * </pre>
     * 
     */
    private String creator;

    /**
     * <pre>
     * 
     * 表字段 : V_WX_PHONE.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 
     * 表字段 : V_WX_PHONE.UPDATER
     * </pre>
     * 
     */
    private Date updater;

    /**
     * <pre>
     * 
     * 表字段 : V_WX_PHONE.IS_DEL
     * </pre>
     * 
     */
    private Integer isDel;

    /**
     * 
     * @return PHONE 
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone 
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 
     * @return OPENID 
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * 
     * @param openid 
     */
    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
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
    public Date getUpdater() {
        return updater;
    }

    /**
     * 
     * @param updater 
     */
    public void setUpdater(Date updater) {
        this.updater = updater;
    }

    /**
     * 
     * @return IS_DEL 
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 
     * @param isDel 
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}