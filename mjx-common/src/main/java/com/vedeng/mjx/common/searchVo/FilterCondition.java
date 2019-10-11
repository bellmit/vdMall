package com.vedeng.mjx.common.searchVo;

import com.vedeng.goods.api.dto.BrandDTO;
import com.vedeng.goods.api.dto.CategoryDTO;
import com.vedeng.goods.api.dto.DepartmentDTO;
import com.vedeng.goods.api.dto.SearchAttrDTO;
import com.vedeng.goods.api.vo.SearchSkuResult;

import java.util.List;

public class FilterCondition {

    private List<DepartmentDTO> deptList;
    private List<BrandDTO> brandList;
    private List<SearchAttrDTO> attrList;
    private List<CategoryDTO> categoryList;




    public List<DepartmentDTO> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DepartmentDTO> deptList) {
        this.deptList = deptList;
    }

    public List<BrandDTO> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<BrandDTO> brandList) {
        this.brandList = brandList;
    }

    public List<SearchAttrDTO> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<SearchAttrDTO> attrList) {
        this.attrList = attrList;
    }

    public List<CategoryDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryDTO> categoryList) {
        this.categoryList = categoryList;
    }


    public void searchSkuResultoFilterCondition(SearchSkuResult searchSkuResult){
       this.deptList = searchSkuResult.getDeptList();
       this.brandList = searchSkuResult.getBrandList();
       this.attrList = searchSkuResult.getAttrList();
       this.categoryList = searchSkuResult.getCategoryList();
    }
}
