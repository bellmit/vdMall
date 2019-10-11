package com.vedeng.mjx.service.goods.impl;

import com.vedeng.goods.api.vo.SearchSkuResult;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.domain.ext.TCategoryJxExt;

import java.util.List;

public class MFloorVo {

    private Integer vCategoryId;
    private Integer categoryCode;
    private String categoryName;
    private String url;

    public Integer getvCategoryId() {
        return vCategoryId;
    }

    public void setvCategoryId(Integer vCategoryId) {
        this.vCategoryId = vCategoryId;
    }

    public Integer getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(Integer categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
