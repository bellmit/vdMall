package com.vedeng.mjx.web.controller.index;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.vedeng.goods.api.RequestParameter;
import com.vedeng.goods.api.SearchParameter;
import com.vedeng.goods.api.dto.CategoryDTO;
import com.vedeng.goods.api.dto.PicDTO;
import com.vedeng.goods.api.vo.SearchSkuResult;
import com.vedeng.goods.api.vo.SkuListResult;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.constant.RedisKeyConstant;
import com.vedeng.mjx.common.enumUtils.BannerEnum;
import com.vedeng.mjx.common.enumUtils.PlaceEnum;
import com.vedeng.mjx.common.indexVo.BannerData;
import com.vedeng.mjx.common.searchVo.FilterCondition;
import com.vedeng.mjx.common.searchVo.GoodsSearchParameter;
import com.vedeng.mjx.common.searchVo.SearchResult;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.TBrandData;
import com.vedeng.mjx.domain.TBrandJx;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.domain.ext.TOpGoodsExtExt;
import com.vedeng.mjx.service.feign.goods.GoodsSearchFeignApi;
import com.vedeng.mjx.service.feign.goods.GoodsServiceFeignApi;
import com.vedeng.mjx.service.goods.BrandService;
import com.vedeng.mjx.service.goods.CategoryService;

import com.vedeng.mjx.service.goods.GoodsInfoService;

import com.vedeng.mjx.service.goods.impl.FloorVo;
import com.vedeng.mjx.service.liam.VCategoryService;
import com.vedeng.mjx.service.redis.RedisOperateService;
import com.vedeng.mjx.service.util.RandomUtil;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.web.controller.base.CommonController;
import com.vedeng.mjx.web.controller.index.vo.CategoryData;
import com.vedeng.mjx.web.controller.index.vo.SkuVoExt;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestMapping
@RestController
public class IndexController extends CommonController {


