package com.vedeng.mjx.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.vedeng.goods.api.RequestParameter;
import com.vedeng.goods.api.dto.PicDTO;
import com.vedeng.goods.api.dto.SkuDTO;
import com.vedeng.goods.api.dto.SkuNature;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.enumUtils.InvoiceTypeEnum;
import com.vedeng.mjx.common.enumUtils.OrderEnum;
import com.vedeng.mjx.common.orderVo.*;
import com.vedeng.mjx.common.util.AddressUtil;
import com.vedeng.mjx.common.util.InvoiceTypeUtil;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.common.util.VeDate;
import com.vedeng.mjx.domain.*;
import com.vedeng.mjx.domain.OrderCountData;
import com.vedeng.mjx.mapper.*;
import com.vedeng.mjx.service.feign.goods.GoodsServiceFeignApi;
import com.vedeng.mjx.service.order.OrderService;
import com.vedeng.mjx.service.util.PhoneUtil;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VOrderMapper tOpOrderMapper;
    @Autowired
    private VOrderGoodsMapper tOpOrderGoodsMapper;
    @Autowired
    private VCancelMapper cancelMapper;
    @Autowired
    private GoodsServiceFeignApi goodsServiceFeignApi;

    @Override
    public List<OrderListData> selectTOpOrder(Integer accountId, Integer status, boolean checkPriceAuth){

        logger.info("订单查询service");
        List<OrderListData> orderListDataList = new ArrayList<>();
        try {
            List<Integer> list = null;
            if(status != null && status != OrderEnum.ALL.getValue()){
                list = new ArrayList<>();
                if(status == OrderEnum.PAY.getValue() || status == OrderEnum.SEND.getValue()){
                    list.add(OrderEnum.PAY.getValue());
                    list.add(OrderEnum.SEND.getValue());
                }else{
                    list.add(status);
                }
            }
            List<VOrder> orderList = tOpOrderMapper.orderList(accountId,list);

            for (VOrder order : orderList) {
                OrderListData orderGoodsData = new OrderListData();
                VOrderGoodsExample exampleGoods = new VOrderGoodsExample();
                exampleGoods.createCriteria().andOrderNoEqualTo(order.getOrderNo()).andIsDelEqualTo(false);
                List<VOrderGoods> goodsList = tOpOrderGoodsMapper.selectByExample(exampleGoods);
                List<OrderGoodsListData> orderGoodsListDataList = new ArrayList<>();
                for (VOrderGoods orderGoods : goodsList) {
                    OrderGoodsListData orderGoodsListData = new OrderGoodsListData();
                    orderGoodsListData.setGoodsUri(orderGoods.getGoodsUri());
                    orderGoodsListData.setGoodsId(orderGoods.getGoodsId());
                    orderGoodsListData.setGoodsModel(orderGoods.getGoodsModel());
                    orderGoodsListData.setSpecs(orderGoods.getSpecs());
                    orderGoodsListData.setStoreRemark(orderGoods.getStoreRemark());
                    orderGoodsListData.setGoodsNum(orderGoods.getGoodsNum());
                    orderGoodsListData.setOrderNo(orderGoods.getOrderNo());
                    orderGoodsListData.setSalesMoney(orderGoods.getSalesMoney().toString());
                    orderGoodsListData.setGoodsSku(orderGoods.getGoodsSku());
                    orderGoodsListData.setGoodsName(orderGoods.getGoodsName());
                    orderGoodsListData.setIsSeven(orderGoods.getIsSeven());
                    orderGoodsListData.setSeven(orderGoods.getSeven());
                    if(orderGoods.getMarketMomey() != null) {
                        orderGoodsListData.setMarketMoney(orderGoods.getMarketMomey().toString());
                        if(orderGoodsListData.getMarketMoney().equals("0.00")){
                            orderGoodsListData.setMarketMoney("0");
                        }
                        if (!checkPriceAuth) {
                            orderGoodsListData.setMarketMoney("0");
                        }
                    }
                    orderGoodsListDataList.add(orderGoodsListData);
                }
                BeanUtils.copyProperties(orderGoodsData, order);

                orderGoodsData.setGoodsList(orderGoodsListDataList);
                if(order.getCancelFormStatus() == null){
                    orderGoodsData.setCancelFormStatus(-1);
                }
                if (!checkPriceAuth) {
                    orderGoodsData.setMarketMoney("0");
                }
                orderListDataList.add(orderGoodsData);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询订单列表异常", e.getMessage());
        }
        return orderListDataList;
    }

    @Override
    public int selectOrderTotal(Integer accountId,Integer status) {
        VOrderExample example = new VOrderExample();
        VOrderExample.Criteria criteria = example.createCriteria().andAccountIdEqualTo(accountId).andIsDelEqualTo(0);
        if(status != null && status != -1) {
            criteria.andOrderStatusEqualTo(status);
        }
        return (int)tOpOrderMapper.countByExample(example);
    }

    @Override
    public boolean saveTOpOrder(Integer accountId, VOrder order) {

        boolean result = true;

        try {
            List<VOrderGoods> goodsList = order.getGoodsList();
            for(VOrderGoods orderGoods : goodsList){
                orderGoods.setIsDel(false);
                int count = tOpOrderGoodsMapper.insertSelective(orderGoods);
                if (count == 0) {
                    result = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            logger.error("添加订单商品失败");

        }

        try {
            order.setAccountId(accountId);
            int count = tOpOrderMapper.insertSelective(order);
            if(count == 0){
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            logger.error("添加订单失败");
        }

        return result;
    }

    @Override
    public boolean updateTOpOrder(VOrder order) {

        boolean result = true;

        VOrder record = new VOrder();
        record.setOrderStatus(order.getOrderStatus());
        if(order.getOrderStatus() == OrderEnum.CHANEL.getValue()){
            if(ObjectUtils.isEmpty(order.getCancelFormStatus())){
                VOrderExample example = new VOrderExample();
                example.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                List<VOrder> orderList = tOpOrderMapper.selectByExample(example);
                if(!CollectionUtils.isEmpty(orderList)) {
                    VOrder vorder = orderList.get(0);
                    record.setCancelFormStatus(vorder.getOrderStatus());
                    if(vorder.getOrderStatus() == 1) {
                        record.setCancelReason("超时关闭");
                    }else{
                        record.setCancelReason("审核中关闭");
                    }
                }
            }else{
                record.setCancelFormStatus(order.getCancelFormStatus());
                record.setCancelReason(order.getCancelReason());
            }
        }
        VOrderExample exampleOrder = new VOrderExample();
        exampleOrder.createCriteria().andOrderNoEqualTo(order.getOrderNo());
        Integer count = tOpOrderMapper.updateByExampleSelective(record,exampleOrder);
        if(count == 0){

            result = false;
        }
        return result;
    }

    @Override
    public int countByOrderNo(String orderNo,Integer orderStatus) {
        VOrderExample example = new VOrderExample();
        example.createCriteria().andOrderNoEqualTo(orderNo).andOrderStatusEqualTo(orderStatus);
        return (int)tOpOrderMapper.countByExample(example);
    }

    @Override
    public int countByOrderNoStatusList(Integer accountId,String orderNo, List<Integer> orderStatus) {
        VOrderExample example = new VOrderExample();
        example.createCriteria().andAccountIdEqualTo(accountId).andOrderNoEqualTo(orderNo).andOrderStatusIn(orderStatus);
        return (int)tOpOrderMapper.countByExample(example);
    }

    @Override
    public boolean deleteOrder(Integer accountId,String orderNo) {

        boolean result = true;

        try {

            VOrder record = new VOrder();
            record.setIsDel(1);
            VOrderExample example = new VOrderExample();
            example.createCriteria().andOrderNoEqualTo(orderNo).andAccountIdEqualTo(accountId);
            int count = tOpOrderMapper.updateByExampleSelective(record,example);
            if(count == 0){
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            logger.error("删除订单失败");
        }
        return result;
    }

    @Override
    public boolean updateOrderAddress(OrderData orderData) {

        VOrder order = new VOrder();

        order.setInvoiceType(InvoiceTypeUtil.getInvoiceType(orderData.getInvoiceType()));//发票类型
        order.setDeliveryUserPhone(orderData.getDeliveryUserPhone());
        order.setDeliveryUserName(orderData.getDeliveryUserName());//收货人名称
        order.setDeliveryUserAddress(orderData.getDeliveryUserArea()+" "+orderData.getDeliveryUserAddress());//收货地址

        order.setInvoiceUserPhone(orderData.getInvoiceTraderContactMobile());
        order.setIsSendInvoice(orderData.getIsSendInvoice());
        order.setOpenInvoiceMode(orderData.getOpenInvoiceMode());//开票方式
        order.setInvoiceUserAddress(orderData.getInvoiceTraderArea()+" "+orderData.getInvoiceTraderAddress());//收票地址
        order.setInvoiceUserName(orderData.getInvoiceTraderContactName());//收票联系人名称

        List<OrderGoodsData> orderGoodsDataList = orderData.getGoodsList();
        if (orderGoodsDataList != null) {
            for (OrderGoodsData orderGoodsData : orderGoodsDataList) {
                VOrderGoods orderGoods = new VOrderGoods();
                orderGoods.setStoreRemark(orderGoodsData.getStoreRemarks());

                VOrderGoodsExample example = new VOrderGoodsExample();
                example.createCriteria().andGoodsSkuEqualTo(orderGoodsData.getSkuNo());
                int count = tOpOrderGoodsMapper.updateByExampleSelective(orderGoods, example);
                if (count == 0) {
                    logger.info("修改订单商品失败");
                    return false;
                }

            }
        }
        VOrderExample example = new VOrderExample();
        example.createCriteria().andOrderNoEqualTo(orderData.getOrderNo());
        int count = tOpOrderMapper.updateByExampleSelective(order, example);
        if (count == 0) {
            logger.info("修改订单失败");
            return false;
        }
        return true;
    }

    @Override
    public int countByOrderNo(Integer accountId,String orderNo) {
        VOrderExample example = new VOrderExample();
        example.createCriteria().andOrderNoEqualTo(orderNo).andIsDelEqualTo(0);
        return (int)tOpOrderMapper.countByExample(example);
    }

    @Override
    public boolean updateOrderService(OrderData orderData) {

        boolean result = true;

        VOrder order = new VOrder();
        order.setSalesMoney(orderData.getJxSalePrice());
        order.setOrderStatus(orderData.getOrderStatus());
        order.setCompanyId(orderData.getCompanyId());
        order.setCompanyName(orderData.getCompanyName());
        order.setDeliveryUserName(orderData.getDeliveryUserName());
        order.setDeliveryUserPhone(orderData.getDeliveryUserPhone());
        order.setDeliveryUserAddress(orderData.getDeliveryUserArea() +" "+ orderData.getDeliveryUserAddress());
        order.setInvoiceAddressId(orderData.getInvoiceTraderAddressId());
        order.setInvoiceUserAddress(orderData.getInvoiceTraderArea() +" "+ orderData.getInvoiceTraderAddress());
        order.setInvoiceUserName(orderData.getInvoiceTraderContactName());
        order.setInvoiceUserPhone(orderData.getInvoiceTraderContactMobile());
        order.setInvoiceUserId(orderData.getInvoiceTraderContactId());
        order.setTotalMoney(orderData.getJxSalePrice());
        order.setIsSendInvoice(orderData.getIsSendInvoice());
        order.setAdditionalClause(orderData.getAdditionalClause());
        order.setInvoiceType(InvoiceTypeUtil.getInvoiceType(orderData.getInvoiceType()));
        order.setPayTime(new Date());

        VOrderExample example = new VOrderExample();
        example.createCriteria().andOrderNoEqualTo(orderData.getOrderNo());
        int count = tOpOrderMapper.updateByExampleSelective(order,example);
        if(count == 0){
            logger.info("修改订单表失败");
            result = false;
        }
        return result;
    }

    @Override
    public List<OrderCountData> selectCountByOrderStstus(Integer accountId) {

        List<OrderCountData> orderCountDataList =  tOpOrderMapper.selectCountByOrderStstus(accountId);
        for(int i = OrderEnum.EXAMINE.getValue(); i <= OrderEnum.CHANEL.getValue(); i++){
            boolean isExist = isExist(orderCountDataList,i);
            if(!isExist){
                OrderCountData orderCountData = new OrderCountData();
                orderCountData.setCount(0);
                orderCountData.setOrderStatus(i);
                orderCountDataList.add(orderCountData);
            }
        }
        return orderCountDataList;
    }

    @Override
    public List<VCancel> cancelList() {
        return cancelMapper.cancelList();
    }

    @Override
    public OrderDetailData selectOrderDetail(Integer accountId,String orderNo,boolean checkPriceAuth) {

        logger.info("订单查询service");
        OrderDetailData orderDetailData = new OrderDetailData();

        try {
            VOrderExample example = new VOrderExample();
            example.createCriteria().andAccountIdEqualTo(accountId).andOrderNoEqualTo(orderNo).andIsDelEqualTo(0);
            List<VOrder> orderList = tOpOrderMapper.selectByExample(example);
            if(!CollectionUtils.isEmpty(orderList)){
                VOrder order = orderList.get(0);
                VOrderGoodsExample exampleGoods = new VOrderGoodsExample();
                exampleGoods.createCriteria().andOrderNoEqualTo(order.getOrderNo()).andIsDelEqualTo(false);
                List<VOrderGoods> goodsList = tOpOrderGoodsMapper.selectByExample(exampleGoods);
                List<OrderGoodsDetailData> orderGoodsDetailDataList = new ArrayList<>();
                for(VOrderGoods orderGoods : goodsList){
                    OrderGoodsDetailData orderGoodsDetailData = new OrderGoodsDetailData();
                    BeanUtils.copyProperties(orderGoodsDetailData, orderGoods);
                    orderGoodsDetailData.setMarketMoney(orderGoods.getMarketMomey().toString());
                    if(orderGoodsDetailData.getMarketMoney().equals("0.00")){
                        orderGoodsDetailData.setMarketMoney("0");
                    }
                    if (!checkPriceAuth) {
                        orderGoodsDetailData.setMarketMoney("0");
                    }
                    if(order.getCancelFormStatus() != null) {
                        if (order.getCancelFormStatus() == OrderEnum.EXAMINE.getValue()) {
                            orderGoodsDetailData.setStoreRemark("");
                        }
                    }else{
                        if(order.getOrderStatus() == OrderEnum.EXAMINE.getValue()){
                            orderGoodsDetailData.setStoreRemark("");
                        }
                    }
                    orderGoodsDetailDataList.add(orderGoodsDetailData);
                }
                BeanUtils.copyProperties(orderDetailData, order);
                orderDetailData.setDeliveryUserPhone(PhoneUtil.getPhone(orderDetailData.getDeliveryUserPhone()));
                if(orderDetailData.getInvoiceUserPhone() != null && orderDetailData.getInvoiceUserPhone().length() == 11){
                    orderDetailData.setInvoiceUserPhone(PhoneUtil.getPhone(orderDetailData.getInvoiceUserPhone()));
                }
                if(order.getCancelFormStatus() == null){
                    orderDetailData.setCancelFormStatus(-1);
                }
                orderDetailData.setGoodList(orderGoodsDetailDataList);
                orderDetailData.setOrderTime(VeDate.getDate(orderDetailData.getAddTime()));
                String confirmContent = "";
                if(orderDetailData.getOrderStatus() == OrderEnum.WAIT_CONFIRM.getValue()) {
                    confirmContent = VeDate.getConfirmContent(orderDetailData.getPayTime());
                }
                orderDetailData.setConfirmContent(confirmContent);
                if(order.getCancelFormStatus() != null) {
                    if (order.getCancelFormStatus() == OrderEnum.EXAMINE.getValue()) {
                        orderDetailData.setIsSendInvoice(0);
                        orderDetailData.setAdditionalClause("");
                    }
                }else{
                    if(order.getOrderStatus() == OrderEnum.EXAMINE.getValue()){
                        orderDetailData.setIsSendInvoice(0);
                        orderDetailData.setAdditionalClause("");
                    }
                }
                if (!checkPriceAuth) {
                    orderDetailData.setMarketMoney("0");
                }
                if(!StringUtil.isBlank(orderDetailData.getDeliveryUserAddress())) {
                    orderDetailData.setDeliveryUserAddress(AddressUtil.getAddress(orderDetailData.getDeliveryUserAddress()));
                }
                if(!StringUtil.isBlank(orderDetailData.getInvoiceUserAddress())) {
                    orderDetailData.setInvoiceUserAddress(AddressUtil.getAddress(orderDetailData.getInvoiceUserAddress()));
                }
                orderDetailData.setInvoiceMessage("");
                Integer invoiceType = orderDetailData.getInvoiceType();
                if(invoiceType == InvoiceTypeEnum.TYPE_1.getType()){
                    orderDetailData.setInvoiceMessage(InvoiceTypeEnum.TYPE_1.getMessage());
                }
                if(invoiceType == InvoiceTypeEnum.TYPE_4.getType()){
                    orderDetailData.setInvoiceMessage(InvoiceTypeEnum.TYPE_4.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("订单查询异常");
            return null;
        }
        return orderDetailData;
    }

    @Override
    public Integer updateOrderGoods(VOrderGoods orderGoods) {
        VOrderGoodsExample exampleGoods = new VOrderGoodsExample();
        exampleGoods.createCriteria().andOrderNoEqualTo(orderGoods.getOrderNo()).andGoodsSkuEqualTo(orderGoods.getGoodsSku());
        int countGoods = tOpOrderGoodsMapper.updateByExampleSelective(orderGoods, exampleGoods);
        return countGoods;
    }

    @Override
    public VOrder getOrderByOrderNo(String orderNo) {
        VOrderExample example = new VOrderExample();
        example.createCriteria().andOrderNoEqualTo(orderNo).andIsDelEqualTo(0);
        List<VOrder> orderList = tOpOrderMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(orderList)){
            return orderList.get(0);
        }
        return null;
    }

    @Override
    public VOrder getOrderByOrderNoAndOrderStatus(String orderNo, Integer orderStatus) {
        VOrderExample example = new VOrderExample();
        example.createCriteria().andOrderNoEqualTo(orderNo).andOrderStatusEqualTo(orderStatus).andIsDelEqualTo(0);
        List<VOrder> orderList = tOpOrderMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(orderList)){
            return orderList.get(0);
        }
        return null;
    }

    @Override
    public VOrderGoods selectOneUrlByOrderNo(String orderNo) {
        return tOpOrderGoodsMapper.selectOneUrlByOrderNo(orderNo);
    }

    public static boolean isExist(List<OrderCountData> orderCountDataList, Integer status){
        boolean isExist = false;
        for(OrderCountData orderCountData : orderCountDataList){
            Integer orderStatus = orderCountData.getOrderStatus();
            if(orderStatus == status){
                isExist = true;
            }
        }
        return isExist;
    }

}
