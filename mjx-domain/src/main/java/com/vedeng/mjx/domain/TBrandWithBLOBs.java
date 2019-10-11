package com.vedeng.mjx.domain;

/**
 * T_BRAND
 */
public class TBrandWithBLOBs extends TBrand {
    /**
     * <pre>
     * 描述
     * 表字段 : T_BRAND.DESCRIPTION
     * </pre>
     * 
     */
    private String description;

    /**
     * 描述
     * @return DESCRIPTION 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}