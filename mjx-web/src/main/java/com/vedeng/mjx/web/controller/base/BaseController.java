package com.vedeng.mjx.web.controller.base;

import com.vedeng.goods.api.dto.DescriptDTO;
import com.vedeng.goods.api.dto.PicDTO;
import com.vedeng.goods.api.dto.SkuDTO;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.constant.RedisKeyConstant;
import com.vedeng.mjx.common.goods.*;
import com.vedeng.mjx.common.util.IpUtils;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.service.feign.goods.GoodsServiceFeignApi;
import com.vedeng.mjx.service.feign.passport.UserInfoServiceFeignClient;
import com.vedeng.mjx.service.feign.passport.WeChatApiServiceApi;
import com.vedeng.mjx.service.redis.RedisOperateService;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import eu.bitwalker.useragentutils.BrowserType;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.vedeng.mjx.common.constant.CommonConstant.USERAGENT_VEDENG_APP_ANDROID;
import static com.vedeng.mjx.common.constant.CommonConstant.USERAGENT_VEDENG_APP_IOS;
import static com.vedeng.passport.api.login.constant.FormType.*;
import static eu.bitwalker.useragentutils.BrowserType.*;

/**
 * @Description: 基础controller
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/25
 */
public class BaseController {

    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 注册登录api接口
     */
    @Resource
    protected UserInfoServiceFeignClient passportApiService;

    /**
     * 商品详情信息调用api接口
     */
    @Resource
    protected GoodsServiceFeignApi goodsServiceFeignApi;


    @Resource
    protected WeChatApiServiceApi weChatApiServiceApi;

    public String getContextUrl() {
        return contextUrl;
    }

    public void setContextUrl(String contextUrl) {
        this.contextUrl = contextUrl;
    }

    @Value("${context_url}")
    protected String contextUrl;

    @Value("${erp.server}")
    protected String erpUrl;
    @Resource
    RedisOperateService redisOperateService;
    String hasPriceAuth="1";
    long priceCacheMinsecond=1000*60;

    protected boolean checkPriceAuth() {

        if (ShiroSessionUtil.getSessionTopAccountJx() == null) {
            return false;
        }
        try {
            //价格添加5分钟缓存
            String hasAuthReids = redisOperateService.getKeyValue(RedisKeyConstant.MOBILE_CHECK_AUTH + ShiroSessionUtil.getSessionTopAccountJx().getMobile());
            if (StringUtils.isBlank(hasAuthReids)) {
                Boolean hasAuth = goodsServiceFeignApi.checkPriceAuth(ShiroSessionUtil.getSessionTopAccountJx().getMobile());
                logger.info("mobile is {} hasAuth is {}", ShiroSessionUtil.getSessionTopAccountJx().getMobile(), hasAuth);
                redisOperateService.setKeyValueWithExpire(RedisKeyConstant.MOBILE_CHECK_AUTH + ShiroSessionUtil.getSessionTopAccountJx().getMobile(), hasAuth ? "1" : "0", priceCacheMinsecond);
                return hasAuth;
            } else {
                return hasAuthReids.equals("1") ? true : false;
            }
        } catch (Exception e) {
            logger.warn("获取价格权限有误", e);
        }
        return false;
    }

    public Integer getAccountId(){
        TopAccountJx accountJx = ShiroSessionUtil.getSessionTopAccountJx();
        if(accountJx==null ){
            return null;
        }
        return accountJx.getAccountId();

//        return 9527;
    }

    /**
     * 获取客户端请求IP地址
     * <b>Description:</b>
     * @param request
     * @return String
     * @Note
     * <b>Author：</b> cooper.xu
     * <b>Date:</b> 2019年07月11日 上午9:50:33
     */
    public static String getIp(HttpServletRequest request) {
//        String ipAddress = request.getHeader("x-forwarded-for");
//        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("Proxy-Client-IP");
//        }
//        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getRemoteAddr();
//            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
//                //根据网卡取本机配置的IP
//                InetAddress inet=null;
//                try {
//                    inet = InetAddress.getLocalHost();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                ipAddress= inet.getHostAddress();
//            }
//        }
//        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
//            if(ipAddress.indexOf(",")>0){
//                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
//            }
//        }
        return IpUtils.getRealIp(request);
    }

