package com.vedeng.mjx.domain;

/**
 * 分享的信息类
 */
public class ShareInfo {
    /**
     * 分享类型(暂定)
     * 1 : 详情页
     * 2 : 关于我们
     */
    private Integer shareType;
    /**
     * 分享补充信息
     */
    private String title;
    private String description;

    private String webpageUrl;

    private String thumbDataUrl;

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebpageUrl() {
        return webpageUrl;
    }

    public void setWebpageUrl(String webpageUrl) {
        this.webpageUrl = webpageUrl;
    }

    public String getThumbDataUrl() {
        return thumbDataUrl;
    }

    public void setThumbDataUrl(String thumbDataUrl) {
        this.thumbDataUrl = thumbDataUrl;
    }
}
