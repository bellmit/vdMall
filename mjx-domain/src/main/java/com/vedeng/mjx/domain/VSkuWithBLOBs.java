package com.vedeng.mjx.domain;

/**
 * V_SKU
 */
public class VSkuWithBLOBs extends VSku {
    /**
     * <pre>
     * 商品标题
     * 表字段 : V_SKU.SKU_TITLE
     * </pre>
     * 
     */
    private String skuTitle;

    /**
     * <pre>
     * 竞品
     * 表字段 : V_SKU.OP_COMPETE
     * </pre>
     * 
     */
    private String opCompete;

    /**
     * <pre>
     * 商品资料
     * 表字段 : V_SKU.OP_GOODSDATA
     * </pre>
     * 
     */
    private String opGoodsdata;

    /**
     * 商品标题
     * @return SKU_TITLE 商品标题
     */
    public String getSkuTitle() {
        return skuTitle;
    }

    /**
     * 商品标题
     * @param skuTitle 商品标题
     */
    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle == null ? null : skuTitle.trim();
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