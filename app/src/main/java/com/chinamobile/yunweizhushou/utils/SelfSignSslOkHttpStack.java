package com.chinamobile.yunweizhushou.utils;

import com.android.volley.toolbox.HurlStack;

import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.OkHttpClient;


/**
 * Created by Administrator on 2018/1/23.
 */

public class SelfSignSslOkHttpStack extends HurlStack {
    private OkHttpClient okHttpClient;
    private Map<String, SSLSocketFactory> socketFactoryMap;
    public SelfSignSslOkHttpStack(Map<String, SSLSocketFactory> factoryMap) {
        this(new OkHttpClient(), factoryMap);
    }
    public SelfSignSslOkHttpStack(OkHttpClient okHttpClient, Map<String, SSLSocketFactory> factoryMap) {
        this.okHttpClient = okHttpClient;
        this.socketFactoryMap = factoryMap;
    }

   /* @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        if ("https".equals(url.getProtocol()) && socketFactoryMap.containsKey(url.getHost())) {
            HttpsURLConnection connection = (HttpsURLConnection) new OkUrlFactory(okHttpClient).open(url);
            connection.setSSLSocketFactory(socketFactoryMap.get(url.getHost()));
            return connection;
        } else {
            return new OkUrlFactory(okHttpClient).open(url);
        }
    }*/




}