    /**
     * 获取客户端请求IP地址
     * <b>Description:</b>
     * @param request
     * @return Integer 1-WEB端 PC   2-微信   3-IOS_APP  4-安卓APP   5-其他   6-ERP  7-m站 8-ipad
     * @Note
     * <b>Author：</b> cooper.xu
     * <b>Date:</b> 2019年07月11日 上午9:50:33
     */
    public Integer getFromType(HttpServletRequest request) {
        //WEBVIEW
        String userAgent = request.getHeader("User-Agent");
        logger.info("User-Agent is {}",userAgent);
        BrowserType browserType = UserAgent.parseUserAgentString(userAgent).getBrowser().getBrowserType();
        logger.info("browserType is {}",browserType.getName());
        //APP
        String appAgent = request.getHeader("app-agent");
        logger.info("appAgent is {}",appAgent);

        if(StringUtil.isNotBlank(appAgent)&&appAgent.contains(USERAGENT_VEDENG_APP_IOS)){
            return FORM_IOS.getFormType();
        }
        if(StringUtil.isNotBlank(appAgent)&&appAgent.contains(USERAGENT_VEDENG_APP_ANDROID)){
            return FORM_ANDROID.getFormType();
        }
        if(browserType.getName().equals(WEB_BROWSER.getName())){
            return FORM_WEB.getFormType();
        }
        if(browserType.getName().equals(MOBILE_BROWSER.getName())){
            return FORM_M.getFormType();
        }
        return FORM_OHTER.getFormType();
    }

    public static void main(String[] args) {
        String userAgent = "Vedeng/1.0.0 (iPhone; iOS 12.3.1; Scale/3.00)";
        BrowserType browserType = UserAgent.parseUserAgentString(userAgent).getBrowser().getBrowserType();
        System.out.println(browserType);
    }

