package com.vedeng.mjx.common.util;

public class AddressUtil {

    public static String getAddress(String address){
        String a = address.substring(0,2);
        if(address.substring(2,address.length()).indexOf(a) != -1) {
            address = address.substring(2,address.length());
        }
        return address;
    }

}
