package com.vedeng.mjx.mapper.ext;

import com.vedeng.mjx.domain.TBrandJx;
import com.vedeng.mjx.domain.ext.SearchParameter;
import com.vedeng.mjx.domain.ext.TCategoryJxExt;
import com.vedeng.mjx.domain.ext.TOpGoodsExtExt;
import com.vedeng.mjx.domain.ext.TOpSkuExt;
import com.vedeng.mjx.mapper.TCategoryJxMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.vedeng.goods.api.RequestParameter;

import java.util.List;
import java.util.Map;

@Repository
public interface TCategoryJxExtMapper extends TCategoryJxMapper {

    /**
     *  获取8个一级分类
     * @return
     */
    List<TCategoryJxExt> queryCategoryOneList();

    /**
     *  根据一级分类id，获取其下面的三个二级分类id
     * @return
     */
    List<TCategoryJxExt> queryCategoryTwoList(Integer categoryId);

    /**
     * 获取一级分类下的三级分类id
     * @return
     */
    List<TCategoryJxExt> queryThreeByOne(Integer categoryId);

    /**
     *  根据三级分类id list 获取所有商品的 skuNo
     */
    List<String> queryGoodsIdListByCategoryId(@Param("categoryIds") List<Integer> categoryIds);

    /**
     *  根据三级分类id列表,获取商品列表
     */
    List<String> queryGoodsListPageByCategoryThree(@Param("categoryId") Integer categoryId);

    List<TOpSkuExt> queryGoodsListPageByCategoryIds(@Param("list") List<Integer> list);


    /**
     *  查询所有的商品sku的列表
     * @param requestParameter
     * @return
     */
    List<TOpSkuExt> queryAllSkuList();

    //获取商品分类
    List<TCategoryJxExt> searchCategory(@Param("searchParam")SearchParameter searchParam);
    //获取商品品牌
    List<TBrandJx> searchBrands(@Param("searchParam")SearchParameter searchParam);
    // 搜索商品 返回商品id
    List<String> searchGoods(@Param("searchParam")SearchParameter searchParam);


}
