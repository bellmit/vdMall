package com.vedeng.mjx.service.feign.goods;//package com.vedeng.template.service.feign.user;
import com.vedeng.goods.api.SearchParameter;
import com.vedeng.goods.api.service.GoodsSearchAPI;
import com.vedeng.goods.api.vo.SearchSkuResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "${goodsName}")
public interface GoodsSearchFeignApi extends GoodsSearchAPI {

//    /**
//     * 根据品牌ID获取搜索结果以及facet  分类 ，品牌，属性，科室
//     * @param searchParameter  brandIdList
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/goods/fillSkuBySkuNos")
//    SearchSkuResult fillSkuBySkuNos(@RequestBody SearchParameter searchParameter) throws Exception;

    /**
     *
     * @param searchParameter  pageSize随机的数量
     * @return
     * @throws Exception
     */
    @PostMapping("/goods/selectRandomSkus")
    SearchSkuResult selectRandomSkus(@RequestBody SearchParameter searchParameter) throws Exception;

}

//@Component
//class UserInfoFeignClientFallback implements UserInfoServiceFeignClient {
//
//    @Override
//    public UserDTO findById(Long aLong) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(0L);
//        userDTO.setUsername("hystrix");
//
//        return userDTO;
//    }
