package com.vedeng.mjx.web.controller.app.goods;

import com.vedeng.goods.api.RequestParameter;
import com.vedeng.goods.api.dto.DepartmentDTO;
import com.vedeng.goods.api.dto.SkuDTO;
import com.vedeng.goods.api.dto.SkuNature;
import com.vedeng.goods.api.exception.ShowErrorMsgException;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.goods.ComBineAttr;
import com.vedeng.mjx.common.goods.GoodsAttrCombine;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.SkuNatureVo;
import com.vedeng.mjx.domain.ext.TOpGoodsExtExt;
import com.vedeng.mjx.service.goods.GoodsInfoService;
import com.vedeng.mjx.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/*
 * class_name: GoodsDetailController
 * package: com.vedeng.mjx.web.controller.app
 * creat_user:Glenn
 * creat_date: 2019/7/22
 * creat_time: 14:36
 * describe: APP商品详情接口
 */
@RequestMapping("/app/goods")
@Api(value = "APP接口-商品详情", tags = {"APP接口-商品详情-商品详情"})
@RestController
public class GoodsDetailController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private GoodsInfoService goodsInfoService;

    /*** 
     * describe: 商品详情信息
     * creat_time: 2019/8/15 10:49
     * param: [param]
     * return: com.vedeng.mjx.common.ResultInfo
     */
    @PostMapping("/goodsDetail")
    public ResultInfo getGoodsDetail(@RequestBody RequestParameter param) {
        /**
         * 1 参数校验
         * 2 查ERP中是否存在该sku
         * 3 erp中不存在 返回errorCode 存在再查运营平台
         * 4 运营平台不存在或不存在 都查询响应信息
         * 5 erp中存在 运营平台存在 返回所有信息
         * 6 erp中存在 运营平台不存在 返回缺失运营信息
         */
        //校验参数skuNo
        if (StringUtil.isBlank(param.getSkuNo())) {
            return ResultInfo.fail(VedengErrorCode.SKU_NO_NOT_EXISTS);
        }

        SkuVO skuVo = null;
        try {
            skuVo = goodsServiceFeignApi.getSkuDetailBySkuNo(param);
        } catch (ShowErrorMsgException e) {
            logger.error("GoodsServiceFeignApi | getSkuDetailBySkuNo ShowErrorMsgException is {}", e.getMessage());
            return ResultInfo.fail("100003", e.getMessage());
        } catch (Exception e) {
            logger.error("GoodsServiceFeignApi | getSkuDetailBySkuNo Exception", e);
            return ResultInfo.fail(VedengErrorCode.UNKNOWN_EXCEPTION);
        }

        //去查运营平台是否存在此商品
        //封装参数
        Map paramMap = new HashMap<String, Object>();
        paramMap.put("skuNo", param.getSkuNo());
        SkuNatureVo skuNatureVo = goodsInfoService.querySkuNatureInfo(paramMap);

        Boolean existsInOp = true;
        if (skuNatureVo == null || skuNatureVo.getPlatFormSkuId() == null) {
            existsInOp = false;
        }

        //封装返回数据的共有信息
        Map data = new HashMap();
        //封装图文详情
        packageDescription(skuVo);
        data.put("goodsEntityInfo", skuVo);

        //封装包装 存储 售后
        GoodsAttrCombine goodsAttrCombine = packageGoodsPackEtc(skuVo.getSkuInfo(), skuVo.getSpuInfo().isShowModel());
        if (goodsAttrCombine != null) {
            data.put("goodsPackEtcCombine", goodsAttrCombine);
        }
        //封装商品参数
        ComBineAttr paramKeyAndValues = packageGoodsAttr(skuVo.getSkuInfo());
        if (paramKeyAndValues != null) {
            data.put("paramKeyAndValues", paramKeyAndValues);
        }

        //获取规格/型号 以及其他属性的集合
        SkuDTO skuDto = new SkuDTO();
        skuDto.setSkuNo(param.getSkuNo());
        List<SkuDTO> skuDTOS;
        try {
            skuDTOS = goodsServiceFeignApi.querySkudtoListBySkuNo(skuDto);
        } catch (Exception e) {
            logger.error("querySkudtoListBySkuNo error is " + e.getMessage(), e);
            return ResultInfo.fail(VedengErrorCode.SKU_ATTR_GOODSAPI_ERROR);
        }

        if (!CollectionUtils.isEmpty(skuDTOS)) {
            //copy一份skuDTOS(运营平台sku集合)
            List<SkuDTO> copySkuDTOS = new ArrayList<>();
            //排序
            //1.利用Set<Map> 去重
            Set<Map<String, Object>> modelSpecShows = new HashSet<>();

            //value为当前选择规格下的属性list
            List<SkuDTO> curModelSpecSkuList = new ArrayList<>();
            for (SkuDTO skuDTO : skuDTOS) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("modelSpecShow", skuDTO.getModelSpecShow());
                SkuNature nature = goodsInfoService.queryGoodsIsOnSale(skuDTO);
                if (nature.getIsOnSale() == 3) {
                    //未在运营平台 跳出
                    continue;
                } else if (nature.getIsOnSale() == 0) {
                    //存在于运营平台且未上架
                    skuDTO.getSkuAttrCombin().setExsitFlag(0);
                } else {
                    //存在于运营平台且已上架
                    skuDTO.getSkuAttrCombin().setExsitFlag(1);
                }

                //运营平台的sku集合
                copySkuDTOS.add(skuDTO);
                if (StringUtil.isBlank(skuDTO.getSkuAttrCombin().getBaseAttributeValues())) {
                    //属性全为空(属性要么全为空 要么全都有属性)所有skuDTO都只会进这个if或者下面else
                    obj.put("skuNo", skuDTO.getSkuNo());

                }
                modelSpecShows.add(obj);
            }

            //对每个去重后的规格/型号 增加标志位:是否应该置灰:该规格/型号下有上架的sku就不置灰
            for (Map<String, Object> specShow : modelSpecShows) {
                List innerList = new ArrayList<HashMap<String, String>>();
                Integer allOffFlag = 1;
                for (SkuDTO skuDTO : copySkuDTOS) {
                    if (skuDTO.getModelSpecShow().equals(specShow.get("modelSpecShow"))) {

                        Map inner = new HashMap<String, String>();
                        inner.put("skuNo", skuDTO.getSkuNo());
                        inner.put("attributeValues", skuDTO.getSkuAttrCombin().getBaseAttributeValues());
                        inner.put("onSaleFlag", skuDTO.getSkuAttrCombin().getExsitFlag());
                        //属性全为空(属性要么全为空 要么全都有属性) 所有skuDTO都只会进这个if或者下面else
                        if (skuDTO.getSkuAttrCombin().getExsitFlag() == 1) {
                            //判断上架
                            //存在于运营平台且已上架
                            allOffFlag = 0;
                        }
                        //属性为空
                        if (!specShow.containsKey("skuNo")) {
                            innerList.add(inner);
                        }
                    }
                }
                specShow.put("allOffFlag", allOffFlag);
                specShow.put("attrList", innerList);
            }

            //排序
            curModelSpecSkuList.sort(Comparator.comparing(SkuDTO::sortBaseAttributeValues));
            data.put("msAndAttrList", modelSpecShows);
        }

        if (existsInOp) {
            //运营平台存在
            //封装运营信息
            packSkuNatureInfo(skuNatureVo, data);
            //判断效验商品销售攻略中字段内容是否都为空
            TOpGoodsExtExt toSetEntity = new TOpGoodsExtExt();
            toSetEntity.setSpuNo(param.getSkuNo());
            TOpGoodsExtExt resEntity = goodsInfoService.queryGoodsInfo(toSetEntity);
            if (resEntity != null) {
                //此判断是：攻略中至少有一个数据
                if ("1".equals(resEntity.getIsFlag())) {
                    data.put("opGoods", 1);
                }
                if (skuVo.getDepartmentList() != null && skuVo.getDepartmentList().size() > 0 && "1".equals(resEntity.getIsOpShows())) {
                    //医疗部门村子且是显示状态
                    data.put("opGoods", 1);
                }
            }
        } else {
            //运营平台不存在
            //也会展示部分信息(ERP后台添加商品到订单里 点击前台订单列表里面商品进入详情页)
            data.put("isOnSale", CommonConstant.TWO);
            List<Map<String, Object>> msAndAttrList = new ArrayList<>();
            List<Map<String, Object>> attrList = new ArrayList<>();

            Map<String, Object> attrListObj = new HashMap<>();
            if(skuVo.getSkuInfo().getSkuAttrCombin()!=null &&StringUtil.isNotBlank(skuVo.getSkuInfo().getSkuAttrCombin().getBaseAttributeValues())){
                attrListObj.put("attributeValues",skuVo.getSkuInfo().getSkuAttrCombin().getBaseAttributeValues());
                attrListObj.put("skuNo",skuVo.getSkuInfo().getSkuNo());
                attrListObj.put("onSaleFlag",CommonConstant.ONE);
                attrList.add(attrListObj);
            }
            Map<String, Object> msAndAttrListObj = new HashMap<>();
            msAndAttrListObj.put("modelSpecShow", skuVo.getSkuInfo().getModelSpecShow());
            msAndAttrListObj.put("skuNo", skuVo.getSkuInfo().getSkuNo());
            msAndAttrListObj.put("allOffFlag", CommonConstant.ZEOR);
            msAndAttrListObj.put("attrList",attrList);
            msAndAttrList.add(msAndAttrListObj);
            data.put("msAndAttrList", msAndAttrList);
        }

        //权限信息
        data.put("isPriceAuth", checkPriceAuth() ? CommonConstant.ONE : CommonConstant.ZEOR);


        return ResultInfo.success(data);
    }


    @PostMapping("/salesStrategy")
    public ResultInfo queryGoodsInfo(@RequestBody RequestParameter parameter) {

        TOpGoodsExtExt toSetEntity = new TOpGoodsExtExt();
        toSetEntity.setSpuNo(parameter.getSkuNo());
        //查询详情业务
        TOpGoodsExtExt resEntity = null;
        try {
            resEntity = goodsInfoService.queryGoodsInfo(toSetEntity);
        } catch (Exception e) {
            logger.error("queryGoodsInfo error", e);
        }
        Map data = new HashMap();
        if (resEntity != null) {
            data.put("goodsEntity", resEntity);
        }

        //调用服务
        SkuVO skuVo = null;
        try {
            skuVo = goodsServiceFeignApi.getSkuDetailBySkuNo(parameter);
        } catch (Exception e) {
            logger.error("GoodsServiceFeignApi | getSkuDetailBySkuNo：error", e);
        }
        if (skuVo != null && skuVo.getDepartmentList() != null && !CollectionUtils.isEmpty(skuVo.getDepartmentList())) {
            data.put("departMent", skuVo.getDepartmentList());
        }
        return ResultInfo.success(data);
    }

    @GetMapping("/{skuNo}/sale-raiders.html")
    public ModelAndView querySaleRaiders(@PathVariable("skuNo") String skuNo) {
        ModelAndView view = new ModelAndView("app/goods/sale-raiders");
        TOpGoodsExtExt toSetEntity = new TOpGoodsExtExt();
        toSetEntity.setSpuNo(skuNo);
        //查询详情业务
        TOpGoodsExtExt resEntity = goodsInfoService.queryGoodsInfo(toSetEntity);

        resEntity.setOpSalespoint(StringUtils.replace(resEntity.getOpSalespoint(),"\n","<br>"));
        resEntity.setOpInstitution(StringUtils.replace(resEntity.getOpInstitution(),"\n","<br>"));
        view.addObject("goodsEntity",resEntity);
        //调用服务
        RequestParameter feignSku = new RequestParameter();
        feignSku.setSkuNo(skuNo);
        SkuVO skuVo = null;
        try {
            skuVo = goodsServiceFeignApi.getSkuDetailBySkuNo(feignSku);
        } catch (Exception e) {
            logger.error("getSkuDetailBySkuNo error", e);

        }
        if(skuVo!=null){
            List<DepartmentDTO> departmentList = skuVo.getDepartmentList();
            if(!CollectionUtils.isEmpty(departmentList)){
                view.addObject("department",departmentList );
            }
        }

        return view;
    }

    /***
     * describe: 封装运营信息
     * creat_time: 2019/8/15 19:58
     * param: [skuNatureVo, data]
     * return: void
     */
    private void packSkuNatureInfo(SkuNatureVo skuNatureVo, Map data) {
        data.put("subTitle", skuNatureVo.getSubTitle());
        data.put("isNew", skuNatureVo.getIsNew());
        data.put("isHot", skuNatureVo.getIsHot());
        data.put("isChosen", skuNatureVo.getIsChosen());
        data.put("isSeven", skuNatureVo.getIsSeven());
        data.put("isOnSale", skuNatureVo.getIsOnSale());
        data.put("opGoods", 0);
    }


}