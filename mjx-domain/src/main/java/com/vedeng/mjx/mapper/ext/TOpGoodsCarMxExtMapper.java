package com.vedeng.mjx.mapper.ext;

import com.vedeng.mjx.domain.ext.VGoodsCarExtMx;
import com.vedeng.mjx.mapper.VGoodsCarMxMapper;

import java.util.List;
import java.util.Map;

/**  购物车的Mapper层
 * @ auther:Jax
 * @ date:  2019/7/1 14:28
 */
public interface TOpGoodsCarMxExtMapper extends VGoodsCarMxMapper {

    List<VGoodsCarExtMx> queryGoodsCarList(VGoodsCarExtMx paramEntity);

    Map<String, String> queryFirst(VGoodsCarExtMx paramEntity);

    int checkFirst(VGoodsCarExtMx paramEntity);

    String queryGoodsCarInfo(String skuNo);

}
