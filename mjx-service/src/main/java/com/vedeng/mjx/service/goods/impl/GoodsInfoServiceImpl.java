package com.vedeng.mjx.service.goods.impl;

import com.vedeng.goods.api.dto.SkuDTO;
import com.vedeng.goods.api.dto.SkuNature;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.SkuNatureVo;
import com.vedeng.mjx.domain.TAttachment;
import com.vedeng.mjx.domain.ext.RequestParameter;
import com.vedeng.mjx.domain.ext.TOpGoodsExtExt;
import com.vedeng.mjx.mapper.ext.TOpGoodsExtExcuteMapper;
import com.vedeng.mjx.service.goods.GoodsInfoService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品明细实现业务层
 * @ auther:
 * @ date:  2019/6/21 15:38
 */
@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    //这是自定义的mapper
    @Resource
    private TOpGoodsExtExcuteMapper tOpGoodsExtExcuteMapper;


    Logger logger = LoggerFactory.getLogger(GoodsInfoServiceImpl.class);

    /**
     * 查询商品信息的明细
     * @param entity
     * @return
     */
    @Override
    public TOpGoodsExtExt queryGoodsInfo(TOpGoodsExtExt entity) {
        //从销售攻略表中获取数据
        Map<String,Object> extMap = tOpGoodsExtExcuteMapper.queryGoodsInfo(entity);

        TOpGoodsExtExt ext = gettOpGoodsExtExt(extMap);
        if (ext !=null){
            //根据销售攻略主键去查对应图片
            List<TAttachment> imgList = tOpGoodsExtExcuteMapper.queryPicList(ext);
            List<String> list = new ArrayList<>();
            if(imgList !=null && imgList.size()>0){
                for (TAttachment en : imgList){
                    if (en !=null){
                        String picUrl = "http://" + (StringUtils.isBlank(en.getDomain()) ? "file1.vedeng.com" : en.getDomain()) + en.getUri();

                        list.add(picUrl);
                    }
                }
            }
            ext.setPicList(list);
            //判断值是否存在
            String isFlag = "1";//判断是否显示攻略(0否，1是)
            if(StringUtils.isEmpty(MapUtils.getString(extMap,"opInstitution"))//适用科室
                    && StringUtils.isEmpty(MapUtils.getString(extMap,"opSalespoint"))//卖点
                    && StringUtils.isEmpty(MapUtils.getString(extMap,"opCompete"))//竞品
                    && StringUtils.isEmpty(MapUtils.getString(extMap,"opGoodsData"))//商品数据
                    && (ext.getPicList() ==null ||  ext.getPicList().size()==0)){
                isFlag = "0";
            }
            ext.setIsFlag(isFlag);
        }
        return ext;
    }
    /**
     * 查询商品信息的明细
     * @param entity
     * @return
     */
    @Override
    public SkuNature queryGoodsIsOnSale(SkuDTO entity) {
        //从销售攻略表中获取数据
        TOpGoodsExtExt extExt =new TOpGoodsExtExt();
        extExt.setSpuNo(entity.getSkuNo());
        Map<String,Object> extMap = tOpGoodsExtExcuteMapper.queryGoodsInfo(extExt);

        SkuNature skuNature=new SkuNature();
        skuNature.setSkuNo(entity.getSkuNo());
        skuNature.setIsOnSale(3);
        if(extMap!=null){
            skuNature.setIsOnSale((Integer) extMap.get("isOnSale"));
        }


        return skuNature;
    }

    private TOpGoodsExtExt gettOpGoodsExtExt(Map<String, Object> extMap) {
        if(extMap!=null && !extMap.isEmpty()){
            TOpGoodsExtExt ext = new TOpGoodsExtExt();
            ext.setOpCompete(MapUtils.getString(extMap,"opCompete"));
            ext.setSpuNo(MapUtils.getString(extMap,"spuNo"));
            ext.setSpuTitle(MapUtils.getString(extMap,"spuTitle"));
            ext.setOpInstitution(MapUtils.getString(extMap,"opInstitution"));
            ext.setOpSalespoint(MapUtils.getString(extMap,"opSalespoint"));
            ext.setOpGoodsData(MapUtils.getString(extMap,"opGoodsData"));
            ext.setGoodsId(MapUtils.getLong(extMap,"goodsId"));
            ext.setIsOnSale(MapUtils.getInteger(extMap,"isOnSale"));
            ext.setIsOpShows(MapUtils.getBoolean(extMap,"isOpShows")==true?"1":"0");
            return ext;
        }
        return null;

    }


    /***
     * 查新商品是否热销还是新品 还是精选
     * @param skuNo
     * @return 0:不是 1:是
     */
    @Override
    public TOpGoodsExtExt queryGoodsHotDataNew(String skuNo) throws Exception{
        if (StringUtil.isBlank(skuNo)){
            return null;
        }
        return tOpGoodsExtExcuteMapper.queryGoodsHotDataNew(skuNo);
    }
    @Override
    public SkuNatureVo querySkuNatureInfo(Map param){
        param.put("platFormId", 1);
        param.put("delFlag", 0);
        SkuNatureVo skuNatureVo = null;
        try {
            List<SkuNatureVo> skuNatureVos = tOpGoodsExtExcuteMapper.querySkuNatureInfo(param);
            if (!CollectionUtils.isEmpty(skuNatureVos)) {
                skuNatureVo = skuNatureVos.get(0);
            }
        } catch (Exception e) {
            logger.error("querySkuNatureInfo error", e);
        }
        return skuNatureVo;
    }

    @Override
    public List<TAttachment> getOpGoodsAttachmentList(TAttachment attachment){
        return tOpGoodsExtExcuteMapper.opGoodsAttachmentList(attachment);
    }

//    @Override
//    public List<FloorVo> getFloorVoList() {
//        FloorVo floorVo1 = new FloorVo();
//        floorVo1.setCategoryOneId(10001);
//        floorVo1.setCategoryOneName("临床检验");
//        FloorVo floorVo2 = new FloorVo();
//        floorVo2.setCategoryOneId(10002);
//        floorVo2.setCategoryOneName("医学影像");
//        FloorVo floorVo3 = new FloorVo();
//        floorVo3.setCategoryOneId(10003);
//        floorVo3.setCategoryOneName("呼吸/麻醉/急救");
//        FloorVo floorVo4 = new FloorVo();
//        floorVo4.setCategoryOneId(10004);
//        floorVo4.setCategoryOneName("诊察/监护器械");
//        FloorVo floorVo5 = new FloorVo();
//        floorVo5.setCategoryOneId(10005);
//        floorVo5.setCategoryOneName("消毒灭菌");
//        FloorVo floorVo6 = new FloorVo();
//        floorVo6.setCategoryOneId(10006);
//        floorVo6.setCategoryOneName("眼科/口腔/妇产科");
//        FloorVo floorVo7 = new FloorVo();
//        floorVo7.setCategoryOneId(10007);
//        floorVo7.setCategoryOneName("中医/康复/理疗");
//        FloorVo floorVo8 = new FloorVo();
//        floorVo8.setCategoryOneId(10845);
//        floorVo8.setCategoryOneName("活血化瘀");
//        List<FloorVo> list= new ArrayList<>();
//        list.add(floorVo1);
//        list.add(floorVo2);
//        list.add(floorVo3);
//        list.add(floorVo4);
//        list.add(floorVo5);
//        list.add(floorVo6);
//        list.add(floorVo7);
//        list.add(floorVo8);
//        return list;
//    }

    @Override
    public List<SkuVO> queryGoodsList(RequestParameter paramMap) {
        return null;
    }
}