    /**
     * 日志
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BrandService brandService;

    @Resource
    private GoodsServiceFeignApi goodsServiceFeignApi;

    @Resource
    private CategoryService categoryService;

    @Resource
    private GoodsInfoService goodsInfoService;
    @Resource
    private GoodsSearchFeignApi goodsSearchFeignApi;
    @Resource
    private VCategoryService vCategoryService;
    @Resource
    RedisOperateService redisOperateService;

    @GetMapping(value = {"/monitor.html"})
    @ResponseBody
    public String monitor() {
        return "OK";
    }
    @GetMapping(value = {"/clearCache.html"})
    @ResponseBody
    public String clearCache() {
        categoryService.clearCache();
        return "OK";
    }

    @GetMapping("dispatch.html")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("dispatch/dispatch");
        return view;
    }

    @GetMapping(value = "/isLogin")
    public Object isLogin(){
        TopAccountJx userEntity = ShiroSessionUtil.getSessionTopAccountJx();
        if (userEntity ==null || userEntity.getAccountId()==null){
            return false;
        }
        return true;
    }

    @GetMapping(value = {"", "/index.html"})
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

        // 根据shiro的session获取用户信息
        TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
        ModelAndView view = new ModelAndView();
        view.addObject("checkPriceAuth", checkPriceAuth());
        //一级分类数据
        List<String> categoryIdlist = Arrays.asList(bdCategoryIds.split(","));

        String key = RedisKeyConstant.MJX_INDEX;
        String keySpare = RedisKeyConstant.MJX_INDEX_SPARE;
        String exist=redisOperateService.getKeyValue(key);
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
                String str=redisOperateService.getKeyValue(keySpare);
                floorList = JSON.parseArray(str, FloorVo.class);
            }
            if(!CollectionUtils.isEmpty(floorList)) {
                redisOperateService.setKeyValueWithExpire(key, JSON.toJSONString(floorList),5 * 60 * 1000);
                redisOperateService.setKeyValue(keySpare, JSON.toJSONString(floorList));
            }
        }

        //设置一级分类数据
        view.addObject("floorList", floorList);//设置楼层数据
        List<TBrandJx> brandList = brandService.queryBrands();

        if (floorList != null){
            //设置分屏、排序
            view = getFloorVo(view, categoryIdlist, floorList);
        }

        //设置品牌数据
        view.addObject("brandList", brandList);

        view.setViewName("index/index");
        return view;
    }

    public ModelAndView getFloorVo(ModelAndView view,List<String> categoryIdlist,List<FloorVo> floorList){
        List<FloorVo> floorCategory1 = new ArrayList<>();
        List<FloorVo> floorCategory2 = new ArrayList<>();
        for(int i =0;i<categoryIdlist.size();i++){
            String categoryId = categoryIdlist.get(i);
            for (FloorVo floorVo : floorList) {
                if (categoryId.equals(floorVo.getCategoryOneId().toString())) {
                    if (i < 8) {
                        floorCategory1.add(floorVo);
                    } else {
                        floorCategory2.add(floorVo);
                    }
                }
            }
        }
        view.addObject("floorCategory1", floorCategory1);
        view.addObject("floorCategory2", floorCategory2);
        return view;
    }

    @GetMapping(value = {"/index"})
    public ResultInfo indexNew(HttpServletRequest request, HttpServletResponse response) {

        Map<String,Object> result = new HashMap<>();
        // 根据shiro的session获取用户信息
        TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
        result.put("checkPriceAuth", checkPriceAuth());

        List<BannerData> bannerDataList = brandService.bannerList(BannerEnum.MJX.getValue(), PlaceEnum.JX_INDEX.getValue(),PlaceEnum.JX_INDEX_SPARE.getValue());
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

    @RequestMapping("/index/list.html")
    public ModelAndView list(GoodsSearchParameter searchParameter,Integer[] filter_brands,
                             Integer[] filter_depts,Integer filter_categoryId,
                             Integer[] filter_attrIds,Integer brandId ,String brand_name,String category_name){
        //分类入口进入  避免重置按钮  3级分类替换一级分类导致  页面展示只带出3级分类
        if(filter_brands!=null) {
            List<Integer> brands = CollectionUtils.arrayToList(filter_brands);
            searchParameter.setBrandIdList(brands);
        }
        if(filter_depts!=null) {
            List<Integer> depts = CollectionUtils.arrayToList(filter_depts);
            searchParameter.setDeptIdList(depts);
        }
        if (filter_attrIds!=null) {
            List<Integer> attrIds = CollectionUtils.arrayToList(filter_attrIds);
            searchParameter.setAttrValueIdList(attrIds);
        }
        ModelAndView view = new ModelAndView();
        List<CategoryDTO> categoryDTOS = null;
        try {
            SearchSkuResult search = null;
            view.addObject("checkPriceAuth", checkPriceAuth());
            /**先走品牌搜索判断再走分类搜索判断再走关键词
             （原因品牌搜索品牌id不能为 null  分类搜索分类id不能为null   关键词搜索  关键词不能为null）*/
            if (searchParameter.getBrandId() != null) {
                search = goodsSearchFeignApi.searchByBrandIds(searchParameter);
            } else if (StringUtil.isBlank(searchParameter.getKeyword())) {
                search = goodsSearchFeignApi.searchByCategoryIdAndLevel(searchParameter);
            } else {
                String keyword = searchParameter.getKeyword();
                searchParameter.setKeyword(keyword.toUpperCase());
                search = goodsSearchFeignApi.searchByKeyword(searchParameter);
                searchParameter.setKeyword(keyword);
             }
            categoryDTOS = vCategoryService.queryLevel3Category(search.getCategoryList());
            search.setCategoryList(categoryDTOS);
            //seo_title
            String seo_title = "贝登精选";
            String keywords=searchParameter.getKeyword();
            if( keywords != null && !"".equals(keywords)){
                seo_title = keywords + " 搜索结果-贝登精选";
            } else if( brand_name != null && !"".equals(brand_name) ){
                seo_title = brand_name + " 品牌-贝登精选";
            } else if( category_name != null && !"".equals(category_name)){
                seo_title = category_name + " 分类-贝登精选";
            }

