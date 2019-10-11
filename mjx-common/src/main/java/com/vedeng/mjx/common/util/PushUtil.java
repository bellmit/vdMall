package com.vedeng.mjx.common.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.vedeng.mjx.common.enumUtils.IntentType;
import com.vedeng.mjx.common.push.IOSNotification;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;
import static org.bouncycastle.jcajce.spec.TLSKeyMaterialSpec.MASTER_SECRET;

public class PushUtil {

    /**
     * 全平台所有人
     * @return
     */
    public static void buildPushObject_all_all_alert() {
        JPushClient jpushClient = new JPushClient("f4a1003383dc9c8ec53127a8", "6e218d62b220110ec8750566", null, ClientConfig.getInstance());
        PushPayload payload = PushPayload.alertAll(ALERT);
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println("result:"+result);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    /**
     * Android单条
     */
    public static PushResult pushAndroid(String phone,String content,String title,Map<String, String> extras) throws APIConnectionException,APIRequestException{
        JPushClient jpushClient = new JPushClient("f4a1003383dc9c8ec53127a8", "6e218d62b220110ec8750566", null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(phone))
                .setNotification(Notification.newBuilder().setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title)
                        .addExtras(extras).build()).build())
                .build();

        PushResult result = jpushClient.sendPush(payload);
        return result;

    }
    /**
     * IOS单条
     * @return
     */
    public static PushResult pushIOS(String phone,String content,String title,Map<String, String> extras) throws APIConnectionException,APIRequestException{
        JPushClient jpushClient = new JPushClient("f4a1003383dc9c8ec53127a8", "6e218d62b220110ec8750566", null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(phone))
                .setNotification(Notification.newBuilder().setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title)
                                .addExtras(extras).build()).build())
                .build();

        PushResult result = jpushClient.sendPush(payload);
        return result;

    }

    /**
     * 全平台单条
     * @return
     */
    public static PushResult pushAll(String appKey,String masterSecret,String phone,String content,String title,Map<String, String> extras) throws APIConnectionException,APIRequestException{
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(phone))
                .setNotification(Notification.newBuilder().setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title).addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setBadge(5).setSound("default").addExtras(extras) .build())
                        .build())
                .build();

        PushResult result = jpushClient.sendPush(payload);
        return result;

    }

    public static void main(String[] args) {
        try {
            Map<String,String> extras = new HashMap<>();
            extras.put("intentType", IntentType.ORDER_DETAIL.getValue());
            extras.put("orderNo","BD197435734");
            extras.put("messageId","1");
            extras.put("messageType","2");
            PushUtil.pushAll("6e218d62b220110ec8750566", "f4a1003383dc9c8ec53127a8", "15952040102", "您的订单【BD197435734】已通过审核，请前往确认。", "您有订单待确认", extras);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

}
