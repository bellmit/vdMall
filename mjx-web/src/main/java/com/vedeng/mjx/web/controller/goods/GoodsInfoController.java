
package com.vedeng.mjx.web.controller.goods;

import com.vedeng.goods.api.RequestParameter;
import com.vedeng.goods.api.dto.SkuDTO;
import com.vedeng.goods.api.dto.SkuNature;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.exception.VedengException;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.ext.TOpGoodsExtExt;
import com.vedeng.mjx.service.goods.GoodsInfoService;
import com.vedeng.mjx.service.goodsCar.GoodsCarService;
import com.vedeng.mjx.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ auther: Jax
 * @ date 2019/6/21 14:43
 * 商品详情Controller层（03_01分支）
 */
@RequestMapping("/goods")
@Api(value = "商品详情controller", tags = {"商品详情控制层"})
@RestController
public class GoodsInfoController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Resource
    private GoodsCarService goodsCarService;

    /**
     * 查询商品销售攻略详情
     *
     * @ param
     */
    @ApiOperation(value = "查询商品销售攻略详情", notes = "商品销售攻略详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "goodsId", value = "商品主键", required = true)
    })
    @GetMapping("/{skuNo}/sale-raiders.html")
    public ModelAndView queryGoodsGVInfo(@PathVariable("skuNo") String skuNo) {

        //判断登录 没登录跳转登录页
        if(getAccountId()==null){
            //没登录 重定向到登录页
            String redirectUrl="redirect:"+contextUrl+"/dispatch.html#/login?backurl="+contextUrl+"/goods/"+skuNo+"/sale-raiders.html";

            ModelAndView mv = new ModelAndView(redirectUrl);
            return mv;
        }
        //没权限 转发到首页
        if(!checkPriceAuth()){
            String url = "redirect:" + contextUrl + "/index.html";
            ModelAndView mv = new ModelAndView(url);
            return mv;
        }
        ModelAndView view = new ModelAndView("goods/sale-raiders");
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
            log.error("服务接口getSkuDetailBySkuNo：发生异常", e);
        }
        view.addObject("departMent", skuVo);
        return view;
    }

    /**
     * 查询商品销售详情
     *
     * @ param
     */
    @ApiOperation(value = "查询商品销售详情", notes = "商品销售详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "goodsId", value = "商品主键", required = true)
    })
    @GetMapping("/{skuNo}/goods-detail.html")
    public ModelAndView queryGoodsInfo(@PathVariable("skuNo") String skuNo) {
        // 判俩skuNo不为空
        if(StringUtil.isBlank(skuNo)){
            throw new VedengException(VedengErrorCode.PARAM_NULL);
        }
        ModelAndView view = new ModelAndView("/goods/goods-detail");
        RequestParameter feignSku = new RequestParameter();
        try {
            view.addObject("loginUrl", "/login.html#/login?backurl=" + getContextUrl() + "goods/goods-detail.html?skuNo=" + skuNo);
            //1.已登录,加入贝登精选 是贝登精选会员 运营后台有此商品(和上下架状态有关) 且价格不为0 不为空
            view.addObject("checkPriceAuth", checkPriceAuth());
            //商品唯一标识
            feignSku.setSkuNo(skuNo);
            SkuVO skuVo = null;
            try {
                skuVo = goodsServiceFeignApi.getSkuDetailBySkuNo(feignSku);
            } catch (Exception e) {
                log.error("GoodsServiceFeignApi | getSkuDetailBySkuNo error is {}", e);
            }

            Map<String,Object> result =new HashMap<>();
            SkuDTO param=new SkuDTO();
            param.setSkuNo(skuNo);

            List<SkuDTO> skuDTOS = null;
            try {
                skuDTOS = goodsServiceFeignApi.querySkudtoListBySkuNo(param);
            } catch (Exception e) {
                log.error("querySkudtoListBySkuNo error is "+e.getMessage(),e);
            }

            //String modelSpecShow=skuDTOS.stream().filter(p->p.getSkuNo().equals(skuNo)).collect(Collectors.toList()).get(0).getModelSpecShow();

            String modelSpecShow="";
            if(skuVo!=null && StringUtil.isNotBlank(skuVo.getSkuInfo().getModelSpecShow())){
                modelSpecShow=skuVo.getSkuInfo().getModelSpecShow();
            }
            if(!CollectionUtils.isEmpty(skuDTOS) &&StringUtil.isNotBlank(modelSpecShow)){
                //copy一份skuDTOS
                List<SkuDTO> copySkuDTOS = new ArrayList<>();
                //排序
                //1.过滤后的Set modelSpecShows
                Set<Map<String,String>> modelSpecShows=new HashSet<>();

                //value为当前选择规格下的属性list
                List<SkuDTO> curModelSpecSkuList=new ArrayList<>();
                for (SkuDTO skuDTO : skuDTOS) {
                    Map<String,String> map =new HashMap<>();
                    map.put("modelSpecShow",skuDTO.getModelSpecShow());
                    SkuNature nature = goodsInfoService.queryGoodsIsOnSale(skuDTO);
                    if(nature.getIsOnSale()==3 && !skuNo.equals(skuDTO.getSkuNo())){
                        //未在运营平台 跳出
                        continue;
                    }else if(nature.getIsOnSale()==0){
                        //存在于运营平台且未上架
                        skuDTO.getSkuAttrCombin().setExsitFlag(0);
                    }else{
                        //存在于运营平台且已上架
                        skuDTO.getSkuAttrCombin().setExsitFlag(1);
                    }

                    //运营平台的sku集合
                    copySkuDTOS.add(skuDTO);
                    if(StringUtil.isBlank(skuDTO.getSkuAttrCombin().getBaseAttributeValues())){
                        //属性全为空(属性要么全为空 要么全都有属性)
                        map.put("skuNo",skuDTO.getSkuNo());

                    }else{
                        //属性都不为空(属性要么全为空 要么全都有属性)
                        if(skuDTO.getModelSpecShow().equals(modelSpecShow) ){
                            //当前规格
                            curModelSpecSkuList.add(skuDTO);
                        }
                    }
                    modelSpecShows.add(map);
                }

                //对每个去重后的规格/型号 增加标志位:是否应该置灰:改规格/型号下有上架的sku就不置灰
                for (Map<String, String> specShow : modelSpecShows) {
                    String zhiHuiFlag="1";
                    for (SkuDTO skuDTO : copySkuDTOS) {
                            if(skuDTO.getModelSpecShow().equals(specShow.get("modelSpecShow"))){
                                //属性全为空(属性要么全为空 要么全都有属性)
                                if (skuDTO.getSkuAttrCombin().getExsitFlag() == 1) {
                                    zhiHuiFlag = "0";
                                }
                            }
                    }
                    specShow.put("zhiHuiFlag",zhiHuiFlag);
                }

//            skuDTOS.stream().filter(p->p.getModelSpecShow().equals(ms))
                //排序
                Collections.sort(curModelSpecSkuList,Comparator.comparing(SkuDTO ::sortBaseAttributeValues));
                result.put("modelSpecShowSet",modelSpecShows);
                result.put("curModelSpecSkuList",curModelSpecSkuList);
            }
            view.addObject("skuAttrList",result);


            boolean isOnSale = false;
            if (skuVo != null) {
                if (skuVo.getSpuInfo() != null) {
                    //查询购物车
                    Map<String, Object> carCount = new HashMap<>();

                    //isCount 0：从商品详情页查购物车的数量，1：查购物车列表以及数量，2：查提交订单页面时的商品
                    Integer countCar=0;
                    if (getAccountId() != null) {
                        countCar = goodsCarService.getGoodCarCount(getAccountId());
                    }
                    carCount.put("totalCount",countCar);
                    view.addObject("carCount", carCount);
                    //去查新品还是热销
                    TOpGoodsExtExt tOpGoodsExt = goodsInfoService.queryGoodsHotDataNew(skuNo);
                    if (tOpGoodsExt != null) {

                        view.addObject("spuTitle", tOpGoodsExt.getSpuTitle());
                        view.addObject("isNew", tOpGoodsExt.getIsNew());
                        view.addObject("isHot", tOpGoodsExt.getIsHot());
                        view.addObject("isChosen", tOpGoodsExt.getIsChosen());
                        view.addObject("isSeven", tOpGoodsExt.getIsSeven());
                    }
                    //判断效验商品销售攻略中字段内容是否都为空
                    if (skuVo.getSkuNature()!=null && skuVo.getSkuNature().getIsOnSale()!=null) {
                        TOpGoodsExtExt toSetEntity = new TOpGoodsExtExt();
                        toSetEntity.setSpuNo(skuNo);
                        TOpGoodsExtExt resEntity = goodsInfoService.queryGoodsInfo(toSetEntity);
                        if (resEntity != null) {
                            //此判断是：攻略中至少有一个数据
                            if ("1".equals(resEntity.getIsFlag())) {
                                view.addObject("opGoods", true);
                            }
                            if (skuVo.getDepartmentList() != null && skuVo.getDepartmentList().size() > 0 && "1".equals(resEntity.getIsOpShows())) {
                                //医疗部门村子且是显示状态
                                view.addObject("opGoods", true);
                            }
                            if (resEntity.getIsOnSale() == 1) {
                                isOnSale = true;
                            }
                        }
                    }
                }
                view.addObject("goodsEntityInfo", skuVo);
            }
//            view.addObject("accountJx", accountJx);
            view.addObject("isOnSale", isOnSale);
        } catch (Exception e) {
            log.error("查询商品销售详情:goods-detail.html发生异常", e);
        }
        return view;
    }

    @ApiOperation(value = "查询与该商品相同规格/型号 的兄弟SKU", notes = "根据传入的sku来查询父级spu 再去查询父级下面所有相同规格/型号 的兄弟SKU 排序和规格/型号去重")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SkuDTO", name = "param", value = "SkuDTO实体类", required = true)
    })
    @PostMapping("/queryAttrsByModelSpec")
    public ResultInfo queryGoodsGVInfo(@RequestBody SkuDTO param) {
        //调用此接口 肯定是属性全不为空的情况
        // 判俩skuNo不为空 和ModelSpecShow 不为空
        if(StringUtil.isBlank(param.getSkuNo()) || StringUtil.isBlank(param.getModelSpecShow(""))){
            return ResultInfo.fail(VedengErrorCode.PARAM_NULL);
        }
        Map<String,Object> result =new HashMap<>();

        List<SkuDTO> skuDTOS = null;
        try {
            skuDTOS = goodsServiceFeignApi.querySkudtoListBySkuNo(param);
        } catch (Exception e) {
            log.error("querySkudtoListBySkuNo error is "+e.getMessage(),e);
        }

        if(!CollectionUtils.isEmpty(skuDTOS)){
            //获取当前sku所在规格

            //此行代码为默认进详情页需要查该页面sku所在规格名
            //调接口时 modelSpecShow 自动传入
//           String modelSpecShow=skuDTOS.stream().filter(p->p.getSkuNo().equals(param.getSkuNo())).collect(Collectors.toList()).get(0).getModelSpecShow();
            //排序
            //1.过滤后的Set modelSpecShows
            Set<String> modelSpecShows=new HashSet<>();

            //value为当前选择规格下的属性list
            List<SkuDTO> curModelSpecSkuList=new ArrayList<>();
            boolean allOnsaleFlag = true;
            for (SkuDTO skuDTO : skuDTOS) {
                modelSpecShows.add(skuDTO.getModelSpecShow());
                if(skuDTO.getModelSpecShow().equals(param.getModelSpecShow("")) ){
                    //判断上架
                    SkuNature nature = goodsInfoService.queryGoodsIsOnSale(skuDTO);

                    if(nature.getIsOnSale()==0){
                        //存在于运营平台且未上架
                        skuDTO.getSkuAttrCombin().setExsitFlag(0);
                    }else if(nature.getIsOnSale()==3){
                        //不存在于运营平台
                        continue;
                    }else{
                        //存在于运营平台且已上架
                        skuDTO.getSkuAttrCombin().setExsitFlag(1);
                        allOnsaleFlag = false;
                    }

                    curModelSpecSkuList.add(skuDTO);
                }
            }
//            skuDTOS.stream().filter(p->p.getModelSpecShow().equals(ms))
            //排序
            Collections.sort(curModelSpecSkuList,Comparator.comparing(SkuDTO ::sortBaseAttributeValues));
            result.put("allOnsaleFlag",allOnsaleFlag);
            result.put("modelSpecShowSet",modelSpecShows);
            result.put("curModelSpecSkuList",curModelSpecSkuList);
            return ResultInfo.success(result);
        }

        return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
    }

}

