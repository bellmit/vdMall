package com.vedeng.mjx.web.controller.app.address;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.exception.VedengException;
import com.vedeng.mjx.domain.TRegion;
import com.vedeng.mjx.domain.ext.VAccountAddressExt;
import com.vedeng.mjx.service.address.AddressService;
import com.vedeng.mjx.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * class_name: AddressController
 * package: com.vedeng.mjx.web.app.address
 * creat_user:JunLee
 * creat_date: 2019/8/5
 * creat_time: 13:13
 * describe: app地址管理
 */
@Api(value="APP接口-地址管理",tags = "APP接口-地址管理")
@RequestMapping("/app/address")
@RestController
@Component("appAddressController")
public class AppAddressController extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AddressService addressService;


    @ApiOperation(value = "查询省市区地址", notes = "查询省市区地址")
    @PostMapping("/regions")
    public ResultInfo queryRegionInfo(@RequestBody TRegion region, HttpServletResponse response) {
        //参数校验
        if (region.getRegionId() == null) {
            ResultInfo.fail(VedengErrorCode.EMPTY_OR_NULL_REGION_ID);
        }
        List<TRegion> regions = addressService.queryRegionInfomation(region);
        if (CollectionUtils.isEmpty(regions)) {
            return ResultInfo.success(VedengErrorCode.NOREGIONS_UNDER_REGION_ID);
        }
        return ResultInfo.success(regions);
    }

    /**
     * 新增地址信息
     * @ param
     */
    @ApiOperation(value = "新增地址信息", notes = "新增地址信息")
    @PostMapping("/insertAddress")
    public ResultInfo insertAddressInfo(@RequestBody VAccountAddressExt paramEntity) {
        if (addressService.queryCount(getAccountId()).equals(CommonConstant.MAX_ADDRESS_COUNT)) {
            return ResultInfo.fail(VedengErrorCode.ADDRESS_COUNT_OVER_TWENTY);
        }
        VAccountAddressExt entity = null;
        try {
            //登陆用户
            paramEntity.setAccountId(getAccountId());
            entity = addressService.insertAddressInfo(paramEntity);
            if (entity == null) {
                return ResultInfo.fail(VedengErrorCode.ADD_ADDRESS_ERROR);
            }
            String[] oriRegionName=entity.getRegionName().split(",");
            String[] afterRegionName = dealZxs(oriRegionName);
            StringBuffer sb=new StringBuffer();
            for (String str : afterRegionName) {
                sb.append(" ").append(str);
            }
            entity.setRegionName(sb.toString());
        } catch (VedengException e) {
            logger.error("catch VedengException error", e);
            return ResultInfo.fail(e);
        }catch (Exception e){
            logger.error("catch Exception error", e);
            return ResultInfo.fail(VedengErrorCode.UNKNOWN_EXCEPTION);
        }
        return ResultInfo.success(entity);
    }
    /**
     * 查询地址列表
     * @ param
     */
    @ApiOperation(value = "查询地址列表", notes = "查询地址列表")
    @PostMapping("/queryAddressList")
    public ResultInfo queryAddressList() {
        VAccountAddressExt paramEntity = new VAccountAddressExt();
        List<VAccountAddressExt> list = null;
        try {
            paramEntity.setAccountId(getAccountId());
            list = addressService.appQueryAddressList(paramEntity);
            if (list.size() == 0) {
                //成功 空集合
                return ResultInfo.success(VedengErrorCode.CUR_USER_NO_ADDRESSES,list);
            }
        } catch (Exception e) {
            logger.error("queryAddressList error", e);
            return ResultInfo.fail(VedengErrorCode.UNKNOWN_EXCEPTION);
        }
        return ResultInfo.success(list);
    }

    /**
     *查询地址详情
     */
    @ApiOperation(value = "查询地址详情", notes = "查询地址详情")
    @PostMapping("/queryAddressInfo")
    public ResultInfo queryAddressInfo(@RequestBody VAccountAddressExt add) {
        //回参
        VAccountAddressExt paramEntity = new VAccountAddressExt();
        VAccountAddressExt entity = null;
        try {
            paramEntity.setAddressId(add.getAddressId());
            entity = addressService.queryAddressInfo(paramEntity);
            if (entity == null) {
                return ResultInfo.fail(VedengErrorCode.CUR_USER_NO_ADDRESSES);
            }
            if(!getAccountId().equals(entity.getAccountId())){
                return ResultInfo.fail(VedengErrorCode.ADDRESS_NOT_BELONG_CUR_USER);
            }
        } catch (Exception e) {
            logger.error("queryAddressInfo error", e);
            return ResultInfo.fail(VedengErrorCode.UNKNOWN_EXCEPTION);
        }
        return ResultInfo.success(entity);
    }

    /**
     * 删除地址
     */
    @ApiOperation(value = "删除地址", notes = "删除地址")
    @PostMapping("/deleteAddressInfo")
    public ResultInfo deleteAddressInfo(@RequestBody VAccountAddressExt add) {
        VAccountAddressExt paramEntity = new VAccountAddressExt();
        int t = 0;
        try {
            paramEntity.setAddressId(add.getAddressId());
            t = addressService.deleteAddressInfo(paramEntity);
            if (t == 0) {
                return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
            }
        } catch (VedengException e) {
            return ResultInfo.fail(e);
        } catch (Exception e) {
            logger.error("deleteAddressInfo error", e);
            return ResultInfo.fail(VedengErrorCode.UNKNOWN_EXCEPTION);
        }
        return ResultInfo.success(VedengErrorCode.SUCCESS_CODE);
    }

    /**
     * 查询默认地址
     */
    @ApiOperation(value = "查询默认地址", notes = "查询默认地址")
    @PostMapping("/queryDefaultAddress")
    public ResultInfo queryDefaultAddress() {
        VAccountAddressExt paramEntity = new VAccountAddressExt();
        Map<String, Object> resultMap = null;
        int t = 0;
        try {
            paramEntity.setAccountId(getAccountId());
            resultMap = addressService.queryDefaultAddressForApp(paramEntity);
            if (resultMap == null) {
                return ResultInfo.fail(VedengErrorCode.CUR_USER_NO_ADDRESSES,new HashMap());
            }
        } catch (Exception e) {
            logger.error("queryDefaultAddress error", e);
            return ResultInfo.fail(VedengErrorCode.UNKNOWN_EXCEPTION);
        }
        return ResultInfo.success(resultMap);
    }


    /***
     * describe: 处理直辖市
     * creat_time: 2019/8/10 17:25
     * param: [ssq]
     * return: java.lang.String[]
     */
    private String[] dealZxs(String[] ssq) {
        boolean a = CommonConstant.ZHIXIASHI_BEIJING.equals(ssq[0]);
        boolean b = CommonConstant.ZHIXIASHI_SHANGHAI.equals(ssq[0]);
        boolean c = CommonConstant.ZHIXIASHI_TIANJING.equals(ssq[0]);
        boolean d = CommonConstant.ZHIXIASHI_CHONGQING.equals(ssq[0]);
        if (a || b || c || d) {
            //有直辖市
            String[] newSsq = new String[ssq.length - 1];
            for (int i = 0; i < ssq.length; i++) {
                if (i != 0) {
                    newSsq[i - 1] = ssq[i];
                }
            }
            return newSsq;
        } else {
            return ssq;
        }
    }

}
