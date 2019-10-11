package com.vedeng.mjx.domain;

/**
 * T_DEPARTMENTS_HOSPITAL
 */
public class TDepartmentsHospital extends TDepartmentsHospitalKey {
    /**
     * <pre>
     * 科室名称
     * 表字段 : T_DEPARTMENTS_HOSPITAL.DEPARTMENT_NAME
     * </pre>
     * 
     */
    private String departmentName;

    /**
     * <pre>
     * 科室说明
     * 表字段 : T_DEPARTMENTS_HOSPITAL.DESCRIPTION
     * </pre>
     * 
     */
    private String description;

    /**
     * <pre>
     * 删除状态 ： 0 未删除  1 已删除
     * 表字段 : T_DEPARTMENTS_HOSPITAL.IS_DELETE
     * </pre>
     * 
     */
    private Boolean isDelete;

    /**
     * <pre>
     * 更新人
     * 表字段 : T_DEPARTMENTS_HOSPITAL.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * 更新时间
     * 表字段 : T_DEPARTMENTS_HOSPITAL.MOD_TIME
     * </pre>
     * 
     */
    private Long modTime;

    /**
     * <pre>
     * 创建人
     * 表字段 : T_DEPARTMENTS_HOSPITAL.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_DEPARTMENTS_HOSPITAL.ADD_TIME
     * </pre>
     * 
     */
    private Long addTime;

    /**
     * 科室名称
     * @return DEPARTMENT_NAME 科室名称
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * 科室名称
     * @param departmentName 科室名称
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    /**
     * 科室说明
     * @return DESCRIPTION 科室说明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 科室说明
     * @param description 科室说明
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 删除状态 ： 0 未删除  1 已删除
     * @return IS_DELETE 删除状态 ： 0 未删除  1 已删除
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 删除状态 ： 0 未删除  1 已删除
     * @param isDelete 删除状态 ： 0 未删除  1 已删除
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 更新人
     * @return UPDATER 更新人
     */
    public Integer getUpdater() {
        return updater;
    }

    /**
     * 更新人
     * @param updater 更新人
     */
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    /**
     * 更新时间
     * @return MOD_TIME 更新时间
     */
    public Long getModTime() {
        return modTime;
    }

    /**
     * 更新时间
     * @param modTime 更新时间
     */
    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    /**
     * 创建人
     * @return CREATOR 创建人
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 创建人
     * @param creator 创建人
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 创建时间
     * @return ADD_TIME 创建时间
     */
    public Long getAddTime() {
        return addTime;
    }

    /**
     * 创建时间
     * @param addTime 创建时间
     */
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}