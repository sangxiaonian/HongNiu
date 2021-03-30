package com.fy.companylibrary.net;


import com.fy.androidlibrary.net.HttpClient;
import com.fy.androidlibrary.net.OkHttp;
import com.fy.androidlibrary.utils.JLog;

import okhttp3.Interceptor;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public class CompanyClient {

    private String baseUrl;
    //
    private HttpClient httpClient;

    private static class InnerLoginClient {
        private static CompanyClient client = new CompanyClient();
    }

    public static CompanyClient getInstance() {
        return InnerLoginClient.client;
    }

    private CompanyClient() {
        httpClient = new HttpClient()
                .setConnectTimeOut(60)

        ;
    }

    public CompanyClient setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        httpClient.baseUrl(baseUrl);
        return this;
    }

    public CompanyClient addInterceptor(Interceptor interceptor) {
        httpClient.addInterceptor(interceptor);
        return this;
    }


    public <T> T creatService(Class<T> t) {

        JLog.i("登录数据：CompanyClient");
        return httpClient.creatService(t);
    }


}
