package com.hongniu.supply.net;


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


    private static HttpClient client;
    private static Retrofit retrofit;

    public static HttpClient getClient() {

        if (client == null) {
            synchronized (HttpClient.class) {
                if (client == null) {
                    retrofit = new Retrofit.Builder()
                            .client(OkHttp.getOkHttp().getBuilder().build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(AppConfigs.base_url)
                            .build();
                }
            }
        }
        return client;
    }

    public <T> T creatService(Class<T> t) {
        return retrofit.create(t);
    }


}