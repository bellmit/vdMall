package com.vedeng.mjx.web.controller.m.goodscar;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.exception.VedengException;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.ext.VGoodsCarExtMx;
import com.vedeng.mjx.service.goodsCar.GoodsCarService;
import com.vedeng.mjx.web.controller.base.BaseController;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.vedeng.mjx.common.constant.CommonConstant.GOODSCAR_NUM_MAX_COUNT;
import static com.vedeng.mjx.common.constant.CommonConstant.ZEOR;
import static com.vedeng.mjx.common.exception.VedengErrorCode.*;

/***
 * class_name: GoodsCarController
 * package: com.vedeng.mjx.web.controller.app.goodscar
 * creat_user:JunLee
 * creat_date: 2019/8/27
 * creat_time: 14:44
 * describe: M站购物车
 */

@RequestMapping("/m/goodsCar")
@RestController
public class MsiteGoodsCarController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private GoodsCarService goodsCarService;

    /**
     * 详情页加入购物车
     *
     * @param
     */
    @ApiOperation(value = "加入购物车", notes = "加入购物车")
    @PostMapping("/addGoodsIntoCar")
    public ResultInfo addGoodsIntoCar(@RequestBody VGoodsCarExtMx paramEntity) {
        //校验
        if (StringUtil.isBlank(paramEntity.getSkuNo()) || paramEntity.getCount() == null || paramEntity.getCount() <= ZEOR || paramEntity.getCount() > GOODSCAR_NUM_MAX_COUNT) {
            return ResultInfo.fail(PARAM_MISS);
        }
        try {
            paramEntity.setAccountId(getAccountId());
            goodsCarService.addGoodsIntoCar(paramEntity);
            return ResultInfo.success(SUCCESS_CODE);
        } catch (VedengException e) {
            logger.error("addGoodsIntoCar VedengException code is {} msg is {}", e.getCode(), e.getMessage());
            return ResultInfo.fail(e);
        } catch (Exception e) {
            logger.error("addGoodsIntoCar error", e);
            return ResultInfo.fail(UNKNOWN_EXCEPTION);
        }
    }


//    /***
//     * 购物车预提交
//     *
//     */
//    @ApiOperation(value = "购物车预提交", notes = "购物车预提交")
//    @PostMapping("/addPreCommit")
//    public ResultInfo insertPrepareGoodsCar() {
//        try {
//            VGoodsCarExtMx paramEntity = new VGoodsCarExtMx();
//            paramEntity.setAccountId(getAccountId());
//            goodsCarService.prepareCommitGoodsCar(paramEntity);
//            return ResultInfo.success(SUCCESS_CODE);
//        } catch (VedengException e) {
//            logger.error("addGoodsIntoCar VedengException code is {} msg is {}", e.getCode(), e.getMessage());
//            return ResultInfo.fail(e);
//        } catch (Exception e) {
//            logger.error("prepareCommitGoodsCar error", e);
//            return ResultInfo.fail(UNKNOWN_EXCEPTION);
//        }
//    }
//
//    /**
//     * 修改复选框
//     *
//     * @param paramEntity：status : 是否选中（0否1是) carMxId : 明细Id
//     */
//    @ApiOperation(value = "修改复选框", notes = "修改复选框")
//    @PostMapping("/updateGoodsDefault")
//    public ResultInfo updateGoodsDefault(@RequestBody VGoodsCarExtMx paramEntity) {
//        //校验
//        if (paramEntity.getStatus() == null || paramEntity.getCarMxId() == null) {
//            return ResultInfo.fail(PARAM_MISS);
//        }
//        logger.info("paramEntity status is {} carMxId is {}", paramEntity.getStatus(), paramEntity.getCarMxId());
//        paramEntity.setAccountId(getAccountId());
//        goodsCarService.updateGoodsIsChoosed(paramEntity);
//        return ResultInfo.success(SUCCESS_CODE);
//    }
//
//    /**
//     * 修改购买数量
//     *
//     * @param
//     */
//    @ApiOperation(value = "修改购买数量", notes = "修改购买数量")
//    @PostMapping("/updateGoodsCount")
//    public ResultInfo updateGoodsCount(@RequestBody VGoodsCarExtMx paramEntity) {
//        //校验
//        if (paramEntity.getCarMxId() == null || paramEntity.getCount() == null) {
//            return ResultInfo.fail(PARAM_MISS);
//        }
//        goodsCarService.updateGoodsMxCount(paramEntity);
//        return ResultInfo.success(SUCCESS_CODE);
//    }
//
//
//    @ApiOperation(value = "查询购物车列表", notes = "查询购物车列表")
//    @PostMapping("/list")
//    public ResultInfo queryGoodsCarList() {
//        Map<String, Object> resultMap = null;
//        try {
//            VGoodsCarExtMx reqCarMx = new VGoodsCarExtMx();
//            reqCarMx.setAccountId(getAccountId());
//            if (checkPriceAuth()) {
//                reqCarMx.setIsMarketPrice(CommonConstant.STRING_ONE);
//            }
//            logger.info("reqCarMx[accountId] is {},reqCarMx[isMarketPrice] is {}",
//                    reqCarMx.getAccountId(), reqCarMx.getIsMarketPrice());
//            resultMap = goodsCarService.appGoodsCarList(reqCarMx);
//            logger.info("goodsCarService.queryGoodsCarList | result is {}", resultMap);
//
//        } catch (Exception e) {
//            logger.error("appGoodsCarList error", e);
//            return ResultInfo.fail(UNKNOWN_EXCEPTION);
//        }
//        return ResultInfo.success(resultMap);
//    }
//
//    /***
//     * 删除购物车
//     * @param
//     *
//     */
//    @PostMapping("/deleteGoodsCarMx")
//    public ResultInfo deleteGoodsCar(@RequestBody VGoodsCarExtMx paramEntity) {
//        // 参数校验
//        if (StringUtil.isBlank(paramEntity.getCarMxIdsExt())) {
//            return ResultInfo.fail(PARAM_MISS);
//        }
//        logger.info("paramEntity carMxIdsExt is {}", paramEntity.getCarMxIdsExt());
//        String[] ids = paramEntity.getCarMxIdsExt().split(",");
//        List<Integer> carMxIdsList = new ArrayList<>();
//        for (String id : Arrays.asList(ids)) {
//            carMxIdsList.add(Integer.valueOf(id));
//        }
//        paramEntity.setAccountId(getAccountId());
//        return ResultInfo.success(SUCCESS_CODE);
//    }

    @ApiOperation(value = "计算购物车商品数量", notes = "在已登录状态下计算购物车商品数量")
    @PostMapping("/getGoodCarCount")
    public ResultInfo getGoodCarCount() {
        Map resultMap = new HashMap();
        Integer count = 0;
        try {
            //查询购物车商品数量
            count = goodsCarService.getGoodCarCount(getAccountId());
            resultMap.put("totalCount", count);
        } catch (Exception e) {
            logger.error("GoodsCarController | getGoodCarCount 查询购物车数量异常", e);
            return ResultInfo.fail(UNKNOWN_EXCEPTION);
        }
        return ResultInfo.success(SUCCESS_CODE, resultMap);
    }

}
