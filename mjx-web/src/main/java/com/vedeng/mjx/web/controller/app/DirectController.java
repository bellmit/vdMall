package com.vedeng.mjx.web.controller.app;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.Direct.DirectService;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/app/")
@RestController
@Api(value = "App功能直达controller", tags = {"信息控制层"})
public class DirectController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DirectService directService;


    //目录直达内容
    @GetMapping("/getDirect")
    public ResultInfo queryRegionInfo() {
        try {
//          1  首页	按钮	 2  搜索	按钮	  3  购物车
//          4  个人中心	  5  消息	按钮	  6  咨询	按钮
//              根据shiro的session获取用户信息
            TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();

            List<Object> list = directService.getDirectUrl(jx.getAccountId());
            return ResultInfo.success(list);
        } catch (Exception e) {
            logger.error("目录直达内容出现异常", e);
            return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
        }


    }

}
