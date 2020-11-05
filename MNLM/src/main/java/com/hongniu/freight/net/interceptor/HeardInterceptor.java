package com.hongniu.freight.net.interceptor;

import android.content.Context;

import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.companylibrary.config.Param;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.utils.InfoUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：  on 2020/2/23.
 */
public class HeardInterceptor implements Interceptor {

    private Context context;
    private String subAppCode;

    public HeardInterceptor(Context context) {
        this.context = context;
    }

    public void setSubAppCode(String subAppCode) {
        this.subAppCode = subAppCode;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        requestBuilder.addHeader("Content-Type", "application/json;charset=UTF-8");
        LoginInfo infor = InfoUtils.getLoginInfo();
        if (infor != null) {
            requestBuilder.addHeader("usercode", infor.getToken());
        }
//                        timestamp，randomNumber这两个字段都用字符串类型的，timestamp时间格式:yyyy-MM-dd hh:mm:ss:SSS
        //精确到毫秒的时间戳
        final String time = ConvertUtils.formatTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss:SSS") ;
        final String random = String.valueOf(ConvertUtils.getRandom(0, 1000000));
        String buffer = Param.AppSecret +
                time +
                random;
        final String sign = ConvertUtils.MD5(buffer.trim().replace(" ", ""));
        requestBuilder.addHeader("timestamp", time)
                .addHeader("randomNumber", random)
                .addHeader("hn_sign", sign)
                .addHeader("codetype", "token")
                .addHeader("hn_app_key", Param.AppKey)
                .addHeader("appVersion", DeviceUtils.getVersionName(context))
                .addHeader("subAppCode", subAppCode)
        ;
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }
}