            view.addObject("brand_name",brand_name);
            view.addObject("category_name",category_name);
            view.addObject("seo_title", seo_title);
            SearchResult searchResult = vCategoryService.getSearchResult(searchParameter.getCurrentPage(), searchParameter.getNavigatePages(), searchParameter.getPageSize(), search);
            view.addObject("filter_categoryId",filter_categoryId);//页面收到该参数时  属性展开  没有则不展开
            view.addObject("searchParameter",searchParameter);
            view.addObject("searchResult",searchResult);
            view.setViewName("goods/goods-class");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @GetMapping("/index/list-api1.html")
    @ResponseBody
    public ResultInfo list_api1( GoodsSearchParameter searchParameter,Integer[] filter_brands,
                                Integer[] filter_depts,Integer filter_categoryId,
                                 Integer[] filter_attrIds,Integer brandId) {
        //品牌入口 走分类搜索   把品牌当成分类搜索条件
        if (brandId!=null) {
            filter_brands=new Integer[1];
            filter_brands[0]=brandId;
        }
        if(filter_brands!=null) {
            List<Integer> brands = CollectionUtils.arrayToList(filter_brands);
            searchParameter.setBrandIdList(brands);
        }
        if(filter_depts!=null) {
            List<Integer> depts = CollectionUtils.arrayToList(filter_depts);
            searchParameter.setDeptIdList(depts);
        }
        if (filter_attrIds!=null) {
            List<Integer> attrIds = CollectionUtils.arrayToList(filter_attrIds);
            searchParameter.setAttrValueIdList(attrIds);
        }
        try {
            SearchSkuResult search=null;
            //判断是关键词搜索还是分类搜索
            if(searchParameter.getBrandId()!=null) {
                search = goodsSearchFeignApi.searchByBrandIds(searchParameter);
            }else if (StringUtil.isBlank(searchParameter.getKeyword())) {
                search = goodsSearchFeignApi.searchByCategoryIdAndLevel(searchParameter);
            }else{
                search = goodsSearchFeignApi.searchByKeyword(searchParameter);
            }
            SearchResult searchResult = vCategoryService.getSearchResult(searchParameter.getCurrentPage(), searchParameter.getNavigatePages(), searchParameter.getPageSize(), search);
            return ResultInfo.success(searchResult);
        } catch (Exception e) {
            e.printStackTrace();
            ResultInfo.fail(e);
        }
        return ResultInfo.fail(new Exception("系统内部错误！"));
    }

    @PostMapping("/index/getAllAttrByCat3")
    @ResponseBody
    public ResultInfo getAllAttrByCat3(SearchParameter SearchParameter ){
        try {
            SearchSkuResult search = goodsSearchFeignApi.getAllAttrByCat3(SearchParameter);
            FilterCondition filterCondition = new FilterCondition();
            filterCondition.searchSkuResultoFilterCondition(search);
            return ResultInfo.success(filterCondition);
        } catch (Exception e) {
            e.printStackTrace();
            ResultInfo.fail(e);
        }
        return ResultInfo.fail(new Exception("系统内部错误"));
    }






    /**
     * @Author Gavin.li
     * @Description 列表页
     * @Date 2019/6/22 20:17
     */

