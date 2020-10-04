package com.hongniu.baselibrary.net;

import com.fy.androidlibrary.net.HttpClient;
import com.fy.androidlibrary.net.OkHttp;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.hongniu.baselibrary.base.BaseApplication;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.utils.Utils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public class BaseClient {
    private final HttpClient httpClient;

    private static class InnerLoginClient {
        private static BaseClient client = new BaseClient();
    }

    public static BaseClient getInstance() {
        return InnerLoginClient.client;
    }

    private BaseClient() {
        httpClient = new HttpClient()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request();
                        Request.Builder requestBuilder = request.newBuilder();
                        requestBuilder.addHeader("Content-Type", "application/json;charset=UTF-8");
                        LoginBean infor = Utils.getLoginInfor();
                        if (infor != null) {
                            requestBuilder.addHeader("usercode", infor.getToken());
                        }
//                        timestamp，randomNumber这两个字段都用字符串类型的，timestamp时间格式:yyyy-MM-dd hh:mm:ss:SSS
                        //精确到毫秒的时间戳
                        final String time = ConvertUtils.formatTime(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss:SSS");
                        final String random = String.valueOf(ConvertUtils.getRandom(0, 1000000));
                        final StringBuffer buffer = new StringBuffer();
                        buffer.append(Param.AppSecret)
                                .append(time.replace(" ", ""))
                                .append(random);
                        final String sign = ConvertUtils.MD5(buffer.toString().trim().replace(" ", ""));
                        requestBuilder.addHeader("timestamp", time)
                                .addHeader("randomNumber", random)
                                .addHeader("hn_sign", sign)
                                .addHeader("codetype", "token")
                                .addHeader("hn_app_key", Param.AppKey)
                                .addHeader("appVersion", DeviceUtils.getVersionName(BaseApplication.getContext()))
                        ;
                        Request newRequest = requestBuilder.build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(OkHttp.getLogInterceptor())
        ;
    }

    public <T> T creatService(Class<T> t) {
        return httpClient.creatService(t);
    }


}
