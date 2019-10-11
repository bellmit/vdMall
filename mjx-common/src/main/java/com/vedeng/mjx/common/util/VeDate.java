package com.vedeng.mjx.common.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VeDate {

    /**
     * 时间格式转换
     *
     * @return 返回时间类型 yyyy年MM月dd日 HH:mm:ss
     */
    public static String getDate(Date addTime) {
        String dateString = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        dateString = formatter.format(addTime);
        return dateString;
    }

    /**
     * 返回待确认订单剩余时间
     * @param addTime
     * @return
     */
    public static String getConfirmContent(Date addTime){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(addTime);
        rightNow.add(Calendar.DAY_OF_YEAR,14);//日期加14天
        Date dt1=rightNow.getTime();
        Date now = new Date();
        long date1 = (dt1.getTime() - now.getTime())/(3600*24*1000)+1;
        long date2 = (dt1.getTime() - now.getTime())/(60*60*1000);
        long date3 = (dt1.getTime() - now.getTime())/(60*1000);
        long date4 = (dt1.getTime() - now.getTime())/(1000);

        StringBuffer result = new StringBuffer();
        if(date1 > 1){
            result.append(date1).append("天");
        }else if(date2 <= 24 && date2 >= 1){
            result.append(date2).append("小时").append(date3-(date2*60)).append("分钟");
        }else if(date3 <= 60 && date3 >= 1){
            result.append(date3).append("分钟");
        }else{
            result.append("1分钟");
        }
        result.append("后,订单自动取消");
        return result.toString();
    }

    public static String getMessageDate(Date time){
        String messageDate = null;
        Calendar date = Calendar.getInstance();
        date.setTime(time);
        int year =  date.get(Calendar.YEAR);
        String month = (date.get(Calendar.MONTH) + 1) + "";
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        System.out.println("消息:年: " + year);
        System.out.println("消息:月: " + month);
        System.out.println("消息:日: " + day);
        System.out.println("消息:时: " + hour);
        System.out.println("消息:分: " + minute);

        Calendar now = Calendar.getInstance();
        int now_year =  now.get(Calendar.YEAR);
        String now_month = (now.get(Calendar.MONTH) + 1) + "";
        int now_day = now.get(Calendar.DAY_OF_MONTH);
        int now_hour = now.get(Calendar.HOUR_OF_DAY);
        int now_minute = now.get(Calendar.MINUTE);
        System.out.println("消息:年: " + now_year);
        System.out.println("消息:月: " + now_month);
        System.out.println("消息:日: " + now_day);
        System.out.println("消息:时: " + now_hour);
        System.out.println("消息:分: " + now_minute);
        if(year != now_year){
            messageDate = year+"年"+month+"月"+day+"日";
        }else if(!month.equals(now_month) || (month.equals(now_month) && day < now_day)){
            messageDate = month+"月"+day+"日";
        }else if(day+1 == now_day){
            messageDate = "昨天"+hour+":"+minute;
        }else{
            messageDate = hour+":"+minute;
        }
        return  messageDate;
    }

}
