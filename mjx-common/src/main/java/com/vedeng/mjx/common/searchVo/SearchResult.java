package com.vedeng.mjx.common.searchVo;

import com.github.pagehelper.PageInfo;
import com.vedeng.goods.api.vo.SearchInfo;

public class SearchResult {
    private PageInfo pageInfo;
    private FilterCondition filterCondition;


    private SearchInfo searchInfo;

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    public SearchResult() {
    }


    public SearchResult(PageInfo pageInfo, FilterCondition filterCondition) {
        this.pageInfo = pageInfo;
        this.filterCondition = filterCondition;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public FilterCondition getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(FilterCondition filterCondition) {
        this.filterCondition = filterCondition;
    }
}
