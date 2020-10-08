package com.hongniu.freight.net.interceptor;

import android.content.Context;
import android.content.Intent;

import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hongniu.freight.utils.InfoUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 作者：  on 2019/10/30.
 * <p>
 * 根据返回的数据，登陆失败
 */
public class LoginOutRespondInterceptor implements Interceptor {
    private   Context context;
    private String action = ".login";
    private long lastTime;

    public LoginOutRespondInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
                               ResponseBody body = response.body();
        if (body != null && body.contentType() != null) {
            try {
                BufferedSource source = body.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = body.contentType().charset(Charset.forName("UTF-8"));
                String json = buffer.clone().readString(charset);
                JsonElement obj = new JsonParser().parse(json);
                if (obj instanceof JsonObject && ((JsonObject) obj).has("code")) {
                    int code = ((JsonObject) obj).get("code").getAsInt();
                    long currentTime = System.currentTimeMillis();
                    if (code == 401 && currentTime - lastTime > 500) {
                        //500ms内只能跳转一次
                        //登陆已失效
                        Intent intent = new Intent();
                        intent.setAction(context.getPackageName() + action);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        lastTime = currentTime;
                        InfoUtils.loginOut();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return response;

    }
}
