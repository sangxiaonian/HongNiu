package com.sang.common.net;


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
    private OkHttpClient.Builder builder;

    private static class InnerClient {
        private static HttpClient client = new HttpClient();
    }


    public static HttpClient getClient() {


        return InnerClient.client;
    }

    private HttpClient() {
        builder = OkHttp.getOkHttp().getBuilder();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(AppConfigs.base_url)
        ;
    }

    public <T> T creatService(Class<T> t) {
        retrofit.client(builder.build());
        return retrofit.build().create(t);
    }

    public void addInterceptor(Interceptor interceptor) {
        builder.addInterceptor(interceptor);
    }


}