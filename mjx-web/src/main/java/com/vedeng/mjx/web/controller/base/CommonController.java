package com.vedeng.mjx.web.controller.base;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.passport.api.wechat.dto.req.ReqShareWeChatDTO;
import com.vedeng.passport.api.wechat.dto.res.ResShareWeChatDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @ auther: Jax
 * @ date 2019/6/21 14:43
 * 商品详情Controller层
 */
@RequestMapping("/common/wechat/")
@RestController
public class CommonController extends BaseController {


    @Value("${mjx.app_id}")
    protected String wxAppId;
    @Value("${mjx.app_secret}")
    protected String wxAppSecret;
    @Value("${context_url}")
    protected String mjxUrl;
    @Value("${bd_categoryIds}")
    protected String bdCategoryIds;
    @Value("${bd_level2categoryIds}")
    protected String bdLevel2categoryIds;
    @Value("${bd_categoryIds_index}")
    protected String bdCategoryIdsIndex;
    @Value("${m_categoryIds}")
    protected String mCategoryIds;
    @Value("${m_level3categoryIds}")
    protected String mLevel3categoryIds;
    @Value("${m_brands}")
    protected String mBrands;
    @Value("${app.url}")
    protected String appUrl;
    @Value("${jg.appkey}")
    protected String jgAppkey;
    @Value("${jg.appMasterSecret}")
    protected String jgAppMasterSecret;
    @Value("${mjx_index}")
    protected String mjxIndex;
    @Value("${mjx_index_spare}")
    protected String mjxIndexSpare;

    @ResponseBody
    @PostMapping("/share")
    public ResultInfo share(@RequestBody UrlDto urlDto) {
        try {
            String url=urlDto.getUrl();
            logger.info("getWXData | url : {}", url);
            if (StringUtil.isJsBlank(url)) {
                return ResultInfo.fail(VedengErrorCode.PARAM_NULL);
            }
            ReqShareWeChatDTO reqShareWeChatDTO = new ReqShareWeChatDTO();
            reqShareWeChatDTO.setUrl(url);
            reqShareWeChatDTO.setAppId(wxAppId);
            reqShareWeChatDTO.setAppSecret(wxAppSecret);
            ResShareWeChatDTO wxShareDto = weChatApiServiceApi.share(reqShareWeChatDTO);
            return ResultInfo.success(wxShareDto);
        } catch (Exception e) {
            return ResultInfo.fail(e);
        }

    }

}


