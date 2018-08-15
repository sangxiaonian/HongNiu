package com.hongniu.baselibrary.net;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.utils.Utils;
import com.sang.common.net.HttpClient;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.JLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                        /**添加header这几步一定要分开写 不然header会无效 别问我为什么
                         *我看了build源码 看返回了一个新的对象 猜想是要一个新的对象来接收
                         * 我就只定义了一个新的对象来接受新的Request
                         * 后面应该就可以，但是我没确定是否成功 ，然后我就全部都拆开了吧buider对象
                         * request的新的对象都分开之后 就能看到成功了。。。。巨大的bug 真是让人头疼
                         */
                        Request request = chain.request();
                        Request.Builder requestBuilder = request.newBuilder();
                        requestBuilder.addHeader("Content-Type", "application/json;charset=UTF-8");
                        LoginBean infor = Utils.getPgetPersonInfor();
                        if (infor != null) {
                            RequestBody body = request.body();
                            String s = body.toString();
                            requestBuilder.addHeader("usercode", infor.getToken())
                                    .addHeader("codetype", "token")
                                    .addHeader("hn_app_key", Param.AppKey)
                                    .addHeader("hn_sign", ConvertUtils.MD5(s, Param.AppSecret))
                            ;
                        }

                        Request newRequest = requestBuilder.build();


                        return chain.proceed(newRequest);
                    }
                });
    }

    public <T> T creatService(Class<T> t) {
        return httpClient.creatService(t);
    }


}
