package com.vedeng.mjx.web.controller.appsearch.category;

import com.alibaba.fastjson.JSON;
import com.vedeng.goods.api.SearchParameter;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.RedisKeyConstant;
import com.vedeng.mjx.common.http.HttpClientUtils;
import com.vedeng.mjx.domain.VCategory;
import com.vedeng.mjx.domain.ext.VCategoryDto;
import com.vedeng.mjx.service.feign.goods.GoodsSearchFeignApi;
import com.vedeng.mjx.service.liam.VCategoryService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/app/category")
public class CategoryController{


    private  Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Resource
    private GoodsSearchFeignApi goodsSearchFeignApi;

    @Resource
    private VCategoryService vCategoryService;

    @Resource
    private RedissonClient redissonClient;

    @PostMapping("/getallcategory")
    public ResultInfo getallCategory(Integer cat1Id){
        List<VCategoryDto> vCategoryDtos = vCategoryService.queryCategoryAllList(cat1Id);
        return ResultInfo.success(vCategoryDtos);
    }


    @PostMapping("/getonecategory")
    public ResultInfo getonecategory(){
        List<VCategory> vCategoryDtos = vCategoryService.queryCategoryOneList();
        return ResultInfo.success(vCategoryDtos);
    }




    @PostMapping("/getcategorybyParentId")
    public ResultInfo getcategorybyParentId(Integer parentId){
        List<VCategory> vCatrgories = vCategoryService.queryCategoryByParentId(parentId);
        return ResultInfo.success(vCatrgories);
    }

    @RequestMapping("/clearRedis/{id}")
    public ResultInfo clearRedis(@PathVariable("id") String id){
        RBucket<Object> keyObject = redissonClient.getBucket(id);
        keyObject.set(null);
        return ResultInfo.success("清除成功");
    }

    @RequestMapping("/queryRedis/{id}")
    public ResultInfo queryRedis(@PathVariable("id") String id){
        RBucket<Object> keyObject = redissonClient.getBucket(id);
        return ResultInfo.success(keyObject.get());
    }

    @PostMapping("/getsearchCategory")
    public ResultInfo getsearchCategory(Integer cat1Id){

        RBucket<Object> keyObject  = redissonClient.getBucket(RedisKeyConstant.mjxallkey);
        if (keyObject.get()==null){
            List<VCategoryDto> vCategoryDtos = vCategoryService.queryCategoryAllList(null);
            keyObject.set(vCategoryDtos,1, TimeUnit.DAYS);
        }
        List<VCategoryDto> vCategoryDtos = (List<VCategoryDto>) keyObject.get();
        logger.info(JSON.toJSONString(vCategoryDtos));
        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setCategoryId(cat1Id);
        searchParameter.setPlatfromId(1);
        searchParameter.setIsOnSale(1);
        searchParameter.setIsJx(0);
        Map<String, List<String>> allCategory = goodsSearchFeignApi.getAllCategory(searchParameter);
        List<String> cat_id_1 = allCategory.get("CAT_ID_1");
        List<String> cat_id_2 = allCategory.get("CAT_ID_2");
        List<String> cat_id_3 = allCategory.get("CAT_ID_3");
        logger.info(cat_id_1.toString());
        logger.info(cat_id_2.toString());
        logger.info(cat_id_2.toString());

        for (int i=0;i<vCategoryDtos.size();i++) {

            Integer categoryId = vCategoryDtos.get(i).getvCategoryId();
            if (categoryId==1){
                System.out.println("sdsdssddds");
            }
            if (cat1Id!=null&&!categoryId.equals(cat1Id)) {
                vCategoryDtos.remove(i);
                i--;
                continue;
            }
            boolean hascatid = cat_id_1.stream().anyMatch((s) -> s.equals(categoryId.toString()));
            if(!hascatid) {
                vCategoryDtos.remove(i);
                i--;
            }else {
                List<VCategoryDto> childCategoryList = vCategoryDtos.get(i).getChildCategoryList();
                for (int j=0;j<childCategoryList.size();j++) {
                    Integer categoryId2 = childCategoryList.get(j).getvCategoryId();
                    boolean hascatid2 = cat_id_2.stream().anyMatch((s) -> s.equals(categoryId2.toString()));
                    if(!hascatid2) {
                        childCategoryList.remove(j);
                        j--;
                    }else {
                        List<VCategoryDto> childCategoryList1 = childCategoryList.get(j).getChildCategoryList();
                        for (int k=0;k<childCategoryList1.size();k++) {
                            Integer categoryId3 = childCategoryList1.get(k).getvCategoryId();
                            boolean hascatid3 = cat_id_3.stream().anyMatch((s) -> s.equals(categoryId3.toString()));
                            if(!hascatid3) {
                                childCategoryList1.remove(k);
                                k--;
                            }
                        }
                        if (childCategoryList1.size()==0){
                            childCategoryList.remove(j);
                            j--;
                        }
                    }
                }
                if (childCategoryList.size()==0){
                    vCategoryDtos.remove(i);
                    i--;
                }
            }
        }
        return ResultInfo.success(vCategoryDtos);
    }



}
