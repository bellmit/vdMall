package com.vedeng.mjx.web.controller.m;


import com.alibaba.fastjson.JSON;
import com.vedeng.goods.api.SearchParameter;
import com.vedeng.goods.api.vo.SearchSkuResult;
import com.vedeng.goods.api.vo.SkuListVO;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.enumUtils.BannerEnum;
import com.vedeng.mjx.common.enumUtils.PlaceEnum;
import com.vedeng.mjx.common.indexVo.BannerData;
import com.vedeng.mjx.domain.TAttachment;
import com.vedeng.mjx.domain.TAttachmentExample;
import com.vedeng.mjx.domain.TBrandData;
import com.vedeng.mjx.mapper.TAttachmentMapper;
import com.vedeng.mjx.service.feign.goods.GoodsSearchFeignApi;
import com.vedeng.mjx.service.goods.BrandService;
import com.vedeng.mjx.service.goods.CategoryService;
import com.vedeng.mjx.service.goods.SearchKeyWordService;
import com.vedeng.mjx.service.goods.impl.MFloorVo;
import com.vedeng.mjx.service.goodsCar.GoodsCarService;
import com.vedeng.mjx.service.util.RandomUtil;
import com.vedeng.mjx.web.controller.base.CommonController;
import com.vedeng.mjx.web.controller.m.vo.MIndexData;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RequestMapping("/m")
@Api(value="M站首页controller",tags={"M站首页控制层"})
@RestController
public class MIndexController extends CommonController {

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
    private GoodsCarService goodsCarService;
    @Resource
    SearchKeyWordService searchKeyWordService;
    @PostMapping(value = {"/index"})
    public ResultInfo mjxIndex() {

        MIndexData mIndexData = new MIndexData();

        List<BannerData> bannerDataList = brandService.bannerList(BannerEnum.MJX.getValue(), PlaceEnum.INDEX.getValue(),PlaceEnum.INDEX_SPARE.getValue());

        List<String> categoryIdlist = Arrays.asList(mCategoryIds.split(","));
        List<MFloorVo> mFloorVoList = categoryService.level1CategoryList(mjxUrl,categoryIdlist);
        List<TBrandData> brandList = brandService.brandDataList(mjxUrl,mBrands);

        List<String> categoryLevel3Idlist = Arrays.asList(mLevel3categoryIds.split(","));
        List<MFloorVo> level3CategoryList = categoryService.level3CategoryList(mjxUrl,getLevel3CategoryId(categoryLevel3Idlist));

        List<String> searchName = searchKeyWordService.searchIndexName();

        SearchSkuResult searchSkuResult = null;
        try {
            SearchParameter searchParameter = new SearchParameter();
            searchParameter.setPageSize(50);
            searchSkuResult = goodsSearchFeignApi.selectRandomSkus(searchParameter);
        } catch (Exception e) {
            log.error("查询selectRandomSkus异常{}",e);
        }
        List<SkuListVO> skuListVOList = new ArrayList<>();
        if(searchSkuResult != null){
            skuListVOList = searchSkuResult.getSkuList();
            for(SkuListVO skuListVO : skuListVOList){
                if(!checkPriceAuth()){
                    skuListVO.setJxMarketPrice(new BigDecimal(0));
                    skuListVO.setJxSalePrice(null);
                }
            }
        }

        mIndexData.setBannerList(bannerDataList);
        mIndexData.setLevel1CategoryList(mFloorVoList);
        mIndexData.setBrandList(brandList);
        mIndexData.setLevel3CategoryList(level3CategoryList);
        mIndexData.setSearchName(searchName);
        mIndexData.setSkuListVOS(skuListVOList);
        mIndexData.setGoodCarCount(goodsCarService.getGoodCarCount(getAccountId()));

        return ResultInfo.success(mIndexData);
    }

    public static List<String> getLevel3CategoryId(List<String> categoryIdlist){
        List<String> categoryIdlistNew = new ArrayList<>();
        Set<Integer> setNum = RandomUtil.getRandomMApp(categoryIdlist.size(),8);
        for(Integer num : setNum){
            categoryIdlistNew.add(categoryIdlist.get(num));
        }
        return categoryIdlistNew;
    }

}


