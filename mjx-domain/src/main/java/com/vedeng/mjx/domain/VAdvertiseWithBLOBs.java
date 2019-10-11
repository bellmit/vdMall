package com.vedeng.mjx.domain;

/**
 * V_ADVERTISE
 */
public class VAdvertiseWithBLOBs extends VAdvertise {
    /**
     * <pre>
     * 备注
     * 表字段 : V_ADVERTISE.REMARK
     * </pre>
     * 
     */
    private String remark;

    /**
     * 备注
     * @return REMARK 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}