    /***
     * describe: 包装 存储 售后 信息封装
     * creat_time: 2019/8/20 11:26
     * param: [skuInfo, showModel]
     * return: com.vedeng.mjx.common.goods.GoodsAttrCombine
     */
    public GoodsAttrCombine packageGoodsPackEtc(SkuDTO skuInfo, Boolean showModel) {
        boolean allShowFlag = StringUtil.isNotBlank(skuInfo.getBaseUnitName()) && showModel || skuInfo.getPackageHeight() != null
                || skuInfo.getPackageLength() != null || skuInfo.getPackageWidth() != null || StringUtil.isNotBlank(skuInfo.getPackingList())
                || skuInfo.getMinOrder() != null;
        if (!allShowFlag) {
            return null;
        }
        GoodsAttrCombine goodsAttrCombine = new GoodsAttrCombine();
        //包装 begin
        GoodsPack goodsPack = new GoodsPack();
        if (showModel && StringUtil.isNotBlank(skuInfo.getBaseUnitName())) {
            goodsPack.setBaseUnitName(skuInfo.getBaseUnitName());
        }
        //(goodsEntityInfo?.skuInfo?.showModel == false) and not #strings.isEmpty(goodsEntityInfo?.skuInfo?.changeNum) and (goodsEntityInfo?.skuInfo?.changeNum >0) and not #strings.isEmpty(goodsEntityInfo?.skuInfo?.minUnitName) and not #strings.isEmpty(goodsEntityInfo?.skuInfo?.baseUnitName)
        Boolean specFlag = !showModel && skuInfo.getChangeNum() != null && skuInfo.getChangeNum().longValue() > 0L
                && StringUtil.isNotBlank(skuInfo.getMinUnitName()) && StringUtil.isNotBlank(skuInfo.getBaseUnitName());
        if (specFlag) {
            goodsPack.setChangeNum(skuInfo.getChangeNum());
            goodsPack.setMinUnitName(skuInfo.getMinUnitName());
            goodsPack.setBaseUnitName(skuInfo.getBaseUnitName());
        }

        Boolean cubeFlag = skuInfo.getPackageHeight() != null && skuInfo.getPackageWidth() != null && skuInfo.getPackageLength() != null;

        if (cubeFlag) {
            if (skuInfo.getPackageLength().compareTo(new BigDecimal("0")) > 0) {
                goodsPack.setPackageLength(skuInfo.getPackageLength());
            }
            if (skuInfo.getPackageWidth().compareTo(new BigDecimal("0")) > 0) {
                goodsPack.setPackageWidth(skuInfo.getPackageWidth());
            }
            if (skuInfo.getPackageHeight().compareTo(new BigDecimal("0")) > 0) {
                goodsPack.setPackageHeight(skuInfo.getPackageHeight());
            }
            if (skuInfo.getGrossWeight()!=null && skuInfo.getGrossWeight().compareTo(new BigDecimal("0")) > 0) {
                goodsPack.setGrossWeight(skuInfo.getGrossWeight());
            }
            if (showModel && StringUtil.isNotBlank(skuInfo.getPackingList())) {
                goodsPack.setPackingList(skuInfo.getPackingList());
            }
        }
        if (!showModel && skuInfo.getMinOrder() != null) {
            goodsPack.setMinOrderInt(skuInfo.getMinOrderInt());
        }
        goodsPack.setGoodsPackStr(showModel);
        goodsAttrCombine.setGoodsPack(goodsPack);
        //包装 end

        //存储begin
        Boolean storageFlag = !showModel && (skuInfo.getStorageConditionOne() != null || StringUtil.isNotBlank(skuInfo.getStorageConditionTwo())
                || StringUtil.isNotBlank(skuInfo.getEffectiveDays()));
        if (storageFlag) {
            GoodsStorage goodsStorage = new GoodsStorage();
            StringBuilder sb =new StringBuilder();
            Integer storageConditionOne = skuInfo.getStorageConditionOne();
            if ( storageConditionOne!= null) {
                goodsStorage.setStorageConditionOne(storageConditionOne);
                if(CommonConstant.ONE.equals(storageConditionOne)){
                    sb.append("常温 ");
                }
                if(CommonConstant.TWO.equals(storageConditionOne)){
                    sb.append("冷冻 ");
                }
                if(CommonConstant.THREE.equals(storageConditionOne)){
                    sb.append("冷藏 ");
                }
            }
            String storageConditionTwo = skuInfo.getStorageConditionTwo();
            if (StringUtil.isNotBlank(storageConditionTwo)) {
                goodsStorage.setStorageConditionTwo(storageConditionTwo);
                if(storageConditionTwo.indexOf("1")>-1){
                    sb.append("阴凉 ");
                }
                if(storageConditionTwo.indexOf("2")>-1){
                    sb.append("干燥 ");
                }
                if(storageConditionTwo.indexOf("3")>-1){
                    sb.append("避光 ");
                }
                if(storageConditionTwo.indexOf("3")>-1){
                    sb.append("防潮 ");
                }
            }
            Integer effectiveDayUnit = skuInfo.getEffectiveDayUnit();
            String effectiveDays = skuInfo.getEffectiveDays();
            if ( StringUtil.isNotBlank(effectiveDays) && effectiveDayUnit !=null) {
                StringBuilder unit =new StringBuilder();
                unit.append(effectiveDays);
                if(CommonConstant.ONE.equals(effectiveDayUnit)){
                    unit.append("天");
                }
                if(CommonConstant.TWO.equals(effectiveDayUnit)){
                    unit.append("月");
                }
                if(CommonConstant.THREE.equals(effectiveDayUnit)){
                    unit.append("年");
                }
                goodsStorage.setEffectiveDays(effectiveDays);
                goodsStorage.setEffectiveDayUnit(effectiveDayUnit);
                goodsStorage.setEffectiveDayAndUnitShowName(unit.toString());
            }

            if(StringUtil.isNotBlank(sb.toString())){
                goodsStorage.setStorageConditionShowName(sb.toString());
            }
            goodsStorage.setGoodsStorageStr(showModel);
            goodsAttrCombine.setGoodsStorage(goodsStorage);
        }
        //存储end

        //售后begin
        if (StringUtil.isNotBlank(skuInfo.getAfterSaleContent()) || StringUtil.isNotBlank(skuInfo.getQaYears())) {
            GoodsAfterSale goodsAfterSale = new GoodsAfterSale();
            String afterSaleContent = skuInfo.getAfterSaleContent();
            if (StringUtil.isNotBlank(afterSaleContent)) {
                goodsAfterSale.setAfterSaleContent(afterSaleContent);
                StringBuilder sb = new StringBuilder();
                if (StringUtil.isNotBlank(afterSaleContent)) {
                    if (StringUtil.isNotBlank(afterSaleContent))
                        if (afterSaleContent.indexOf("1") >= 0) {
                            sb.append("包安装 ");
                        }
                    if (afterSaleContent.indexOf("2") >= 0) {
                        sb.append("包培训 ");
                    }
                    if (afterSaleContent.indexOf("3") >= 0) {
                        sb.append("物流点自提 ");
                    }
                    if (afterSaleContent.indexOf("4") >= 0) {
                        sb.append("送货上门，含卸货上楼 ");
                    }
                    if (afterSaleContent.indexOf("5") >= 0) {
                        sb.append("送货上门，含卸货到楼下 ");
                    }
                }
                goodsAfterSale.setAfterSaleContentShowName(sb.toString());
            }
            if (StringUtil.isNotBlank(skuInfo.getQaYears())) {
                goodsAfterSale.setQaYears(skuInfo.getQaYears());
                String qaYearsShowName=skuInfo.getQaYears()+"年";
                goodsAfterSale.setQaYearsShowName(qaYearsShowName);
            }
            goodsAfterSale.setGoodsAfterSaleStr();
            goodsAttrCombine.setGoodsAfterSale(goodsAfterSale);
        }
        return goodsAttrCombine;
    }


