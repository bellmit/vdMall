package com.vedeng.mjx.mapper.ext;

import com.vedeng.mjx.domain.VGoodsCarMx;
import com.vedeng.mjx.domain.ext.VGoodsCarExtMx;

import java.util.List;
import java.util.Map;

/**  购物车的Mapper层
 * @ auther:Jax
 * @ date:  2019/7/1 14:28
 */
public interface VGoodsCarMxExtMapper {

    List<VGoodsCarExtMx> queryGoodsCarList(VGoodsCarExtMx paramEntity);
    List<VGoodsCarExtMx> queryGoodsCarMxList(VGoodsCarExtMx paramEntity);
    List<VGoodsCarExtMx> queryGoodsCarMxListCom(VGoodsCarExtMx paramEntity);

    Map<String, String> queryFirst(VGoodsCarExtMx paramEntity);

    int checkFirst(VGoodsCarExtMx paramEntity);
    Integer queryGoodsCarInfo(String skuNo);

    int updateGoodsDefault(VGoodsCarExtMx paramEntity);
    void updateGoodsIsChoosed(VGoodsCarExtMx paramEntity);
    void updateGoodsIsChoosedAll(VGoodsCarExtMx paramEntity);

    int deleteGoodsCar(VGoodsCarExtMx paramEntity);

    int updateGoodsCount(VGoodsCarExtMx paramEntity);
    void updateGoodsMxCount(VGoodsCarExtMx paramEntity);
    void softDeleteGoodsCarMx(VGoodsCarExtMx paramEntity);

    int insertUserCar(VGoodsCarExtMx paramEntity);

    int addGoodsIntoCar(VGoodsCarExtMx paramEntity);
    int addGoodsCarMxOnDupKeyUpdate(VGoodsCarExtMx paramEntity);

    int insertPrepareGoodsCar(VGoodsCarExtMx paramEntity);
    void exitPreCommitGoodsCar(VGoodsCarExtMx paramEntity);

    Integer querySkuNoCount(VGoodsCarExtMx paramEntity);

    Map<String, Object> queryExisetSkuNo(VGoodsCarExtMx paramEntity);
    VGoodsCarExtMx existsSkuNoInNoCommit(String skuNo);
    VGoodsCarExtMx queryByCarMxId(Integer carMxId);

    int updateIsCloseStatus(VGoodsCarExtMx paramEntity);
    void deleteGoodsCarMxs(VGoodsCarExtMx paramEntity);

    String queryIsCloseStatus(VGoodsCarExtMx paramEntity);
    String queryIsDel(Integer carMxId);

    int updateIsCommitStatus(VGoodsCarExtMx paramEntity);

    List<VGoodsCarMx> selectVGoodsCarMxList(List<String> list);

    int updateGoodsCarList(List<VGoodsCarMx> list);

    List<VGoodsCarMx> getGoodCarCount(Integer accountAddressId);
    List<VGoodsCarExtMx> queryPreCommitGoodsMxs(VGoodsCarExtMx paramEntity);

}
