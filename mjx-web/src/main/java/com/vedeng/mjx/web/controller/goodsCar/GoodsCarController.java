package com.vedeng.mjx.web.controller.goodsCar;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.exception.VedengException;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.ext.VGoodsCarExtMx;
import com.vedeng.mjx.service.goodsCar.GoodsCarService;
import com.vedeng.mjx.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

/**
 * @ auther: Jax
 * @ date 2019/7/12
 * 购物车Controller层
 */
@RequestMapping("/mjx/goodsCar")
@Api(value = "购物车controller", tags = {"购物车控制层"})
@RestController
public class GoodsCarController extends BaseController {
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
        if (StringUtil.isBlank(paramEntity.getSkuNo()) || paramEntity.getCount() == null ||paramEntity.getCount() <= 0 || paramEntity.getCount() >= 9999) {
            return ResultInfo.fail(VedengErrorCode.PARAM_MISS);
        }
        try {
            paramEntity.setAccountId(getAccountId());
            goodsCarService.addGoodsIntoCar(paramEntity);
        } catch (Exception e) {
            logger.error("新增异常", e);
            return ResultInfo.fail("0", e.getMessage());
        }
            return ResultInfo.success("加入采购车成功");
    }


    /***
     * 首页购物车点击提交订单
     * @param paramEntity：被选择的skuNo商品，多个又逗号拼接
     *
     */
    @ApiOperation(value = "购物车预提交", notes = "购物车预提交")
    @PostMapping("/addOrderGoodsCar")
    public ResultInfo insertPrepareGoodsCar(@RequestBody VGoodsCarExtMx paramEntity) {
        int p = 0;
        try {
            paramEntity.setAccountId(getAccountId());
            p = goodsCarService.insertPrepareGoodsCar(paramEntity);
        } catch (Exception e) {
            //modby glenn
            logger.error("提交异常", e);
            if (e instanceof VedengException) {
                VedengException exception = (VedengException) e;
                return ResultInfo.fail(exception.getCode(), exception.getMessage());
            }
            return ResultInfo.fail(VedengErrorCode.UNKNOWN_EXCEPTION);
        }
        if (p > 0) {
            return ResultInfo.success(VedengErrorCode.SUCCESS_CODE);
        }
        return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
    }

    /**
     * 修改复选框
     *
     * @param paramEntity：被选择的skuNo商品，多个又逗号拼接
     */
    @ApiOperation(value = "修改复选框", notes = "修改复选框")
    @PostMapping("/updateGoodsDefault")
    public ResultInfo updateGoodsDefault(@RequestBody VGoodsCarExtMx paramEntity) {
        int p = 0;
        try {
            paramEntity.setAccountId(getAccountId());
            p = goodsCarService.updateGoodsDefault(paramEntity);
        } catch (Exception e) {
            //modby glenn
            logger.error("修改异常", e);
            return ResultInfo.fail("0", e.getMessage());
        }
        if (p > 0) {
            return ResultInfo.success("修改成功");
        }
        return ResultInfo.fail("0", "修改失败");
    }

    /**
     * 修改购买数量
     *
     * @param
     */
    @ApiOperation(value = "修改购买数量", notes = "修改购买数量")
    @PostMapping("/updateGoodsCount")
    public ResultInfo updateGoodsCount(@RequestBody VGoodsCarExtMx paramEntity) {
        int p = 0;
        try {
            paramEntity.setAccountId(getAccountId());
            p = goodsCarService.updateGoodsCount(paramEntity);
        } catch (Exception e) {
            //modby glenn
            logger.error("购物车修改异常", e);
            return ResultInfo.fail("0", e.getMessage());
        }
        if (p > 0) {
            return ResultInfo.success("修改成功");
        }
        return ResultInfo.fail("0", "修改失败");
    }

    /**
     * 查询购物车列表
     *
     * @param paramEntity 其中 isCount 0：从商品详情页查购物车的数量，1：查购物车列表以及数量，2：查提交订单页面时的商品
     * @return ResultInfo
     */
    @ApiOperation(value = "查询购物车列表", notes = "查询购物车列表")
    @PostMapping("/queryGoodsCarList")
    public ResultInfo queryGoodsCarList(@RequestBody VGoodsCarExtMx paramEntity) {
        Map<String, Object> resultMap = null;
        try {
            paramEntity.setAccountId(getAccountId());
            if (checkPriceAuth()) {
                paramEntity.setIsMarketPrice("1");
            }
            logger.info("goodsCarService.queryGoodsCarList | paramEntity[accountId] is {},paramEntity[isMarketPrice] is {},paramEntity[isCount] is {}",
                    paramEntity.getAccountId(), paramEntity.getIsMarketPrice(), paramEntity.getIsCount());
            resultMap = goodsCarService.queryGoodsCarList(paramEntity);
            if (resultMap.isEmpty()) {
                //modby glenn
                return ResultInfo.fail("success", "购物车暂无数据");
            }
        } catch (Exception e) {
            //modby glenn
            logger.error("查询列表异常", e);
            return ResultInfo.fail("500", e.getMessage());
        }
        return ResultInfo.success(resultMap);
    }

    /***
     * 删除购物车
     * @param
     *
     */
    @ApiOperation(value = "删除购物车", notes = "删除购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "shuNos", value = "sku", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "carId", value = "购物车", required = false)
    })
    @PostMapping("/deleteGoodsCar")
    public ResultInfo deleteGoodsCar(@RequestBody VGoodsCarExtMx paramEntity) {
        int p = 0;
        try {
            paramEntity.setAccountId(getAccountId());
            p = goodsCarService.deleteGoodsCar(paramEntity);
        } catch (Exception e) {
            //modby glenn
            logger.error("删除购物车异常", e);
            return ResultInfo.fail("0", e.getMessage());
        }
        if (p > 0) {
            return ResultInfo.success("删除成功");
        }
        return ResultInfo.fail("0", "删除失败");
    }

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
            return ResultInfo.fail(VedengErrorCode.UNKNOWN_EXCEPTION);
        }

        return ResultInfo.success(VedengErrorCode.SUCCESS_CODE, resultMap);
    }

}
