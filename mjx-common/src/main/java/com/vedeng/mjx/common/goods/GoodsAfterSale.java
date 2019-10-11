package com.vedeng.mjx.common.goods;

import com.vedeng.mjx.common.util.StringUtil;

/**
 * @ auther:
 * @ date:  2019/8/19 16:23
 */
public class GoodsAfterSale {

    //1 包安装 2包培训 3 物流点自提 4送货上门，含卸货上楼 5送货上门，含卸货到楼下
    private String afterSaleContent;
    private String afterSaleContentShowName;

    //质保年限  年
    private String qaYears;
    private String qaYearsShowName;

    private String goodsAfterSaleStr;

    public String getAfterSaleContentShowName() {
        return afterSaleContentShowName;
    }

    public void setAfterSaleContentShowName(String afterSaleContentShowName) {
        this.afterSaleContentShowName = afterSaleContentShowName;
    }

    public String getQaYearsShowName() {
        return qaYearsShowName;
    }

    public void setQaYearsShowName(String qaYearsShowName) {
        this.qaYearsShowName = qaYearsShowName;
    }

    public String getAfterSaleContent() {
        return afterSaleContent;
    }

    public void setAfterSaleContent(String afterSaleContent) {
        this.afterSaleContent = afterSaleContent;
    }

    public String getQaYears() {
        return qaYears;
    }

    public void setQaYears(String qaYears) {
        this.qaYears = qaYears;
    }

    public String getGoodsAfterSaleStr() {
        return goodsAfterSaleStr;
    }

    public void setGoodsAfterSaleStr() {
        StringBuilder sb = new StringBuilder();
        if (StringUtil.isNotBlank(afterSaleContent)) {
                sb.append("售后内容：");
            if (afterSaleContent.indexOf("1") >= 0) {
                sb.append("包安装 ");
            }
            if (afterSaleContent.indexOf("2") >= 0) {
                sb.append("包培训 ");
            }
            if (afterSaleContent.indexOf("3") >= 0) {
                sb.append("物流点自提 ");
            }
            if (afterSaleContent.indexOf("4") >= 0) {
                sb.append("送货上门，含卸货上楼 ");
            }
            if (afterSaleContent.indexOf("5") >= 0) {
                sb.append("送货上门，含卸货到楼下 ");
            }
        }
        if(StringUtil.isNotBlank(qaYears)){

            if(StringUtil.isNotBlank(qaYears)){
                sb.append("质保年限：").append(qaYears + "年");
            }
        }
        this.goodsAfterSaleStr = sb.toString();
    }
}
