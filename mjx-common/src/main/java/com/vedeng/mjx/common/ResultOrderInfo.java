package com.vedeng.mjx.common;

import com.github.pagehelper.PageInfo;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.exception.VedengException;
import com.vedeng.mjx.common.orderVo.OrderListData;

/**
 * @Description: 响应结果
 * @author: Wyatt
 * @Date: 2019-05-30 14:20
 */
public class ResultOrderInfo {

    private String code = "success";

    private Object data = null;

    private String message;

    private boolean success = true;

    private Integer status;

    private long total;

    public ResultOrderInfo(String code, Object data, String message, boolean success, Integer status) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = success;
        this.status = status;
    }

    public ResultOrderInfo(Object data,Integer total,Integer orderStatus) {
        this.data = data;
        this.status = orderStatus;
        this.total = total;
    }

    public static ResultOrderInfo success(Object data,Integer total,Integer orderStatus) {
        return new ResultOrderInfo(data,total,orderStatus);
    }

    public static ResultOrderInfo fail(VedengErrorCode errorCode, Object data, Integer status) {
        return new ResultOrderInfo(errorCode.getCode(), data, errorCode.getMessage(), false,status);
    }

    public static ResultOrderInfo fail(VedengErrorCode errorCode) {
        return new ResultOrderInfo(errorCode.getCode(), null, errorCode.getMessage(), false,null);
    }

    public static ResultOrderInfo fail(VedengException exception) {
        return new ResultOrderInfo(exception.getCode(), null, exception.getMessage(), false,null);
    }

    public static ResultOrderInfo fail(Throwable e) {
        return fail(VedengErrorCode.UNKNOWN_EXCEPTION);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public static ResultOrderInfo fail(String errorCode , String errorMessage) {
        return new ResultOrderInfo(errorCode, null, errorMessage, false,null);
    }

    public static ResultOrderInfo success(VedengErrorCode errorCode,Integer status) {
        return new ResultOrderInfo(errorCode.getCode(), null, errorCode.getMessage(), true,status);
    }

    public static ResultOrderInfo success(VedengErrorCode errorCode, Object data,Integer status) {
        return new ResultOrderInfo(errorCode.getCode(), data, errorCode.getMessage(), true,status);
    }
}

