package com.sang.thirdlibrary.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sang.thirdlibrary.push.config.PushConfig;


/**
 * 作者： ${PING} on 2017/12/22.
 * <p>
 * 此处为友盟推送点击后的广播处理
 * <p>
 */

public abstract class PushBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean open = intent.getBooleanExtra(PushConfig.OPEN, false);
        String msg = intent.getStringExtra(PushConfig.msg);
        if (open) {
            dealOpenMsg(context, msg);
        } else {
            dealCloseMsg(context, msg);
        }


    }

    /**
     * 忽略通知栏消息
     *
     * @param context
     * @param msg
     */
    public abstract void dealCloseMsg(Context context, String msg);

    /**
     * 点击通知栏消息
     *
     * @param context
     * @param msg
     */
    public abstract void dealOpenMsg(Context context, String msg);

}
