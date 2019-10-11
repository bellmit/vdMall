package com.vedeng.mjx.service.util;

public class PhoneUtil {

    public static String getPhone(String phone){
        String phone1 = phone.substring(0,3);
        String phone2 = phone.substring(7,11);
        phone = phone1 +"****"+ phone2;
        return phone;
    }

}
