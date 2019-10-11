package com.vedeng.mjx.service.util.youmengPush.android;


import com.vedeng.mjx.service.util.youmengPush.AndroidNotification;
import org.springframework.stereotype.Component;

@Component
public class AndroidBroadcast extends AndroidNotification{

    public AndroidBroadcast(){

    }

    public AndroidBroadcast(String appkey,String appMasterSecret) throws Exception {
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "broadcast");
    }
}
