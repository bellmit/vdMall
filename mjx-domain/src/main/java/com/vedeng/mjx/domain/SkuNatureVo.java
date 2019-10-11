package com.vedeng.mjx.domain;

/***
 * class_name: SkuNatureVo
 * package: com.vedeng.mjx.domain
 * creat_user:JunLee
 * creat_date: 2019/8/15
 * creat_time: 16:55
 * describe: 封装SKU运营信息(用于接口返回)
 */

public class SkuNatureVo {

    private Integer platFormSkuId;
    private Integer skuId;
    private Integer spuId;
    private String skuNo;
    //运营副标题
    private String subTitle;
    private String skuName;
    private String showName;
    private Integer isOnSale;//是否上下架 1:是，0否
    private Integer isNew;//是否新品
    private Integer isHot;//是否热销
    private Integer isSeven;//0：否；1:是（七天无理由）
    private Integer isOpShow;//是否显示攻略：0不显示，1显示
    private Integer isChosen;//是否精选U：0否，1是
    //适用机构
    private String opInstitution;
    //卖点U
    private String opSalespoint;
    //竞品U
    private String opCompete;
    //商品资料U
    private String opGoodsData;
    /**
     * 贝登市场价
     */
    private String marketPrice;
    /**
     * 会员价
     */
    private String memberPrice;
    /**
     * 成本价
     */
    private String costPrice;

    /**
     * 贝登销售价
     */
    private String salePrice;

    public Integer getPlatFormSkuId() {
        return platFormSkuId;
    }

    public void setPlatFormSkuId(Integer platFormSkuId) {
        this.platFormSkuId = platFormSkuId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getIsSeven() {
        return isSeven;
    }

    public void setIsSeven(Integer isSeven) {
        this.isSeven = isSeven;
    }

    public Integer getIsOpShow() {
        return isOpShow;
    }

    public void setIsOpShow(Integer isOpShow) {
        this.isOpShow = isOpShow;
    }

    public Integer getIsChosen() {
        return isChosen;
    }

    public void setIsChosen(Integer isChosen) {
        this.isChosen = isChosen;
    }

    public String getOpInstitution() {
        return opInstitution;
    }

    public void setOpInstitution(String opInstitution) {
        this.opInstitution = opInstitution;
    }

    public String getOpSalespoint() {
        return opSalespoint;
    }

    public void setOpSalespoint(String opSalespoint) {
        this.opSalespoint = opSalespoint;
    }

    public String getOpCompete() {
        return opCompete;
    }

    public void setOpCompete(String opCompete) {
        this.opCompete = opCompete;
    }

    public String getOpGoodsData() {
        return opGoodsData;
    }

    public void setOpGoodsData(String opGoodsData) {
        this.opGoodsData = opGoodsData;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }
}
