package com.vedeng.mjx.web.xxltask;


import com.google.common.collect.Lists;
import com.vedeng.mjx.common.constand.CommonUrl;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.http.HttpsClient;
import com.vedeng.mjx.common.orderVo.OrderData;
import com.vedeng.mjx.common.orderVo.OrderGoodsData;
import com.vedeng.mjx.common.util.HttpClientUtil;
import com.vedeng.mjx.domain.*;
import com.vedeng.mjx.domain.ext.VAccountAddressExt;
import com.vedeng.mjx.mapper.TopAccountJxMapper;
import com.vedeng.mjx.mapper.VOrderGoodsMapper;
import com.vedeng.mjx.mapper.VOrderMapper;
import com.vedeng.mjx.service.address.AddressService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.ShardingUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;


@JobHandler(value = "accountSyncToErpTask")
@Component
public class AccountSyncToErpTask extends IJobHandler {

    Logger logger= LoggerFactory.getLogger(AccountSyncToErpTask.class);

    @Value("${erp.server}")
    private String erpUrl;
    @Autowired
    com.vedeng.mjx.mapper.TOpAccountMapper opAccountMapper;

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        logger.info("erpUrl:"+erpUrl);
        ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
        logger.info("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingVO.getIndex(), shardingVO.getTotal());
        XxlJobLogger.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingVO.getIndex(), shardingVO.getTotal());

        TOpAccountExample example = new TOpAccountExample();
        example.createCriteria().andSyncStatusIn(Lists.newArrayList(0,2));
        List< TOpAccount> orderList = opAccountMapper.selectByExample(example);
        for(TOpAccount topAccountJx : orderList){
            try {
                String url = erpUrl + CommonUrl.ACOUNT_DATA_TO_ERP_URL;
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
                String result = HttpClientUtil.post(url, paramMap);
                TOpAccount accountJx = new TOpAccount();
                accountJx.setSyncStatus(1);
                accountJx.setAccountId(topAccountJx.getAccountId());
                opAccountMapper.updateByPrimaryKeySelective(accountJx);
            }catch(Exception e){
                logger.error("账户注册信息推送信息到ERP异常-------------------------------"+topAccountJx.getAccountId(),e);
                TOpAccount accountJx = new TOpAccount();
                accountJx.setSyncStatus((topAccountJx.getSyncStatus()==null?0:topAccountJx.getSyncStatus())+2);
                accountJx.setAccountId(topAccountJx.getAccountId());
                opAccountMapper.updateByPrimaryKeySelective(accountJx);
            }

        }
        return SUCCESS;
    }
}
