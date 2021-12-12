package com.sang.thirdlibrary.push.client.umen;

import android.content.Context;


import com.fy.androidlibrary.utils.DeviceUtils;
import com.sang.thirdlibrary.push.NotificationUtils;
import com.sang.thirdlibrary.push.client.IPush;
import com.sang.thirdlibrary.push.inter.PlushDealWithMessageListener;
import com.sang.thirdlibrary.push.inter.PlushRegisterListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.api.UPushRegisterCallback;
import com.umeng.message.entity.UMessage;



/**
 * 作者： ${PING} on 2018/6/26.
 * 友盟推送
 */

public class PushUmeng implements IPush<UMessage> {


    private  PushAgent mPushAgent;
    private int notifyId;



    private PlushRegisterListener registerListener;
    private PlushDealWithMessageListener<UMessage> dealWithMessageListener;
    private boolean isAgree;


    public PushUmeng() { }

    private void configUmeng(Context context){
        UMConfigure.init(context, PushConstants.APP_KEY, PushConstants.CHANNEL,
                UMConfigure.DEVICE_TYPE_PHONE, PushConstants.MESSAGE_SECRET);
        mPushAgent = PushAgent.getInstance(context);
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
        mPushAgent.register(new UPushRegisterCallback() {

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

        registerRomChannel(context);
    }

    public void init(final Context context, boolean isAgreed){
        UMConfigure.setLogEnabled(true);
        //预初始化
        preInit(context);
        this.isAgree=isAgreed;
        if (!isAgreed){
            return;
        }
        boolean isMainProcess = UMUtils.isMainProgress(context);
        if (isMainProcess) {
            //启动优化：建议在子线程中执行初始化
            new Thread(new Runnable() {
                @Override
                public void run() {
                    configUmeng(context.getApplicationContext());
                }
            }).start();
        } else {
            //若不是主进程（":channel"结尾的进程），直接初始化sdk，不可在子线程中执行
            configUmeng(context.getApplicationContext());
        }
    }

    private void preInit(Context context){
        //解决厂商通知点击时乱码等问题
        PushAgent.setup(context, PushConstants.APP_KEY, PushConstants.MESSAGE_SECRET);
        UMConfigure.preInit(context, PushConstants.APP_KEY, PushConstants.CHANNEL);
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
        if (isAgree) {
            PushAgent.getInstance(context).onAppStart();
        }
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
