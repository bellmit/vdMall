package com.vedeng.mjx.service.vdengJoin.impl;


import com.alibaba.fastjson.JSONObject;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constand.CommonUrl;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.util.HttpClientUtil;
import com.vedeng.mjx.domain.TOpAccount;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.mapper.ext.TopAccountJxExtMapper;
import com.vedeng.mjx.service.vdengJoin.VedengJoinService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName VedengJoinServiceImpl
 * @Description TODO
 * @Author Cooper.xu
 * @Date 2019-07-01 16:59
 * @Version 1.0
 **/
@Service
public class VedengJoinServiceImpl implements VedengJoinService {

    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${erp.server}")
    private String erpServer;

    @Resource
    private TopAccountJxExtMapper topAccountJxExtMapper;
    @Autowired
    com.vedeng.mjx.mapper.TOpAccountMapper opAccountMapper;
    @Override
    public ResultInfo applyJoinVedeng(String mobile) {
        //根据手机号获取该账号信息
        TopAccountJx topAccountJx = topAccountJxExtMapper.selectByMobile(mobile);
        if (topAccountJx != null) {
            //推送信息到ERP
            String url = erpServer + CommonUrl.VEDENG_JOIN_TO_ERP_URL;
            //拼接参数
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("webAccountId", topAccountJx.getAccountId().toString());
            paramMap.put("ssoAccountId", topAccountJx.getSsoAccountId().toString());
            paramMap.put("from", topAccountJx.getFrom().toString());
            paramMap.put("mobile", topAccountJx.getMobile());
            paramMap.put("companyName", topAccountJx.getCompanyName());
            paramMap.put("weixinOpenid", topAccountJx.getWeixinOpenid());
            paramMap.put("isVedengJoin", CommonConstant.STATUS_1);
            //发出请求
            logger.info("贝登精选立即申请推送信息到ERP开始-------------------------------");
            String result = HttpClientUtil.post(url, paramMap);
            logger.info("贝登精选立即申请推送信息到ERP结束-------------------------------" + result);
            if (StringUtils.isNotBlank(result)) {
                JSONObject resultJson = (JSONObject) JSONObject.parseObject(result).get("data");
                if (resultJson != null) {
                    //是否有效 0-否 1-是
                    String isEnable = String.valueOf(resultJson.get("isEnable"));
                    //是否贝登精选会员 0-否 1-是
                    String isVedengJx = String.valueOf(resultJson.get("isVedengJx"));
                    //是否申请加入 0-否 1-是
                    String isVedengJoin = String.valueOf(resultJson.get("isVedengJoin"));
                    if (CommonConstant.ERP_IS_ENABLE_0_JX.equals(isEnable)) {//账户失效
                        return ResultInfo.fail(VedengErrorCode.VEDENG_JOIN_ACCOUNT_FORBIDDEN);
                    } else if (CommonConstant.ERP_IS_VEDENG_JX_1_JX.equals(isVedengJx)) {//是否是贝登精选会员
                        return ResultInfo.success(VedengErrorCode.VEDENG_JOIN_IS_JOINED);
                    } else if (CommonConstant.ERP_IS_VEDENG_JOIN_1_JX.equals(isVedengJoin)) {//是否申请加入
                        return ResultInfo.success(VedengErrorCode.VEDENG_JOIN_IS_IN_REVIEW);
                    } else {
                        return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
                    }
                } else {
                    return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
                }
            } else {
                return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
            }
        } else {
            return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
        }
    }



    @Override
    public void pushAcountDataToERP(String mobile) {
        TopAccountJx topAccountJx = topAccountJxExtMapper.selectByMobile(mobile);
        if (topAccountJx == null) {
            return;
        }
        try {
            //根据手机号获取该账号信息
                //推送信息到ERP
                String url = erpServer + CommonUrl.ACOUNT_DATA_TO_ERP_URL;
                //拼接参数
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("webAccountId", topAccountJx.getAccountId().toString());
                paramMap.put("ssoAccountId", topAccountJx.getSsoAccountId().toString());
                paramMap.put("from", topAccountJx.getFrom().toString());
                paramMap.put("mobile", topAccountJx.getMobile());
                paramMap.put("companyName", topAccountJx.getCompanyName());
                paramMap.put("weixinOpenid", topAccountJx.getWeixinOpenid());
                paramMap.put("isVedengJoin", CommonConstant.STATUS_0);
                //发出请求
                logger.info("账户注册信息推送信息到ERP开始-------------------------------");
                String result = HttpClientUtil.postExceptionThrow(url, paramMap);
                logger.info("账户注册信息推送信息到ERP结束-------------------------------" + result);
                TOpAccount accountJx = new TOpAccount();
                accountJx.setSyncStatus(1);
                accountJx.setAccountId(topAccountJx.getAccountId());
                opAccountMapper.updateByPrimaryKeySelective(accountJx);


        } catch (Exception e) {
            try {
                    TOpAccount accountJx = new TOpAccount();
                    accountJx.setSyncStatus(0);
                    accountJx.setAccountId(topAccountJx.getAccountId());
                    opAccountMapper.updateByPrimaryKeySelective(accountJx);
            } catch (Exception e2) {//ignor

            } logger.error("注册信息推送到ERP异常：", e);
        }
    }
}
