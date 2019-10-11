package com.vedeng.mjx.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.vedeng.goods.api.RequestParameter;
import com.vedeng.goods.api.service.GoodsSearchAPI;
import com.vedeng.goods.api.vo.SearchSkuResult;
import com.vedeng.goods.api.vo.SkuListResult;
import com.vedeng.goods.api.vo.SkuListVO;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.domain.*;
import com.vedeng.mjx.domain.ext.SearchParameter;
import com.vedeng.mjx.domain.ext.TCategoryJxExt;
import com.vedeng.mjx.domain.ext.TOpGoodsExtExt;
import com.vedeng.mjx.domain.ext.TOpSkuExt;
import com.vedeng.mjx.mapper.TAttachmentMapper;
import com.vedeng.mjx.mapper.VCancelMapper;
import com.vedeng.mjx.mapper.VCategoryMapper;
import com.vedeng.mjx.mapper.ext.TCategoryJxExtMapper;
import com.vedeng.mjx.service.feign.goods.GoodsSearchFeignApi;
import com.vedeng.mjx.service.feign.goods.GoodsServiceFeignApi;
import com.vedeng.mjx.service.goods.CategoryService;
import com.vedeng.mjx.service.goods.GoodsInfoService;
import com.vedeng.mjx.service.util.RandomUtil;
import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author ：Gavin.li
 * @date ：Created in 2019/6/26 16:10
 * @description：
 * @modified By：
 * @version:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Resource
    private TCategoryJxExtMapper tCategoryJxExtMapper;
    @Resource
    private VCategoryMapper categoryMapper;
    @Resource
    RedissonConnectionFactory redissonConnectionFactory;
    @Resource
    private GoodsSearchAPI goodsSearchAPI;
    @Resource
    private TAttachmentMapper tAttachmentMapper;

    @Resource
    private GoodsServiceFeignApi goodsServiceFeignApi;
    private static Integer MAX_INDEX_CATEGORY_SKU_SIZE = 6;
    private static Integer MAX_INDEX_CATEGORY_SKU_NO_SIZE = 10;

    /**
     * 获取8个一级分类
     *
     * @return
     */
    @Override
    public List<FloorVo> queryCategoryList(String mjxUrl,List<String> categoryIdlist,String level2categoryIds,String bdCategoryIdsIndex) {

        try {
            List<String> level2categoryIdList = Arrays.asList(level2categoryIds.split(","));
            //查询所有指定二级分类
            List<OneCategoryData> oneCategoryDataList = categoryMapper.selectLevel1CategoryList(level2categoryIdList);

            List<FloorVo> list = getFloorVo(mjxUrl,categoryIdlist);
            for (FloorVo v : list) {
                if (isCategory(v.getCategoryOneId(), bdCategoryIdsIndex)) {
                    SearchSkuResult searchSkuResult = fillFloor(v.getCategoryOneId());
                    System.out.println("searchSkuResult:"+JSON.toJSONString(searchSkuResult));
                    if (searchSkuResult == null) {
                        continue;
                    }
                    v.setSkuList(searchSkuResult.getSkuList());//传给前端分离的首页
                    List<TCategoryJxExt> categoryTwoList = new ArrayList<>();
                    Integer categoryId = v.getCategoryOneId();
                    for (OneCategoryData oneCategoryData : oneCategoryDataList) {
                        Integer parentId = oneCategoryData.getParentId();
                        if (categoryId.equals(parentId)) {
                            TCategoryJxExt categoryJxExt = new TCategoryJxExt();
                            categoryJxExt.setCategoryId(oneCategoryData.getCategoryId());
                            categoryJxExt.setCategoryCode(oneCategoryData.getCategoryCode());
                            categoryJxExt.setCategoryName(oneCategoryData.getCategoryName());
                            categoryTwoList.add(categoryJxExt);
                        }
                    }
                    v.setCategoryTwoList(categoryTwoList);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("cache error", e);
        }
        return Collections.emptyList();
    }

    public boolean isCategory(Integer categoryId,String bdCategoryIdsIndex){
        List<String> categoryList = Arrays.asList(bdCategoryIdsIndex.split(","));
        for(String str : categoryList){
            if(str.equals(categoryId.toString())) {
                return true;
            }
        }
        return false;
    }


    public List<FloorVo> getFloorVo(String mjxUrl,List<String> categoryIdlist) {
        List<FloorVo> floorVoList = new ArrayList<>();
        //查询所有指定一级分类
        List<OneCategoryData> oneCategoryDataList = categoryMapper.selectLevel1CategoryList(categoryIdlist);
        System.out.println("oneCategoryDataList:"+JSON.toJSONString(oneCategoryDataList));
        for(OneCategoryData oneCategoryData : oneCategoryDataList){
            FloorVo floorVo = new FloorVo();
            floorVo.setCategoryOneId(oneCategoryData.getCategoryId());
            floorVo.setCategoryOneCode(oneCategoryData.getCategoryCode());
            floorVo.setCategoryOneName(oneCategoryData.getCategoryName());
            floorVo.setUrl(mjxUrl+"images/con-list/con-list"+oneCategoryData.getCategoryId()+".jpg");
            floorVoList.add(floorVo);
        }
        return floorVoList;
    }

    public List<MFloorVo> getMFloorVo(String mjxUrl,List<String> categoryIdlist) {
        List<MFloorVo> floorVoList = new ArrayList<>();
        //查询所有指定一级分类
        List<OneCategoryData> oneCategoryDataList = categoryMapper.selectLevel1CategoryList(categoryIdlist);
        for(OneCategoryData oneCategoryData : oneCategoryDataList){
            MFloorVo floorVo = new MFloorVo();
            floorVo.setvCategoryId(oneCategoryData.getCategoryId());
            floorVo.setCategoryCode(oneCategoryData.getCategoryCode());
            floorVo.setCategoryName(oneCategoryData.getCategoryName());
            floorVo.setUrl(mjxUrl+"images/con-list/con-list"+oneCategoryData.getCategoryId()+".jpg");
            floorVoList.add(floorVo);
        }
        return floorVoList;
    }

    public List<MFloorVo> getMFloorVoPng(String mjxUrl,List<String> categoryIdlist) {
        List<MFloorVo> floorVoList = new ArrayList<>();
        //查询所有指定一级分类
        List<OneCategoryData> oneCategoryDataList = categoryMapper.selectLevel1CategoryList(categoryIdlist);
        for(OneCategoryData oneCategoryData : oneCategoryDataList){
            MFloorVo floorVo = new MFloorVo();
            floorVo.setvCategoryId(oneCategoryData.getCategoryId());
            floorVo.setCategoryCode(oneCategoryData.getCategoryCode());
            floorVo.setCategoryName(oneCategoryData.getCategoryName());
            floorVo.setUrl(mjxUrl+"images/con-list/con-list"+oneCategoryData.getCategoryId()+".png");
            floorVoList.add(floorVo);
        }
        return floorVoList;
    }

    public List<TCategoryJxExt> getCategoryTwoList(String categoryIds,String categoryNames){
        List<String> categoryIdList = Arrays.asList(categoryIds.split(","));
        List<String> categoryNameList = Arrays.asList(categoryNames.split(","));
        List<TCategoryJxExt> categoryTwoList = new ArrayList<>();
        for(int i = 0; i < categoryIdList.size(); i++){
            TCategoryJxExt categoryJxExt = new TCategoryJxExt();
            categoryJxExt.setCategoryId(Integer.parseInt(categoryIdList.get(i)));
            categoryJxExt.setCategoryName(categoryNameList.get(i));
            categoryTwoList.add(categoryJxExt);
        }
        return categoryTwoList;
    }

    public void clearCache() {
        RedisConnection redissonConnection = redissonConnectionFactory.getConnection();
        redissonConnection.del("INDEX_CACHE_06".getBytes());
        redissonConnection.close();
    }

    @Override
    public List<MFloorVo> level1CategoryList(String mjxUrl,List<String> levelCategoryList) {
        List<MFloorVo> floorVoList = getMFloorVoPng(mjxUrl,levelCategoryList);
        return floorVoList;
    }

    @Override
    public List<MFloorVo> level3CategoryList(String mjxUrl,List<String> levelCategoryList) {
        List<MFloorVo> floorVoList = getMFloorVo(mjxUrl,levelCategoryList);
        return floorVoList;
    }

    @Override
    public List<OneCategoryData> level3CategoryList() {

        List<OneCategoryData> oneCategoryDataList = new ArrayList<>();
        return oneCategoryDataList;
    }

    @Override
    public List<String> searchName() {
        List<String> list = new ArrayList<>();
        list.add("呼吸机");
        list.add("心电图机");
        list.add("监护仪");
        list.add("制氧机");
        list.add("血压计");
        list.add("除颤仪");
        list.add("麻醉剂");
        list.add("黑白超");
        return list;
    }

    @Override
    public List<FloorVo> loadCategoryList() {
        List<TCategoryJxExt> categoryOneList = tCategoryJxExtMapper.queryCategoryOneList();
        //封装数据
        List<FloorVo> floorList = new ArrayList<>();
        long start = System.currentTimeMillis();
        try {
            for (int i = 0; i < categoryOneList.size(); i++) {
                TCategoryJxExt cateOne = categoryOneList.get(i);
                FloorVo floorVo=new FloorVo();
                floorVo.setCategoryOneName( cateOne.getCategoryName());
                floorVo.setCategoryOneId(cateOne.getCategoryId());
                //根据一级分类查其下面的三个二级分类id,name
                List<TCategoryJxExt> categoryTwoList = tCategoryJxExtMapper.queryCategoryTwoList(cateOne.getCategoryId());
                floorVo.setCategoryTwoList(categoryTwoList);//三个二级分类

                //在前六个楼层配置商品数据
                if (i < 6) {
                    //获取该一级分类下的所有三级分类
                    List<TCategoryJxExt> categoryThreeList = tCategoryJxExtMapper.queryThreeByOne(cateOne.getCategoryId());
                    List<Integer> cateThreeIdList = new ArrayList<>();
                    for (int j = 0; j < categoryThreeList.size(); j++) {
                        cateThreeIdList.add(categoryThreeList.get(j).getCategoryId());
                    }
                    floorVo.setCategoryThreeIdList(cateThreeIdList);
                }
                floorList.add(floorVo);

            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return floorList;
    }

    List<SkuVO> fillFloor(List<Integer> cateThreeIdList){
        try{
            if(CollectionUtils.isEmpty(cateThreeIdList)){
                return Collections.emptyList();
            }
            //获取spu_no
            List<String> spuLists = tCategoryJxExtMapper.queryGoodsIdListByCategoryId(cateThreeIdList);
            //通过spu_no 获取商品信息
            Collections.shuffle(spuLists);
            List<String> result = spuLists.size() > MAX_INDEX_CATEGORY_SKU_NO_SIZE ? spuLists.subList(0, MAX_INDEX_CATEGORY_SKU_NO_SIZE) : spuLists;
            RequestParameter param = new RequestParameter();
            param.setSkuNoList(result);
            SkuListResult skuRes = goodsServiceFeignApi.listSkuBySkuNoList(param);
            List<SkuVO> skuVOList=skuRes.getData().size()> MAX_INDEX_CATEGORY_SKU_SIZE ? skuRes.getData().subList(0, MAX_INDEX_CATEGORY_SKU_SIZE) : skuRes.getData();
            return skuVOList;
        }catch(Exception e){
            logger.error("",e);
            return Collections.emptyList();
        }

    }

    /**
     * 通过分类获取商品列表
     */
    @Override
    public List<String> queryGoodsSkuListByCategoryId(SearchParameter searchParam) {
        //通过一级分类id 或者 二级分类id  获取三级分类id
        List<TCategoryJxExt> categoryThreeList = tCategoryJxExtMapper.queryThreeByOne(searchParam.getCategoryId());
        List<Integer> cateThreeIdList = new ArrayList<>();
        for (int i = 0; i < categoryThreeList.size(); i++) {
            cateThreeIdList.add(categoryThreeList.get(i).getCategoryId());
        }

        List<String> skuNoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(cateThreeIdList)) {
            List<TOpSkuExt> skuList = tCategoryJxExtMapper.queryGoodsListPageByCategoryIds(cateThreeIdList);
            for (int i = 0; i < skuList.size(); i++) {
                skuNoList.add(skuList.get(i).getSkuNo());
            }
        }
        return skuNoList;
    }

    SearchSkuResult fillFloor(Integer categoryId){
        try{
            com.vedeng.goods.api.SearchParameter searchParameter = new com.vedeng.goods.api.SearchParameter();
            searchParameter.setCategoryId(categoryId);
            searchParameter.setPlatfromId(1);

            List<String> listNew = new ArrayList<>();
            List<String> list = goodsSearchAPI.searchSkuNoByCategoryIdAndPlatformId(searchParameter);
            if(list.size() > 6) {
                Set<Integer> sets = RandomUtil.getRandom(list.size());
                Iterator<Integer> it = sets.iterator();
                while (it.hasNext()) {
                    listNew.add(list.get(it.next()));
                }
            }else{
                listNew = list;
            }
            SearchSkuResult searchSkuResult = null;
            if(listNew.size() != 0) {
                com.vedeng.goods.api.SearchParameter searchParameterSku = new com.vedeng.goods.api.SearchParameter();
                searchParameterSku.setSkuNoList(listNew);
                searchParameterSku.setPlatfromId(1);
                searchSkuResult = goodsSearchAPI.fillSkuBySkuNos(searchParameterSku);
            }
            return searchSkuResult;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @param searchParameter (当前页和分类id作为参数)
     * @return
     */
    public List<String> queryGoodsSkuListByThreeCategoryId(SearchParameter searchParameter) {
        //传入当前页和分类id的值
        PageHelper.startPage(searchParameter.getCurrentPage(), 10);
        return tCategoryJxExtMapper.queryGoodsListPageByCategoryThree(searchParameter.getCategoryId());
    }

    /**
     * 获取所有商品的skuNo
     */
    @Override
    public List<String> queryAllGoodsSkuList() {
        List<TOpSkuExt> skuList = tCategoryJxExtMapper.queryAllSkuList();
        List<String> skuNoList = new ArrayList<>();
        for (int i = 0; i < skuList.size(); i++) {
            skuNoList.add(skuList.get(i).getSkuNo());
        }
        return skuNoList;
    }


    //获取一级/二级分类下的三级分类
    @Override
    public List<Map<String, Object>> queryCategoryThreeByTopCate(Integer categoryId) {
        List<TCategoryJxExt> res = tCategoryJxExtMapper.queryThreeByOne(categoryId);
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < res.size(); i++) {
            Map<String, Object> category = new HashMap<>();
            category.put("categoryId", res.get(i).getCategoryId());
            category.put("categoryName", res.get(i).getCategoryName());
            list.add(category);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> searchCategories(SearchParameter searchParameter) {
        return null;
    }

    @Override
    public List<String> searchBrands(SearchParameter searchParameter) {
        return null;
    }
}
