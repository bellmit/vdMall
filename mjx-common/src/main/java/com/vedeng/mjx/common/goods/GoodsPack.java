package com.vedeng.mjx.common.goods;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vedeng.goods.api.utils.MySerializerUtils;
import com.vedeng.mjx.common.util.StringUtil;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ auther:
 * @ date:  2019/8/19 16:22
 */
public class GoodsPack implements Serializable {

    //包装单位
    private String baseUnitName;
    //换算数量
    private Long changeNum;
    //最小单位
    private String minUnitName;
    //长
    @JsonSerialize(using = MySerializerUtils.class)
    private BigDecimal packageLength;
    //宽
    @JsonSerialize(using = MySerializerUtils.class)
    private BigDecimal packageWidth;
    //高
    @JsonSerialize(using = MySerializerUtils.class)
    private BigDecimal packageHeight;
    //毛重
    @JsonSerialize(using = MySerializerUtils.class)
    private BigDecimal grossWeight;
    //包装清单
    private String packingList;
    //最小起订量
    private Integer minOrderInt;


    private String goodsPackStr;

    public String getBaseUnitName() {
        return baseUnitName;
    }

    public void setBaseUnitName(String baseUnitName) {
        this.baseUnitName = baseUnitName;
    }

    public Long getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(Long changeNum) {
        this.changeNum = changeNum;
    }

    public String getMinUnitName() {
        return minUnitName;
    }

    public void setMinUnitName(String minUnitName) {
        this.minUnitName = minUnitName;
    }

    public BigDecimal getPackageLength() {
        return packageLength;
    }

    public void setPackageLength(BigDecimal packageLength) {
        this.packageLength = packageLength;
    }

    public BigDecimal getPackageWidth() {
        return packageWidth;
    }

    public void setPackageWidth(BigDecimal packageWidth) {
        this.packageWidth = packageWidth;
    }

    public BigDecimal getPackageHeight() {
        return packageHeight;
    }

    public void setPackageHeight(BigDecimal packageHeight) {
        this.packageHeight = packageHeight;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getPackingList() {
        return packingList;
    }

    public void setPackingList(String packingList) {
        this.packingList = packingList;
    }

    public Integer getMinOrderInt() {
        return minOrderInt;
    }

    public void setMinOrderInt(Integer minOrderInt) {
        this.minOrderInt = minOrderInt;
    }

    public String getGoodsPackStr() {
        return goodsPackStr;
    }

    public void setGoodsPackStr(Boolean showModel) {
        StringBuilder sb = new StringBuilder();
        if (showModel) {
            sb.append("包装单位：");
            sb.append(baseUnitName);
        }
        if (!showModel && changeNum != null && changeNum > 0L && StringUtil.isNotBlank(minUnitName)
                && StringUtil.isNotBlank(baseUnitName)) {
            sb.append("包装规格：");
            sb.append(changeNum).append(minUnitName).append("/").append(baseUnitName);
        }

        if (packageLength != null && packageWidth != null && packageHeight != null) {
            sb.append(" 包装体积：");
            if (packageLength.compareTo(new BigDecimal("0")) > 0) {
                sb.append("长度 ").append(packageLength).append(" cm");
            }
            if (packageWidth.compareTo(new BigDecimal("0")) > 0) {
                sb.append(" 宽度 ").append(packageWidth).append(" cm");
            }
            if (packageHeight.compareTo(new BigDecimal("0")) > 0) {
                sb.append(" 高度 ").append(packageHeight).append(" cm");
            }
            if (grossWeight != null && grossWeight.compareTo(new BigDecimal("0")) > 0) {
                sb.append(" 毛重").append(grossWeight).append(" kg");
            }
            if (showModel && StringUtil.isNotBlank(packingList)) {
                sb.append(" 包装清单：").append(packingList);
            }
        }
        if (!showModel && minOrderInt != null) {
            sb.append(" 最小起订量：").append(minOrderInt).append(minUnitName);
        }
        this.goodsPackStr = sb.toString();
    }
}
