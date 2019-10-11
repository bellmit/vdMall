package com.vedeng.mjx.domain.ext;

import java.util.List;

public class RequestParameter {
    private String skuNo;
    /**
     * 搜索关键词
     */
    private String keyword;
    /**
     * 搜索品牌
     */
    private String brandId;
    /**
     * 限制的sku，如果为空，则查询所有
     */
    private List<String> skuNoList;
    //属性id
    private List<Integer> attrIdList;
    //科室Id
    private List<Integer> deptIdList;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public List<String> getSkuNoList() {
        return skuNoList;
    }

    public void setSkuNoList(List<String> skuNoList) {
        this.skuNoList = skuNoList;
    }

    public List<Integer> getAttrIdList() {
        return attrIdList;
    }

    public void setAttrIdList(List<Integer> attrIdList) {
        this.attrIdList = attrIdList;
    }

    public List<Integer> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<Integer> deptIdList) {
        this.deptIdList = deptIdList;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }
}