    @GetMapping("/index/list1.html")
    public ModelAndView list(HttpServletRequest request, String keywords, Integer category,
                             String brand, Integer filter_category, String filter_brands,
                             String category_name, String brand_name) {
        ModelAndView view = new ModelAndView();
        view.addObject("checkPriceAuth", checkPriceAuth());
        RequestParameter parameter = new RequestParameter();
        List<String> skuNos = new ArrayList<>();

        List<Map<String, Object>> cateThreeList = new ArrayList<>();

        if (category != null   && category > 0) {
            com.vedeng.mjx.domain.ext.SearchParameter searchParam = new com.vedeng.mjx.domain.ext.SearchParameter();
            searchParam.setCurrentPage(1);//设置当前页

            if ( filter_category != null && filter_category > 0) {
                searchParam.setCategoryId(filter_category);
                skuNos = categoryService.queryGoodsSkuListByThreeCategoryId(searchParam);
            } else {
                searchParam.setCategoryId(category);

                skuNos = categoryService.queryGoodsSkuListByCategoryId(searchParam);
            }

            //获取三级分类
            cateThreeList = categoryService.queryCategoryThreeByTopCate(category);
        } else {
            skuNos = categoryService.queryAllGoodsSkuList();
        }


        String brandParam = null;
        if (StringUtils.isNotEmpty(filter_brands)) {
            brandParam = filter_brands;
        } else {
            brandParam = brand;
        }

        //获取一级分类，或者二级分类下的所有三级分类

        parameter.setSkuNoList(skuNos);
        parameter.setBrandId(brandParam);
        parameter.setKeyword(keywords);
        parameter.setCurrentPage(1);
        SkuListResult result = goodsServiceFeignApi.listSkuBySkuNoList(parameter);//.searchSku(parameter);
        List<SkuVO> list = result.getData();

        if (category == null) {
            category = 0;
        }
        if ("".equals(brand)) {
            brand = null;
        }

        //seo_title
       String seo_title = "贝登精选";
        if( keywords != null && !"".equals(keywords)){
            seo_title = keywords + " 搜索结果-贝登精选";
        } else if( brand_name != null && !"".equals(brand_name) ){
            seo_title = brand_name + " 品牌-贝登精选";
        } else if( category_name != null && !"".equals(category_name)){
            seo_title = category_name + " 分类-贝登精选";
        }


        view.addObject("keywords", keywords);
        view.addObject("filter_category", filter_category);
        view.addObject("category", category);
        view.addObject("category_name", category_name);
        view.addObject("brand", brand);
        view.addObject("brand_name", brand_name);
        view.addObject("filter_brands", filter_brands);
        view.addObject("seo_title", seo_title);




        view.addObject("list", fillSku(list));
        view.addObject("page", result.getPage());
        view.addObject("brandList", result.getBrandList());
        view.addObject("categoryList", cateThreeList);
        view.addObject("total", result.getPage().getTotal());
        view.setViewName("goods/goods-class");
        return view;
    }

    /**
     * 懒加载数据
     *
     * @param filter_category
     * @param page
     * @return
     */
    @GetMapping("/index/list-api.html")
    public Map<String, Object> list_api(String keywords, Integer category, String brand, Integer filter_category, String filter_brands, Integer page) {
        ModelAndView view = new ModelAndView();
        RequestParameter parameter = new RequestParameter();
        List<String> skuNos = new ArrayList<>();


        if (category != null && !"".equals(category) && category > 0) {
            com.vedeng.mjx.domain.ext.SearchParameter searchParam = new com.vedeng.mjx.domain.ext.SearchParameter();
            if (page == null || "".equals(page)) {
                page = 2;//懒加载默认从第二页开始
            }
            searchParam.setCurrentPage(page); //请求页数
            if (filter_category != null && !"".equals(filter_category) && filter_category > 0) {
                searchParam.setCategoryId(filter_category);
                skuNos = categoryService.queryGoodsSkuListByThreeCategoryId(searchParam);
            } else {
                searchParam.setCategoryId(category);
                skuNos = categoryService.queryGoodsSkuListByCategoryId(searchParam);
            }

        } else {
            skuNos = categoryService.queryAllGoodsSkuList();
        }

        String brandParam = null;
        if (StringUtils.isNotEmpty(filter_brands)) {
            brandParam = filter_brands;
        } else {
            brandParam = brand;
        }

        //获取一级分类下的所有分类

        parameter.setSkuNoList(skuNos);
        parameter.setBrandId(brandParam);
        parameter.setKeyword(keywords);
        parameter.setCurrentPage(page);
        SkuListResult result = goodsServiceFeignApi.listSkuBySkuNoList(parameter);//.searchSku(parameter);
        Map<String, Object> map = new HashMap<>();

        map.put("list", fillSku(result.getData()));
        return map;
    }

