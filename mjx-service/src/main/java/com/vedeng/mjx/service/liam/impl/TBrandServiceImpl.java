package com.vedeng.mjx.service.liam.impl;

import com.vedeng.goods.api.SearchParameter;
import com.vedeng.goods.api.response.BrandResponse;
import com.vedeng.mjx.domain.*;
import com.vedeng.mjx.mapper.TBrandMapper;
import com.vedeng.mjx.mapper.VBrandMapper;
import com.vedeng.mjx.service.feign.goods.GoodsSearchFeignApi;
import com.vedeng.mjx.service.liam.TBrandService;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TBrandServiceImpl implements TBrandService {

    @Resource
    private TBrandMapper tBrandMapper;

    @Resource
    private VBrandMapper vBrandMapper;

    @Resource
    private GoodsSearchFeignApi goodsSearchFeignApi;

    public List<BrandResponse> getAllBrand(){

        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setPlatfromId(1);
        searchParameter.setIsOnSale(1);
        List<BrandResponse> allBrand = goodsSearchFeignApi.getAllBrand(searchParameter);
        return allBrand;
    }

    public List<TBrand> getBrandnyEntity(TBrand tBrand){

        List<TBrand> tBrands = tBrandMapper.selectBrandbyEntity(tBrand);

        return tBrands;
    }

    @Override
    public int deleteBrandbyId(Integer id) {
        TBrandKey key=new TBrandKey();
        key.setBrandId(id);
        int i = tBrandMapper.deleteByPrimaryKey(key);
        return i;
    }

    public TBrand getBrandById(Integer id){

        TBrandKey tBrandKey = new TBrandKey();
        tBrandKey.setBrandId(id);
        TBrand tBrands = tBrandMapper.selectByPrimaryKey(tBrandKey);
        return tBrands;

    }

    public VBrand getVBrandById(Integer id){
        VBrandExample vBrandExample = new VBrandExample();
        vBrandExample.createCriteria().andErpBrandIdEqualTo(id);
        List<VBrand> vBrands = vBrandMapper.selectByExample(vBrandExample);
        if(vBrands!=null&&vBrands.size()!=0) {
            return vBrands.get(0);
        }
        return null;
    }


}
