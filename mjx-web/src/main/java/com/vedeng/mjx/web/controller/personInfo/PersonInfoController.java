package com.vedeng.mjx.web.controller.personInfo;

import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.web.controller.base.CommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Gavin.li
 * @date ：Created in 2019/7/12 14:08
 * @description：
 * @modified By：
 * @version:
 */
@RestController
@RequestMapping("/personInfo")
public class PersonInfoController extends CommonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     *  个人中心
     * @return
     */
    @GetMapping("/index.html")
    public String index(){
        //用户登陆信息
        TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
        //订单数量信息
        System.out.println(jx);

        return null;
    }


}
