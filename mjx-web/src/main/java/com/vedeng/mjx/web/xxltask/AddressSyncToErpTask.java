package com.vedeng.mjx.web.xxltask;


import com.vedeng.mjx.common.address.TraderMjxContactAdderss;
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
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JobHandler(value = "addressSyncToErpTask")
@Component
public class AddressSyncToErpTask extends IJobHandler {

    Logger logger = LoggerFactory.getLogger(AddressSyncToErpTask.class);
    @Autowired
    private AddressService addressService;

    @Override
    public ReturnT<String> execute(String paramStr) {
        XxlJobLogger.log(">>>>>>>>>>> AddressSyncToErpTask start.");
        XxlJobLogger.log("param is {}", paramStr);
        // 分片参数
        ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
        int shardingIndex = shardingVO.getIndex();
        int shardingTotal = shardingVO.getTotal();
        XxlJobLogger.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingIndex, shardingTotal);

        //封装参数
        Map param = new HashMap<String, Object>();
        param.put("shardingTotal", Integer.valueOf(shardingTotal));
        param.put("shardingIndex", Integer.valueOf(shardingIndex));

        //查询需要推送到erp的地址
        List<TraderMjxContactAdderss> addersses = null;
        try {
            addersses = addressService.queryNoSyncToErpAddr(param);
        } catch (Exception e) {
            logger.error("queryNoSyncToErpAddr error", e);
            return FAIL;
        }
        if (!CollectionUtils.isEmpty(addersses)) {
            XxlJobLogger.log("addersses size is {}", addersses.size());
            //调用erp接口推送地址信息
            for (TraderMjxContactAdderss adders : addersses) {
                Integer adderssId = adders.getMjxContactAdderssId();
                JSONObject jsonObject = null;
                try {
                    jsonObject = addressService.httpSyncToErp(adders);
                } catch (Exception e) {
                    XxlJobLogger.log("httpSyncToErp error addressId is {}", adderssId);
                }
                //成功状态
                boolean flag = jsonObject != null && jsonObject.containsKey("code") && "0".equals(jsonObject.get("code").toString());
                Map updateParam = new HashMap<String, Object>();
                if (flag) {
                    XxlJobLogger.log("httpSyncToErp success jsonObject is {} ,addressId is {}", jsonObject.toString(), adderssId);
                    //成功 修改这条记录状态IS_SYNC_TOERP 为1 已经同步到erp

                    updateParam.put("syncToErpFlag", 1);
                    updateParam.put("accountAddressId", adders.getMjxContactAdderssId());
                    //1无含义 仅传值保持不为null
                    updateParam.put("syncToErpTimes", 1);

                } else {
                    updateParam.put("syncToErpFlag", 0);
                    XxlJobLogger.log("httpSyncToErp fail jsonObject is {},addressId is {}", jsonObject.toString(), adderssId);
                }
                Integer result = addressService.updateSyncAddr(updateParam);
                if (result == 1) {
                    XxlJobLogger.log("update success accountAddressId is {},syncToErpFlag is {}", adders.getMjxContactAdderssId(), 1);
                } else {
                    XxlJobLogger.log("update fail accountAddressId is {},syncToErpFlag is {}", adders.getMjxContactAdderssId(), 1);
                }

            }
        } else {

            XxlJobLogger.log("no addresses sync to erp or error");
        }
        return SUCCESS;
    }
}
