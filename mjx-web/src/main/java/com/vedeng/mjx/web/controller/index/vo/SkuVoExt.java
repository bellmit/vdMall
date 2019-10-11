package com.vedeng.mjx.web.controller.index.vo;

import com.vedeng.goods.api.vo.SkuVO;

public class SkuVoExt extends SkuVO {
    private boolean newFlag;
    private boolean hotFlag;
    private boolean sevenFlag;

    public boolean isNewFlag() {
        return newFlag;
    }

    public void setNewFlag(boolean newFlag) {
        this.newFlag = newFlag;
    }

    public boolean isHotFlag() {
        return hotFlag;
    }

    public void setHotFlag(boolean hotFlag) {
        this.hotFlag = hotFlag;
    }


    public boolean isSevenFlag() {
        return sevenFlag;
    }

    public void setSevenFlag(boolean sevenFlag) {
        this.sevenFlag = sevenFlag;
    }

}
