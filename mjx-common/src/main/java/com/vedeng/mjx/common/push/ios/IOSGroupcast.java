package com.vedeng.mjx.common.push.ios;

import com.vedeng.mjx.common.push.IOSNotification;
import org.json.JSONObject;

public class IOSGroupcast extends IOSNotification {
	public IOSGroupcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "groupcast");	
	}
	
	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
