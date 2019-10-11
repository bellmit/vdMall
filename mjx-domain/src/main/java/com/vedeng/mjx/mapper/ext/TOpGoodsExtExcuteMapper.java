package com.vedeng.mjx.mapper.ext;

import com.vedeng.mjx.domain.SkuNatureVo;
import com.vedeng.mjx.domain.TAttachment;
import com.vedeng.mjx.domain.ext.TOpGoodsExtExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TOpGoodsExtExcuteMapper {
    Map<String,Object> queryGoodsInfo(TOpGoodsExtExt entity);

    TOpGoodsExtExt queryGoodsHotDataNew(String spuNo);
    /***
     * describe: 根据平台id skuNo 查询运营信息
     * creat_time: 2019/8/15 16:54
     * param: [param]
     * return: java.util.List<com.vedeng.mjx.domain.SkuNatureVo>
     */
    List<SkuNatureVo> querySkuNatureInfo(Map param);

    List<TAttachment> queryPicList(TOpGoodsExtExt ext);

    List<TAttachment> opGoodsAttachmentList(TAttachment attachment);
}
