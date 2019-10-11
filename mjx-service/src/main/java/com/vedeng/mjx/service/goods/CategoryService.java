package com.vedeng.mjx.service.goods;

import com.vedeng.goods.api.RequestParameter;
import com.vedeng.goods.api.vo.SkuListResult;
import com.vedeng.mjx.domain.OneCategoryData;
import com.vedeng.mjx.domain.TCategoryJx;
import com.vedeng.mjx.domain.TOpGoodsExt;
import com.vedeng.mjx.domain.VCategory;
import com.vedeng.mjx.domain.ext.SearchParameter;
import com.vedeng.mjx.domain.ext.TCategoryJxExt;
import com.vedeng.mjx.domain.ext.TOpGoodsExtExt;
import com.vedeng.mjx.service.goods.impl.FloorVo;
import com.vedeng.mjx.service.goods.impl.MFloorVo;

import java.util.List;
import java.util.Map;

public interface CategoryService {


    /**
     *  获取首页的8个一级分类
     * @return
     */
    List<FloorVo> queryCategoryList(String mjxUrl,List<String> categoryIdlist,String level2categoryIds,String bdCategoryIdsIndex);

    /**
     * 不走缓存
     * @return
     */
    public List<FloorVo> loadCategoryList();

    List<String> queryGoodsSkuListByCategoryId( SearchParameter searchParameter);

    /**
     * 通过三级分类获取商品列表
     * @return
     */
    List<String> queryGoodsSkuListByThreeCategoryId(SearchParameter searchParameter);

    //获取三级分类列表
    List<Map<String,Object>> queryCategoryThreeByTopCate(Integer categoryId);

//    List<String> searchSkuNo(SearchParameter searchParameter);
    List<String> queryAllGoodsSkuList();

    /**
     *  根据搜索参数查询相应的分类
     */
    List<Map<String,Object>> searchCategories(SearchParameter searchParameter);

    /**
     *  根据传入的参数查询相应的品牌id 列表
     */
    List<String> searchBrands(SearchParameter searchParameter);

    /**
     * 清空缓存
     */
     void clearCache();

    List<MFloorVo> level1CategoryList(String mjxUrl, List<String> levelCategoryList);

    List<MFloorVo> level3CategoryList(String mjxUrl, List<String> levelCategoryList);

     List<OneCategoryData> level3CategoryList();

     List<String> searchName();

}
