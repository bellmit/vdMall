package com.vedeng.mjx.web.controller.app.order;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vedeng.goods.api.RequestParameter;
import com.vedeng.goods.api.dto.PicDTO;
import com.vedeng.goods.api.dto.SkuDTO;
import com.vedeng.goods.api.dto.SkuNature;
import com.vedeng.goods.api.vo.SkuListResult;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.common.MessageNotice;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.ResultOrderInfo;
import com.vedeng.mjx.common.enumUtils.MessageEnum;
import com.vedeng.mjx.common.enumUtils.OrderEnum;
import com.vedeng.mjx.common.enumUtils.OrderSrcEnum;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.http.HttpClientUtils;
import com.vedeng.mjx.common.http.HttpsClient;
import com.vedeng.mjx.common.orderVo.OrderData;
import com.vedeng.mjx.common.orderVo.OrderDetailData;
import com.vedeng.mjx.common.orderVo.OrderGoodsData;
import com.vedeng.mjx.common.orderVo.OrderListData;
import com.vedeng.mjx.common.page.PageModel;
import com.vedeng.mjx.common.util.OrderNoDict;
import com.vedeng.mjx.common.util.PushUtil;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.*;
import com.vedeng.mjx.domain.ext.VAccountAddressExt;
import com.vedeng.mjx.mapper.VCancelMapper;
import com.vedeng.mjx.mapper.VOrderGoodsMapper;
import com.vedeng.mjx.mapper.VOrderMapper;
import com.vedeng.mjx.mapper.VSkuMapper;
import com.vedeng.mjx.service.address.AddressService;
import com.vedeng.mjx.service.feign.goods.GoodsServiceFeignApi;
import com.vedeng.mjx.service.goodsCar.GoodsCarService;
import com.vedeng.mjx.service.message.MessageService;
import com.vedeng.mjx.service.order.OrderService;
import com.vedeng.mjx.service.util.PhoneUtil;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.web.controller.app.order.vo.AppOrderData;
import com.vedeng.mjx.web.controller.app.order.vo.SaveOrderData;
import com.vedeng.mjx.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ auther: Marin
 * @ date 2019/7/10 14:14
 * 订单Controller层（03分支）
 */
