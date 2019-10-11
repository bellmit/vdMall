package com.vedeng.mjx.common.searchVo;

import com.vedeng.goods.api.SearchParameter;

import java.util.List;

public class GoodsSearchParameter extends SearchParameter {

    private Integer navigatePages;

    public Integer getNavigatePages() {
        return navigatePages == null ? 4 : navigatePages;
    }

    public void setNavigatePages(Integer navigatePages) {
        this.navigatePages = navigatePages;
    }


}
