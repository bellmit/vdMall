package com.vedeng.mjx.domain.ext;

import com.vedeng.mjx.domain.SearchVoCategory;
import com.vedeng.mjx.domain.VCategory;

import java.util.List;

public class VCategoryDto extends SearchVoCategory{

    private List<VCategoryDto> childCategoryList;

    public List<VCategoryDto> getChildCategoryList() {
        return childCategoryList;
    }

    public void setChildCategoryList(List<VCategoryDto> childCategoryList) {
        this.childCategoryList = childCategoryList;
    }
}
