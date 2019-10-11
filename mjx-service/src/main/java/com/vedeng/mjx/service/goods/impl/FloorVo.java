package com.vedeng.mjx.service.goods.impl;

import com.vedeng.goods.api.vo.SearchSkuResult;
import com.vedeng.goods.api.vo.SkuListVO;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.domain.ext.TCategoryJxExt;

import java.util.List;

public class FloorVo {
    private String categoryOneName;
    private Integer categoryOneCode;
    private Integer categoryOneId;
    private String url;
    private List<TCategoryJxExt> categoryTwoList;
    private List<SkuVO> productList;
    private List<SkuListVO> skuList;

    public List<Integer> getCategoryThreeIdList() {
        return categoryThreeIdList;
    }

    public void setCategoryThreeIdList(List<Integer> categoryThreeIdList) {
        this.categoryThreeIdList = categoryThreeIdList;
    }

    public Integer getCategoryOneCode() {
        return categoryOneCode;
    }

    public void setCategoryOneCode(Integer categoryOneCode) {
        this.categoryOneCode = categoryOneCode;
    }

    private  List<Integer> categoryThreeIdList;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoryOneName() {
        return categoryOneName;
    }

    public void setCategoryOneName(String categoryOneName) {
        this.categoryOneName = categoryOneName;
    }

    public Integer getCategoryOneId() {
        return categoryOneId;
    }

    public void setCategoryOneId(Integer categoryOneId) {
        this.categoryOneId = categoryOneId;
    }

    public List<TCategoryJxExt> getCategoryTwoList() {
        return categoryTwoList;
    }

    public void setCategoryTwoList(List<TCategoryJxExt> categoryTwoList) {
        this.categoryTwoList = categoryTwoList;
    }

    public List<SkuVO> getProductList() {
        return productList;
    }

    public void setProductList(List<SkuVO> productList) {
        this.productList = productList;
    }

    public List<SkuListVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuListVO> skuList) {
        this.skuList = skuList;
    }
}
