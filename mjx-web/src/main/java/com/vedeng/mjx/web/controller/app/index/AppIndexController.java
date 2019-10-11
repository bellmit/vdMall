package com.vedeng.mjx.web.controller.app.index;


import com.alibaba.fastjson.JSON;
import com.vedeng.goods.api.SearchParameter;
import com.vedeng.goods.api.service.GoodsSearchAPI;
import com.vedeng.goods.api.vo.SearchSkuResult;
import com.vedeng.goods.api.vo.SkuListVO;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.RedisKeyConstant;
import com.vedeng.mjx.common.enumUtils.BannerEnum;
import com.vedeng.mjx.common.enumUtils.PlaceEnum;
import com.vedeng.mjx.common.indexVo.BannerData;
import com.vedeng.mjx.common.page.PageModel;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.*;
import com.vedeng.mjx.mapper.TAttachmentMapper;
import com.vedeng.mjx.service.feign.goods.GoodsSearchFeignApi;
import com.vedeng.mjx.service.feign.goods.GoodsServiceFeignApi;
import com.vedeng.mjx.service.goods.BrandService;
import com.vedeng.mjx.service.goods.CategoryService;
import com.vedeng.mjx.service.goods.GoodsInfoService;
import com.vedeng.mjx.service.goods.SearchKeyWordService;
import com.vedeng.mjx.service.goods.impl.FloorVo;
import com.vedeng.mjx.service.goods.impl.MFloorVo;
import com.vedeng.mjx.service.liam.VCategoryService;
import com.vedeng.mjx.service.redis.RedisOperateService;
import com.vedeng.mjx.service.util.RandomUtil;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.web.controller.base.CommonController;
import com.vedeng.mjx.web.controller.index.vo.CategoryData;
import com.vedeng.mjx.web.controller.m.vo.MIndexData;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestMapping("/app")
@Api(value="app首页controller",tags={"app首页控制层"})
@RestController
public class AppIndexController extends CommonController {

