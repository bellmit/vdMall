package com.vedeng.mjx.service.message.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vedeng.mjx.common.PushMessageVo.PushOneMessageInfo;
import com.vedeng.mjx.domain.Vmessage;
import com.vedeng.mjx.mapper.VMessageMapper;
import com.vedeng.mjx.service.message.PushMessageService;
import com.vedeng.mjx.service.util.youmengPush.AndroidNotification;
import com.vedeng.mjx.service.util.youmengPush.PushClient;
import com.vedeng.mjx.service.util.youmengPush.android.*;
import com.vedeng.mjx.service.util.youmengPush.ios.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PushMessageServiceImpl implements PushMessageService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private VMessageMapper vmessageMapper;

    @Resource
    private PushClient pushClient;

    /**
     * 个人订单信息发送
     *
     * @return
     */
    @Override
    public String pushOneAndroidMessage(PushOneMessageInfo pushOneMessageInfo) throws Exception {
        /**(单播)指定设备发送消息*/
        AndroidUnicast unicast = null;
        Vmessage vmessage = vmessageMapper.selectByPrimaryKey(pushOneMessageInfo.getMessageId());
        unicast = new AndroidUnicast(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret());
        unicast.setDeviceToken(pushOneMessageInfo.getDeviceToken());
        unicast.goAppAfterOpen();
        unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        unicast.setProductionMode();
        unicast.setTicker("订单通知");
        // Set customized fields
        unicast.setExtraField("BIZ_ID", vmessage.getBizId() != null ? vmessage.getBizId().toString() : null);
        unicast.setText(vmessage.getMessageContent());
        String title = getTypeInfo(pushOneMessageInfo.getType());
        unicast.setTitle(title);
        String result = pushClient.send(unicast);
        return result;

    }

    @Override
    public void pushAllAndroid(PushOneMessageInfo pushMessageInfo) throws Exception {
        Vmessage vmessage = vmessageMapper.selectByPrimaryKey(pushMessageInfo.getMessageId());

        AndroidBroadcast broadcast = new AndroidBroadcast(pushMessageInfo.getAppKey(), pushMessageInfo.getAppMasterSecret());
        broadcast.setTicker("Android broadcast ticker");
        broadcast.setTitle(vmessage.getMessageTitle());
        broadcast.setText(vmessage.getMessageContent());
//		broadcast.goAppAfterOpen();
//        broadcast.goUrlAfterOpen("https://www.baidu.com");
        broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        broadcast.setProductionMode();
        // Set customized fields
        broadcast.setExtraField("test", "helloworld");
        pushClient.send(broadcast);


    }

    private String getTypeInfo(Integer type) {
        String typeInfo = null;
        switch (type) {
            case 1:
                typeInfo = "订单已关闭";
                break;
            case 2:
                typeInfo = "您有订单待确认";
                break;
            case 3:
                typeInfo = "订单已付款";
                break;
            case 4:
                typeInfo = "订单已收货";
                break;
        }
        return typeInfo;
    }

    @Override
    public String pushOneIosMessage(PushOneMessageInfo pushOneMessageInfo) throws Exception {
        IOSUnicast unicast = new IOSUnicast(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret());
        JSONObject result = new JSONObject();
        JSONObject custom = new JSONObject();
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        Vmessage vmessage = vmessageMapper.selectByPrimaryKey(pushOneMessageInfo.getMessageId());

        object.put("messageTitle", vmessage.getMessageTitle()); //IOS推送标题
        object.put("pushContent", vmessage.getMessageContent());//推送内容
        array.add(object);
        result.put("BIZ_ID", vmessage.getBizId());
        custom.put("result", result);
        unicast.setDeviceToken(pushOneMessageInfo.getDeviceToken());
        unicast.setAlert("订单通知");
        unicast.setBadge(0);
//        unicast.setStartTime("2018-10-28 12:23:23");
        unicast.setSound("default");
        unicast.setTestMode();
        unicast.setCustomizedField("message", custom.toString());
        System.out.println("==iosDeviceToken:" + unicast.getPostBody());

        String resultInfo = pushClient.send(unicast);
        return resultInfo;
    }



    public void sendAndroidGroupcast(PushOneMessageInfo pushOneMessageInfo) throws Exception {
        AndroidGroupcast groupcast = new AndroidGroupcast(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret());
        /*
         * TODO Construct the filter condition: "where": { "and": [ {"tag":"test"},
         * {"tag":"Test"} ] }
         */
        org.json.JSONObject filterJson = new org.json.JSONObject();
        JSONObject whereJson = new JSONObject();
        org.json.JSONArray tagArray = new org.json.JSONArray();
        JSONObject testTag = new JSONObject();
        JSONObject TestTag = new JSONObject();
        testTag.put("tag", "test");
        TestTag.put("tag", "Test");
        tagArray.put(testTag);
        tagArray.put(TestTag);
        whereJson.put("and", tagArray);
        filterJson.put("where", whereJson);
        System.out.println(filterJson.toString());

        groupcast.setFilter(filterJson);
        groupcast.setTicker("Android groupcast ticker");
        groupcast.setTitle("中文的title");
        groupcast.setText("Android groupcast text");
        groupcast.goAppAfterOpen();
        groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        groupcast.setProductionMode();
        pushClient.send(groupcast);
    }

    public void sendAndroidCustomizedcast(PushOneMessageInfo pushOneMessageInfo) throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret());
        // TODO Set your alias here, and use comma to split them if there are
        // multiple alias.
        // And if you have many alias, you can also upload a file containing these
        // alias, then
        // use file_id to send customized notification.

        customizedcast.setAlias(pushOneMessageInfo.getAlias(), "user_phone");
        customizedcast.setBuilderId(222);

        customizedcast.setTicker("Android customizedcast ticker");
        customizedcast.setTitle("中文的title");
        customizedcast.setText("Android customizedcast text");
        customizedcast.setPlayVibrate(false);
        customizedcast.setPlayLights(false);
        customizedcast.setPlaySound(true);
        customizedcast.goAppAfterOpen();
        // 后加
        customizedcast.setCustomField("custom");
        customizedcast.setExpireTime("2018-05-14 21:17:58");
        customizedcast.setDescription("222");
        /**customizedcast.setProductionMode();正式模式*/
        customizedcast.setTestMode();//测试
        customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        customizedcast.setProductionMode();
        pushClient.send(customizedcast);
    }

    public void sendAndroidCustomizedcastFile(PushOneMessageInfo pushOneMessageInfo) throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret());
        // TODO Set your alias here, and use comma to split them if there are
        // multiple alias.
        // And if you have many alias, you can also upload a file containing these
        // alias, then
        // use file_id to send customized notification.
        String fileId = pushClient.uploadContents(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret(), "aa" + "\n" + "bb" + "\n" + "alias");
        customizedcast.setFileId(fileId, "user");
        customizedcast.setTicker("Android customizedcast ticker");
        customizedcast.setTitle("中文的title");
        customizedcast.setText("Android customizedcast text");
        customizedcast.goAppAfterOpen();
        customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        customizedcast.setProductionMode();
        pushClient.send(customizedcast);
    }

    public void sendAndroidFilecast(PushOneMessageInfo pushOneMessageInfo) throws Exception {
        AndroidFilecast filecast = new AndroidFilecast(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret());
        // TODO upload your device tokens, and use '\n' to split them if there are
        // multiple tokens
        String fileId = pushClient.uploadContents(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret(), "aa" + "\n" + "bb");
        filecast.setFileId(fileId);
        filecast.setTicker("Android filecast ticker");
        filecast.setTitle("中文的title");
        filecast.setText("Android filecast text");
        filecast.goAppAfterOpen();
        filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        pushClient.send(filecast);
    }

    public void sendIOSBroadcast(PushOneMessageInfo pushOneMessageInfo) throws Exception {
        IOSBroadcast broadcast = new IOSBroadcast(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret());
        broadcast.setAlert("IOS 广播测试");
        broadcast.setBadge(0);
        broadcast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        // broadcast.setTestMode();
        /**broadcast.setProductionMode();正式版*/
        broadcast.setTestMode();//测试版
        // Set customized fields
        broadcast.setCustomizedField("test", "helloworld");
        pushClient.send(broadcast);
    }


    public void sendIOSGroupcast(PushOneMessageInfo pushOneMessageInfo) throws Exception {
        IOSGroupcast groupcast = new IOSGroupcast(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret());
        /*
         * TODO Construct the filter condition: "where": { "and": [
         * {"tag":"iostest"} ] }
         */
        org.json.JSONObject filterJson = new org.json.JSONObject();
        JSONObject whereJson = new JSONObject();
        org.json.JSONArray tagArray = new org.json.JSONArray();
        JSONObject testTag = new JSONObject();
        testTag.put("tag", "iostest");
        tagArray.put(testTag);
        whereJson.put("and", tagArray);
        filterJson.put("where", whereJson);
        System.out.println(filterJson.toString());

        // Set filter condition into rootJson
        groupcast.setFilter(filterJson);
        groupcast.setAlert("IOS 组播测试");
        groupcast.setBadge(0);
        groupcast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        groupcast.setTestMode();
        pushClient.send(groupcast);
    }

    public void sendIOSCustomizedcast(PushOneMessageInfo pushOneMessageInfo) throws Exception {
        IOSCustomizedcast customizedcast = new IOSCustomizedcast(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret());
        customizedcast.setAlias(pushOneMessageInfo.getAlias(), "user_phone");
        customizedcast.setAlert("小V提醒您,您有订单状态有更新，请点击查看");
        customizedcast.setBadge(0);
        customizedcast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        customizedcast.setTestMode();
        pushClient.send(customizedcast);
    }


    public void sendIOSFilecast(PushOneMessageInfo pushOneMessageInfo) throws Exception {
        IOSFilecast filecast = new IOSFilecast(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret());
        // TODO upload your device tokens, and use '\n' to split them if there are
        // multiple tokens
        String fileId = pushClient.uploadContents(pushOneMessageInfo.getAppKey(), pushOneMessageInfo.getAppMasterSecret(), "aa" + "\n" + "bb");
        filecast.setFileId(fileId);
        filecast.setAlert("IOS 文件播测试");
        filecast.setBadge(0);
        filecast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        filecast.setTestMode();
        pushClient.send(filecast);
    }

}
