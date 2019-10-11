package com.vedeng.mjx.web.controller.vedengJoin;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Maps;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.feign.passport.dto.ReqUserDto;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.service.vdengJoin.VedengJoinService;
import com.vedeng.mjx.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName VedengJoinController
 * @Description 申请加入贝登精选
 * @Author Cooper.xu
 * @Date 2019-07-01 16:44
 * @Version 1.0
 **/
@CrossOrigin
@RestController
@RequestMapping(value={"/apply","/app/apply","/m/apply"})
@Api(value = "申请加入贝登精选接口", tags = {"申请加入贝登精选接口控制层"})
public class VedengJoinController extends BaseController {

    @Autowired
    private VedengJoinService vedengJoinService;

    /**
     * @Description 申请加入贝登精选方法
     * @Author cooper.xu
     * @Date 2019-07-01 13:15
     * @return
     */
    @ApiOperation(value = "申请加入贝登精选方法", notes = "mobile 必填",response = ResultInfo.class)
    @PostMapping("/vedengJoin")
    public ResultInfo vedengJoin(@RequestBody ReqUserDto reqUserDto){
        if(ShiroSessionUtil.getSessionTopAccountJx()==null){
            return ResultInfo.fail(VedengErrorCode.USER_NOT_LOGIN_YET);
        }
        try{
            Map<String ,String> message= Maps.newHashMap();
            ResultInfo result=null;
            logger.info("申请加入贝登精选开始----------------------------");
            //获取请求的用户的信息
            TopAccountJx topAccountJx = ShiroSessionUtil.getSessionTopAccountJx();
            if (StringUtil.isNotBlank(topAccountJx.getMobile())){
                logger.info("申请加入贝登精选结束----------------------------");
                //进行申请操作
                  result= vedengJoinService.applyJoinVedeng(topAccountJx.getMobile());
                  message.put("message",result.getMessage());
                  return result.data(message);
            }else {
                logger.info("申请加入贝登精选结束----------------------------");
                return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
            }

        }catch (Exception e){
            logger.error("申请加入贝登精选异常",e);
            return ResultInfo.fail(e);
        }
    }

}
