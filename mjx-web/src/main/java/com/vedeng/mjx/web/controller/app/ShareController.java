package com.vedeng.mjx.web.controller.app;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.domain.ShareInfo;
import com.vedeng.mjx.web.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


/**
 * 分享接口
 */
@RequestMapping("/app")
@RestController
public class ShareController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${app.goods.detail.share_title}")
    private String goodsDetailShareTitle;
    @Value("${app.goods.detail.share_description}")
    private String getGoodsDetailShareDescription;
    @Value("${app.goods.detail.share_webpageUrl}")
    private String getGoodsDetailShareWebpageUrl;
//    @Value("${app.goods.detail.share_thumbDataUrl}")
//    private String getGoodsDetailShareThumbDataUrl;
    @Value("${app.aboutUs.share_title}")
    private String aboutUsShareTitle;
    @Value("${app.aboutUs.share_description}")
    private String aboutUsShareDescription;
    @Value("${app.aboutUs.share_webpageUrl}")
    private String aboutUsShareWebpageUrl;
    @Value("${app.aboutUs.share_thumbDataUrl}")
    private String aboutUsShareThumbDataUrl;
    @Value("${mjx.static_timestamp}")
    private String staticTimestamp;
    /**
     * 分享类型(for APP)
     * 1 : 详情页
     * 2 : 关于我们
     */
    @PostMapping("/share/getShareData")
    @ResponseBody
    public ResultInfo getShareInfo(@RequestBody ShareInfo shareInfo) {
        //校验参数
        if(shareInfo==null || shareInfo.getShareType()==null){
            return ResultInfo.fail(VedengErrorCode.SHAREINFO_NO_SHARETYPE);
        }
        logger.info("ShareInfo is {}", shareInfo);
        if(shareInfo.getShareType()==1){
            //详情
            ShareInfo detail =new ShareInfo();
            detail.setShareType(shareInfo.getShareType());
            detail.setDescription(getGoodsDetailShareDescription);
            detail.setTitle(goodsDetailShareTitle);
            detail.setWebpageUrl(getGoodsDetailShareWebpageUrl);
            return ResultInfo.success(detail);
        }
        if(shareInfo.getShareType()==2){
            //关于我们
            ShareInfo aboutUs =new ShareInfo();
            aboutUs.setShareType(shareInfo.getShareType());
            aboutUs.setDescription(aboutUsShareDescription);
            aboutUs.setTitle(aboutUsShareTitle);
            aboutUs.setWebpageUrl(aboutUsShareWebpageUrl);
            aboutUs.setThumbDataUrl(aboutUsShareThumbDataUrl+"?t="+staticTimestamp);
            return ResultInfo.success(aboutUs);
        }
        return ResultInfo.fail(VedengErrorCode.UNKNOWN_EXCEPTION);
    }

}
