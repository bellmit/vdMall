package com.vedeng.mjx.common.http;


import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * https client
 *
 * @author sunxy 2015年10月30日 上午11:03:58
 * @since 1.0
 */
public class HttpsClient {
    final static Logger log = LoggerFactory.getLogger(HttpsClient.class);
    private final String url;
    private final Map<String, String> params = new HashMap<>();
    private HttpResponse response;
    private int statusCode;
    private String result;

    public HttpsClient(String url) {
        this.url = url;
    }

    public HttpsClient(String url, Map<String, String> params) {
        this.url = url;
        this.params.putAll(params);
    }

    public void addParam(String name, String value) {
        params.put(name, value);
    }

    public String sendHttps(int timeout_time) {
        org.apache.http.client.HttpClient httpClient = null;
        try {
            httpClient = mkHttpsClient(timeout_time);
            HttpResponse aResponse = httpClient.execute(getHttpPost());
            setResponse(aResponse);
            httpClient.getConnectionManager().shutdown();
        } catch (Exception e) {
            httpClient.getConnectionManager().shutdown();
            throw new RuntimeException("访问【" + this + "】时发生异常：" + e.getMessage(), e);
        }
        if (!isRedirect()) {
            return result;
        } else {
            throw new RuntimeException("需要转发!");
        }
    }

    private org.apache.http.client.HttpClient mkHttpsClient(int timeout_time) throws KeyManagementException, NoSuchAlgorithmException {
        org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
        if (url.toLowerCase().startsWith("https")) {
            httpclient = getInstance(httpclient);
        }
        if (timeout_time > 0) {
            httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout_time);
            httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeout_time);
        }
        return httpclient;
    }

    private org.apache.http.client.HttpClient getInstance(org.apache.http.client.HttpClient client) throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", 443, ssf));
        ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
        return new DefaultHttpClient(mgr, client.getParams());
    }

    private HttpPost getHttpPost() throws UnsupportedEncodingException {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(urlEncodedFormEntity);
        return httpPost;
    }

    private boolean isRedirect() {
        return statusCode == 301 || statusCode == 302;
    }

    private void setResponse(HttpResponse aResponse) throws IOException {
        this.response = aResponse;
        this.statusCode = response.getStatusLine().getStatusCode();
        this.result = getResult(aResponse);

    }

    private String getResult(HttpResponse aResponse) throws IOException {
        HttpEntity entity = aResponse.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        } else {
            return "error:entity is null!";
        }
    }

    public static void post(String url,Map<String,Object> maps) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> formParams = new ArrayList<>();
            for(String key : maps.keySet()) {
                Object value = maps.get(key);
                if (value != null){
                    formParams.add(new BasicNameValuePair(key, value.toString()));
                }
            }

            httpPost.setHeader("Content-Type","application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(formParams));

            HttpResponse response = httpclient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            System.out.println("code:"+code);
            String result = EntityUtils.toString(response.getEntity());
//            JSONObject obj = JSONObject.parseObject(result);
//            System.out.println("result:"+obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject httpPost(String url, JSONObject jsonParam){
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            String str = "";
            try {
                /**读取服务器返回过来的json字符串数据**/
                str = EntityUtils.toString(result.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.fromObject(str);
            } catch (Exception e) {
            }
        } catch (IOException e) {
        }
        return jsonResult;
    }

    public static void main(String[] args) {
        String str = "{\"accountId\":5061,\"deliveryAddressId\":580,\"deliveryUserAddress\":\"安徽蚌埠固镇县 asdasd\",\"deliveryUserName\":\"王魏\",\"deliveryUserPhone\":\"15900956640\",\"goodsList\":[{\"marketMomey\":16250.00,\"productNum\":1,\"skuNo\":\"V253482\"},{\"marketMomey\":105000.00,\"productNum\":1,\"skuNo\":\"V278350\"}],\"marketMomney\":0,\"orderNo\":\"BD197537016\",\"orderStatus\":0,\"orderType\":1,\"paymentMode\":0,\"phone\":\"15900956640\",\"productNum\":2,\"productTypeNum\":2,\"remakes\":\"\"}\n";
        JSONObject result = HttpsClient.httpPost("http://172.16.3.166:80/order/saleorder/saveBDAddSaleorder.do", JSONObject.fromObject(str));
        System.out.println(result);
    }

}
