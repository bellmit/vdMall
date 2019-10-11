package com.vedeng.mjx.domain.ext;

import com.vedeng.mjx.domain.TOpAccountAddress;
import com.vedeng.mjx.domain.TRegion;

import java.util.List;

/**
 * T_OP_ACCOUNT_ADDRESS
 */
public class VAccountAddressExt extends TOpAccountAddress {
    /**
     * 最终详细地址
     */
    private String resultAddress;

    /**
     * 0是新增，1是修改
     */
    private String flag;


    /**
     * 当前页数
     */
    private Integer pagerNumber;

    /**
     * 当前行数
     */
    private Integer pageSize;

    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 省份Code
     */
    private String provinceCode;
    /**
     * 城市Code
     */
    private String cityCode;
    /**
     * 区Code
     */
    private String districtCode;

    /**
     * 标识位参数标识，1:省份，2:城市，3:区县
     */
    String flagId;

    /**
     * 省市区的集合
     */
    List<TRegion> regionList;

    /**
     * 地址是否默认0否1是
     */
    private String isDefAddress;
    /**
     * 票据是否默认0否1是
     */
    private String isDefTicket;

    /**
     * 地址主键
     */
    private Integer addressId;

    /**
     * <pre>
     * 区域（T_REGION主键）
     * 表字段 : T_OP_ACCOUNT_ADDRESS.REGION_ID
     * </pre>
     *
     */
    private Integer regionId;

    /**
     * <pre>
     * 多级地址id，逗号拼接（冗余字段）
     * 表字段 : T_OP_ACCOUNT_ADDRESS.REGION_IDS
     * </pre>
     *
     */
    private String regionIds;

    /**
     * <pre>
     * 多级地址拼接（冗余字段）
     * 表字段 : T_OP_ACCOUNT_ADDRESS.REGION_NAME
     * </pre>
     *
     */
    private String regionName;

    /**
     * <pre>
     * 登陆人id
     * 表字段 : T_OP_ACCOUNT_ADDRESS.ACCOUNT_ID
     * </pre>
     *
     */
    private Integer accountId;

    /**
     * <pre>
     * 收货（票）人名称
     * 表字段 : T_OP_ACCOUNT_ADDRESS.ACCOUNT_NAME
     * </pre>
     *
     */
    private String accountName;

    /**
     * <pre>
     * 地址详情
     * 表字段 : T_OP_ACCOUNT_ADDRESS.ADDRESS_INFO
     * </pre>
     *
     */
    private String addressInfo;

    /**
     * <pre>
     * 手机
     * 表字段 : T_OP_ACCOUNT_ADDRESS.PHONE
     * </pre>
     *
     */
    private String phone;

    /**
     * <pre>
     * 是否默认 0-否 | 1-默认
     * 表字段 : T_OP_ACCOUNT_ADDRESS.IS_DEFAULT
     * </pre>
     *
     */
    private Boolean isDefault;

    /**
     * <pre>
     * 邮政编码
     * 表字段 : T_OP_ACCOUNT_ADDRESS.POSTCODE
     * </pre>
     *
     */
    private String postcode;

    /**
     * <pre>
     * 地址排序
     * 表字段 : T_OP_ACCOUNT_ADDRESS.SORT
     * </pre>
     *
     */
    private Integer sort;

    /**
     * <pre>
     * 添加时间
     * 表字段 : T_OP_ACCOUNT_ADDRESS.ADD_TIME
     * </pre>
     *
     */
    private Long addTime;

    /**
     * <pre>
     * 更新时间
     * 表字段 : T_OP_ACCOUNT_ADDRESS.MOD_TIME
     * </pre>
     *
     */
    private Long modTime;

    /**
     * <pre>
     * 创建人
     * 表字段 : T_OP_ACCOUNT_ADDRESS.CREATOR
     * </pre>
     *
     */
    private Integer creator;

    /**
     * <pre>
     * 更新人
     * 表字段 : T_OP_ACCOUNT_ADDRESS.UPDATER
     * </pre>
     *
     */
    private Integer updater;

    /**
     * <pre>
     * 标签（如：南京仓库）
     * 表字段 : T_OP_ACCOUNT_ADDRESS.LABEL
     * </pre>
     *
     */
    private String label;

