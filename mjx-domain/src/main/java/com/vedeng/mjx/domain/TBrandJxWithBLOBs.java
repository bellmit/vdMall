package com.vedeng.mjx.domain;

/**
 * T_BRAND_JX
 */
public class TBrandJxWithBLOBs extends TBrandJx {
    /**
     * <pre>
     * 售后说明
     * 表字段 : T_BRAND_JX.AFTER_SALE_DESCRIPTION
     * </pre>
     * 
     */
    private String afterSaleDescription;

    /**
     * 售后说明
     * @return AFTER_SALE_DESCRIPTION 售后说明
     */
    public String getAfterSaleDescription() {
        return afterSaleDescription;
    }

    /**
     * 售后说明
     * @param afterSaleDescription 售后说明
     */
    public void setAfterSaleDescription(String afterSaleDescription) {
        this.afterSaleDescription = afterSaleDescription == null ? null : afterSaleDescription.trim();
    }
}