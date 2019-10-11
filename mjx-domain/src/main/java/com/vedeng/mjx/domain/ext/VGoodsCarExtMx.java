package com.vedeng.mjx.domain.ext;

import com.vedeng.mjx.domain.VGoodsCarMx;

import java.util.List;

/**
 * @ auther:购物车扩展实体类
 * @ date:  2019/7/10 14:30
 */
public class VGoodsCarExtMx extends VGoodsCarMx {

    /**
     * 购物车中的商品集合
     */
    List<TOpGoodsExtExt> goodsList;

    /**
     * 是否关闭（0否1是）
     */
    String isClose;

    /**
     * 明细ID
     */
    private Integer carMxId;

    /**
     * 传参类型isCount:0从商品详情页查购物车的数量，1查购物车列表，2：查提交订单页面时的商品
     * @return
     */
    private String isCount;

    /**
     * 客户ID
     * @return
     */
    private Integer accountId;

    /**
     * 购物车ID
     * @return
     */
    private Integer carId;

    /**
     * 批量skuNo用逗号隔开
     * @return
     */
    private String skuNos;

    /**
     * skuNo集合
     * @return
     */
    private List<String> skuNoList;

    /**
     * 是否有权限看市场价(0否1是)
     * @return
     */
    private String isMarketPrice;

    /**
     * 状态
     * @return
     */
    private Integer status;

    private String carMxIdsExt;
    private Integer isOnSale;

    private List<Integer> carMxIdList;

    public List<Integer> getCarMxIdList() {
        return carMxIdList;
    }

    public void setCarMxIdList(List<Integer> carMxIdList) {
        this.carMxIdList = carMxIdList;
    }

    public Integer getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }

    public String getCarMxIdsExt() {
        return carMxIdsExt;
    }

    public void setCarMxIdsExt(String carMxIdsExt) {
        this.carMxIdsExt = carMxIdsExt;
    }

    @Override
    public Integer getCarMxId() {
        return carMxId;
    }

    @Override
    public void setCarMxId(Integer carMxId) {
        this.carMxId = carMxId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIsMarketPrice() {
        return isMarketPrice;
    }

    public void setIsMarketPrice(String isMarketPrice) {
        this.isMarketPrice = isMarketPrice;
    }

    public List<String> getSkuNoList() {
        return skuNoList;
    }

    public void setSkuNoList(List<String> skuNoList) {
        this.skuNoList = skuNoList;
    }

    public String getSkuNos() {
        return skuNos;
    }

    public String getIsClose() {
        return isClose;
    }

    public void setIsClose(String isClose) {
        this.isClose = isClose;
    }

    public void setSkuNos(String skuNos) {
        this.skuNos = skuNos;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getIsCount() {
        return isCount;
    }

    public void setIsCount(String isCount) {
        this.isCount = isCount;
    }

    public List<TOpGoodsExtExt> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<TOpGoodsExtExt> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public Integer getCarId() {
        return carId;
    }

    @Override
    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}

