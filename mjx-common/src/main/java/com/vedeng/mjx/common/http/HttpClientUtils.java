package com.vedeng.mjx.common.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.key.CryptBase64Tool;
import com.vedeng.mjx.common.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;

public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * <b>Description:</b><br>
     * post方式请求Api(包含分页信息)
     *
     * @param url
     * @param paraMap
     * @param paraMap
     * @return
     * @throws IOException
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年5月8日 下午6:45:27
     */
    public static String post(String url, Object paraMap) throws IOException {
        // 序列化成 json
        String data = JSONObject.toJSONString(paraMap);
        logger.info("start get dbcenter data:" + url+data);
        // 准备发送的参数
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("data", data);

        // http post 参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> $it = paramMap.entrySet().iterator();
        while ($it.hasNext()) {
            Map.Entry<String, String> entry = $it.next();
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        // http post 请求
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","application/json");
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        CloseableHttpResponse response = httpclient.execute(httpPost);

        int status = response.getStatusLine().getStatusCode();
        logger.info(status+"end get dbcenter data:" + url+data+status);
        // logger.debug("Http Post Status:" + status);

        try {
            // 返回结果 格式转换
            String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            logger.info("result:"+JSON.toJSONString(result));
            return result;

        } catch (Exception e) {
            logger.error(status+"error get dbcenter data:" + url+data+"\t");
        } finally {
            response.close();
        }
        return null;
    }

}
