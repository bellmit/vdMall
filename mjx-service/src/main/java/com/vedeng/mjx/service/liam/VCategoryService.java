package com.vedeng.mjx.service.liam;

import com.vedeng.goods.api.dto.CategoryDTO;
import com.vedeng.goods.api.vo.SearchSkuResult;
import com.vedeng.mjx.common.searchVo.SearchResult;
import com.vedeng.mjx.domain.VCategory;
import com.vedeng.mjx.domain.ext.VCategoryDto;

import java.util.List;

public interface VCategoryService {

    public List<VCategory> queryCategoryOneList();

    public VCategory queryCategoryById(Integer id);

    public List<VCategoryDto> queryCategoryAllList(Integer cat1Id);

    public List<VCategory> queryCategoryByParentId(Integer parentid);

    public List<CategoryDTO> queryLevel3Category(List<CategoryDTO> categoryDTOS);

    public List<CategoryDTO> queryLevel3CategoryI(List<Integer> categoryDTOS);

    public SearchResult getSearchResult(Integer currentPage, Integer pageSize, Integer navigatePages, SearchSkuResult search);
}
