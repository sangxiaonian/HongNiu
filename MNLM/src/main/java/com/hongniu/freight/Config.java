package com.hongniu.freight;

import android.app.Application;
import android.content.Context;

import com.fy.androidlibrary.net.OkHttp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.CompanyClient;
import com.hongniu.freight.huoyun.FreightClient;
import com.hongniu.freight.net.interceptor.HeardInterceptor;
import com.hongniu.freight.net.interceptor.LoginOutRespondInterceptor;
import com.sang.thirdlibrary.map.verify.VerifyClient;
import com.sang.thirdlibrary.pay.wechat.WeChatAppPay;

/**
 * 作者：  on 2020/10/7.
 */
public class Config {

    public static void init(Application context,boolean isDebug){
        if (isDebug) {
            Param.baseUrl = Param.debugUrl;
        } else {
            Param.baseUrl = Param.releaseUrl;
        }
        CompanyClient.getInstance()
                .addInterceptor(new HeardInterceptor(context))
                .addInterceptor(new LoginOutRespondInterceptor(context))
                .addInterceptor(OkHttp.getLogInterceptor())//添加log日志
                .setBaseUrl(Param.baseUrl);

        //初始化微信
//        WeChatAppPay.getInstance().init(context.getString(R.string.weChatAppid));
        // 初始化实人认证 SDK
        VerifyClient.getInstance()
                .setAppID(context.getString(R.string.verify_appID))
                .setSecret(context.getString(R.string.verify_secret))
                .setSDKlicense(context.getString(R.string.verify_SDKlicense))
                .initClient( BuildConfig.IS_DEBUG);

        //融云
//        ChactHelper.getHelper().initHelper(this);
//        registerUM();
//
//        //保活
        FreightClient.getClient().startKeepLive((Application) context,context.getString(R.string.app_name),"正在使用",R.mipmap.ic_launcher);
    }

}
