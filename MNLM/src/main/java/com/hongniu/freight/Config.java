package com.hongniu.freight;

import android.app.Application;

import com.fy.androidlibrary.net.OkHttp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.CompanyClient;
import com.hongniu.freight.huoyun.FreightClient;
import com.hongniu.freight.net.interceptor.HeardInterceptor;
import com.hongniu.freight.net.interceptor.LoginOutRespondInterceptor;
import com.sang.thirdlibrary.map.verify.VerifyClient;
import com.umeng.commonsdk.debug.E;

/**
 * 作者：  on 2020/10/7.
 */
public class Config {

    Application application;
    private boolean isDebug;


    //人脸识别正式版本
    String verify_appID="IDAGnuKK";//人脸识别正式版本
    String verify_secret="9gE8cd5DgLfm3oJX3aezDUUWHq96MXb8IQuPqlQUkLxXefUndP5D5xObPECzpO6y";
    String verify_SDKlicense="pYXTl8k0mN99he+yGFM+SMWNWmbReaV1gamC88dGg65hnjzZDeQDHbkZ/uBxPk8jE1WK4BfAUrS+6a08xkvBptMW6H1zwHnVAxzd6nddDrDKvgHMl+E/YDRsN2ty6UdyQYsHRclY6iu12sVRdzIeEPBN/InzEBPzQsenQOWDEo+Q5DOPtE/i4C0KokT0x5JI9/RWVioxKp4RrLSNOArr+KVIfc/a74+yfTaMhQCyAdg2onpNSuzHSeY5egtno0QBSCLLmoxTQwUB1F/bfXngRdV9dFbbmQBpOV00KNbqGYmer+s3RvR2lkrGB48MuEJHWA3hgGRquw03ncG8zf/RZg==";






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

        CompanyClient.getInstance()
                .addInterceptor(new HeardInterceptor(application))
                .addInterceptor(new LoginOutRespondInterceptor(application))
                .addInterceptor(OkHttp.getLogInterceptor());//添加log日志

        //初始化微信
//        WeChatAppPay.getInstance().init(context.getString(R.string.weChatAppid));
        // 初始化实人认证 SDK
        VerifyClient.getInstance()
                .setAppID(verify_appID)
                .setSecret(verify_secret)
                .setSDKlicense(verify_SDKlicense)
                .initClient(isDebug);

        //融云
//        ChactHelper.getHelper().initHelper(this);
//        registerUM();
//
//        //保活
        FreightClient.getClient().startKeepLive((Application) context, context.getString(R.string.app_name), "正在使用", R.mipmap.ic_launcher);
    }

    public void intNetClient(String apiUrl) {
        if (apiUrl!=null) {
            CompanyClient.getInstance()
                    .setBaseUrl(apiUrl);
        }

    }

}
