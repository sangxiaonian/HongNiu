package com.sang.thirdlibrary.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fy.androidlibrary.utils.DeviceUtils;

/**
 * 作者： ${PING} on 2018/9/11.
 * 地图上的导航状态消息被点击
 */
public class MapClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DeviceUtils.moveToFront(context);
    }
}
