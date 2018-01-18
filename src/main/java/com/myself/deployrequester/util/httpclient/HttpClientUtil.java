package com.myself.deployrequester.util.httpclient;

import com.myself.deployrequester.biz.design.dynamiccomponent.scanner.FolderScanner;
import com.myself.deployrequester.dto.QueryCriteriaDTO;
import com.myself.deployrequester.util.Log4jUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by QueRenJie on ${date}
 */
public class HttpClientUtil {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(HttpClientUtil.class);

    static public String doGet(String url, Map<String, Object> otherParamMap, Exception exception) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
//            Date d = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log4jUtil.info(logger, "其他参数：" + getOtherParamInfoFromMap(otherParamMap));
            Log4jUtil.info(logger, "executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                Log4jUtil.info(logger, "response status: " + response.getStatusLine());
                if (entity != null) {
                    return EntityUtils.toString(entity);
                } else {
                    return "unknown error";
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "服务器问题", e);
            exception = e;
        } catch (IOException e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "服务器问题", e);
            exception = e;
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                exception = e;
            }
        }
        return null;
    }

    static public String doPost(String url, Map<String, Object> params, Map<String, Object> otherParamMap) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列
        List<NameValuePair> nameValuePairsParams = new ArrayList<NameValuePair>();
        if (params != null) {
            Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                nameValuePairsParams.add(new BasicNameValuePair(entry.getKey(), (String)entry.getValue()));
            }
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(nameValuePairsParams, "UTF-8");
            httppost.setEntity(uefEntity);
//            Date d = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log4jUtil.info(logger, "参数：" + getOtherParamInfoFromMap(params));
            Log4jUtil.info(logger, "其他参数：" + getOtherParamInfoFromMap(otherParamMap));
            Log4jUtil.info(logger, "executing request(post):" + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
//                    Date d2 = new Date();
//                    Log4jUtil.info(logger, "当前时间：" + sdf.format(d2));
                    Log4jUtil.info(logger, "Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    return EntityUtils.toString(entity, "UTF-8");
                } else {
                    return "unknown error";
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "服务器问题", e);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            Log4jUtil.error(logger, "服务器问题", e1);
        } catch (IOException e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "服务器问题", e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getOtherParamInfoFromMap(Map<String, Object> otherParamMap) {
        Iterator<Map.Entry<String, Object>> iterator = otherParamMap.entrySet().iterator();
        String info = "";
        while (iterator.hasNext()) {
            Map.Entry<String, Object> mapEntry = iterator.next();
            info += mapEntry.getKey() + " = " + mapEntry.getValue() + "\n";
        }
        return info;
    }
}
