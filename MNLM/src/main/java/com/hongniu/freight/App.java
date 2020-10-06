package com.hongniu.freight;

import android.content.Context;

import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.net.OkHttp;
import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.baselibrary.BaseApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.CompanyClient;
import com.google.gson.Gson;
import com.hongniu.freight.entity.UmenToken;
import com.hongniu.freight.net.interceptor.HeardInterceptor;
import com.hongniu.freight.net.interceptor.LoginOutRespondInterceptor;
import com.hongniu.freight.receiver.MyPushReceiver;
import com.hongniu.thirdlibrary.chact.ChactHelper;
import com.hongniu.freight.huoyun.FreightClient;
import com.hongniu.thirdlibrary.pay.wechat.WeChatAppPay;
import com.hongniu.thirdlibrary.push.NotificationUtils;
import com.hongniu.thirdlibrary.push.client.PushClient;
import com.hongniu.thirdlibrary.push.client.PushUmeng;
import com.hongniu.thirdlibrary.push.inter.PlushDealWithMessageListener;
import com.hongniu.thirdlibrary.push.inter.PlushRegisterListener;
import com.hongniu.thirdlibrary.verify.VerifyClient;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者：  on 2020/2/5.
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Param.baseUrl = Param.debugUrl;
        } else {
            Param.baseUrl = Param.releaseUrl;
        }

        ImageLoader.getLoader().headErrorImg(R.mipmap.icon_default_avatar_100);
        ImageLoader.getLoader().headPlaceholder(R.mipmap.icon_default_avatar_100);
        ImageLoader.getLoader().globalPlaceholder(R.drawable.ovl_5_f7f7f7);
        ImageLoader.getLoader().globalErrorImg(R.drawable.ovl_5_f7f7f7);

        CompanyClient.getInstance()
                .addInterceptor(new HeardInterceptor(this))
                .addInterceptor(new LoginOutRespondInterceptor(this))
                .addInterceptor(OkHttp.getLogInterceptor())//添加log日志
                .setBaseUrl(Param.baseUrl);

        //初始化微信
        WeChatAppPay.getInstance().init(getString(R.string.weChatAppid));
        // 初始化实人认证 SDK
        VerifyClient.getInstance()
                .setAppID(getString(R.string.verify_appID))
                .setSecret(getString(R.string.verify_secret))
                .setSDKlicense(getString(R.string.verify_SDKlicense))
                .initClient( BuildConfig.IS_DEBUG);

        //融云
        ChactHelper.getHelper().initHelper(this);
        registerUM();

        //保活
        FreightClient.getClient().startKeepLive(this,getString(R.string.app_name),"正在使用",R.mipmap.ic_launcher);


    }

    public void registerUM() {
        PushClient plushClient = PushClient.getClient();

        PushUmeng pushUmeng = new PushUmeng(this,getString(R.string.UMAppKey),getString(R.string.UMpushSercet));

        plushClient.setPlush(pushUmeng);
        plushClient.setPlushRegisterListener(new PlushRegisterListener() {
            @Override
            public void onSuccess(String data) {
                JLog.d("推送注册成功:" + data);
                SharedPreferencesUtils.getInstance().putString(Param.UMENGTOKEN, data);
                EventBus.getDefault().postSticky(new UmenToken());
            }

            @Override
            public void onFailure(String code, String errorReson) {
                JLog.e("推送注册失败:" + code + " :  " + errorReson);
            }
        });
        NotificationUtils.getInstance().setReceiver(MyPushReceiver.class);
        plushClient.setPlushDealWithMessageListener(new PlushDealWithMessageListener() {
            @Override
            public void dealMessage(Context context, Object message) {
                JLog.i("接收到推送信息：" + message.toString());
                if (message instanceof UMessage) {
                    UMessage msg = (UMessage) message;
                    NotificationUtils
                            .getInstance()
//                                .setSound(R.raw.notify_sound)//自定义声音
                            .setIcon(R.mipmap.ic_launcher)
                            .setReceiver(MyPushReceiver.class)
                            .showNotification(context
                                    , msg.ticker == null ? "新消息来了" : msg.ticker
                                    , msg.title == null ? "新消息来了" : msg.title
                                    , msg.text == null ? "新消息来了" : msg.text
                                    , new Gson().toJson(message)
                            );
                }
            }
        });

    }
}
