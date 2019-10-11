
package com.vedeng.mjx.web.controller.user;

import com.alibaba.fastjson.JSON;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CookieType;
import com.vedeng.mjx.common.util.CookieUtils;
import com.vedeng.mjx.domain.*;
import com.vedeng.mjx.service.account.AccountService;
import com.vedeng.mjx.service.message.MessageService;
import com.vedeng.mjx.service.order.OrderService;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.web.controller.base.BaseController;
import com.vedeng.mjx.web.controller.user.vo.UserData;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ auther: Marin
 * @ date 2019/7/10 14:14
 * 订单Controller层（03分支）
 */
@RequestMapping({"/user","/app/user"})
@Api(value="用户controller",tags={"用户控制层"})
@RestController
public class UserController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    /**
     * 个人中心订单数量
     * @return
     */
    @RequestMapping(value = "/selectUser")
    public ResultInfo selectUser(){

        TopAccountJx userEntity =  ShiroSessionUtil.getSessionTopAccountJx();

        Map<String,Object> result = new HashMap<>();
        result.put("accountJx",userEntity);

        try {
            List<OrderCountData> orderCountDataList = orderService.selectCountByOrderStstus(userEntity.getAccountId());
            result.put("orderCountDataList",orderCountDataList);
            result.put("noReadMessageCount", messageService.selectNoReadCount(getAccountId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("1001","查询订单数量失败");
        }
        return ResultInfo.success(result);
    }

    /**
     * 退出登陆
     * @return
     */
    @RequestMapping(value = "/outLogin")
    public ResultInfo outLogin(HttpServletRequest request,HttpServletResponse responce){
        ShiroSessionUtil.clearSessionTopAccountJx(responce);
        CookieUtils.deleteLoginCookie(request,responce, CookieType.APP_LOGIN_TOKEN.getKey());
        return ResultInfo.success(true);
    }

    /**
     * erp绑定客服更新mjx用户公司名称
     * @param userData
     * @return
     */
    @PostMapping(value = "/updateCompanyName")
    public ResultInfo updateCompanyName(@RequestBody UserData userData){

        log.info("【updateCompanyName】:"+ JSON.toJSONString(userData));
        try {
            TopAccountJx account = accountService.selectByAcountId(userData.getAccountId());
            if(account == null){
                return ResultInfo.fail("1001","用户不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("1001","查询用户失败");
        }

        try {
            TopAccountJx topAccountJx = new TopAccountJx();
            topAccountJx.setAccountId(userData.getAccountId());
            topAccountJx.setCompanyName(userData.getCompanyName());
            boolean result = accountService.updateTopAccountJxByAccountId(topAccountJx);
            if(!result){
                return ResultInfo.fail("1001","修改公司名称失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail("1001","修改公司名称失败");
        }
        return ResultInfo.success(true);
    }

}

