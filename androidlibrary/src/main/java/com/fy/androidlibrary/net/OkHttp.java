package com.fy.androidlibrary.net;

import android.os.Build;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 作者： ${桑小年} on 2018/7/28.
 * 努力，为梦长留
 */
public class OkHttp {

    private OkHttpClient.Builder builder;



    public OkHttp() {
        builder = new OkHttpClient.Builder();

        builder.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .sslSocketFactory(CustomSSLSocketFactory.createSSLSocketFactory())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
        ;
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

    public static HttpLoggingInterceptor getLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public void addInterceptor(Interceptor interceptor) {
        if (!builder.interceptors().contains(interceptor)) {
            builder.addInterceptor(interceptor);
        }
    }


}
