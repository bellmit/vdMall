package com.vedeng.mjx.common;

public class MessageNotice {

    public static String verifyNoticeTitle = "您有订单待确认";
    public static String payNoticeTitle = "订单已付款";
    public static String finishTitle = "订单已收货";
    public static String closeTitle = "订单已关闭";
    private String verifySuccess;
    private String payResult;

    public static String getVerifyNoticeTitle(String orderNo) {
        return "您的订单【"+orderNo+"】已通过审核，请前往确认。";
    }

    public static String getPayNoticeTitle(String orderNo) {
        return "您的订单【"+orderNo+"】已付款，请静候收货";
    }

    public static String getFinishTitle(String orderNo) {
        return "您的订单【" + orderNo + "】已全部收货，请前往查看。";
    }

    public static String getCloseTitle(String orderNo) {
        return "您的订单【" + orderNo + "】由于超时未确认，已自动关闭，请前往查看";
    }
}
