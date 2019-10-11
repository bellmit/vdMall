package com.vedeng.mjx.service.liam;

import com.vedeng.goods.api.response.BrandResponse;
import com.vedeng.mjx.domain.TBrand;
import com.vedeng.mjx.domain.VBrand;

import java.util.List;

public interface TBrandService {

    public List<BrandResponse> getAllBrand();

    public List<TBrand> getBrandnyEntity(TBrand tBrand);

    public int deleteBrandbyId(Integer id);

    public TBrand getBrandById(Integer id);

    VBrand getVBrandById(Integer id);

}