    /**
     * 日志
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BrandService brandService;

    @Resource
    private CategoryService categoryService;
    @Resource
    private GoodsSearchFeignApi goodsSearchFeignApi;
    @Resource
    RedisOperateService redisOperateService;
    @Resource
    SearchKeyWordService searchKeyWordService;

    @PostMapping(value = "/appUrl")
    public ResultInfo appUrl(){
        Map<String,Object> result = new HashMap<>();
        result.put("appUrl",appUrl);
        return ResultInfo.success(result);
    }

    @PostMapping(value = {"/index"})
    public ResultInfo mjxIndex() {

        MIndexData mIndexData = new MIndexData();

        List<BannerData> bannerDataList = brandService.bannerList(BannerEnum.APP.getValue(), PlaceEnum.INDEX.getValue(),PlaceEnum.INDEX_SPARE.getValue());

        List<String> categoryIdlist = Arrays.asList(mCategoryIds.split(","));
        List<MFloorVo> mFloorVoList = categoryService.level1CategoryList(mjxUrl,categoryIdlist);
        List<TBrandData> brandList = brandService.brandDataList(mjxUrl,mBrands);

        List<String> categoryLevel3Idlist = Arrays.asList(mLevel3categoryIds.split(","));
        List<MFloorVo> level3CategoryList = categoryService.level3CategoryList(mjxUrl,getLevel3CategoryId(categoryLevel3Idlist));

        List<String> searchName = searchKeyWordService.searchIndexName();

        mIndexData.setCheckPriceAuth(checkPriceAuth());
        mIndexData.setBannerList(bannerDataList);
        mIndexData.setLevel1CategoryList(mFloorVoList);
        mIndexData.setBrandList(brandList);
        mIndexData.setLevel3CategoryList(level3CategoryList);
        mIndexData.setSearchName(searchName);

        return ResultInfo.success(mIndexData);
    }

    @PostMapping(value = "/goodsList")
    public ResultInfo goodsList(@RequestBody PageModel pageModel){
        SearchSkuResult searchSkuResult = null;
        try {
            SearchParameter searchParameter = new SearchParameter();
            Integer pageNo = pageModel.getPageNo();
            searchParameter.setCurrentPage(pageNo);
            searchParameter.setPageSize(20);
            if(pageNo == 3){
                searchParameter.setPageSize(10);
            }
            if(pageNo > 3){
                searchParameter.setPageSize(0);
            }
            searchSkuResult = goodsSearchFeignApi.selectSkus(searchParameter);
        } catch (Exception e) {
            log.error("查询selectSkus异常{}",e);
        }
        List<SkuListVO> skuListVOList = new ArrayList<>();
        if(searchSkuResult != null){
            skuListVOList = searchSkuResult.getSkuList();
        }
        return ResultInfo.success(skuListVOList);
    }

    public static List<String> getLevel3CategoryId(List<String> categoryIdlist){
        List<String> categoryIdlistNew = new ArrayList<>();
        Set<Integer> setNum = RandomUtil.getRandomMApp(categoryIdlist.size(),8);
        for(Integer num : setNum){
            categoryIdlistNew.add(categoryIdlist.get(num));
        }
        return categoryIdlistNew;
    }

    @PostMapping(value = {"/mjx/index"})
    public ResultInfo indexNew(HttpServletRequest request, HttpServletResponse response) {

        Map<String,Object> result = new HashMap<>();
        // 根据shiro的session获取用户信息
        TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
        result.put("checkPriceAuth", checkPriceAuth());

        List<BannerData> bannerDataList = brandService.bannerList(BannerEnum.APP.getValue(),PlaceEnum.JX_INDEX.getValue(),PlaceEnum.JX_INDEX_SPARE.getValue());
        result.put("bannerList",bannerDataList);

        //一级分类数据
        List<String> categoryIdlist = Arrays.asList(bdCategoryIds.split(","));

        String exist=redisOperateService.getKeyValue(mjxIndex);
        List<FloorVo> floorList = null;
        if(!StringUtil.isBlank(exist)) {
            log.info("读取缓存");
            floorList = JSON.parseArray(exist, FloorVo.class);
            //判断分类下是否读到商品
            if(exist.indexOf(RedisKeyConstant.PRODUCT_EXIST_WIND) == -1){
                floorList = null;
            }
        }
        if(CollectionUtils.isEmpty(floorList)){
            log.info("读取数据库，并加入缓存");
            floorList = categoryService.queryCategoryList(mjxUrl,categoryIdlist,bdLevel2categoryIds,bdCategoryIdsIndex);
            //如果读取数据库失败，则读取备用缓存
            if(CollectionUtils.isEmpty(floorList)){
                log.info("读取备用缓存");
                String str=redisOperateService.getKeyValue(mjxIndexSpare);

                floorList = JSON.parseArray(str, FloorVo.class);
            }
            if(!CollectionUtils.isEmpty(floorList)) {
                redisOperateService.setKeyValueWithExpire(mjxIndex, JSON.toJSONString(floorList),5 * 60 * 1000);
                redisOperateService.setKeyValue(mjxIndexSpare, JSON.toJSONString(floorList));
            }
        }

        if (floorList != null){
            //设置分屏、排序
            result = getFloorVoWind(result, categoryIdlist, floorList);
        }

        //设置一级分类数据
        result.put("floorList",floorList);
        List<TBrandJx> brandList = brandService.queryBrandsList(mjxUrl);

        result.put("brandList",brandList);
        return ResultInfo.success(result);
    }

    public Map<String,Object> getFloorVoWind(Map<String,Object> result,List<String> categoryIdlist,List<FloorVo> floorList){
        List<CategoryData> category1 = new ArrayList<>();
        List<CategoryData> category2 = new ArrayList<>();
        for(int i =0;i<categoryIdlist.size();i++){
            String categoryId = categoryIdlist.get(i);
            for (FloorVo floorVo : floorList) {
                CategoryData categoryData = new CategoryData();
                if (categoryId.equals(floorVo.getCategoryOneId().toString())) {
                    categoryData.setCategoryId(floorVo.getCategoryOneId());
                    categoryData.setCategoryCode(floorVo.getCategoryOneCode());
                    categoryData.setCategoryName(floorVo.getCategoryOneName());
                    categoryData.setUrl(floorVo.getUrl());
                    if (i < 8) {
                        category1.add(categoryData);
                    } else {
                        category2.add(categoryData);
                    }
                }
            }
        }
        result.put("category1", category1);
        result.put("category2", category2);
        return result;
    }

}


