package com.sang.thirdlibrary.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;


import com.google.gson.Gson;
import com.sang.thirdlibrary.R;
import com.sang.thirdlibrary.push.config.PushConfig;
import com.umeng.message.entity.UMessage;

import java.util.Random;


//import android.app.NotificationChannel;

/**
 * 作者： ${PING} on 2018/3/7.
 */

public class NotificationUtils {

    private static NotificationUtils utils;
    public String CHANNEL_ID = "12654";
    public String CHANNEL_NORMAL_ID = "12655";
    public String CHANNEL_NAME = "消息通知";

    private Class receiver;
    private int notify_sound;


    public static NotificationUtils getInstance() {

        if (utils == null) {
            synchronized (NotificationUtils.class) {
                if (utils == null) {
                    utils = new NotificationUtils();
                }
            }
        }
        return utils;
    }

    public void setReceiver(Class receiver) {
        this.receiver = receiver;
    }

    private PendingIntent getDeletedIntent(Context context, String msg) {
        Intent intent = new Intent();
        intent.setClass(context, receiver);
        intent.putExtra(PushConfig.msg, msg);
        intent.putExtra(PushConfig.OPEN, false);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getBroadcast(context, new Random().nextInt(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getClickIntent(Context context, String msg) {
        Intent intent = new Intent();
        intent.setClass(context, receiver);
        intent.putExtra(PushConfig.msg, msg);
        intent.putExtra(PushConfig.OPEN, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getBroadcast(context, new Random().nextInt(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private int notifyId;

    public void showNotification(Context context, UMessage msg) {
        notifyId++;
        showNotification(context, notifyId, msg);
    }

    public void showNotification(Context context, int notifyId, UMessage msg) {
        NotificationCompat.Builder builder = creatNotificationBuider(context);
        NotificationManager notificationManager = getNotificationManager(context);


        builder.setTicker(msg.ticker == null ? "新消息来了" : msg.ticker);
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle(msg.title == null ? "新消息来了" : msg.title);
        //第二行内容 通常是通知正文
        builder.setContentText(msg.text == null ? "新消息来了" : msg.text);
        builder.setContentIntent(getClickIntent(context, new Gson().toJson(msg)))
                .setDeleteIntent(getDeletedIntent(context, new Gson().toJson(msg)));
        Notification notification = builder.build();
        if (showVoice()) {
            notification.sound = getSound(context);
        }
        notificationManager.notify(notifyId, notification);
    }


    public void showNotification(Context context, int notifyId, String ticker, String title, String text) {
        NotificationCompat.Builder builder = creatNotificationBuider(context);
        NotificationManager notificationManager = getNotificationManager(context);
        builder.setTicker(ticker == null ? "新消息来了" : ticker);
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle(title == null ? "新消息来了" : title);
        //第二行内容 通常是通知正文
        builder.setContentText(text == null ? "新消息来了" : text);
        Notification notification = builder.build();
        if (showVoice()) {
            notification.sound = getSound(context);
        }
        notificationManager.notify(notifyId, notification);
    }


    private NotificationManager getNotificationManager(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
            notificationManager.createNotificationChannel(creatChan(context, getCHANNEL_ID(), CHANNEL_NAME));
        }


        return notificationManager;
    }


    public String getCHANNEL_ID() {
        return showVoice() ? CHANNEL_ID : CHANNEL_NORMAL_ID;
    }

    private Uri getSound(Context context) {
        String uri = "android.resource://" + context.getPackageName() + "/" + notify_sound;
        return Uri.parse(uri);
    }


    private NotificationCompat.Builder creatNotificationBuider(Context context) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, getCHANNEL_ID());
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标

        if (showVoice()) {
            builder.setSound(getSound(context));
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }
        //下拉显示的大图标
        builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        return builder;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel creatChan(Context context, String CHANNEL_ID, String CHANNEL_NAME) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.setBypassDnd(true);    //设置绕过免打扰模式
        channel.canBypassDnd();       //检测是否绕过免打扰模式
        channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//设置在锁屏界面上显示这条通知
        channel.setDescription("description of this notification");
        channel.setLightColor(Color.GREEN);
        channel.setName(CHANNEL_NAME);
        channel.setShowBadge(true);
        channel.setVibrationPattern(new long[]{100, 100});
        channel.enableVibration(true);

        if (showVoice()) {
            channel.setSound(getSound(context), Notification.AUDIO_ATTRIBUTES_DEFAULT);
        }

        return channel;
    }


    public boolean showVoice() {
        return notify_sound!=0;
    }


    /**
     * 设置是否使用自定义语音
     * @param notify_sound
     * @return
     */
    public NotificationUtils setSound(int notify_sound) {
        this.notify_sound=notify_sound;
        return this;
    }
}