    /***
     * describe: 封装商品参数
     * creat_time: 2019/8/20 11:45
     * param: [skuInfo]
     * return: com.vedeng.mjx.common.goods.ComBineAttr
     */
    public ComBineAttr packageGoodsAttr(SkuDTO skuInfo) {
        String[] techKey = skuInfo.getTechnicalParameterKey();
        String[] specKey = skuInfo.getSpecParameterKey();
        String[] perfKey = skuInfo.getPerformanceParameterKey();
        boolean flag = (techKey != null && techKey.length != 0) || (specKey != null && specKey.length != 0)
                || (perfKey != null && perfKey.length != 0);
        if (flag) {
            ComBineAttr paramKeyAndValues = new ComBineAttr();
            if (techKey != null && techKey.length != 0) {
                List<String> technicalParameterKey = Arrays.asList(techKey);
                List<String> technicalParameterValue = Arrays.asList(skuInfo.getTechnicalParameterValue());
                paramKeyAndValues.setTechnicalParameterKey(technicalParameterKey);
                paramKeyAndValues.setTechnicalParameterValue(technicalParameterValue);
            }
            if (specKey != null && specKey.length != 0) {
                List<String> specParameterKey = Arrays.asList(specKey);
                List<String> specParameterValue = Arrays.asList(skuInfo.getSpecParameterValue());
                paramKeyAndValues.setSpecParameterKey(specParameterKey);
                paramKeyAndValues.setSpecParameterValue(specParameterValue);
            }
            if (perfKey != null && perfKey.length != 0) {
                List<String> performanceParameterKey = Arrays.asList(perfKey);
                List<String> performanceParameterValue = Arrays.asList(skuInfo.getPerformanceParameterValue());
                paramKeyAndValues.setPerformanceParameterKey(performanceParameterKey);
                paramKeyAndValues.setPerformanceParameterValue(performanceParameterValue);
            }
            paramKeyAndValues.setComBineAttrStr();
            return paramKeyAndValues;
        } else {
            return null;
        }
    }

