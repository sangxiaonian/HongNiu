package com.hongniu.supply.utils;

import android.content.Context;

import com.sang.common.utils.JLog;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class DemoNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {
        JLog.i("被点击了");
        return false;
    }

    @Override
    public void onThirdPartyPushState(String pushType, String action, long resultCode) {
        super.onThirdPartyPushState(pushType, action, resultCode);
        JLog.i("第三方推送结果：" + resultCode);
    }
}