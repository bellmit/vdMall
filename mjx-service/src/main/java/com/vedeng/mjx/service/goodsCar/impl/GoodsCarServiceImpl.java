package com.vedeng.mjx.service.goodsCar.impl;

import com.vedeng.goods.api.RequestParameter;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.exception.VedengException;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.VGoodsCarMx;
import com.vedeng.mjx.domain.ext.VGoodsCarExtMx;
import com.vedeng.mjx.mapper.ext.VGoodsCarMxExtMapper;
import com.vedeng.mjx.service.feign.goods.GoodsServiceFeignApi;
import com.vedeng.mjx.service.goodsCar.GoodsCarService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.vedeng.mjx.common.constant.CommonConstant.*;
import static com.vedeng.mjx.common.exception.VedengErrorCode.*;

/**
 * 购物车实现业务层
 *
 * @ auther:Jax
 * @ date:  2019/7 15:38
 */
@Service
public class GoodsCarServiceImpl implements GoodsCarService {
    @Resource
    private VGoodsCarMxExtMapper vGoodsCarMxExtMapper;
    @Resource
    private GoodsServiceFeignApi goodsServiceFeignApi;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /***
     * 查询购物车列表
     * @param
     * @param paramEntity isCount：0：从商品详情页查购物车的数量，1：查购物车列表以及数量，2：查提交订单页面时的商品
     *                  accountId：客户ID
     */
    @Override
    public Map<String, Object> queryGoodsCarList(VGoodsCarExtMx paramEntity) throws VedengException {
        Map<String, Object> resultMap = null;
        if (paramEntity != null) {
            resultMap = new HashMap<String, Object>();
            int isCheckedCount = 0;
            //判断是不是第一次进来0否，1是
            Map<String, String> map = checkFirst(paramEntity);
            if (map == null || StringUtil.isBlank(MapUtils.getString(map, "carId"))) {
                return resultMap;
            }
            //查询购物车明细中的所有数据
            paramEntity.setCarId(MapUtils.getInteger(map, "carId"));
            List<VGoodsCarExtMx> goodsList = vGoodsCarMxExtMapper.queryGoodsCarList(paramEntity);
            if (goodsList != null && goodsList.size() > 0) {
                if ("0".equals(paramEntity.getIsCount())) {
                    //isCount:0从商品详情页查购物车的数量，1查购物车列表
                    resultMap.put("totalCount", goodsList.size());//购物车中商品数量()
                    return resultMap;
                }
                List<Map<String, Object>> onSaleGoodsList = new ArrayList<Map<String, Object>>();//上架商品的集合
                List<Map<String, Object>> downSaleGoodsList = new ArrayList<Map<String, Object>>();//下架商品的集合
                String isMarketPrice = paramEntity.getIsMarketPrice();
                resultMap.put("isMarketPrice", isMarketPrice);
                boolean showTotalPrice = true;
                for (VGoodsCarExtMx goodsCar : goodsList) {
                    if (goodsCar != null) {
                        if ("1".equals(goodsCar.getIsDefault())) {
                            isCheckedCount += 1;//默认 则加一
                        }
                        Map<String, Object> goodsMap = new HashMap<>();
                        //根据goodsCar中的skuId去查数据（调商品服务）
                        RequestParameter feignSku = new RequestParameter();
                        feignSku.setSkuNo(goodsCar.getSkuNo());//把购物车中的商品唯一码set进实体
                        SkuVO skuEntity = null;
                        try {
                            skuEntity = goodsServiceFeignApi.getSkuDetailBySkuNo(feignSku);
                        } catch (Exception e) {
                            //服务接口getSkuDetailBySkuNo：发生异常 吃掉异常 防止页面异常
                            logger.error("goodsServiceFeignApi getSkuDetailBySkuNo error", e);
                        }
                        if (skuEntity != null) {

                            showTotalPrice = packageGoodsMap(onSaleGoodsList, downSaleGoodsList, goodsCar, goodsMap, skuEntity, paramEntity, showTotalPrice);
                        }
                    }
                }
                if ("2".equals(paramEntity.getIsCount())) {
                    //仅供wind的页面
                    String isClose = vGoodsCarMxExtMapper.queryIsCloseStatus(paramEntity);
                    if (StringUtil.isBlank(isClose)) {
                        isClose = "0";//默认打开
                    }
                    resultMap.put("isClose", isClose);//购物车上架商品集合
                }
                resultMap.put("onSaleGoodsList", onSaleGoodsList);//购物车上架商品集合
                resultMap.put("downSaleGoodsList", downSaleGoodsList);//购物车下架商品集合
                resultMap.put("isFirst", map.get("isFirst"));//是否第一次进入购物车(0否1是)
                resultMap.put("isCheckedCount", isCheckedCount);//购物车已选的商品数量
                resultMap.put("carId", paramEntity.getCarId());//购物车主键
                resultMap.put("totalCount", goodsList.size());//购物车中商品数量()
                resultMap.put("domainName", "http://mjx.vedeng.com/");//详情页的域名
                resultMap.put("showTotalPrice", showTotalPrice);
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> appGoodsCarList(VGoodsCarExtMx paramEntity) {
        Map<String, Object> resultMap = new HashMap();
        //商品集合
        List<Map<String, Object>> allGoodsList = new ArrayList();
        Integer checkedCategoryCount = ZEOR;
        Integer totalGoodsCount = ZEOR;
        //判断是不是第一次进来0否，1是
        Map<String, String> map = checkFirst(paramEntity);
        if (map == null || StringUtil.isBlank(MapUtils.getString(map, "carId"))) {
            //用户还从未在购物车中添加过商品
            //购物车商品集合
            resultMap.put("goodsList", allGoodsList);
            //是否要显示新手弹窗(0否1是)
            resultMap.put("isShowWin", ZEOR);
            return resultMap;
        }
        //查询购物车明细中的所有数据
        paramEntity.setCarId(MapUtils.getInteger(map, "carId"));
        List<VGoodsCarExtMx> goodsList;
        if (CommonConstant.STRING_ZERO.equals(paramEntity.getIsCommit())) {
            goodsList = vGoodsCarMxExtMapper.queryGoodsCarMxList(paramEntity);
            //主入口购物车列表
        } else {
            //提交订单后的列表
            goodsList = vGoodsCarMxExtMapper.queryGoodsCarMxListCom(paramEntity);
        }
        //先按isOnSale降序 再按添加时间降序
        goodsList.sort(Comparator.comparingInt(VGoodsCarExtMx::getIsOnSale).thenComparingLong(VGoodsCarExtMx::getAddLongTime).reversed());

        BigDecimal totalPrice = new BigDecimal(CommonConstant.STRING_ZERO);

        for (VGoodsCarExtMx goodsCar : goodsList) {
            //根据goodsCar中的skuId去查数据（调商品服务）
            RequestParameter feignSku = new RequestParameter();
            feignSku.setSkuNo(goodsCar.getSkuNo());
            SkuVO skuEntity = null;
            try {
                skuEntity = goodsServiceFeignApi.getSkuDetailBySkuNo(feignSku);

            } catch (Exception e) {
                //服务接口getSkuDetailBySkuNo：发生异常 吃掉异常 防止页面异常
                logger.error("goodsServiceFeignApi getSkuDetailBySkuNo error", e);
            }
            if (skuEntity != null) {
                Map<String, Object> goodsMap = new HashMap<>();
                //名称
                String skuName = skuEntity.getSkuInfo() == null ? "" : skuEntity.getSkuInfo().getSkuName();
                //订货号
                String skuNo = goodsCar.getSkuNo();
                //数量
                int goodsCount = goodsCar.getCount();
                //图片
                String picUrl = skuEntity.getFirstPic() == null ? "" : skuEntity.getFirstPic().getUrl360();
                //显示规格或者型号的值
                String specs = skuEntity.getSkuInfo() == null ? "" : skuEntity.getSkuInfo().getModelSpecShow();
                //规格或者型号
                String specsShowName = skuEntity.getSkuInfo() == null ? "" : skuEntity.getSkuInfo().getModelSpecShowChineseName();
                goodsMap.put("skuName", skuName);
                goodsMap.put("skuNo", skuNo);
                goodsMap.put("picUrl", picUrl);
                goodsMap.put("specs", specs);
                goodsMap.put("specsShowName", specsShowName);
                goodsMap.put("carMxId", goodsCar.getCarMxId());
                goodsMap.put("inOnSale", goodsCar.getIsOnSale());
                if (STRING_ONE.equals(goodsCar.getIsDefault())) {
                    //默认选中 则加一
                    totalGoodsCount += goodsCount;
                    goodsMap.put("status", Integer.valueOf(goodsCar.getIsDefault()));
                    checkedCategoryCount++;
                }
                goodsMap.put("goodsCount", goodsCount);
                BigDecimal marketPrice = skuEntity.getSkuNature().getBdMarketPrice();
                if (marketPrice == null) {
                    marketPrice = new BigDecimal(ZEOR);
                }
                goodsMap.put("marketPrice", marketPrice);
                totalPrice = totalPrice.add(marketPrice.multiply(new BigDecimal(goodsCount)));
                allGoodsList.add(goodsMap);

            } else {
                logger.info("getSkuDetailBySkuNo error skuEntity is null");
            }
        }
        //总价
        resultMap.put("totalPrice", totalPrice);
        //购物车商品集合
        resultMap.put("goodsList", allGoodsList);
        //是否显示新手弹窗
        Integer isShowWin = STRING_ONE.equals(MapUtils.getString(map, "isFirst")) ? ZEOR : ONE;
        resultMap.put("isShowWin", isShowWin);
        //购物车已选的商品种类
        resultMap.put("checkedCategoryCount", checkedCategoryCount);
        //是否有价格权限
        resultMap.put("hasAuthSeeMPrice", paramEntity.getIsMarketPrice());
        //已选中商品的全部数量
        resultMap.put("checkedTotalCount", totalGoodsCount);

        return resultMap;
    }


    /**
     * 修改复选框
     *
     * @param paramEntity：skuNos 被选择的skuNo商品，多个又逗号拼接
     *                           :status 0否1是
     */
    @Override
    @Transactional
    public int updateGoodsDefault(VGoodsCarExtMx paramEntity) throws VedengException {
        int p = 0;
        if (paramEntity != null) {
            if (StringUtil.isNotBlank(paramEntity.getSkuNos())) {
                //根据客户去查对应的购物车ID
                Map<String, String> map = vGoodsCarMxExtMapper.queryFirst(paramEntity);
                if (map != null && StringUtil.isNotBlank(MapUtils.getString(map, "carId"))) {
                    String[] str = paramEntity.getSkuNos().split(",");
                    List<String> skuNoList = Arrays.asList(str);
                    paramEntity.setSkuNoList(skuNoList);
                    paramEntity.setCarId(Integer.valueOf(MapUtils.getString(map, "carId")));
                    p = vGoodsCarMxExtMapper.updateGoodsDefault(paramEntity);
                }
            }
        }
        return p;
    }

    /**
     * 修改复选框
     *
     * @param paramEntity：carMxId 明细Id  status : 0否1是
     */
    @Override
    @Transactional
    public void updateGoodsIsChoosed(VGoodsCarExtMx paramEntity) {
        if (ZEOR.equals(paramEntity.getStatus()) || ONE.equals(paramEntity.getStatus())) {
            //单选
            vGoodsCarMxExtMapper.updateGoodsIsChoosed(paramEntity);
        } else {
            if (CommonConstant.TWO.equals(paramEntity.getStatus())) {
                paramEntity.setStatus(ZEOR);
            } else {
                paramEntity.setStatus(ONE);
            }
            //根据客户去查对应的购物车ID
            Map<String, String> map = vGoodsCarMxExtMapper.queryFirst(paramEntity);
            paramEntity.setCarId(Integer.valueOf(MapUtils.getString(map, "carId")));
            //多选
            vGoodsCarMxExtMapper.updateGoodsIsChoosedAll(paramEntity);
        }
    }

    /***
     * 删除购物车
     * @param paramEntity
     * @return
     */
    @Override
    @Transactional
    public int deleteGoodsCar(VGoodsCarExtMx paramEntity) throws VedengException {
        int p = 0;
        if (paramEntity != null && StringUtil.isNotBlank(paramEntity.getSkuNos())) {
            if (paramEntity.getCarId() == null) {
                throw new VedengException(FAIL_GOODSCAR_CARID);//购物车唯一码不能为空
            }
            String[] str = paramEntity.getSkuNos().split(",");
            List<String> skuNoList = Arrays.asList(str);
            paramEntity.setSkuNoList(skuNoList);
            p = vGoodsCarMxExtMapper.deleteGoodsCar(paramEntity);
        }
        return p;
    }

    /***
     * 删除购物车中商品
     * @param
     * @return
     */
    @Override
    @Transactional
    public void deleteGoodsCarMxList(VGoodsCarExtMx paramEntity) {
        vGoodsCarMxExtMapper.deleteGoodsCarMxs(paramEntity);
    }

    /**
     * 修改数量
     *
     * @param paramEntity
     * @return
     */
    @Override
    @Transactional
    public int updateGoodsCount(VGoodsCarExtMx paramEntity) throws VedengException {
        int p = 0;
        if (paramEntity != null && StringUtil.isNotBlank(paramEntity.getSkuNo())) {
            if (paramEntity.getCarId() == null) {
                throw new VedengException(FAIL_GOODSCAR_CARID);//购物车唯一码不能为空
            }
            p = vGoodsCarMxExtMapper.updateGoodsCount(paramEntity);
        }
        return p;
    }

    /**
     * 修改数量
     *
     * @param paramEntity
     * @return
     */
    @Override
    public void updateGoodsMxCount(VGoodsCarExtMx paramEntity) {
        vGoodsCarMxExtMapper.updateGoodsMxCount(paramEntity);
    }

    /**
     * 购物车预提交订单
     *
     * @param paramEntity
     * @return
     */
    @Override
    @Transactional
    public int insertPrepareGoodsCar(VGoodsCarExtMx paramEntity) {
        int p = 0;
        if (paramEntity != null && StringUtil.isNotBlank(paramEntity.getSkuNos())) {
            if (paramEntity.getCarId() == null) {
                throw new VedengException(FAIL_GOODSCAR_CARID);//购物车唯一码不能为空
            }
            String[] str = paramEntity.getSkuNos().split(",");
            //判断有重复商品
            if (str.length != 1) {
                boolean repeatFlag = true;
                for (int i = 0; i < str.length; i++) {
                    for (int j = 0; j < str.length; j++) {
                        if (i != j && str[i].equals(str[j])) {
                            repeatFlag = false;
                        }
                    }
                }
                if (!repeatFlag) {
                    //购物车内商品重复
                    throw new VedengException(FAIL_GOODSCAR_GOODS_REPAEAT);
                }
            }

            //判断是否有下架商品
            boolean flag = true;
            RequestParameter param = new RequestParameter();
            for (String skuNo : str) {
                param.setSkuNo(skuNo);
                Integer onsale = vGoodsCarMxExtMapper.queryGoodsCarInfo(skuNo);
                if (onsale == 0) {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                throw new VedengException(FAIL_GOODSCAR_HAVEDOWNSALE_TWO);
            }

            //判断购物车中是否有异步操作导致状态变为已删除的商品
            boolean isDelFlag = true;
            String carMxIdsExt = paramEntity.getCarMxIdsExt();
            String[] ids = carMxIdsExt.split(",");
            for (String carMxId : ids) {
                String isDel = vGoodsCarMxExtMapper.queryIsDel(Integer.valueOf(carMxId));
                if (isDel.equals("1")) {
                    isDelFlag = false;
                    break;
                }
            }
            if (!isDelFlag) {
                throw new VedengException(FAIL_GOODSCAR_HAVE_ALREADY_DELETE);
            }

            List<String> skuNoList = Arrays.asList(str);
            paramEntity.setSkuNoList(skuNoList);
            //把原先的在IS_COMMIT：1全部改为0
            p = vGoodsCarMxExtMapper.updateIsCommitStatus(paramEntity);

            p = vGoodsCarMxExtMapper.insertPrepareGoodsCar(paramEntity);//修改购物车明细中的IS_COMMIT：是否提交（0未提交，1预提交，2已提交）
            if (p > 0) {
                vGoodsCarMxExtMapper.updateIsCloseStatus(paramEntity);//修改主表中的isClose
            }
        }
        return p;
    }

    /**
     * 购物车预提交
     *
     * @param paramEntity paramEntity[accountId] 用户id
     * @return
     */
    @Override
    public void prepareCommitGoodsCar(VGoodsCarExtMx paramEntity) {
        List<VGoodsCarExtMx> skuNoList = vGoodsCarMxExtMapper.queryPreCommitGoodsMxs(paramEntity);
        if (CollectionUtils.isEmpty(skuNoList)) {
            throw new VedengException(FAIL_GOODSCAR_NO_SELECT);
        }
        boolean delFlag = false;
        for (VGoodsCarExtMx vGoodsCarExtMx : skuNoList) {
            if (STRING_ONE.equals(vGoodsCarExtMx.getIsDel())) {
                delFlag = true;
                break;
            }
        }
        if (delFlag) {
            throw new VedengException(FAIL_GOODSCAR_HAVE_ALREADY_DELETE);
        }
        //判断是否有下架商品
        boolean downSaleFlag = false;
        for (VGoodsCarExtMx vGoodsCarExtMx : skuNoList) {
            Integer onsale = vGoodsCarMxExtMapper.queryGoodsCarInfo(vGoodsCarExtMx.getSkuNo());
            if (ZEOR.equals(onsale)) {
                downSaleFlag = true;
            }
        }
        if (downSaleFlag) {
            throw new VedengException(FAIL_GOODSCAR_HAVEDOWNSALE_TWO);
        }
    }

    /**
     * 加入购物车
     *
     * @param paramEntity
     * @return
     */
    @Override
    @Transactional
    public void addGoodsIntoCar(VGoodsCarExtMx paramEntity) {
        //判断是否有下架商品
        Integer onSale = vGoodsCarMxExtMapper.queryGoodsCarInfo(paramEntity.getSkuNo());
        if (onSale == null || onSale == 0) {
            throw new VedengException(FAIL_GOODSCAR_HAVEDOWNSALE_ONE);
        }
        //检查用户是否加入过购物车
        Map<String, String> map = vGoodsCarMxExtMapper.queryFirst(paramEntity);
        if (map == null) {
            //没有购物车
            //在购物车的主表中创建新的数据，并返回当前carId
            vGoodsCarMxExtMapper.insertUserCar(paramEntity);
        } else {
            //有关联的购物车
            paramEntity.setCarId(MapUtils.getInteger(map, "carId"));
        }
        //查看当前用户购物车明细中是否存在99种skuNo
        Integer count = vGoodsCarMxExtMapper.querySkuNoCount(paramEntity);
        if (count > GOODSCAR_CATEGORY_MAX_COUNT) {
            //该用户购物车内商品种类超过99种
            throw new VedengException(FAIL_GOODSCAR_COUNT);
        }
        //直接新增

        int affectRows = vGoodsCarMxExtMapper.addGoodsCarMxOnDupKeyUpdate(paramEntity);
        if (affectRows == ZEOR) {
            //没新增成功 说明有存在的数据 此时更新
            Map<String, Object> mapExist = vGoodsCarMxExtMapper.queryExisetSkuNo(paramEntity);
            int cruuentCount = MapUtils.getInteger(mapExist, "count");
            if (cruuentCount + paramEntity.getCount() > GOODSCAR_NUM_MAX_COUNT) {
                paramEntity.setCount(GOODSCAR_NUM_MAX_COUNT);
            }else{
                paramEntity.setCount(cruuentCount + paramEntity.getCount());
            }
            vGoodsCarMxExtMapper.updateGoodsCount(paramEntity);//修改数量
        } else {
            if (count.equals(GOODSCAR_CATEGORY_MAX_COUNT)) {
                //该用户购物车内商品种类等于99种加上这种>99 回滚
                throw new VedengException(FAIL_GOODSCAR_COUNT);
            }
        }
    }

    @Override
    public List<VGoodsCarMx> selectVGoodsCarMxList(List<String> skuList) {
        return vGoodsCarMxExtMapper.selectVGoodsCarMxList(skuList);
    }

    @Override
    @Transactional
    public void exitPreCommitGoodsCar(VGoodsCarExtMx paramEntity) {
        List<Integer> carMxIdList = paramEntity.getCarMxIdList();
        for (Integer carMxId : carMxIdList) {
            //先查是否有本类商品
            //有 增加数量 删除自己
            //否 更新状态
            VGoodsCarExtMx self = vGoodsCarMxExtMapper.queryByCarMxId(carMxId);

            VGoodsCarExtMx exists = vGoodsCarMxExtMapper.existsSkuNoInNoCommit(self.getSkuNo());
            if (exists != null) {
                //存在当前商品
                logger.info("exists skuNo [exists]carMxId is {} [self]carMxId is {}", exists.getCarMxId(), self.getCarMxId());
                int cruuentCount = exists.getCount();
                if (cruuentCount + self.getCount() > 9999) {
                    exists.setCount(9999);
                } else {
                    exists.setCount(cruuentCount + self.getCount());
                }
                //增加数量
                vGoodsCarMxExtMapper.updateGoodsMxCount(exists);
                //删除自己
                vGoodsCarMxExtMapper.softDeleteGoodsCarMx(self);
            } else {
                //更新购物车明细状态为0
                vGoodsCarMxExtMapper.exitPreCommitGoodsCar(paramEntity);
            }

        }

    }

    @Override
    public Integer getGoodCarCount(Integer userId) {
        List<VGoodsCarMx> carMxs = vGoodsCarMxExtMapper.getGoodCarCount(userId);
        Integer count = 0;
        for (VGoodsCarMx carMx : carMxs) {
            SkuVO skuEntity = null;
            try {
                RequestParameter req = new RequestParameter();
                req.setSkuNo(carMx.getSkuNo());
                skuEntity = goodsServiceFeignApi.getSkuDetailBySkuNo(req);

            } catch (Exception e) {
                //服务接口getSkuDetailBySkuNo：发生异常 吃掉异常 防止页面异常
                logger.error("goodsServiceFeignApi getSkuDetailBySkuNo error", e);
            }
            if (skuEntity != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int updateGoodsCarList(List<VGoodsCarMx> list) {
        return vGoodsCarMxExtMapper.updateGoodsCarList(list);
    }

    /***
     * 检查是否第一次进来
     * @param
     * @param paramEntity 实体
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, String> checkFirst(VGoodsCarExtMx paramEntity) {
        //查询是否进入过购物车列表（0否1是）
        Map<String, String> map = vGoodsCarMxExtMapper.queryFirst(paramEntity);
        if (map != null && "0".equals(MapUtils.getString(map, "isFirst"))) {
            //修改购物车主表状态为1, 说明已经进来过了
            int up = vGoodsCarMxExtMapper.checkFirst(paramEntity);
        }
        return map;
    }


    /***
     * describe: 封装bean
     * creat_time: 2019/7/23 15:33
     * param: [onSaleGoodsList, downSaleGoodsList, goodsCar, goodsMap, skuEntity]
     * [上架List,下架List,前台传入参数bean,返回goodsMap,SkuVO]
     * return: void
     */
    private boolean packageGoodsMap(List<Map<String, Object>> onSaleGoodsList, List<Map<String, Object>> downSaleGoodsList, VGoodsCarExtMx goodsCar, Map<String, Object> goodsMap, SkuVO skuEntity, VGoodsCarExtMx paramEntity, boolean showTotalPrice) {
        String skuName = skuEntity.getSkuInfo() == null ? "" : skuEntity.getSkuInfo().getSkuName();//名称
        String skuNo = goodsCar.getSkuNo();//订货号
        int goodsCount = goodsCar.getCount();//数量
        String picUrl = skuEntity.getFirstPic() == null ? "" : skuEntity.getFirstPic().getUrl360();//图片
        String specs = skuEntity.getSkuInfo() == null ? "" : skuEntity.getSkuInfo().getModelSpecShow();//显示规格或者型号的值
        String specsShowName = skuEntity.getSkuInfo() == null ? "" : skuEntity.getSkuInfo().getModelSpecShowChineseName();//规格或者型号
        //查询购物车商品详情(商品上下架,0否1是)
        Integer isOnSale = vGoodsCarMxExtMapper.queryGoodsCarInfo(skuNo);

        goodsMap.put("skuName", skuName);
        goodsMap.put("skuNo", skuNo);
        goodsMap.put("picUrl", picUrl);
        goodsMap.put("specs", specs);
        goodsMap.put("specsShowName", specsShowName);
        if (isOnSale != null && isOnSale == 1) { //上架
            //被授予权限的人，可以看价格
            if ("1".equals(paramEntity.getIsMarketPrice())) {
                if (skuEntity.getSkuInfo() != null) {
                    BigDecimal price = skuEntity.getSkuInfo().getJxMarketPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
                    goodsMap.put("price", price.toString());
                }
                if (skuEntity.getSkuInfo().getJxMarketPrice().compareTo(new BigDecimal("0.00")) == 0) {
                    showTotalPrice = false;
                }
            }
            goodsMap.put("carMxId", goodsCar.getCarMxId());
            goodsMap.put("goodsCount", goodsCount);
            goodsMap.put("status", Integer.valueOf(goodsCar.getIsDefault()));//是否默认（0否1是）
            goodsMap.put("salePrice", "");
            onSaleGoodsList.add(goodsMap);
        } else {
            goodsMap.put("status", 0);//是否默认（0否1是）
            downSaleGoodsList.add(goodsMap);
        }
        return showTotalPrice;
    }


}
