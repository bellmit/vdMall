package com.vedeng.mjx.web.controller.address;

import com.alibaba.fastjson.JSON;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.exception.VedengException;
import com.vedeng.mjx.domain.ext.VAccountAddressExt;
import com.vedeng.mjx.service.address.AddressService;
import com.vedeng.mjx.web.controller.base.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ auther: Jax
 * @ date 2019/6/21 14:43
 * 商品详情Controller层
 */
@RequestMapping("/mjx/address")
@RestController
public class AddressController extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AddressService addressService;



    /***
     * 查询省市区地址
     * @param flagId : 1:省份
     */
    @ApiOperation(value = "查询省市区地址", notes = "查询省市区地址")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "flagId", value = "参数标识，1:省份", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "regionId", value = "省市区的主键", required = false)
    })
    @GetMapping("/regions")
    public ResultInfo queryRegionInfo(String flagId,Integer regionId){
        VAccountAddressExt paramEntity  = new VAccountAddressExt();
        VAccountAddressExt entity = null;
        try{
            paramEntity.setFlagId(flagId);
            paramEntity.setRegionId(regionId);
            entity = addressService.queryRegionInfo(paramEntity);
            if (entity != null && entity.getRegionList().size()==0){
                return ResultInfo.fail("0","暂无数据");
            }
        }
        catch (Exception e){
            logger.error("查询省市区异常",e);
            return ResultInfo.fail("0",e.getMessage());
        }
        return ResultInfo.success(entity);
    }

    /**
     * 新增地址信息
     * @ param
     */
    @ApiOperation(value = "新增地址信息", notes = "新增地址信息")
    @PostMapping("/insertAddress")
    public ResultInfo insertAddressInfo( @RequestBody VAccountAddressExt paramEntity){
        VAccountAddressExt entity = null;
        try{
            paramEntity.setAccountId(getAccountId());//登陆用户
            entity = addressService.insertAddressInfo(paramEntity);
            if (entity ==null ){
                return ResultInfo.fail("0","保存失败");
            }
        }
        catch (Exception e){
            //modby glenn
            logger.error("查询省市区异常",e);
            return ResultInfo.fail("0",e.getMessage());
        }
        return ResultInfo.success(entity);
    }
    /**
     * 查询地址列表
     * @ param
     */
    @ApiOperation(value = "查询地址列表", notes = "查询地址列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "receiveName", value = "收件人", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "accountId", value = "登陆人ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "pageNumber", value = "当前页数", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "pageSize", value = "当前行数", required = false)
    })
    @GetMapping("/queryAddressList")
    public ResultInfo queryAddressList(Integer pageNumber,Integer pageSize) {
        VAccountAddressExt paramEntity = new VAccountAddressExt();//接受参数的
        List<VAccountAddressExt> list = null;
        try {
            paramEntity.setPagerNumber(pageNumber);
            paramEntity.setPageSize(pageSize);
            paramEntity.setAccountId(getAccountId());
            list = addressService.queryAddressList(paramEntity);
            if (list == null || list.size() == 0) {
                return ResultInfo.fail("0", "暂无数据");
            }
        } catch (Exception e) {
            logger.error("查询异常", e);
            return ResultInfo.fail("0", e.getMessage());
        }
        return ResultInfo.success(list);
    }

    /**
     *查询地址详情
     */
    @ApiOperation(value = "查询地址详情", notes = "查询地址详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "addressId", value = "地址主键", required = false)
    })
    @GetMapping("/queryAddressInfo")
    public ResultInfo queryAddressInfo(
            Integer addressId
    ){
        VAccountAddressExt paramEntity  = new VAccountAddressExt();//接受参数的
        VAccountAddressExt entity  = null;
        try{

            paramEntity.setAddressId(addressId);
            entity = addressService.queryAddressInfo(paramEntity);

            if(!entity.getAccountId().equals(getAccountId())){
                return ResultInfo.fail(VedengErrorCode.AUTH_FAILED);
            }
        }
        catch (Exception e){
            logger.error("查询异常",e);
            return ResultInfo.fail("0",e.getMessage());
        }
        return ResultInfo.success(entity);
    }

    /**
     * 删除地址
     */
    @ApiOperation(value = "删除地址", notes = "删除地址")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "addressId", value = "地址主键", required = true)
    })
    @GetMapping("/deleteAddressInfo")
    public ResultInfo deleteAddressInfo(Integer addressId) {
        VAccountAddressExt paramEntity = new VAccountAddressExt();//接受参数的
        int t = 0;
        try {
            paramEntity.setAddressId(addressId);
            t = addressService.deleteAddressInfo(paramEntity);
            if (t == 0) {
                return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
            }
        } catch (Exception e) {
            logger.error("查询异常", e);
            if(e instanceof VedengException){
                VedengException exception = (VedengException) e;
            return ResultInfo.fail(exception.getCode(), exception.getMessage());
            }
            return ResultInfo.fail(VedengErrorCode.UNKNOWN_EXCEPTION);
        }
        return ResultInfo.success(VedengErrorCode.SUCCESS_CODE);
    }

    /**
     * 查询默认地址
     */
    @ApiOperation(value = "查询默认地址", notes = "查询默认地址")
    @GetMapping("/queryDefaultAddress")
    public ResultInfo queryDefaultAddress(Integer addressId) {
        VAccountAddressExt paramEntity = new VAccountAddressExt();
        Map<String, Object> resultMap = null;
        int t = 0;
        try {
            paramEntity.setAddressId(addressId);
            paramEntity.setAccountId(getAccountId());
            resultMap = addressService.queryDefaultAddress(paramEntity);

        } catch (Exception e) {
            logger.error("查询异常", e);
            return ResultInfo.fail("0", e.getMessage());
        }
        System.out.println("resultMap:"+ JSON.toJSONString(resultMap));
        if(resultMap == null){
            return ResultInfo.fail("noAddress","当前用户没有设置收货地址");
        }
        return ResultInfo.success(resultMap);
    }

}
