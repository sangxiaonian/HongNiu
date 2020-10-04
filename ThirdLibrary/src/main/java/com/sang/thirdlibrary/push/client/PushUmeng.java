package com.sang.thirdlibrary.push.client;

import android.content.Context;


import com.fy.androidlibrary.utils.DeviceUtils;
import com.sang.thirdlibrary.push.NotificationUtils;
import com.sang.thirdlibrary.push.inter.PlushDealWithMessageListener;
import com.sang.thirdlibrary.push.inter.PlushRegisterListener;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;



/**
 * 作者： ${PING} on 2018/6/26.
 * 友盟推送
 */

public class PushUmeng implements IPush<UMessage> {


    private final PushAgent mPushAgent;
    private int notifyId;



    private PlushRegisterListener registerListener;
    private PlushDealWithMessageListener<UMessage> dealWithMessageListener;

    public PushUmeng(Context context) {
        mPushAgent = PushAgent.getInstance(context);
        //注册rom渠道
        registerRomChannel(context);
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                if (registerListener != null) {
                    registerListener.onSuccess(deviceToken);
                }
            }

            @Override
            public void onFailure(String s, String s1) {
                if (registerListener != null) {
                    registerListener.onFailure(s, s1);
                }
            }
        });
        //自定义消息处理
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                notifyId++;
                if (dealWithMessageListener != null) {
                    dealWithMessageListener.dealMessage(context, msg);
                }else {
                    NotificationUtils.getInstance().showNotification(context, notifyId, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
    }

    private void registerRomChannel(Context context) {
        if (DeviceUtils.getDeviceBrand().equalsIgnoreCase("Huawei") || DeviceUtils.getDeviceBrand().equalsIgnoreCase("honor")) {//如果是华为手机
            //注册推送服务，每次调用register方法都会回调该接口
//            HuaWeiRegister.register(context);
        } else if (DeviceUtils.getDeviceBrand().equalsIgnoreCase("Xiaomi")) {//小米手机渠道
//            MiPushRegistar.register(context, xiaomi_id, xiaomi_key);
        } else if (DeviceUtils.getDeviceBrand().equalsIgnoreCase("meizu")) {//魅族手机渠道{
//            MeizuRegister.register(context, meizu_id, meizu_key);
        }

    }

    /**
     * 注册推送结果监听
     *
     * @param registerListener
     */
    @Override
    public void setPlushRegisterListener(PlushRegisterListener registerListener) {
        this.registerListener = registerListener;
    }


    /**
     * 处理接收到推送消息监听
     *
     * @param dealWithMessageListener 处理接收到推送消息监听
     */
    @Override
    public void setPlushDealWithMessageListener(PlushDealWithMessageListener dealWithMessageListener) {
        this.dealWithMessageListener = dealWithMessageListener;
    }

    /**
     * 点击统计自定义消息
     *
     * @param context
     * @param msg
     */
    @Override
    public void trackMsgClick(Context context, UMessage msg) {
        UTrack.getInstance(context).trackMsgClick(msg);
    }

    /**
     * 点击忽略统计自定义消息
     *
     * @param context
     * @param msg
     */
    @Override
    public void trackMsgDismissed(Context context, UMessage msg) {
        UTrack.getInstance(context).trackMsgDismissed(msg);
    }

    @Override
    public void onAppStart(Context context) {
        PushAgent.getInstance(context).onAppStart();
    }

    /**
     * 判断是否支持当前推送模式
     *
     * @param context
     * @return
     */
    @Override
    public boolean isSupport(Context context) {
        return true;
    }

}
