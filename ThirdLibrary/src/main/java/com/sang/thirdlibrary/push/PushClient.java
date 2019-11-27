package com.sang.thirdlibrary.push;

import android.content.Context;

import com.sang.common.utils.JLog;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UHandler;
import com.umeng.message.UmengNotificationClickHandler;

/**
 * 作者：  on 2019/11/3.
 */
public class PushClient {

    private UmengNotificationClickHandler notificationClickHandler;

    private static class InnerClass{
        private static PushClient client=new PushClient();
    }

    public static PushClient getInstance(){
        return InnerClass.client;
    }

    private PushClient() {
    }



    IUmengRegisterCallback callback;

    public PushClient addCallback(IUmengRegisterCallback callback) {
        this.callback = callback;
        return this;
    }

   private String appKey;
   private String messageSecret;

    public PushClient setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public PushClient setMessageSecret(String messageSecret) {
        this.messageSecret = messageSecret;
        return this;
    }

    public PushClient setNotificationClickHandler(UmengNotificationClickHandler notificationClickHandler) {
        this.notificationClickHandler = notificationClickHandler;
        return this;
    }

    public void register(Context context){
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
// 参数一：当前上下文context；
// 参数二：应用申请的Appkey（需替换）；
// 参数三：渠道名称；
// 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
// 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(context,
                appKey, "Umeng",
                UMConfigure.DEVICE_TYPE_PHONE, messageSecret);
//获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(context);
        mPushAgent.setNotificaitonOnForeground(true);
//注册推送服务，每次调用register方法都会回调该接口
        if (notificationClickHandler!=null) {
            mPushAgent.setNotificationClickHandler(notificationClickHandler);
        }
        mPushAgent.register(callback);
    }

}
