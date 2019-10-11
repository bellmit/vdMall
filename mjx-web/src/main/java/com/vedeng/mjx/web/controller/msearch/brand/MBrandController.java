package com.vedeng.mjx.web.controller.msearch.brand;


import com.vedeng.goods.api.response.BrandResponse;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.domain.TBrand;
import com.vedeng.mjx.domain.TBrandJx;
import com.vedeng.mjx.service.goods.BrandService;
import com.vedeng.mjx.service.liam.TBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/m/brand")
@RestController
public class MBrandController {


    @Resource
    private TBrandService tBrandService;

    @Autowired
    private BrandService brandService;


    @RequestMapping("/getAllBrand")
    public ResultInfo getAllBrand(){

        List<BrandResponse> allBrand = tBrandService.getAllBrand();
        return ResultInfo.success(allBrand);
    }

    @RequestMapping("/getBrandnyEntity")
    public ResultInfo getBrandnyEntity(TBrand tBrand){

        List<TBrand> brandnyEntity = tBrandService.getBrandnyEntity(tBrand);
        return ResultInfo.success(brandnyEntity);
    }


    @RequestMapping("deleteBrandbyId")
    public ResultInfo deleteBrandbyId(Integer id){
        int i = tBrandService.deleteBrandbyId(id);
        if (i==1) {
            return ResultInfo.success("删除成功！");
        }else {
            return ResultInfo.fail("","删除失败");
        }
    }


    @RequestMapping("getHotBrands")
    public ResultInfo getHotBrands(){
        List<TBrandJx> brandList = brandService.queryBrands();
        return  ResultInfo.success(brandList);
    }
}



