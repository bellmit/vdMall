package com.vedeng.mjx.service.goods;

import com.vedeng.goods.api.dto.SkuDTO;
import com.vedeng.goods.api.dto.SkuNature;
import com.vedeng.goods.api.vo.SkuVO;
import com.vedeng.mjx.domain.SkuNatureVo;
import com.vedeng.mjx.domain.TAttachment;
import com.vedeng.mjx.domain.ext.RequestParameter;
import com.vedeng.mjx.domain.ext.TOpGoodsExtExt;

import java.util.List;
import java.util.Map;

/**
 * @ auther: Jax
 * @ date:  2019/6/21 15:37
 */

public interface GoodsInfoService {
    TOpGoodsExtExt queryGoodsInfo(TOpGoodsExtExt entity);

    List<SkuVO> queryGoodsList(RequestParameter paramMap);

    SkuNature queryGoodsIsOnSale(SkuDTO entity);

    TOpGoodsExtExt queryGoodsHotDataNew(String spuNo) throws Exception;

    SkuNatureVo querySkuNatureInfo(Map param);

    List<TAttachment> getOpGoodsAttachmentList(TAttachment attachment);

}
