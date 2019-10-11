package com.vedeng.mjx.web.controller.app.order.vo;

public class AppOrderData {

    private Integer status;
    private String orderNo;
    private Integer pageNo;
    private Integer pageSize;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

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
