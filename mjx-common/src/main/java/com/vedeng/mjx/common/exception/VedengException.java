package com.vedeng.mjx.common.exception;

/**
 * 全局异常
 */
public class VedengException extends RuntimeException {
    private String code;
    private String message;

    public VedengException(VedengErrorCode errorCode) {
        super(errorCode.getCode());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
