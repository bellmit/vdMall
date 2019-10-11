package com.vedeng.mjx.service.order;

import com.vedeng.mjx.common.orderVo.OrderData;
import com.vedeng.mjx.common.orderVo.OrderDetailData;
import com.vedeng.mjx.common.orderVo.OrderListData;
import com.vedeng.mjx.domain.OrderCountData;
import com.vedeng.mjx.domain.VCancel;
import com.vedeng.mjx.domain.VOrder;
import com.vedeng.mjx.domain.VOrderGoods;

import java.util.List;

public interface OrderService {

    List<OrderListData> selectTOpOrder(Integer accountId, Integer status, boolean checkPriceAuth);

    int selectOrderTotal(Integer accountId,Integer status);

    boolean saveTOpOrder(Integer accountId, VOrder order);

    boolean updateTOpOrder(VOrder order);

    int countByOrderNo(String orderNo,Integer orderStatus);

    int countByOrderNoStatusList(Integer accountId,String orderNo,List<Integer> orderStatus);

    boolean deleteOrder(Integer accountId ,String orderNo);

    boolean updateOrderAddress(OrderData orderData);

    int countByOrderNo(Integer accountId,String orderNo);

    boolean updateOrderService(OrderData orderData);

    List<OrderCountData> selectCountByOrderStstus(Integer accountId);

    List<VCancel> cancelList();

    OrderDetailData selectOrderDetail(Integer accountId,String orderNo,boolean checkPriceAuth);

    Integer updateOrderGoods(VOrderGoods orderGoods);

    VOrder getOrderByOrderNo(String orderNo);

    VOrder getOrderByOrderNoAndOrderStatus(String orderNo,Integer orderStatus);

    VOrderGoods selectOneUrlByOrderNo(String orderNo);

}
