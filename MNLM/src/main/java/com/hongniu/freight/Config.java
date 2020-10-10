package com.hongniu.freight;

import android.app.Application;

import com.fy.androidlibrary.net.OkHttp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.CompanyClient;
import com.hongniu.freight.huoyun.FreightClient;
import com.hongniu.freight.net.interceptor.HeardInterceptor;
import com.hongniu.freight.net.interceptor.LoginOutRespondInterceptor;
import com.sang.thirdlibrary.map.verify.VerifyClient;

/**
 * 作者：  on 2020/10/7.
 */
public class Config {

    Application application;
    private boolean isDebug;


    private static class Inner {
        private static Config config = new Config();
    }

    public static Config getInstance() {
        return Inner.config;
    }

    private Config() {
    }

    public void init(Application context, boolean isDebug) {
        this.application = context;
        this.isDebug = isDebug;
        //初始化微信
//        WeChatAppPay.getInstance().init(context.getString(R.string.weChatAppid));
        // 初始化实人认证 SDK
        VerifyClient.getInstance()
                .setAppID(context.getString(R.string.verify_appID))
                .setSecret(context.getString(R.string.verify_secret))
                .setSDKlicense(context.getString(R.string.verify_SDKlicense))
                .initClient(isDebug);

        //融云
//        ChactHelper.getHelper().initHelper(this);
//        registerUM();
//
//        //保活
        FreightClient.getClient().startKeepLive((Application) context, context.getString(R.string.app_name), "正在使用", R.mipmap.ic_launcher);
    }

    public void intNetClient( ) {
        if (isDebug) {
            Param.baseUrl = Param.debugUrl;
        } else {
            Param.baseUrl = Param.releaseUrl;
        }
        CompanyClient.getInstance()
                .addInterceptor(new HeardInterceptor(application))
                .addInterceptor(new LoginOutRespondInterceptor(application))
                .addInterceptor(OkHttp.getLogInterceptor())//添加log日志
                .setBaseUrl(Param.baseUrl);

    }

}
