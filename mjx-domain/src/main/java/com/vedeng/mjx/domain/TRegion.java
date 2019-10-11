package com.vedeng.mjx.domain;

/**
 * T_REGION
 */
public class TRegion extends TRegionKey {
    /**
     * <pre>
     * 该地区父节点的地区编号
     * 表字段 : T_REGION.PARENT_ID
     * </pre>
     * 
     */
    private Integer parentId;

    /**
     * <pre>
     * 地区名
     * 表字段 : T_REGION.REGION_NAME
     * </pre>
     * 
     */
    private String regionName;

    /**
     * <pre>
     * 地区类型
     * 表字段 : T_REGION.REGION_TYPE
     * </pre>
     * 
     */
    private Boolean regionType;

    /**
     * <pre>
     * 办事处的id,这里有一个bug,同一个省不能有多个办事处,该字段只记录最新的那个办事处的id
     * 表字段 : T_REGION.AGENCY_ID
     * </pre>
     * 
     */
    private Integer agencyId;

    /**
     * 该地区父节点的地区编号
     * @return PARENT_ID 该地区父节点的地区编号
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 该地区父节点的地区编号
     * @param parentId 该地区父节点的地区编号
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 地区名
     * @return REGION_NAME 地区名
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * 地区名
     * @param regionName 地区名
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    /**
     * 地区类型
     * @return REGION_TYPE 地区类型
     */
    public Boolean getRegionType() {
        return regionType;
    }

    /**
     * 地区类型
     * @param regionType 地区类型
     */
    public void setRegionType(Boolean regionType) {
        this.regionType = regionType;
    }

    /**
     * 办事处的id,这里有一个bug,同一个省不能有多个办事处,该字段只记录最新的那个办事处的id
     * @return AGENCY_ID 办事处的id,这里有一个bug,同一个省不能有多个办事处,该字段只记录最新的那个办事处的id
     */
    public Integer getAgencyId() {
        return agencyId;
    }

    /**
     * 办事处的id,这里有一个bug,同一个省不能有多个办事处,该字段只记录最新的那个办事处的id
     * @param agencyId 办事处的id,这里有一个bug,同一个省不能有多个办事处,该字段只记录最新的那个办事处的id
     */
    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }
}