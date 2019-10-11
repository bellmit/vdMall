package com.vedeng.mjx.service.message;


import com.vedeng.mjx.common.PushMessageVo.PushOneMessageInfo;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.domain.Vmessage;

import java.util.List;
import java.util.Map;

public interface PushMessageService {

//    public String pushAndroidMessage(List<String> infoData, Integer type, Integer messageId);

    public String pushOneAndroidMessage(PushOneMessageInfo pushOneMessageInfo) throws Exception;

    public String pushOneIosMessage(PushOneMessageInfo pushOneMessageInfo) throws Exception;

    void pushAllAndroid(PushOneMessageInfo pushMessageInfo) throws Exception;

    public void sendAndroidGroupcast(PushOneMessageInfo pushOneMessageInfo) throws Exception;

    public void sendAndroidCustomizedcast(PushOneMessageInfo pushOneMessageInfo) throws Exception;

    public void sendAndroidCustomizedcastFile(PushOneMessageInfo pushOneMessageInfo) throws Exception;

    public void sendAndroidFilecast(PushOneMessageInfo pushOneMessageInfo) throws Exception;

    public void sendIOSBroadcast(PushOneMessageInfo pushOneMessageInfo) throws Exception;

    public void sendIOSGroupcast(PushOneMessageInfo pushOneMessageInfo) throws Exception;

    public void sendIOSCustomizedcast(PushOneMessageInfo pushOneMessageInfo) throws Exception;

    public void sendIOSFilecast(PushOneMessageInfo pushOneMessageInfo) throws Exception;
}