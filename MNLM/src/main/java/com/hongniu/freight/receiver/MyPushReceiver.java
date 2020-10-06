package com.hongniu.freight.receiver;

import android.content.Context;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;
import com.hongniu.thirdlibrary.push.PushBroadcastReceiver;

/**
 * 作者：  on 2019/12/16.
 */
public class MyPushReceiver extends PushBroadcastReceiver {
    @Override
    public void dealCloseMsg(Context context, String msg) {

    }

    @Override
    public void dealOpenMsg(Context context, String msg) {

        if (DeviceUtils.isRuning(context)) {
            if (DeviceUtils.isBackGround(context)) {
                DeviceUtils.moveToFront(context);
            }
        } else {
            DeviceUtils.openApp(context);
        }
        JLog.i("点击打开了通知栏");
    }
}