@RequestMapping("/app/order")
@Api(value="订单controller",tags={"订单控制层"})
@RestController
public class AppOrderController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private GoodsCarService goodsCarService;
    @Autowired
    private GoodsServiceFeignApi goodsServiceFeignApi;
    @Autowired
    private VOrderGoodsMapper tOpOrderGoodsMapper;
    @Autowired
    private MessageService messageService;

    /**
     * 查询订单列表
     * @param appOrderData
     * @return
     */
    @PostMapping("/selectOrderList")
    public ResultInfo selectOrder(@RequestBody AppOrderData appOrderData){

        Integer status = appOrderData.getStatus();
        boolean checkPriceAuth = checkPriceAuth();
        PageHelper.startPage(appOrderData.getPageNo(), appOrderData.getPageSize());
        List<OrderListData> orderList = orderService.selectTOpOrder(getAccountId(), status, checkPriceAuth);
        int total = orderService.selectOrderTotal(getAccountId(),status);
        PageInfo<OrderListData> pageInfo = new PageInfo<OrderListData>(orderList);
        Map<String,Object> result = new HashMap<>();
        boolean hasNextPage = true;
        if(((appOrderData.getPageNo()-1)*appOrderData.getPageSize())+pageInfo.getSize() >= total){
            hasNextPage = false;
        }
        result.put("hasNextPage",hasNextPage);
        result.put("orderList",pageInfo.getList());
        return ResultInfo.success(result);
    }

    /**
     * 添加订单
     * @param saveOrderData
     * @return
     */
    @PostMapping(value = "/saveOrder")
    public ResultInfo saveOrder(@RequestBody SaveOrderData saveOrderData){

        log.info("saveOrderData:"+JSON.toJSONString(saveOrderData));
        //如果订单来源为空，默认微信
        if(saveOrderData.getOrderSrc() == null){
            saveOrderData.setOrderSrc(OrderSrcEnum.WECHAT.getValue());
        }
        //非空校验
        ResultInfo checkResult = ChenkOrder.saveNoEmpty(saveOrderData);
        if(!"success".equals(checkResult.getCode())){
            return checkResult;
        }

        String orderNo = ChenkOrder.getOrderNo(getAccountId());

        VOrder order = new VOrder();
        order.setOrderNo(orderNo);//订单号
        order.setOrderSrc(saveOrderData.getOrderSrc());//订单来源
        order.setOrderRemark(saveOrderData.getRemarks());//备注

        BigDecimal sumSalesMoney = new BigDecimal(0);//销售价合计
        BigDecimal sumMarketMoney = new BigDecimal(0);//市场价合计
        Integer productTypeNum = 0;//商品种类数量
        Integer productNum = 0;//商品数量

        //查询用户收货地址
        VAccountAddressExt accountAddress = addressService.selectVAccountAddressById(saveOrderData.getAccountAddressId());

        if(accountAddress == null){
            log.error("查询用户收货地址"+VedengErrorCode.USER_ADDRESS_EXIST);
            return ResultInfo.fail(VedengErrorCode.USER_ADDRESS_EXIST);
        }

        order.setDeliveryAddressId(accountAddress.getAccountAddressId());
        order.setDeliveryUserName(accountAddress.getAccountName());
        String regionName = accountAddress.getRegionName().replace(","," ");
        order.setDeliveryUserAddress(regionName+" "+accountAddress.getAddressInfo());
        order.setDeliveryUserPhone(accountAddress.getPhone());

        //查询购物车
        List<VGoodsCarMx> goodsCarMxList = goodsCarService.selectVGoodsCarMxList(saveOrderData.getPlaceNo());
        if(CollectionUtils.isEmpty(goodsCarMxList)){
            log.error("查询购物车:"+VedengErrorCode.GOODS_CAR_EXIST);
            return ResultInfo.fail(VedengErrorCode.GOODS_CAR_EXIST);
        }
        if(saveOrderData.getPlaceNo().size() != goodsCarMxList.size()){
            log.error("查询购物车:"+VedengErrorCode.GOODS_CAR_EXIST);
            return ResultInfo.fail(VedengErrorCode.GOODS_CAR_EXIST);
        }

        List<VOrderGoods> orderGoodsList = new ArrayList<>();

        for (VGoodsCarMx goodsCarMx: goodsCarMxList){
            VOrderGoods orderGoods = new VOrderGoods();
            orderGoods.setOrderNo(orderNo);
            orderGoods.setGoodsNum(goodsCarMx.getCount());

            SkuVO skuDetailBySkuNo = null;
            try {
                RequestParameter var1 = new RequestParameter();
                var1.setSkuNo(goodsCarMx.getSkuNo());
                skuDetailBySkuNo = goodsServiceFeignApi.getSkuDetailBySkuNo(var1);

            } catch (Exception e) {
                log.error("查询商品失败:"+VedengErrorCode.GOODS_ERR);
                return ResultInfo.fail(VedengErrorCode.GOODS_ERR);
            }
            if(skuDetailBySkuNo == null){
                log.error("查询商品为空:"+VedengErrorCode.GOODS_EXIST);
                return ResultInfo.fail(VedengErrorCode.GOODS_EXIST);
            }

            SkuNature skuNature = skuDetailBySkuNo.getSkuNature();
            if(skuNature == null){
                log.error("skuNature为空:"+VedengErrorCode.GOODS_ON_SALE);
                return ResultInfo.fail(VedengErrorCode.GOODS_ON_SALE);
            }
            if(skuNature.getIsOnSale() == 0){
                log.error("商品已下架:"+VedengErrorCode.GOODS_ON_SALE);
                return ResultInfo.fail(VedengErrorCode.GOODS_ON_SALE);
            }
            Integer isSeven = skuNature.getIsSeven();
            orderGoods.setIsSeven(isSeven);
            if (isSeven == 1) {
                orderGoods.setSeven(VedengErrorCode.SEVEN.getMessage());
            }
            Integer count = goodsCarMx.getCount();

            BigDecimal marketMomey = skuNature.getBdMarketPrice();
            orderGoods.setMarketMomey(new BigDecimal(0));//市场价
            if(marketMomey != null) {
                orderGoods.setMarketMomey(marketMomey);//市场价
                BigDecimal marketMomeyCount = marketMomey.multiply(new BigDecimal(count));
                sumMarketMoney = sumMarketMoney.add(marketMomeyCount);//计入市场价合计
            }

            BigDecimal salesMoney = skuNature.getSalePrice();
            orderGoods.setSalesMoney(new BigDecimal(0));//销售价
            if(salesMoney != null) {
                orderGoods.setSalesMoney(salesMoney);//销售价
                BigDecimal saleMoneyCount = salesMoney.multiply(new BigDecimal(count));
                sumSalesMoney = sumSalesMoney.add(saleMoneyCount);//计入销售价合计
            }

            PicDTO picDTO = skuDetailBySkuNo.getFirstPic();
            if(picDTO != null && picDTO.getUrl360() != null) {
                orderGoods.setGoodsUri(picDTO.getUrl360());//商品图片
            }

            SkuDTO skuDTO = skuDetailBySkuNo.getSkuInfo();

            orderGoods.setGoodsName(skuDTO.getSkuName());//商品名称
            orderGoods.setGoodsSku(skuDTO.getSkuNo());//商品skuNo
            orderGoods.setGoodsModel(skuDTO.getModelSpecShowChineseName());//规格型号名称
            orderGoods.setSpecs(skuDTO.getModelSpecShow());//规格型号
            orderGoodsList.add(orderGoods);

            productTypeNum++;
            productNum += goodsCarMx.getCount();

        }

        TopAccountJx userEntity =  ShiroSessionUtil.getSessionTopAccountJx();
        order.setCompanyName(userEntity.getCompanyName());
        order.setMarketMomney(sumMarketMoney);
        order.setSalesMoney(sumSalesMoney);
        order.setProductTypeNum(productTypeNum);
        order.setProductNum(productNum);
        order.setGoodsList(orderGoodsList);
        order.setTotalMoney(sumMarketMoney);

        try {

            JSONObject httpResult = ChenkOrder.httpErpOrder(erpUrl + ChenkOrder.saveUrl,userEntity,order,accountAddress,saveOrderData);
            if(!ObjectUtils.isEmpty(httpResult)) {
                Map maps = (Map) httpResult;
                if (maps.containsKey("code")) {
                    String code = maps.get("code").toString();
                    if (code.equals("0")) {
                        order.setIsPush(true);
                    }
                    log.info("通知erp添加订单结果,code:" + code+",单号:"+orderNo);
                }
            }
        } catch (IOException e) {
            log.error("通知erp添加订单失败:"+e);
        }

        boolean result = orderService.saveTOpOrder(getAccountId(),order);
        if(!result){
            log.error("添加订单"+VedengErrorCode.ADD_ORDER_ERR);
            return ResultInfo.fail(VedengErrorCode.ADD_ORDER_ERR);
        }

        Integer isCommit = goodsCarService.updateGoodsCarList(goodsCarMxList);
        if(isCommit == 0){
            log.error("修改购物车"+VedengErrorCode.UPDATE_GOODS_CAR_ERR);
        }
        return ResultInfo.success(true);
    }


    /**
     * 确认订单
     * @param order
     * @return
     */
    @PostMapping(value = "/confirmOrder")
    public ResultInfo confirmOrder(@RequestBody VOrder order){
        log.info("订单确认:"+JSON.toJSONString(order));
        if(StringUtil.isBlank(order.getOrderNo())){
            return ResultInfo.fail(VedengErrorCode.ORDERNO_NOTNULL);
        }
        VOrder vOrder = orderService.getOrderByOrderNoAndOrderStatus(order.getOrderNo(),OrderEnum.WAIT_CONFIRM.getValue());
        if(vOrder == null){
            return ResultInfo.fail(VedengErrorCode.ORDER_STATUS_ERR);
        }
        if(!vOrder.getAccountId().equals(getAccountId())){
            return ResultInfo.fail(VedengErrorCode.NO_POWER);
        }

        order.setAccountId(getAccountId());
        order.setOrderStatus(OrderEnum.CONFIRM.getValue());
        boolean result = orderService.updateTOpOrder(order);
        if(!result){
            return ResultInfo.fail(VedengErrorCode.CONFIRM_ORDER_ERR);
        }

        try {

            JSONObject httpResult = ChenkOrder.httpErpConfirmOrder(erpUrl + ChenkOrder.updateUrl,order.getOrderNo());
            if(ObjectUtils.isEmpty(httpResult)){
                return ResultInfo.fail(VedengErrorCode.UPDATE_ERPORDER_ERR);
            }

        } catch (IOException e) {
            log.error("确认订单异常:"+e);
            return ResultInfo.fail(VedengErrorCode.UPDATE_ERPORDER_ERR);
        }
        return ResultInfo.success(true);
    }

    /**
     * 取消订单
     * @param order
     * @return
     */
    @PostMapping(value = "/cancelOrder")
    public ResultInfo cancelOrder(@RequestBody VOrder order){
        log.info("取消确认:"+JSON.toJSONString(order));
        if(StringUtil.isBlank(order.getOrderNo())){
            return ResultInfo.fail(VedengErrorCode.ORDERNO_NOTNULL);
        }

        List<Integer> orderStatusList = new ArrayList<>();
        orderStatusList.add(OrderEnum.EXAMINE.getValue());
        orderStatusList.add(OrderEnum.WAIT_CONFIRM.getValue());
        orderStatusList.add(OrderEnum.CONFIRM.getValue());
        int isOrderNo = orderService.countByOrderNoStatusList(getAccountId(),order.getOrderNo(),orderStatusList);
        if(isOrderNo == 0){
            return ResultInfo.fail(VedengErrorCode.ORDER_STATUS_ERR);
        }

        order.setAccountId(getAccountId());
        order.setOrderStatus(OrderEnum.CHANEL.getValue());
        boolean result = orderService.updateTOpOrder(order);
        if(!result){
            return ResultInfo.fail(VedengErrorCode.CANCEL_ORDER_ERR);
        }

        try {
            OrderData orderData = new OrderData();
            orderData.setOrderNo(order.getOrderNo());
            orderData.setOrderStatus(OrderEnum.PAY.getValue());
            orderData.setCloseComments(order.getCancelReason());
            JSONObject jsonObject = HttpsClient.httpPost(erpUrl + ChenkOrder.updateUrl, JSONObject.fromObject(orderData));
            if(ObjectUtils.isEmpty(jsonObject)){
                return ResultInfo.fail(VedengErrorCode.INFORM_ERP_ERR);
            }
        } catch (Exception e) {
            log.error("通知erp关闭订单失败"+e);
            return ResultInfo.fail(VedengErrorCode.INFORM_ERP_ERR);
        }
        return ResultInfo.success(true);
    }

    /**
     * 删除订单
     * @param order
     * @return
     */
    @PostMapping(value = "/deleteOrder")
    public ResultInfo deleteOrder(@RequestBody VOrder order){

        if(StringUtil.isBlank(order.getOrderNo())){
            return ResultInfo.fail(VedengErrorCode.ORDERNO_NOTNULL);
        }

        VOrder vOrder = orderService.getOrderByOrderNoAndOrderStatus(order.getOrderNo(),OrderEnum.CHANEL.getValue());
        if(vOrder == null){
            return ResultInfo.fail(VedengErrorCode.ORDER_STATUS_ERR);
        }
        if(!vOrder.getAccountId().equals(getAccountId())){
            return ResultInfo.fail(VedengErrorCode.NO_POWER);
        }
        boolean result = orderService.deleteOrder(getAccountId(),order.getOrderNo());
        if(!result){
            return ResultInfo.fail(VedengErrorCode.DELETE_ORDER_ERR);
        }
        return ResultInfo.success(true);
    }

    /**
     * 查询取消原因列表
     * @return
     */
    @PostMapping(value = "/cancelList")
    public ResultInfo cancelList(){
        return ResultInfo.success(orderService.cancelList());
    }

    /**
     * 查询订单详情
     * @param appOrderData
     * @return
     */
    @PostMapping(value = "/selectOrderDetail")
    public ResultInfo selectOrderDetail(@RequestBody AppOrderData appOrderData){

        if(StringUtil.isBlank(appOrderData.getOrderNo())){
            return ResultInfo.fail(VedengErrorCode.ORDERNO_NOTNULL);
        }

        VOrder order = orderService.getOrderByOrderNo(appOrderData.getOrderNo());
        if(order == null){
            return ResultInfo.fail(VedengErrorCode.NO_ORDER_DETAIL);
        }
        if(!order.getAccountId().equals(getAccountId())){
            return ResultInfo.fail(VedengErrorCode.AUTH_FAILED);
        }

        OrderDetailData result = null;
        try {
            boolean checkPriceAuth = checkPriceAuth();
            result = orderService.selectOrderDetail(getAccountId(),appOrderData.getOrderNo(),checkPriceAuth);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail(VedengErrorCode.ORDER_ERR);
        }
        if(result == null){
            return ResultInfo.fail(VedengErrorCode.ORDER_ERR);
        }
        return ResultInfo.success(result);
    }

    @RequestMapping(value = "/make")
    public ResultInfo make(@RequestBody AppOrderData appOrderData){

        if(StringUtil.isBlank(appOrderData.getOrderNo())){
            return ResultInfo.fail(VedengErrorCode.ORDERNO_NOTNULL);
        }

        Map<String,Object> result = new HashMap<>();
        VOrder order = orderService.getOrderByOrderNo(appOrderData.getOrderNo());
        if(order == null){
            return ResultInfo.fail(VedengErrorCode.NO_ORDER_DETAIL);
        }
        if(!order.getAccountId().equals(getAccountId())){
            return ResultInfo.fail(VedengErrorCode.AUTH_FAILED);
        }

        result.put("money",order.getTotalMoney());
        result.put("orderNo",order.getOrderNo());
        return ResultInfo.success(result);

    }

}
