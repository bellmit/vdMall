package com.vedeng.mjx.web.controller.m.vo;

import com.vedeng.goods.api.vo.SkuListVO;
import com.vedeng.mjx.common.indexVo.BannerData;
import com.vedeng.mjx.domain.TBrandData;
import com.vedeng.mjx.service.goods.impl.MFloorVo;

import java.util.List;

public class MIndexData {

    private boolean checkPriceAuth;
    private List<BannerData> bannerList;
    private List<MFloorVo> level1CategoryList;
    private List<TBrandData> brandList;
    private List<MFloorVo> level3CategoryList;
    private List<String> searchName;
    private List<SkuListVO> skuListVOS;
    private Integer goodCarCount;

    public boolean isCheckPriceAuth() {
        return checkPriceAuth;
    }

    public void setCheckPriceAuth(boolean checkPriceAuth) {
        this.checkPriceAuth = checkPriceAuth;
    }

    public List<BannerData> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerData> bannerList) {
        this.bannerList = bannerList;
    }

    public List<MFloorVo> getLevel1CategoryList() {
        return level1CategoryList;
    }

    public void setLevel1CategoryList(List<MFloorVo> level1CategoryList) {
        this.level1CategoryList = level1CategoryList;
    }

    public List<TBrandData> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<TBrandData> brandList) {
        this.brandList = brandList;
    }

    public List<MFloorVo> getLevel3CategoryList() {
        return level3CategoryList;
    }

    public void setLevel3CategoryList(List<MFloorVo> level3CategoryList) {
        this.level3CategoryList = level3CategoryList;
    }

    public List<String> getSearchName() {
        return searchName;
    }

    public void setSearchName(List<String> searchName) {
        this.searchName = searchName;
    }

    public List<SkuListVO> getSkuListVOS() {
        return skuListVOS;
    }

    public void setSkuListVOS(List<SkuListVO> skuListVOS) {
        this.skuListVOS = skuListVOS;
    }

    public Integer getGoodCarCount() {
        return goodCarCount;
    }

    public void setGoodCarCount(Integer goodCarCount) {
        this.goodCarCount = goodCarCount;
    }
}
