package com.vedeng.mjx.domain.ext;

import com.vedeng.mjx.domain.TOpSkuWithBLOBs;

import java.util.Date;
import java.util.List;

/**
 * @author ：Gavin.li
 * @date ：Created in 2019/7/13 14:17
 * @description：
 * @modified By：
 * @version:
 */
public class TOpSkuExt extends TOpSkuWithBLOBs {
    /**
     * 判断是否显示攻略(0否，1是)
     */
    private String isFlag;

    public String getIsFlag() {
        return isFlag;
    }

    private String isOpShows;

    public String getIsOpShows() {
        return isOpShows;
    }

    public void setIsOpShows(String isOpShows) {
        this.isOpShows = isOpShows;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }

    /**
     * 竞品
     */
    private String opCompete;

    public String getOpCompete() {
        return opCompete;
    }

    public void setOpCompete(String opCompete) {
        this.opCompete = opCompete;
    }

    /**
     * 销售攻略的图片实体集合
     */
    List<String> picList;

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }


    /**
     * <pre>
     * 订货号
     * 表字段 : T_OP_GOODS_EXT.SPU_NO
     * </pre>
     *
     */
    private String spuNo;

    private String model;

    /**
     * 商品资料
     * OP_GOODSDATA
     */
    private String opGoodsData;

    public String getOpGoodsData() {
        return opGoodsData;
    }

    public void setOpGoodsData(String opGoodsData) {
        this.opGoodsData = opGoodsData;
    }

    /**
     * <pre>
     * 商品标题
     * 表字段 : T_OP_GOODS_EXT.SPU_TITLE
     * </pre>
     *
     */
    private String spuTitle;

    /**
     * <pre>
     * 科室
     * 表字段 : T_OP_GOODS_EXT.OP_DEPARTMENTS
     * </pre>
     *
     */
    private String opDepartments;

    /**
     * <pre>
     * 所属机构
     * 表字段 : T_OP_GOODS_EXT.OP_INSTITUTION
     * </pre>
     *
     */
    private String opInstitution;

    /**
     * <pre>
     * 卖点
     * 表字段 : T_OP_GOODS_EXT.OP_SALESPOINT
     * </pre>
     *
     */
    private String opSalespoint;

    /**
     * <pre>
     * 状态
     * 表字段 : T_OP_GOODS_EXT.STATUS
     * </pre>
     *
     */
    private Integer status;

    /**
     * <pre>
     * 是否新品
     * 表字段 : T_OP_GOODS_EXT.IS_NEW
     * </pre>
     *
     */
    private Integer isNew;

    /**
     * <pre>
     * 是否热销
     * 表字段 : T_OP_GOODS_EXT.IS_HOT
     * </pre>
     *
     */
    private Integer isHot;

    /**
     * <pre>
     * 七天无理由
     * 表字段 : T_OP_GOODS_EXT.IS_SEVEN
     * </pre>
     *
     */
    private Integer isSeven;

    /**
     * <pre>
     * 商品来源
     * 表字段 : T_OP_GOODS_EXT.OP_GOODS_RESOURCE
     * </pre>
     *
     */
    private String opGoodsResource;

    /**
     * <pre>
     * 创建人
     * 表字段 : T_OP_GOODS_EXT.CREATOR
     * </pre>
     *
     */
    private Integer creator;

    /**
     * <pre>
     * 修改时间
     * 表字段 : T_OP_GOODS_EXT.UPDATE_TIME
     * </pre>
     *
     */
    private Date updateTime;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_OP_GOODS_EXT.CREATE_TIME
     * </pre>
     *
     */
    private Date createTime;

    /**
     * <pre>
     * 更新人
     * 表字段 : T_OP_GOODS_EXT.UPDATER
     * </pre>
     *
     */
    private Integer updater;

    /**
     * <pre>
     * 删除标记（0否1是）
     * 表字段 : T_OP_GOODS_EXT.IS_DEL
     * </pre>
     *
     */

    /**
     * 商品标题
     * @return SPU_TITLE 商品标题
     */
    public String getSpuTitle() {
        return spuTitle;
    }

    /**
     * 商品标题
     * @param spuTitle 商品标题
     */
    public void setSpuTitle(String spuTitle) {
        this.spuTitle = spuTitle == null ? null : spuTitle.trim();
    }

    /**
     * 科室
     * @return OP_DEPARTMENTS 科室
     */
    public String getOpDepartments() {
        return opDepartments;
    }

    /**
     * 科室
     * @param opDepartments 科室
     */
    public void setOpDepartments(String opDepartments) {
        this.opDepartments = opDepartments == null ? null : opDepartments.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    /**
     * 所属机构
     * @return OP_INSTITUTION 所属机构
     */
    public String getOpInstitution() {
        return opInstitution;
    }

    /**
     * 所属机构
     * @param opInstitution 所属机构
     */
    public void setOpInstitution(String opInstitution) {
        this.opInstitution = opInstitution == null ? null : opInstitution.trim();
    }

    /**
     * 卖点
     * @return OP_SALESPOINT 卖点
     */
    public String getOpSalespoint() {
        return opSalespoint;
    }

    /**
     * 卖点
     * @param opSalespoint 卖点
     */
    public void setOpSalespoint(String opSalespoint) {
        this.opSalespoint = opSalespoint == null ? null : opSalespoint.trim();
    }



    /**
     * 商品来源
     * @return OP_GOODS_RESOURCE 商品来源
     */
    public String getOpGoodsResource() {
        return opGoodsResource;
    }

    /**
     * 商品来源
     * @param opGoodsResource 商品来源
     */
    public void setOpGoodsResource(String opGoodsResource) {
        this.opGoodsResource = opGoodsResource == null ? null : opGoodsResource.trim();
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
     * 修改时间
     * @return UPDATE_TIME 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 创建时间
     * @return CREATE_TIME 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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


}
