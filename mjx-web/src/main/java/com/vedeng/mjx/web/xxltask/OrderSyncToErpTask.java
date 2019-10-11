package com.vedeng.mjx.web.xxltask;


import com.alibaba.fastjson.JSON;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.http.HttpsClient;
import com.vedeng.mjx.common.orderVo.OrderData;
import com.vedeng.mjx.common.orderVo.OrderGoodsData;
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
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@JobHandler(value = "orderSyncToErpTask")
@Component
public class OrderSyncToErpTask extends IJobHandler {

    Logger logger= LoggerFactory.getLogger(OrderSyncToErpTask.class);

    @Value("${erp.server}")
    private String erpUrl;
    private static String saveUrl = "/order/saleorder/saveBDAddSaleorder.do";

    @Resource
    private TopAccountJxMapper accountJxMapper;
    @Autowired
    private VOrderMapper orderMapper;
    @Autowired
    private VOrderGoodsMapper orderGoodsMapper;
    @Autowired
    private AddressService addressService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        logger.info("erpUrl:"+erpUrl);
        ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
        logger.info("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingVO.getIndex(), shardingVO.getTotal());
        XxlJobLogger.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingVO.getIndex(), shardingVO.getTotal());

        VOrderExample example = new VOrderExample();
        example.createCriteria().andIsPushEqualTo(false).andIsDelEqualTo(0);
        List<VOrder> orderList = orderMapper.selectByExample(example);
        for(VOrder order : orderList){

            VOrderGoodsExample exampleGoods = new VOrderGoodsExample();
            exampleGoods.createCriteria().andOrderNoEqualTo(order.getOrderNo()).andIsDelEqualTo(false);
            List<VOrderGoods> orderGoodsList = orderGoodsMapper.selectByExample(exampleGoods);
            List<OrderGoodsData> orderGoodsDataList = new ArrayList<>();
            for (VOrderGoods orderGoods : orderGoodsList){
                OrderGoodsData orderGoodsData = new OrderGoodsData();
                orderGoodsData.setSkuNo(orderGoods.getGoodsSku());
                orderGoodsData.setProductNum(orderGoods.getGoodsNum());
                orderGoodsData.setMarketMomey(orderGoods.getMarketMomey());
                orderGoodsData.setJxSalePrice(orderGoods.getSalesMoney());
                orderGoodsDataList.add(orderGoodsData);
            }
            OrderData orderData = new OrderData();
            orderData.setAccountId(order.getAccountId());
            orderData.setOrderNo(order.getOrderNo());

            TopAccountJx accountJx = accountJxMapper.selectByPrimaryKey(order.getAccountId());
            orderData.setUsername(accountJx.getCompanyName());
            orderData.setPhone(accountJx.getMobile());
            orderData.setDeliveryAddressId(order.getDeliveryAddressId());
            orderData.setDeliveryUserName(order.getDeliveryUserName());

            //查询用户收货地址
            VAccountAddressExt accountAddress = addressService.selectVAccountAddressById(order.getDeliveryAddressId());
            if(accountAddress == null){
                logger.error("查询用户收货地址"+ VedengErrorCode.USER_ADDRESS_EXIST);
            }

            String regionName = accountAddress.getRegionName().replace(","," ");
            List<String> regionNameList = Arrays.asList(accountAddress.getRegionIds().split(","));
            for(int j = 0;j<regionNameList.size();j++){
                String str = regionNameList.get(j);
                if(j == 0) orderData.setDeliveryLevel1Id(str);
                if(j == 1) orderData.setDeliveryLevel2Id(str);
                if(j == 2) orderData.setDeliveryLevel3Id(str);
            }
            orderData.setDeliveryUserArea(regionName);
            orderData.setDeliveryUserAddress(accountAddress.getAddressInfo());
            orderData.setDeliveryUserPhone(accountAddress.getPhone());
            orderData.setMarketMomney(order.getMarketMomney());
            orderData.setJxSalePrice(order.getSalesMoney());
            orderData.setProductTypeNum(order.getProductTypeNum());
            orderData.setProductNum(order.getProductNum());
            orderData.setOrderStatus(0);
            orderData.setOrderType(1);
            orderData.setRemakes(order.getOrderRemark());
            orderData.setPaymentMode(0);
            orderData.setGoodsList(orderGoodsDataList);
            JSONObject result = HttpsClient.httpPost(erpUrl+saveUrl, JSONObject.fromObject(orderData));
            logger.info("result:"+result);

            if(!ObjectUtils.isEmpty(result)){
                Map maps = (Map)result;
                if(maps.containsKey("code")){
                    String code = maps.get("code").toString();
                    if(code.equals("0")){
                        logger.info("通知erp添加订单成功,code:"+code);
                        VOrder record = new VOrder();
                        record.setIsPush(true);
                        VOrderExample exampleUpdate = new VOrderExample();
                        exampleUpdate.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                        int count = orderMapper.updateByExampleSelective(record,exampleUpdate);
                        if(count == 0){
                            logger.error("推送erp成功，更新订单失败");
                        }
                    }else{
                        logger.error("通知erp添加订单失败,code:"+code);
                    }
                }else{
                    logger.error("通知erp添加订单失败:code不存在");
                }
            }else{
                logger.error("通知erp添加订单失败:erp接口异常");
            }
        }
        return SUCCESS;
    }
}