    public void packageDescription(SkuVO skuVO){
        StringBuilder sb = new StringBuilder();
        sb.append("<meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'><meta name='format-detection' content='telephone=no'><style>*{ margin:0; padding:0;} table{ width:100%!important;}img{ border:0; max-width:100%; height:auto!important;}</style>");
        if(skuVO.getBrandInfo()!=null && !CollectionUtils.isEmpty(skuVO.getBrandInfo().getAuthCertFileList())){
            //品牌授权书
            sb.append("<div>");
            for (PicDTO pic :skuVO.getBrandInfo().getAuthCertFileList()) {
                sb.append("<img src='").append(pic.getUrl()).append("'>");
            }
            sb.append("</div>");
        }
        if(skuVO.getDecriptInfo()!=null && StringUtil.isNotBlank(skuVO.getDecriptInfo().getDescript())){
            //商品详情
            sb.append("<span>").append(skuVO.getDecriptInfo().getDescript()).append("</span>");
        }
        if(skuVO.getRegistNumInfo()!=null && !CollectionUtils.isEmpty(skuVO.getRegistNumInfo().getRegistrationNumberFileList())){
            //注册证/备案凭证
            sb.append("<div>");
            for (PicDTO pic : skuVO.getRegistNumInfo().getRegistrationNumberFileList()) {
                sb.append("<img src='").append(pic.getUrl()).append("'>");
            }
            sb.append("</div>");
        }
        if(!CollectionUtils.isEmpty(skuVO.getSkuInfo().getSkuCheckFileList())){
            sb.append("<span><div>");
            for (PicDTO pic : skuVO.getSkuInfo().getSkuCheckFileList()) {
                sb.append("<img src='").append(pic.getUrl()).append("'>");
            }
            sb.append("</div></span>");
        }
        if(CollectionUtils.isEmpty(skuVO.getSkuInfo().getSkuCheckFileList()) && !CollectionUtils.isEmpty(skuVO.getSpuInfo().getSpuCheckFileList())){
            sb.append("<span><div>");
            for (PicDTO pic : skuVO.getSpuInfo().getSpuCheckFileList()) {
                sb.append("<img src='").append(pic.getUrl()).append("'>");
            }
            sb.append("</div></span>");
        }

        if(!CollectionUtils.isEmpty(skuVO.getSkuInfo().getSkuPatentFileList())){
            sb.append("<span><div>");
            for (PicDTO pic : skuVO.getSkuInfo().getSkuPatentFileList()) {
                sb.append("<img src='").append(pic.getUrl()).append("'>");
            }
            sb.append("</div></span>");
        }
        if(CollectionUtils.isEmpty(skuVO.getSkuInfo().getSkuPatentFileList()) && !CollectionUtils.isEmpty(skuVO.getSpuInfo().getSpuPatentFileList())){
            sb.append("<span><div>");
            for (PicDTO pic : skuVO.getSpuInfo().getSpuPatentFileList()) {
                sb.append("<img src='").append(pic.getUrl()).append("'>");
            }
            sb.append("</div></span>");
        }

        DescriptDTO descriptDTO =new DescriptDTO();
        descriptDTO.setDescript(sb.toString());
        skuVO.setDecriptInfo(descriptDTO);
    }

    /***
     * describe: 手机号格式校验 规则:手机号格式为第一位数字1开头的11位数字手机号码，且前两位不为“10”“11”“12”。格式不正确则报错“手机号码格式不正确”
     * creat_time: 2019/8/27 16:36
     * param: [phone]
     * return: boolean
     */
    public boolean checkWrongPhone(String phone){
        String oneTwo = phone.trim().substring(0, 2);
        String one = phone.trim().substring(0, 1);
        return !CommonConstant.STRING_ONE.equals(one) || CommonConstant.STRING_ONE_ONE.equals(oneTwo) ||
                CommonConstant.STRING_ONE_ZERO.equals(oneTwo) || CommonConstant.STRING_ONE_TWO.equals(oneTwo) || phone.trim().length()>CommonConstant.INTEGER_ELEVEN;
    }

}
