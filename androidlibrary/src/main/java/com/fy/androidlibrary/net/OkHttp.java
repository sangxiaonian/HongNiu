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

    private static class InnerClass {
        private static OkHttp okHttp = new OkHttp();
    }

    public static OkHttp getOkHttp() {
        return InnerClass.okHttp;
    }

    private OkHttp() {
        builder = new OkHttpClient.Builder();
        //Android 4.4及以下的系统默认不支持TLS协议，进行适配
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            try {
//                // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
//                final X509TrustManager trustAllCert =
//                        new X509TrustManager() {
//                            @Override
//                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                            }
//
//                            @Override
//                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                            }
//
//                            @Override
//                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                                return new java.security.cert.X509Certificate[0];
//                            }
//                        };
//                final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
//                builder.sslSocketFactory(sslSocketFactory, trustAllCert);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
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
