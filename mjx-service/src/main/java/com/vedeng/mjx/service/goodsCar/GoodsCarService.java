package com.vedeng.mjx.service.goodsCar;

import com.vedeng.mjx.domain.VGoodsCarMx;
import com.vedeng.mjx.domain.ext.VGoodsCarExtMx;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ auther:
 * @ date:  2019/7/1 13:41
 */
public interface GoodsCarService {

    Map<String, Object> queryGoodsCarList(VGoodsCarExtMx accountId);

    Map<String, Object> appGoodsCarList(VGoodsCarExtMx paramEntity);

    int updateGoodsDefault(VGoodsCarExtMx paramEntity);

    @Transactional
    void updateGoodsIsChoosed(VGoodsCarExtMx paramEntity);

    int deleteGoodsCar(VGoodsCarExtMx paramEntity);

    void updateGoodsMxCount(VGoodsCarExtMx paramEntity);

    @Transactional
    void deleteGoodsCarMxList(VGoodsCarExtMx paramEntity);

    int updateGoodsCount(VGoodsCarExtMx paramEntity);

    int insertPrepareGoodsCar(VGoodsCarExtMx paramEntity);

    @Transactional
    void prepareCommitGoodsCar(VGoodsCarExtMx paramEntity);

    @Transactional
    void addGoodsIntoCar(VGoodsCarExtMx paramEntity);

    List<VGoodsCarMx> selectVGoodsCarMxList(List<String> skuList);

    int updateGoodsCarList(List<VGoodsCarMx> list);

    void exitPreCommitGoodsCar(VGoodsCarExtMx paramEntity);

    Integer getGoodCarCount(Integer userId);

}