    /**
     * <pre>
     * 删除标志 0-未删除 | 1-已删除
     * 表字段 : T_OP_ACCOUNT_ADDRESS.IS_DELETE
     * </pre>
     *
     */
    private Boolean isDelete;
    /**
     * <pre>
     * 用户ID
     * 表字段 : T_OP_ACCOUNT.PHONE
     * </pre>
     *
     */
    private String curUserPhoneNum;


    public String getCurUserPhoneNum() {
        return curUserPhoneNum;
    }

    public void setCurUserPhoneNum(String curUserPhoneNum) {
        this.curUserPhoneNum = curUserPhoneNum;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIsDefAddress() {
        return isDefAddress;
    }

    public void setIsDefAddress(String isDefAddress) {
        this.isDefAddress = isDefAddress;
    }

    public String getIsDefTicket() {
        return isDefTicket;
    }

    public void setIsDefTicket(String isDefTicket) {
        this.isDefTicket = isDefTicket;
    }



    /**
     * 区域（T_REGION主键）
     * @return REGION_ID 区域（T_REGION主键）
     */
    public Integer getRegionId() {
        return regionId;
    }

    /**
     * 区域（T_REGION主键）
     * @param regionId 区域（T_REGION主键）
     */
    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    /**
     * 多级地址id，逗号拼接（冗余字段）
     * @return REGION_IDS 多级地址id，逗号拼接（冗余字段）
     */
    public String getRegionIds() {
        return regionIds;
    }

    /**
     * 多级地址id，逗号拼接（冗余字段）
     * @param regionIds 多级地址id，逗号拼接（冗余字段）
     */
    public void setRegionIds(String regionIds) {
        this.regionIds = regionIds == null ? null : regionIds.trim();
    }

    /**
     * 多级地址拼接（冗余字段）
     * @return REGION_NAME 多级地址拼接（冗余字段）
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * 多级地址拼接（冗余字段）
     * @param regionName 多级地址拼接（冗余字段）
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    /**
     * 登陆人id
     * @return ACCOUNT_ID 登陆人id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 登陆人id
     * @param accountId 登陆人id
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 收货（票）人名称
     * @return ACCOUNT_NAME 收货（票）人名称
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 收货（票）人名称
     * @param accountName 收货（票）人名称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    /**
     * 地址详情
     * @return ADDRESS_INFO 地址详情
     */
    public String getAddressInfo() {
        return addressInfo;
    }

    /**
     * 地址详情
     * @param addressInfo 地址详情
     */
    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo == null ? null : addressInfo.trim();
    }

    /**
     * 手机
     * @return PHONE 手机
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 手机
     * @param phone 手机
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 是否默认 0-否 | 1-默认
     * @return IS_DEFAULT 是否默认 0-否 | 1-默认
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 是否默认 0-否 | 1-默认
     * @param isDefault 是否默认 0-否 | 1-默认
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 邮政编码
     * @return POSTCODE 邮政编码
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * 邮政编码
     * @param postcode 邮政编码
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    /**
     * 地址排序
     * @return SORT 地址排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 地址排序
     * @param sort 地址排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 添加时间
     * @return ADD_TIME 添加时间
     */
    public Long getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     * @param addTime 添加时间
     */
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
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
     * 标签（如：南京仓库）
     * @return LABEL 标签（如：南京仓库）
     */
    public String getLabel() {
        return label;
    }

    /**
     * 标签（如：南京仓库）
     * @param label 标签（如：南京仓库）
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * 删除标志 0-未删除 | 1-已删除
     * @return IS_DELETE 删除标志 0-未删除 | 1-已删除
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 删除标志 0-未删除 | 1-已删除
     * @param isDelete 删除标志 0-未删除 | 1-已删除
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getFlagId() {
        return flagId;
    }

    public void setFlagId(String flagId) {
        this.flagId = flagId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
    public List<TRegion> getRegionList() {
        return regionList;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setRegionList(List<TRegion> regionList) {
        this.regionList = regionList;
    }

    public Integer getPagerNumber() {
        return pagerNumber;
    }

    public void setPagerNumber(Integer pagerNumber) {
        this.pagerNumber = pagerNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getResultAddress() {
        return resultAddress;
    }

    public void setResultAddress(String resultAddress) {
        this.resultAddress = resultAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VAccountAddressExt that = (VAccountAddressExt) o;
        //主键ID一致默认为相同
        if(that.getAddressId()==null ||this.getAddressId()==null){
            return false;
        }
        if (that.getAddressId() == this.getAddressId()) {
            return true;
        }

        return false;
    }


}