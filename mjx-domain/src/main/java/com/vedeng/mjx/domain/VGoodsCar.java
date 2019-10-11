package com.vedeng.mjx.domain;

import java.util.Date;

/**
 * V_GOODS_CAR
 */
public class VGoodsCar extends VGoodsCarKey {
    /**
     * <pre>
     * 用户主键
     * 表字段 : V_GOODS_CAR.USER_ID
     * </pre>
     * 
     */
    private Integer userId;

    /**
     * <pre>
     * 客户是否进入过购物车（0否，1是）
     * 表字段 : V_GOODS_CAR.IS_FIRST
     * </pre>
     * 
     */
    private String isFirst;

    /**
     * <pre>
     * 备注
     * 表字段 : V_GOODS_CAR.REMARK
     * </pre>
     * 
     */
    private String remark;

    /**
     * <pre>
     * 创建人ID
     * 表字段 : V_GOODS_CAR.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 创建时间
     * 表字段 : V_GOODS_CAR.ADD_TIME
     * </pre>
     * 
     */
    private Date addTime;

    /**
     * <pre>
     * 修改人
     * 表字段 : V_GOODS_CAR.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 修改时间
     * 表字段 : V_GOODS_CAR.MOD_TIME
     * </pre>
     * 
     */
    private Date modTime;

    /**
     * <pre>
     * 是否删除(0否，1是)
     * 表字段 : V_GOODS_CAR.IS_DEL
     * </pre>
     * 
     */
    private String isDel;

    /**
     * 用户主键
     * @return USER_ID 用户主键
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户主键
     * @param userId 用户主键
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 客户是否进入过购物车（0否，1是）
     * @return IS_FIRST 客户是否进入过购物车（0否，1是）
     */
    public String getIsFirst() {
        return isFirst;
    }

    /**
     * 客户是否进入过购物车（0否，1是）
     * @param isFirst 客户是否进入过购物车（0否，1是）
     */
    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst == null ? null : isFirst.trim();
    }

    /**
     * 备注
     * @return REMARK 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
     * 是否删除(0否，1是)
     * @return IS_DEL 是否删除(0否，1是)
     */
    public String getIsDel() {
        return isDel;
    }

    /**
     * 是否删除(0否，1是)
     * @param isDel 是否删除(0否，1是)
     */
    public void setIsDel(String isDel) {
        this.isDel = isDel == null ? null : isDel.trim();
    }
}