    private List<SkuVoExt> fillSku(List<SkuVO> list) {
        List<SkuVoExt> listex = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(list)) {
            for (SkuVO info : list) {
                try {
                    TOpGoodsExtExt opt = goodsInfoService.queryGoodsHotDataNew(info.getSkuInfo().getSkuNo());
                    if (opt == null) {
                        continue;
                    }
                    SkuVoExt ext = new SkuVoExt();

                    BeanUtils.copyProperties(ext, info);
                    if (ext.getFirstPic() == null || StringUtils.isBlank(ext.getFirstPic().getUrl())) {
                        PicDTO defaultDto = new PicDTO();
                        defaultDto.setUrl("/images/nopic.jpg");
                        ext.setFirstPic(defaultDto);
                    }
                    ext.setHotFlag(CommonConstant.STATUS_1.equals(opt.getIsHot()));
                    ext.setNewFlag(CommonConstant.STATUS_1.equals(opt.getIsNew()));
                    ext.setSevenFlag(CommonConstant.STATUS_1.equals(opt.getIsSeven()));
                    if (!checkPriceAuth()) {
                        ext.getSkuInfo().setJxMarketPrice(null);
                    }
                    listex.add(ext);
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
        return listex;
    }

    @GetMapping("/index/search.html")
    public ModelAndView search(String keywords) {
        ModelAndView view = new ModelAndView();
        view.addObject("keywords",keywords);
        view.setViewName("index/search");
        return view;
    }

    @GetMapping("/index/searchResult.html")
    public ModelAndView searchResult(String keyword, String filter_brands, String filter_category) {
        ModelAndView view = new ModelAndView();
        RequestParameter parameter = new RequestParameter();
        parameter.setKeyword(keyword);
        parameter.setBrandId(filter_brands);
        SkuListResult result = goodsServiceFeignApi.searchSku(parameter);
        List<SkuVO> list = result.getData();
        view.addObject("keyword", keyword);
        view.addObject("filter_brands", filter_brands);
        view.addObject("filter_category", filter_category);
        view.addObject("list", fillSku(list));
        view.addObject("page", result.getPage());
        view.addObject("brandList", result.getBrandList());
        view.setViewName("index/searchResult.html");
        return view;
    }


    /**
     * 根据筛选条件获取品牌
     *
     * @return
     */
    @GetMapping("/index/get-brands.html")
    public String getBrands(@RequestBody SearchParameter searchParameter) {
        return "brands";
    }

    /**
     * 根据筛选条件获取品牌和分类
     *
     * @return
     */
    @GetMapping("/index/get-categories.html")
    public List<Map<String, Object>> getCategories(@RequestBody SearchParameter searchParameter) {

        List<Map<String, Object>> cateList = new ArrayList<>();//
        Map<String, Object> cate1 = new HashMap<>();
        cate1.put("category_id", 1);
        cate1.put("name", "科美达");

        Map<String, Object> cate2 = new HashMap<>();
        cate2.put("category_id", 2);
        cate2.put("name", "电子超声");
        cateList.add(cate1);
        cateList.add(cate2);
        return cateList;
    }

    /**
     * 获取分类和品牌
     *
     * @param searchParameter
     * @return
     */
    @GetMapping("/index/get-brands-categories.html")
    public Map<String, Object> getBrandsAndCategories(@RequestBody SearchParameter searchParameter) {

        Map<String, Object> data = new HashMap<>();
        data.put("brandList", null);
        data.put("categoryList", null);

        return data;
    }

    /**
     * @Author Gavin.li
     * @Description 何为贝登精选
     * @Date 2019/6/22 20:14
     */
    @GetMapping("/introduction.html")
    public ModelAndView introduction() {
        ModelAndView view = new ModelAndView();
        view.addObject("checkPriceAuth", checkPriceAuth());
        view.setViewName("index/introduction");
        return view;
    }

    /**
     * @Author Gavin.li
     * @Description 加入贝登精选
     * @Date 2019/6/22 20:16
     */
    @GetMapping("/joinus.html")
    public ModelAndView application() {
        ModelAndView view = new ModelAndView();
        view.addObject("checkPriceAuth", checkPriceAuth());
        view.setViewName("index/application");
        return view;
    }

    /**
     *  cc 客服配置页
     */
    @GetMapping("/chatdialog.html")
    public ModelAndView chatdialog(){
        ModelAndView mv = new ModelAndView("index/chatdialog");
        return mv;
    }



}


