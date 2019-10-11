package com.vedeng.mjx.web.controller.message;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.domain.TOpAccount;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.domain.VWxPhone;
import com.vedeng.mjx.service.account.AccountService;
import com.vedeng.mjx.web.controller.base.BaseController;
import com.vedeng.mjx.web.controller.base.CommonController;
import com.vedeng.mjx.web.controller.message.vo.HTTPClientUtils;
import com.vedeng.mjx.web.controller.message.vo.JsonUtil;
import com.vedeng.mjx.web.controller.message.vo.TokenUtils;
import com.vedeng.mjx.web.controller.message.vo.WxMessageVo;
import com.vedeng.passport.api.wechat.dto.req.ReqOuthWeChatDTO;
import com.vedeng.passport.api.wechat.dto.req.ReqTemplateVariable;
import com.vedeng.passport.api.wechat.dto.res.ResOuthWeChatDTO;
import com.vedeng.passport.api.wechat.dto.res.ResWeChatDTO;
import com.vedeng.passport.api.wechat.service.WeChatApiService;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.shiro.authc.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/wx")
@RestController
@Api(value = "M站微信信息controller", tags = {"信息控制层"})
public class WxMessageController extends CommonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;
    @Autowired
    private WeChatApiService weChatApiService;

    @PostMapping(value = "/getWxConfiguration")
    public ResultInfo getWxConfiguration(){
        Map<String,Object> result = new HashMap<>();
        result.put("appId",wxAppId);
        result.put("appSecret",wxAppSecret);
        return ResultInfo.success(result);
    }

    @PostMapping(value = "/isWxPhone")
    public ResultInfo isWxPhone(@RequestBody WxMessageVo wxMessageVo){

        if(StringUtils.isBlank(wxMessageVo.getCode())){
            return ResultInfo.fail("1001","code不能为空");
        }

        String getAccessUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxAppId + "&secret=" + wxAppSecret
                + "&code=" + wxMessageVo.getCode() + "&grant_type=authorization_code";

        // 发送
        RestTemplate restTemplate = new RestTemplate();
        String accessTokenJson = restTemplate.getForObject(getAccessUrl, String.class);
        Map<String, Object> mapObject = JsonUtil.parseJSON2Map(accessTokenJson);
        log.info("mapObject:",JSON.toJSONString(mapObject));
        // 授权用户唯一标识
        String openid = (String) mapObject.get("openid");
        log.info("openid:",openid);
        if(StringUtils.isBlank(openid)){
            return ResultInfo.fail("40163","无效code");
        }

        Map<String,Object> maps = new HashMap<>();
        maps.put("openid",openid);
        return ResultInfo.success(maps);

    }

    @PostMapping(value = "/isBind")
    public ResultInfo isBind(@RequestBody WxMessageVo wxMessageVo){

        if(StringUtils.isBlank(wxMessageVo.getOpenid())){
            return ResultInfo.fail("1001","openid不能为空");
        }

        VWxPhone wxPhone = accountService.selectWxphoneByOpenid(wxMessageVo.getOpenid());
        if(wxPhone != null && wxPhone.getPhone() != null){
            Map<String,Object> maps = new HashMap<>();
            maps.put("phone",wxPhone.getPhone());
            maps.put("openid",wxMessageVo.getOpenid());
            return ResultInfo.success("Bind",maps);
        }
        Map<String,Object> maps = new HashMap<>();
        maps.put("phone","");
        maps.put("openid",wxMessageVo.getOpenid());
        return ResultInfo.success("noBind",maps);

    }

    @PostMapping(value = "/bindWxPhone")
    public ResultInfo bindWxPhone(@RequestBody WxMessageVo wxMessageVo){

        if(StringUtils.isBlank(wxMessageVo.getOpenid())){
            return ResultInfo.fail("1001","openid不能为空");
        }
        if(StringUtils.isBlank(wxMessageVo.getPhone())){
            return ResultInfo.fail("1001","手机号不能为空");
        }
        if(StringUtils.isBlank(wxMessageVo.getSmsCode())){
            return ResultInfo.fail("1001","短信验证码不能为空");
        }

        // 授权用户唯一标识
        String openid = wxMessageVo.getOpenid();
        if(StringUtils.isBlank(openid)) {
            return ResultInfo.fail("1001","微信授权失败");
        }

        int count = 0;
        VWxPhone wxPhone = accountService.selectWxphoneByOpenid(wxMessageVo.getOpenid());
        if(wxPhone != null){
            count = accountService.updatexPhone(wxMessageVo.getPhone(),wxMessageVo.getOpenid());
        }else {
            count = accountService.saveWxPhone(wxMessageVo.getPhone(), wxMessageVo.getOpenid());
        }
        if (count != 0) {
            return ResultInfo.success("绑定成功");
        }
        return ResultInfo.fail("1001","绑定失败");

    }

    @RequestMapping(value = "/wxchat/getCode")
    public Object auth(@RequestParam(value = "code") String code) {
        if (StringUtils.isBlank(code)) {
            return "授权失败";
        }
        return code;
    }

    @RequestMapping(value = "/wxchat/send", method = { RequestMethod.GET, RequestMethod.POST })
    public ResWeChatDTO send(@RequestBody ReqTemplateVariable reqTemplateVariable) {
        log.info("reqTemplateVariable入参:"+JSON.toJSONString(reqTemplateVariable));

        try {

            VWxPhone wxPhone = accountService.selectWxphoneByPhone(reqTemplateVariable.getMobile());
            if(wxPhone == null || wxPhone.getOpenid() == null){
                ResWeChatDTO resWeChatDTO = new ResWeChatDTO();
                resWeChatDTO.setCode("noBind");
                resWeChatDTO.setMessage("用户未绑定微信");
                return resWeChatDTO;
            }
            reqTemplateVariable.setAppId(wxAppId);
            reqTemplateVariable.setAppSecret(wxAppSecret);
            reqTemplateVariable.setOpenId(wxPhone.getOpenid());
            log.info("reqTemplateVariable处理后:"+JSON.toJSONString(reqTemplateVariable));
            ResWeChatDTO resWeChatDTO = weChatApiService.sendWxTemplateMsg(reqTemplateVariable);
            log.info("resWeChatDTO:"+JSON.toJSONString(resWeChatDTO));
            return resWeChatDTO;
        } catch (Exception e) {
            log.error("微信推送失败",e);
            ResWeChatDTO resWeChatDTO = new ResWeChatDTO();
            resWeChatDTO.setCode("error");
            resWeChatDTO.setMessage("微信推送异常");
            return resWeChatDTO;
        }
    }

    @RequestMapping(value = "/initialize", method = { RequestMethod.GET, RequestMethod.POST })
    public ResultInfo initialize() {
        try {
            accountService.initialize();
        } catch (Exception e) {
            log.error("初始化异常:",e);
        }
        return ResultInfo.success("初始化成功");
    }

}
