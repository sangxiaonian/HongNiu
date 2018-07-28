package com.hongniu.supply.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 作者： ${桑小年} on 2018/7/28.
 * 努力，为梦长留
 */
public class OkHttp {

    private static OkHttp okHttp;
    private final OkHttpClient.Builder builder;


    public static OkHttp getOkHttp() {
        if (okHttp == null) {
            synchronized (OkHttp.class) {
                if (okHttp == null) {

                    okHttp = new OkHttp();
                }
            }
        }
        return okHttp;
    }

    public OkHttp() {
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(getLogInterceptor());
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

    private static HttpLoggingInterceptor getLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


}
