package com.vedeng.mjx.common.page;

public class PageModel {

    private Integer pageNo;
    private Integer pageSize;

    public Integer getPageNo() {
        if(this.pageNo == null){
            pageNo = 1;
        }
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        if(this.pageSize == null){
            pageSize = 20;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
