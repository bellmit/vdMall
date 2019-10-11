package com.vedeng.mjx.domain;

/**
 * V_BRAND
 */
public class VBrandWithBLOBs extends VBrand {
    /**
     * <pre>
     * seo描述
     * 表字段 : V_BRAND.SEO_DESCRIPTION
     * </pre>
     * 
     */
    private String seoDescription;

    /**
     * seo描述
     * @return SEO_DESCRIPTION seo描述
     */
    public String getSeoDescription() {
        return seoDescription;
    }

    /**
     * seo描述
     * @param seoDescription seo描述
     */
    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription == null ? null : seoDescription.trim();
    }
}