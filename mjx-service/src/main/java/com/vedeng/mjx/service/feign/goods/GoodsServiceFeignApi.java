package com.vedeng.mjx.service.feign.goods;//package com.vedeng.template.service.feign.user;
import com.vedeng.goods.api.RequestParameter;
import com.vedeng.goods.api.service.GoodsInfoAPI;
import com.vedeng.goods.api.vo.SkuVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${goodsName}")
public interface GoodsServiceFeignApi extends GoodsInfoAPI {

    /**
     * ��ȡ����sku
     * @param requestParameter .skuNo
     * @return
     */
    @PostMapping("/goods/skuDetail")
    SkuVO getSkuDetailBySkuNo(@RequestBody RequestParameter requestParameter);

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
