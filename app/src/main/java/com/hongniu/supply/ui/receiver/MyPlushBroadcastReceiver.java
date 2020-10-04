package com.hongniu.supply.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.supply.entity.PushBean;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.sang.thirdlibrary.push.NotificationUtils;
import com.sang.thirdlibrary.push.config.PushConfig;
import com.umeng.message.entity.UMessage;

/**
 * 作者： ${PING} on 2017/12/22.
 * <p>
 * 此处为友盟推送点击后的广播处理
 * <p>
 * 友盟推送的消息     1 、消息中心 放在 custom 字段中
 * 2、跳转到指定页面的消息，存放在 uMessage.extra.get("data")
 */

public class MyPlushBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean open = intent.getBooleanExtra(PushConfig.OPEN, false);
        String stringExtra = intent.getStringExtra(PushConfig.msg);
        UMessage uMessage = null;
        //如果是友盟推送的消息
        //如果没有任何内容，默认打开App
        if (stringExtra == null) {
            if (DeviceUtils.isRuning(context)) {
                if (DeviceUtils.isBackGround(context)) {
                    DeviceUtils.moveToFront(context);
                }
            } else {
                DeviceUtils.openApp(context);
            }
            return;
        } else {
            //如果有内容，先解析一下是否是友盟推送的信息
            try {
                uMessage = new Gson().fromJson(stringExtra, UMessage.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                DeviceUtils.openApp(context);
            }
            if (uMessage == null) {
                return;
            }
        }

        if (open) {

            String custom = uMessage.custom;
            try {
                PushBean pushBean = new Gson().fromJson(custom, PushBean.class);
                dealPush(context,pushBean);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }


    }

    private void dealPush(Context context, PushBean pushBean) {
        if (pushBean != null) {
            if ("openapp".equals(pushBean.action)) {
                //打开app
                DeviceUtils.openApp(context);
            } else if ("opencarmatchlist".equals(pushBean.action)) {
                //打开车货匹配接单列表
                ArouterUtils.getInstance()
                        .builder(ArouterParamsMatch.activity_match_estimate_order)
                        .withInt(Param.TYPE, 1)
                        .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation(context);

            } else if ("openevaluationlist".equals(pushBean.action)) {
                //打开车货匹配我的找车
                ArouterUtils.getInstance()
                        .builder(ArouterParamsMatch.activity_match_my_order)
                        .withInt(Param.TYPE, 0)
                        .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation(context);

            }else {
                DeviceUtils.openApp(context);
            }
        }
    }

}
