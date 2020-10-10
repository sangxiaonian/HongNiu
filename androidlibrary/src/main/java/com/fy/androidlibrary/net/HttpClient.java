package com.fy.androidlibrary.net;


import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/17 16:35
 */
public class HttpClient {


    private static Retrofit.Builder retrofit;
    private OkHttp builder;



    public HttpClient() {
        builder = new OkHttp();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        ;
    }

    public void baseUrl(String url){
        retrofit.baseUrl(url);
    }

    public <T> T creatService(Class<T> t) {
        retrofit.client(builder.getBuilder().build());
        return retrofit.build().create(t);
    }

    public HttpClient addInterceptor(Interceptor interceptor) {
        builder.addInterceptor(interceptor);
        return this;
    }

    public   Retrofit.Builder getRetrofit() {
        return retrofit;
    }

    /**
     * 更改服务器连接超时时间
     * @param timeOut 连接超时时间 秒
     * @return
     */
    public HttpClient setConnectTimeOut(int timeOut) {
        builder.getBuilder().connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
        ;
        return this;
    }
}