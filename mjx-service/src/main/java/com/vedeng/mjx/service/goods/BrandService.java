package com.vedeng.mjx.service.goods;

import com.vedeng.mjx.common.indexVo.BannerData;
import com.vedeng.mjx.domain.TBrandData;
import com.vedeng.mjx.domain.TBrandJx;

import java.util.List;

public interface BrandService {

    /**
     *  获取六个品牌
     */
    List<TBrandJx> queryBrands();

    List<TBrandJx> queryBrandsList(String mjxUrl);

    TBrandJx selectBrandById();

    List<TBrandData> brandDataList(String mjxUrl,String mBrands);

    List<BannerData> bannerList(Integer platfromPortId,String comments,String jx_comments);

    List<BannerData> bannerListApp(Integer platfromPortId,String comments);

}
