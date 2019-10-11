package com.vedeng.mjx.web.controller.app.goodscar;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.exception.VedengException;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.ext.VGoodsCarExtMx;
import com.vedeng.mjx.service.goodsCar.GoodsCarService;
import com.vedeng.mjx.web.controller.base.BaseController;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
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
 * describe: APP购物车
 */

@RequestMapping("/app/goodsCar")
@RestController
public class AppGoodsCarController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private GoodsCarService goodsCarService;

    /**
     * 加入购物车
     *
     * @param
     */
    @PostMapping("/addGoodsIntoCar")
    public ResultInfo addGoodsIntoCar(@RequestBody VGoodsCarExtMx paramEntity) {
        //校验
        if (StringUtil.isBlank(paramEntity.getSkuNo()) || paramEntity.getCount() == null ||paramEntity.getCount() <= ZEOR || paramEntity.getCount() > GOODSCAR_NUM_MAX_COUNT) {
            return ResultInfo.fail(PARAM_MISS);
        }
        try {
            paramEntity.setAccountId(getAccountId());
            goodsCarService.addGoodsIntoCar(paramEntity);
            return ResultInfo.success(SUCCESS_CODE);
        } catch (VedengException e) {
            logger.error("addGoodsIntoCar VedengException code is {} msg is {}",e.getCode(),e.getMessage());
            return ResultInfo.fail(e);
        } catch (Exception e) {
            logger.error("addGoodsIntoCar error", e);
            return ResultInfo.fail(UNKNOWN_EXCEPTION);
        }
    }


    /***
     * 购物车预提交
     *
     */
    @PostMapping("/addPreCommit")
    public ResultInfo insertPrepareGoodsCar(@RequestBody VGoodsCarExtMx paramEntity) {
        try {
            if(CollectionUtils.isEmpty(paramEntity.getCarMxIdList())){
                return ResultInfo.fail(PARAM_MISS);
            }
            goodsCarService.prepareCommitGoodsCar(paramEntity);
            return ResultInfo.success(SUCCESS_CODE);
        } catch (VedengException e) {
            logger.error("addGoodsIntoCar VedengException code is {} msg is {}", e.getCode(), e.getMessage());
            return ResultInfo.fail(e);
        } catch (Exception e) {
            logger.error("prepareCommitGoodsCar error", e);
            return ResultInfo.fail(UNKNOWN_EXCEPTION);
        }
    }



    /**
     * 修改复选框
     * @param paramEntity：status : 是否选中（0否1是) carMxId : 明细Id
     */
    @PostMapping("/updateGoodsDefault")
    public ResultInfo updateGoodsDefault(@RequestBody VGoodsCarExtMx paramEntity) {
        //校验
        if (paramEntity.getStatus() == null) {
            return ResultInfo.fail(PARAM_MISS);
        }
        if((CommonConstant.ZEOR.equals(paramEntity.getStatus())|| CommonConstant.ONE.equals(paramEntity.getStatus()))&& paramEntity.getCarMxId()==null){
            return ResultInfo.fail(PARAM_MISS);
        }
        logger.info("paramEntity status is {} carMxId is {}", paramEntity.getStatus(), paramEntity.getCarMxId());
        paramEntity.setAccountId(getAccountId());
        goodsCarService.updateGoodsIsChoosed(paramEntity);
        return ResultInfo.success(SUCCESS_CODE);
    }

    /**
     * 修改购买数量
     *
     * @param
     */
    @ApiOperation(value = "修改购买数量", notes = "修改购买数量")
    @PostMapping("/updateGoodsCount")
    public ResultInfo updateGoodsCount(@RequestBody VGoodsCarExtMx paramEntity) {
        //校验
        if (paramEntity.getCarMxId() == null || paramEntity.getCount() == null) {
            return ResultInfo.fail(PARAM_MISS);
        }
        goodsCarService.updateGoodsMxCount(paramEntity);
        return ResultInfo.success(SUCCESS_CODE);
    }


    /***
     * describe: 购物车列表
     * creat_time: 2019/9/6 9:04
     * param: [reqCarMx]
     * return: com.vedeng.mjx.common.ResultInfo
     */
    @PostMapping("/list")
    public ResultInfo queryGoodsCarList(@RequestBody VGoodsCarExtMx reqCarMx) {
        Map<String, Object> resultMap = null;
        try {
            //校验isCount参数
            if(StringUtil.isBlank(reqCarMx.getIsCommit())){
                return ResultInfo.fail(PARAM_MISS);
            }
            logger.info("queryGoodsCarList isCommit is {}",reqCarMx.getIsCommit());
            reqCarMx.setAccountId(getAccountId());
            reqCarMx.setIsMarketPrice(checkPriceAuth()?CommonConstant.STRING_ONE:CommonConstant.STRING_ZERO);
            logger.info("reqCarMx[accountId] is {},reqCarMx[isMarketPrice] is {}",
                    reqCarMx.getAccountId(), reqCarMx.getIsMarketPrice());
            resultMap = goodsCarService.appGoodsCarList(reqCarMx);
        } catch (Exception e) {
            logger.error("queryGoodsCarList error", e);
            return ResultInfo.fail(UNKNOWN_EXCEPTION);
        }
        return ResultInfo.success(resultMap);
    }

    /***
     * 删除购物车明细
     * @param
     *
     */
    @PostMapping("/deleteGoodsCarMx")
    public ResultInfo deleteGoodsCar(@RequestBody VGoodsCarExtMx paramEntity) {
        // 参数校验
        if (paramEntity.getCarMxIdList()==null || CollectionUtils.isEmpty(paramEntity.getCarMxIdList()) ) {
            return ResultInfo.fail(PARAM_MISS);
        }

        logger.info("paramEntity carMxIdList is {}", paramEntity.getCarMxIdList());
        goodsCarService.deleteGoodsCarMxList(paramEntity);
        return ResultInfo.success(SUCCESS_CODE);
    }

    /***
     * describe: 查询购物车内商品数量
     * creat_time: 2019/9/6 9:05
     * param: []
     * return: com.vedeng.mjx.common.ResultInfo
     */
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
