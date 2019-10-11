package com.vedeng.mjx.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.vedeng.mjx.common.enumUtils.PlaceEnum;
import com.vedeng.mjx.common.indexVo.BannerData;
import com.vedeng.mjx.domain.*;
import com.vedeng.mjx.mapper.TBrandJxMapper;
import com.vedeng.mjx.mapper.TBrandMapper;
import com.vedeng.mjx.mapper.VAdvertiseMapper;
import com.vedeng.mjx.mapper.VBrandMapper;
import com.vedeng.mjx.service.goods.BrandService;
import com.vedeng.mjx.service.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author ：Gavin.li
 * @date ：Created in 2019/6/26 16:09
 * @description：
 * @modified By：
 * @version:
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Resource
    private TBrandJxMapper brandJxMapper;
    @Resource
    private TBrandMapper brandMapper;
    @Resource
    private VBrandMapper vBrandMapper;
    @Resource
    private VAdvertiseMapper advertiseMapper;

    /**
     *  获取六个固定的品牌
     * @return
     */
    @Override
    public List<TBrandJx> queryBrands() {
        List<TBrandJx> brandList = new ArrayList<>();
        TBrandJx brand1 = new TBrandJx();
        brand1.setBrandName("迈瑞Mindray");
        brand1.setBrandId(1345);

        TBrandJx brand2 = new TBrandJx();
        brand2.setBrandName("GE医疗");
        brand2.setBrandId(1137);

        TBrandJx brand3 = new TBrandJx();
        brand3.setBrandName("yuwell鱼跃");
        brand3.setBrandId(1484);

        TBrandJx brand4 = new TBrandJx();
        brand4.setBrandName("Aeonmed谊安");
        brand4.setBrandId(1411);

        TBrandJx brand5 = new TBrandJx();
        brand5.setBrandName("澳华");
        brand5.setBrandId(4198);

        TBrandJx brand6 = new TBrandJx();
        brand6.setBrandName("3M");
        brand6.setBrandId(93);

        brandList.add(brand1);
        brandList.add(brand2);
        brandList.add(brand3);
        brandList.add(brand4);
        brandList.add(brand5);
        brandList.add(brand6);

        return brandList;
    }

    @Override
    public List<TBrandJx> queryBrandsList(String mjxUrl) {
        List<TBrandJx> brandList = new ArrayList<>();
        TBrandJx brand1 = new TBrandJx();
        brand1.setBrandName("迈瑞Mindray");
        brand1.setBrandId(1345);
        brand1.setLogoUri(mjxUrl+"images/brand/brand"+brand1.getBrandId()+".jpg");

        TBrandJx brand2 = new TBrandJx();
        brand2.setBrandName("GE医疗");
        brand2.setBrandId(1137);
        brand2.setLogoUri(mjxUrl+"images/brand/brand"+brand2.getBrandId()+".jpg");

        TBrandJx brand3 = new TBrandJx();
        brand3.setBrandName("yuwell鱼跃");
        brand3.setBrandId(1484);
        brand3.setLogoUri(mjxUrl+"images/brand/brand"+brand3.getBrandId()+".jpg");

        TBrandJx brand4 = new TBrandJx();
        brand4.setBrandName("Aeonmed谊安");
        brand4.setBrandId(1411);
        brand4.setLogoUri(mjxUrl+"images/brand/brand"+brand4.getBrandId()+".jpg");

        TBrandJx brand5 = new TBrandJx();
        brand5.setBrandName("澳华");
        brand5.setBrandId(4198);
        brand5.setLogoUri(mjxUrl+"images/brand/brand"+brand5.getBrandId()+".jpg");

        TBrandJx brand6 = new TBrandJx();
        brand6.setBrandName("3M");
        brand6.setBrandId(93);
        brand6.setLogoUri(mjxUrl+"images/brand/brand"+brand6.getBrandId()+".jpg");

        brandList.add(brand1);
        brandList.add(brand2);
        brandList.add(brand3);
        brandList.add(brand4);
        brandList.add(brand5);
        brandList.add(brand6);

        return brandList;
    }

    /**
     *  select 品牌
     */
    public TBrandJx selectBrandById(){
        TBrandJx TBrandJx = new TBrandJx();
//        TBrandJx = brandMapper.selectByPrimaryKey(93);
        return TBrandJx;
    }

    @Override
    public List<TBrandData> brandDataList(String mjxUrl,String mBrands) {

        List<String> mBrandsList = Arrays.asList(mBrands.split(","));
        List<TBrandData> brandList = vBrandMapper.selectBrandList(getBrandDataList(mBrandsList));
        System.out.println("brandList:"+JSON.toJSONString(brandList));
        List<TBrandData> brandListNew = new ArrayList<>();
        for(TBrandData brandData : brandList){
            brandData.setUrl(mjxUrl+"images/brand/brand"+brandData.getBrandId()+".png");
            brandListNew.add(brandData);
        }
        return brandListNew;
    }

    public static List<String> getBrandDataList(List<String> mBrandsList){
        List<String> mBrandsListNew = new ArrayList<>();
        Set<Integer> setNum = RandomUtil.getRandomMApp(mBrandsList.size(),6);
        for(Integer num : setNum){
            mBrandsListNew.add(mBrandsList.get(num));
        }
        return mBrandsListNew;
    }

    @Override
    public List<BannerData> bannerList(Integer platfromPortId,String comments,String jx_comments) {

        List<BannerData> bannerList = new ArrayList<>();
        //读取符合条件的banner图
        List<BannerSourceData> bannerDataList = advertiseMapper.selectBannerList(platfromPortId,comments);
        //没有符号条件的banner图，则读取备用的
        if(CollectionUtils.isEmpty(bannerDataList)){
            bannerDataList = advertiseMapper.selectBannerSpare(platfromPortId, jx_comments);
            if(!CollectionUtils.isEmpty(bannerDataList)) {
                BannerSourceData bannerSourceData = bannerDataList.get(0);
                bannerList.add(getBanner(bannerSourceData));
            }
        }else{
            for(BannerSourceData bannerSourceData : bannerDataList){
                bannerList.add(getBanner(bannerSourceData));
            }
        }
        return bannerList;
    }

    public BannerData getBanner(BannerSourceData bannerSourceData){
        BannerData bannerData = new BannerData();
        bannerData.setImgUrl("http://" + bannerSourceData.getDomain() + bannerSourceData.getUrl());
        bannerData.setUrl(bannerSourceData.getPageLinks());
        bannerData.setShowType(bannerSourceData.getShowType());
        return bannerData;
    }

    @Override
    public List<BannerData> bannerListApp(Integer platfromPortId,String comments) {

        List<BannerData> bannerList = new ArrayList<>();
        List<BannerSourceData> bannerDataList = advertiseMapper.selectBannerList(platfromPortId,comments);
        if(CollectionUtils.isEmpty(bannerDataList)){
            bannerDataList = advertiseMapper.selectBannerSpare(platfromPortId, PlaceEnum.INDEX_SPARE.getValue());
        }
        for(BannerSourceData bannerSourceData : bannerDataList){
            BannerData bannerData = new BannerData();
            bannerData.setImgUrl("http://"+bannerSourceData.getDomain()+bannerSourceData.getUrl());
            bannerData.setUrl(bannerSourceData.getPageLinks());
            bannerList.add(bannerData);
        }
        return bannerList;
    }

}
