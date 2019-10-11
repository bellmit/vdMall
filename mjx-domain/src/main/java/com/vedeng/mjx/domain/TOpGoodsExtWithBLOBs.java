package com.vedeng.mjx.domain;

/**
 * T_OP_GOODS_EXT
 */
public class TOpGoodsExtWithBLOBs extends TOpGoodsExt {
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
     * 竞品
     * 表字段 : T_OP_GOODS_EXT.OP_COMPETE
     * </pre>
     * 
     */
    private String opCompete;

    /**
     * <pre>
     * 商品资料
     * 表字段 : T_OP_GOODS_EXT.OP_GOODSDATA
     * </pre>
     * 
     */
    private String opGoodsdata;

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
     * 竞品
     * @return OP_COMPETE 竞品
     */
    public String getOpCompete() {
        return opCompete;
    }

    /**
     * 竞品
     * @param opCompete 竞品
     */
    public void setOpCompete(String opCompete) {
        this.opCompete = opCompete == null ? null : opCompete.trim();
    }

    /**
     * 商品资料
     * @return OP_GOODSDATA 商品资料
     */
    public String getOpGoodsdata() {
        return opGoodsdata;
    }

    /**
     * 商品资料
     * @param opGoodsdata 商品资料
     */
    public void setOpGoodsdata(String opGoodsdata) {
        this.opGoodsdata = opGoodsdata == null ? null : opGoodsdata.trim();
    }
}