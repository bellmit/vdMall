package com.vedeng.mjx.domain;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Vmessage {

    private Integer messageId;

    private Integer messageType;

    private String messageTitle;

    private String messagePic;

    private String messageContent;

    private String orderNo;

    private String goodIconUrl;

    private Boolean isRead;

    private Date readTime;

    private Integer userId;

    private Integer bizId;

    private Date addTime;

    private Integer creator;

    private Date modTime;

    private Integer updater;

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

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle == null ? null : messageTitle.trim();
    }

    public String getMessagePic() {
        return messagePic;
    }

    public void setMessagePic(String messagePic) {
        this.messagePic = messagePic == null ? null : messagePic.trim();
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGoodIconUrl() {
        return goodIconUrl;
    }

    public void setGoodIconUrl(String goodIconUrl) {
        this.goodIconUrl = goodIconUrl;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public String getReadTime() {
        if (null != readTime) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
            String str = df.format(readTime);
            return str;
        }
        return "";
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBizId() {
        return bizId;
    }

    public void setBizId(Integer bizId) {
        this.bizId = bizId;
    }

    public String getAddTime() {
        if (null != addTime) {
            return VeDate.getMessageDate(this.addTime);
        }
        return null;

    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }
}