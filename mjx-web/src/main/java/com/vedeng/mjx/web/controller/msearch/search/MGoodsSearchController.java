package com.vedeng.mjx.web.controller.msearch.search;


import com.vedeng.goods.api.SearchParameter;
import com.vedeng.goods.api.dto.CategoryDTO;
import com.vedeng.goods.api.vo.SearchInfo;
import com.vedeng.goods.api.vo.SearchSkuResult;
import com.vedeng.goods.api.yxgParamDtos.YxgSearchParameter;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.searchVo.FilterCondition;
import com.vedeng.mjx.common.searchVo.GoodsSearchParameter;
import com.vedeng.mjx.common.searchVo.SearchResult;
import com.vedeng.mjx.domain.TBrand;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.domain.VBrand;
import com.vedeng.mjx.domain.VCategory;
import com.vedeng.mjx.service.feign.goods.GoodsSearchFeignApi;
import com.vedeng.mjx.service.liam.TBrandService;
import com.vedeng.mjx.service.liam.VCategoryService;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.web.controller.base.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/m/goodsSearch")
public class MGoodsSearchController extends BaseController {

    @Resource
    private GoodsSearchFeignApi goodsSearchFeignApi;

    @Resource
    private VCategoryService vCategoryService;

    @Resource
    private TBrandService tbrandService;

@PostMapping("/bykeyWords")
@ResponseBody
    public ResultInfo getgoodsbysolr( GoodsSearchParameter searchParameter){
    SearchSkuResult search =null;
    Integer currentPage = searchParameter.getCurrentPage();
    Integer pageSize = searchParameter.getPageSize();
    Integer navigatePages=searchParameter.getNavigatePages();
    if(currentPage==null){
        currentPage = 1;
    }

    if (pageSize==null)
    {
        pageSize =10;
    }
    if (navigatePages==null)
    {
        navigatePages =4;
    }
    try {
        searchParameter.setCurrentPage(currentPage);
        searchParameter.setPageSize(pageSize);
        search= goodsSearchFeignApi.searchByKeyword(searchParameter);

        SearchResult searchResult = vCategoryService.getSearchResult(currentPage, pageSize, navigatePages, search);

        return ResultInfo.success(searchResult);

    } catch (Exception e) {
        e.printStackTrace();
        return ResultInfo.fail(e);
    }

}

    @PostMapping("/searchByCategoryIdAndLevel")
    @ResponseBody
    public ResultInfo searchByCategoryIdAndLevel( GoodsSearchParameter searchParameter){
        SearchSkuResult search =null;
        Integer currentPage = searchParameter.getCurrentPage();
        Integer pageSize = searchParameter.getPageSize();
        Integer navigatePages=searchParameter.getNavigatePages();
        if(currentPage==null){
            currentPage = 1;
        }
        if (pageSize==null) {
            pageSize =10;
        }
        if (navigatePages==null) {
            navigatePages =4;
        }
        try {
            searchParameter.setCurrentPage(currentPage);
            searchParameter.setPageSize(pageSize);
            search= goodsSearchFeignApi.searchByCategoryIdAndLevel(searchParameter);
            List<CategoryDTO> categoryDTOS = vCategoryService.queryLevel3Category(search.getCategoryList());
            search.setCategoryList(categoryDTOS);
            SearchResult searchResult = vCategoryService.getSearchResult(currentPage, pageSize, navigatePages, search);
            return ResultInfo.success(searchResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail(e);
        }
    }
@PostMapping("/getAllAttrByCat3")
@ResponseBody
public FilterCondition getAllAttrByCat3(SearchParameter SearchParameter ){
    try {
        SearchSkuResult search = goodsSearchFeignApi.getAllAttrByCat3(SearchParameter);
        FilterCondition filterCondition = new FilterCondition();
        filterCondition.searchSkuResultoFilterCondition(search);
        return filterCondition;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


    @PostMapping("/getNewSearch")
    @ResponseBody
    public ResultInfo getNewSearch(@RequestBody YxgSearchParameter yxgSearchParameter, HttpServletRequest request) {
        SearchSkuResult search = null;
        Integer currentPage = yxgSearchParameter.getCurrentPage();
        Integer pageSize = yxgSearchParameter.getPageSize();
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        try {
            yxgSearchParameter.setPlatformId(1);
            yxgSearchParameter.setCurrentPage(currentPage);
            yxgSearchParameter.setPageSize(pageSize);
            if (StringUtils.isNotBlank(yxgSearchParameter.getKeyword())){
                TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
                if (jx != null) {
                    yxgSearchParameter.setUserId(jx.getAccountId());
                }
                String remoteAddr =request.getHeader("userIp");
                yxgSearchParameter.setUserIp(remoteAddr);
                yxgSearchParameter.setTerminalType(3);
            }
            yxgSearchParameter.setPlatformId(1);
            search = goodsSearchFeignApi.yxgSearch(yxgSearchParameter);
            SearchResult searchResult = vCategoryService.getSearchResult(currentPage, pageSize, 4, search);
            SearchInfo searchInfo =new SearchInfo();
            if (yxgSearchParameter.getBrandId()!=null) {
                Integer brandId = yxgSearchParameter.getBrandId();
                VBrand brand = tbrandService.getVBrandById(brandId);
                if (brand!=null){
                    searchInfo.setTitleName(brand.getBrandName());
                }
                if (yxgSearchParameter.getFilterCategory()!=null){
                    Integer categoryId = yxgSearchParameter.getFilterCategory();
                    VCategory vCategory=vCategoryService.queryCategoryById(categoryId);
                    if (vCategory!=null){
                        searchInfo.setCatLevel( vCategory.getLevel());
                        searchInfo.setSubtitleName(vCategory.getCategoryName());
                    }
                }
            }else if (yxgSearchParameter.getCategoryId()!=null) {
                Integer categoryId = yxgSearchParameter.getCategoryId();
                VCategory vCategory=vCategoryService.queryCategoryById(categoryId);
                if (vCategory!=null){
                    searchInfo.setCatLevel( vCategory.getLevel());
                    searchInfo.setTitleName(vCategory.getCategoryName());
                }
            }else if (yxgSearchParameter.getKeyword()!=null){
                searchInfo.setTitleName(yxgSearchParameter.getKeyword());
            }
            searchInfo.setCheckPriceAuth(checkPriceAuth());
            searchResult.setSearchInfo(searchInfo);
            return ResultInfo.success(searchResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail(e);
        }
    }


    @PostMapping("/getNewFilterSearch")
    @ResponseBody
    public ResultInfo getNewFilterSearch(@RequestBody YxgSearchParameter yxgSearchParameter) {
        SearchSkuResult search = null;
        Integer currentPage = yxgSearchParameter.getCurrentPage();
        Integer pageSize = yxgSearchParameter.getPageSize();
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        try {
            yxgSearchParameter.setPlatformId(1);
            yxgSearchParameter.setCurrentPage(currentPage);
            yxgSearchParameter.setPageSize(pageSize);
            search = goodsSearchFeignApi.yxgfilterSearch(yxgSearchParameter);
            SearchResult searchResult = vCategoryService.getSearchResult(currentPage, pageSize, 4, search);
            return ResultInfo.success(searchResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail(e);

        }
    }


    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }




}
