package com.hongniu.freight.huoyun;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.fy.androidlibrary.utils.JLog;
import com.hdgq.locationlib.LocationOpenApi;
import com.hdgq.locationlib.entity.ShippingNoteInfo;
import com.hdgq.locationlib.keeplive.KeepLive;
import com.hdgq.locationlib.keeplive.config.ForegroundNotification;
import com.hdgq.locationlib.keeplive.config.ForegroundNotificationClickListener;
import com.hdgq.locationlib.keeplive.config.KeepLiveService;
import com.hdgq.locationlib.listener.OnResultListener;


/**
 * 作者：  on 2020/7/18.
 */
public class FreightClient {

    static FreightClient client;

    public static FreightClient getClient() {
        if (client==null){
            synchronized (FreightClient.class){
                if (client==null){
                    client=new FreightClient();
                }
            }
        }
        return client;

    }


    public void startKeepLive(Application application,String title,String content,int iconId) {
        ForegroundNotification foregroundNotification =
                new ForegroundNotification(title, content, iconId, new ForegroundNotificationClickListener() {
                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {
                    }
                });
        KeepLive.startWork(application, KeepLive.RunMode.ENERGY, foregroundNotification, new KeepLiveService() {
            @Override
            public void onWorking() {
                JLog.i("开始保活");
            }

            @Override
            public void onStop() {

            }
        });

    }

    /**
     * @param context              必须为activity
     * @param appId                网络货运企业APP 的唯一标识,即为包名
     * @param appSecurity          平台申请的密钥
     * @param enterpriseSenderCode 唯一标识码  登录使用的账户
     * @param isDebug          环境 debug为测试 release为正式环境
     * @param listener             返回结果的回调函数
     */
    public void initSdk(Activity context, String appId, String
            appSecurity, String enterpriseSenderCode, boolean
                                isDebug, OnResultListener listener) {
            LocationOpenApi.init(context, appId, appSecurity, enterpriseSenderCode, isDebug?"debug":"release", listener);

    }

    /**
     * 对应某批货物的某个运单启运时，需驾驶员点击按钮确认启运，此时触发本方法
     *
     * @param context
     * @param shippingNoteInfos 运单信息数组
     * @param listener
     */
    public void start(Context context, OnResultListener listener, ShippingNoteInfo...
            shippingNoteInfos) {
        LocationOpenApi.start(context, shippingNoteInfos, listener);
    }

    /**
     * 当货物到达时，需驾驶员点击货物到达按钮，触发结束定位方法。
     *
     * @param context
     * @param shippingNoteInfos 运单数组
     * @param listener
     */
    public void stop(Context context, OnResultListener listener, ShippingNoteInfo...
            shippingNoteInfos) {
        LocationOpenApi.stop(context, shippingNoteInfos, listener);
    }

}
