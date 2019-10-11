package com.vedeng.mjx.web.controller.order;

import com.alibaba.fastjson.JSON;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.enumUtils.OrderEnum;
import com.vedeng.mjx.common.http.HttpClientUtils;
import com.vedeng.mjx.common.http.HttpsClient;
import com.vedeng.mjx.common.orderVo.OrderData;
import com.vedeng.mjx.common.orderVo.OrderGoodsData;
import com.vedeng.mjx.common.util.OrderNoDict;
import com.vedeng.mjx.domain.*;
import com.vedeng.mjx.domain.ext.VAccountAddressExt;
import com.vedeng.mjx.web.controller.order.vo.SaveOrderData;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class ChenkOrder {

    public static String saveUrl = "/order/saleorder/saveBDAddSaleorder.do";
    public static String updateUrl = "/order/saleorder/updateBDSaleStatus.do";

    protected static ResultInfo saveNoEmpty(SaveOrderData saveOrderData) {
        if (ObjectUtils.isEmpty(saveOrderData.getAccountAddressId()))
            return ResultInfo.fail("1001", "请添加用户收货地址");
        if (ObjectUtils.isEmpty(saveOrderData.getOrderSrc()))
            return ResultInfo.fail("1001", "订单来源不能为空");
        if (ObjectUtils.isEmpty(saveOrderData.getPlaceNo()))
            return ResultInfo.fail("1001", "购物车中没有商品");

        return ResultInfo.success(true);
    }

    public static ResultInfo updateNoEmptyAddress(OrderData order){
        if (ObjectUtils.isEmpty(order.getAccountId()))
            return ResultInfo.fail("1001", "账户号不能为空");
        if (ObjectUtils.isEmpty(order.getOrderNo()))
            return ResultInfo.fail("1001", "订单号不能为空");

        return ResultInfo.success(true);
    }

    public static JSONObject httpErpOrder(String url,TopAccountJx userEntity, VOrder order, VAccountAddressExt accountAddress, SaveOrderData saveOrderData) throws IOException {

        List<OrderGoodsData> orderGoodsDataList = new ArrayList<>();
        List<VOrderGoods> goodsList = order.getGoodsList();
        for (VOrderGoods orderGoods : goodsList){
            OrderGoodsData orderGoodsData = new OrderGoodsData();
            orderGoodsData.setSkuNo(orderGoods.getGoodsSku());
            orderGoodsData.setProductNum(orderGoods.getGoodsNum());
            orderGoodsData.setMarketMomey(orderGoods.getMarketMomey());
            orderGoodsData.setJxSalePrice(orderGoods.getSalesMoney());
            orderGoodsDataList.add(orderGoodsData);
        }
        OrderData orderData = new OrderData();
        orderData.setAccountId(userEntity.getAccountId());
        orderData.setOrderNo(order.getOrderNo());
        orderData.setUsername(userEntity.getCompanyName());
        orderData.setPhone(userEntity.getMobile());
        orderData.setDeliveryAddressId(accountAddress.getAccountAddressId());
        orderData.setDeliveryUserName(accountAddress.getAccountName());
        String regionName = accountAddress.getRegionName().replace(","," ");
        List<String> regionNameList = Arrays.asList(accountAddress.getRegionIds().split(","));
        for(int i = 0;i<regionNameList.size();i++){
            String str = regionNameList.get(i);
            if(i == 0) orderData.setDeliveryLevel1Id(str);
            if(i == 1) orderData.setDeliveryLevel2Id(str);
            if(i == 2) orderData.setDeliveryLevel3Id(str);
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
        orderData.setRemakes(saveOrderData.getRemarks());
        orderData.setPaymentMode(0);
        orderData.setGoodsList(orderGoodsDataList);
        System.out.println("orderData:"+JSON.toJSONString(orderData));
        JSONObject result = HttpsClient.httpPost(url, JSONObject.fromObject(orderData));
        return result;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }
        return map;
    }

    public static JSONObject httpErpConfirmOrder(String updateUrl,String orderNo) throws IOException {
        //确认订单后通知erp
        OrderData orderData = new OrderData();
        orderData.setOrderNo(orderNo);
        orderData.setOrderStatus(1);
        JSONObject result = HttpsClient.httpPost(updateUrl, JSONObject.fromObject(orderData));
        return result;
    }

    public static String getOrderNo(Integer accountId){
        int num = (int)((Math.random()*9+1)*10000) + accountId;
        return OrderNoDict.getOrderNum(num,17);
    }

}
