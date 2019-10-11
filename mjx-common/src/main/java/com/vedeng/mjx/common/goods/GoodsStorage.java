package com.vedeng.mjx.common.goods;

import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.util.StringUtil;

import java.io.Serializable;

/**
 * @ auther:
 * @ date:  2019/8/19 16:23
 */
public class GoodsStorage implements Serializable {
    //存储条件一 1常温、2冷冻、3冷藏
    private Integer storageConditionOne;
    //存储条件二 1阴凉、2干燥、3避光、4防潮   逗号隔开
    private String storageConditionTwo;

    private String storageConditionShowName;
    //有效期
    private String effectiveDays;
    //产品有效期单位 1 天   2 月  3 年
    private Integer effectiveDayUnit;
    private String effectiveDayAndUnitShowName;

    //showStr
    private String goodsStorageStr;

    public String getEffectiveDayAndUnitShowName() {
        return effectiveDayAndUnitShowName;
    }

    public void setEffectiveDayAndUnitShowName(String effectiveDayAndUnitShowName) {
        this.effectiveDayAndUnitShowName = effectiveDayAndUnitShowName;
    }

    public String getStorageConditionShowName() {
        return storageConditionShowName;
    }

    public void setStorageConditionShowName(String storageConditionShowName) {
        this.storageConditionShowName = storageConditionShowName;
    }

    public Integer getStorageConditionOne() {
        return storageConditionOne;
    }

    public void setStorageConditionOne(Integer storageConditionOne) {
        this.storageConditionOne = storageConditionOne;
    }

    public String getStorageConditionTwo() {
        return storageConditionTwo;
    }

    public void setStorageConditionTwo(String storageConditionTwo) {
        this.storageConditionTwo = storageConditionTwo;
    }

    public String getEffectiveDays() {
        return effectiveDays;
    }

    public void setEffectiveDays(String effectiveDays) {
        this.effectiveDays = effectiveDays;
    }

    public Integer getEffectiveDayUnit() {
        return effectiveDayUnit;
    }

    public void setEffectiveDayUnit(Integer effectiveDayUnit) {
        this.effectiveDayUnit = effectiveDayUnit;
    }

    public String getGoodsStorageStr() {
        return goodsStorageStr;
    }

    public void setGoodsStorageStr(Boolean showModel) {
        StringBuilder sb =new StringBuilder();
        if(!showModel && (storageConditionOne!=null|| storageConditionTwo!=null || StringUtil.isNotBlank(effectiveDays))){
            if(storageConditionOne!=null || storageConditionTwo!=null){
                sb.append("存储条件：");
            }
            if(storageConditionOne!=null){

                if(CommonConstant.ONE.equals(storageConditionOne)){
                    sb.append("常温 ");
                }
                if(CommonConstant.TWO.equals(storageConditionOne)){
                    sb.append("冷冻 ");
                }
                if(CommonConstant.THREE.equals(storageConditionOne)){
                    sb.append("冷藏 ");
                }
            }

            if(StringUtil.isNotBlank(storageConditionTwo)){

                if(storageConditionTwo.indexOf("1")>-1){
                    sb.append("阴凉 ");
                }
                if(storageConditionTwo.indexOf("2")>-1){
                    sb.append("干燥 ");
                }
                if(storageConditionTwo.indexOf("3")>-1){
                    sb.append("避光 ");
                }
                if(storageConditionTwo.indexOf("3")>-1){
                    sb.append("防潮 ");
                }
            }

            if(StringUtil.isNotBlank(effectiveDays) && effectiveDayUnit!=null){
                sb.append("有效期：");
                sb.append(effectiveDays);
                if(CommonConstant.ONE.equals(effectiveDayUnit)){
                    sb.append("天");
                }
                if(CommonConstant.TWO.equals(effectiveDayUnit)){
                    sb.append("月");
                }
                if(CommonConstant.THREE.equals(effectiveDayUnit)){
                    sb.append("年");
                }
            }
        }

        this.goodsStorageStr = sb.toString();
    }
}
