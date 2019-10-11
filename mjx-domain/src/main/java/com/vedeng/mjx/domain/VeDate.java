package com.vedeng.mjx.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VeDate {

    public static String getMessageDate(Date time){
        String messageDate = null;
        Calendar date = Calendar.getInstance();
        date.setTime(time);
        int year =  date.get(Calendar.YEAR);
        String month = (date.get(Calendar.MONTH) + 1) + "";
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);

        Calendar now = Calendar.getInstance();
        int now_year =  now.get(Calendar.YEAR);
        String now_month = (now.get(Calendar.MONTH) + 1) + "";
        int now_day = now.get(Calendar.DAY_OF_MONTH);
        int now_hour = now.get(Calendar.HOUR_OF_DAY);
        int now_minute = now.get(Calendar.MINUTE);
        if(year != now_year){
            messageDate = year+"年"+month+"月"+day+"日";
        }else if(!month.equals(now_month) || (month.equals(now_month) && day+1 < now_day)){
            messageDate = month+"月"+day+"日";
        }else if(day+1 == now_day){
            messageDate = "昨天"+hour+":"+theUnit(minute);
        }else{
            messageDate = hour+":"+theUnit(minute);;
        }
        return  messageDate;
    }

    /**
     * 当前分钟是否是个位
     * @param minute
     * @return
     */
    public static String theUnit(int minute){
        boolean isNum = false;
        for(int i = 0; i <= 9; i++){
            if(minute == i){
                isNum = true;
            }
        }
        String minuteStr = "" + minute;
        if(isNum){
            minuteStr = "0" + minute;
        }
        return minuteStr;
    }

    /**
     * 功能描述：返回小时
     * @return
     */
    public static int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 是否是今天
     * @param addTime
     * @return
     */
    public static boolean isDate(Date addTime){
        String messageDate = null;
        Calendar date = Calendar.getInstance();
        date.setTime(addTime);
        int year =  date.get(Calendar.YEAR);
        String month = (date.get(Calendar.MONTH) + 1) + "";
        int day = date.get(Calendar.DAY_OF_MONTH);

        Calendar now = Calendar.getInstance();
        int now_year =  now.get(Calendar.YEAR);
        String now_month = (now.get(Calendar.MONTH) + 1) + "";
        int now_day = now.get(Calendar.DAY_OF_MONTH);

        if(year == now_year && month.equals(now_month) && day == now_day){
            return true;
        }
        return false;
    }

}
