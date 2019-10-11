package com.vedeng.mjx.web.controller.message.vo;

public class MessageVo {

    private Integer messageId;
    private Integer messageType;
    private Integer pageNo;
    private Integer pageSize;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
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
