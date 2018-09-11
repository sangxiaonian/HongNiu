package com.sang.common.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;


/**
 * 作者： ${PING} on 2018/3/7.
 */

public class NotificationUtils {

    private String CHANNEL_ID = "10018";
    private String CHANNEL_NAME = "通知";
    private Uri playSound;
    private PendingIntent deletedIntent;
    private PendingIntent clickIntent;
    private String title;
    private String content;
    private int icon;



    public String getCHANNEL_ID() {
        return CHANNEL_ID;
    }

    public NotificationUtils setCHANNEL_ID(String CHANNEL_ID) {
        if (CHANNEL_ID != null) {
            this.CHANNEL_ID = CHANNEL_ID;
        }
        return this;
    }

    public String getCHANNEL_NAME() {
        return CHANNEL_NAME;
    }

    public NotificationUtils setCHANNEL_NAME(String CHANNEL_NAME) {
        if (CHANNEL_NAME != null) {
            this.CHANNEL_NAME = CHANNEL_NAME;
        }
        return this;
    }

    public Uri getPlaySound() {
        return playSound;
    }


    public NotificationUtils setPlaySound(Uri playSound) {
        this.playSound = playSound;
        return this;
    }
    public NotificationUtils setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public NotificationUtils setDeletedIntent(Context context, PendingIntent deletedIntent) {
//        Intent intent = new Intent();
//        intent.setClass(context, PlushBroadcastReceiver.class);
//        intent.putExtra(Param.msg, msg);
//        intent.putExtra(Param.OPEN, false);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.deletedIntent = deletedIntent;
        return this;

    }

    public NotificationUtils setClickIntent(Context context, PendingIntent clickIntent) {
//        Intent intent = new Intent();
//        intent.setClass(context, PlushBroadcastReceiver.class);
//        intent.putExtra(Param.msg, msg);
//        intent.putExtra(Param.OPEN, false);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.clickIntent = clickIntent;
        return this;
    }

    public NotificationUtils setTitle(String title) {
        this.title=title;
        return this;
    }

    public NotificationUtils setContent(String content) {
        this.content=content;
        return this;
    }


    public Notification creatNotification(Context context){
        NotificationCompat.Builder builder = creatNotificationBuider(context);
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle(title == null ? "新消息来了" :title);
        //第二行内容 通常是通知正文
        builder.setContentText(content== null ? "新消息来了" :content);

        if (clickIntent != null) {
            builder.setContentIntent(clickIntent);
        }
        if (deletedIntent != null) {
            builder.setContentIntent(deletedIntent);
        }

        Notification notification = builder.build();
        notification.sound = getPlaySound();
        notification.vibrate=null;
        return notification;
    }


    public void showNotification(Context context, int notifyId) {
        JLog.i("发出显示消息信息");
        NotificationManager notificationManager = getNotificationManager(context);


        notificationManager.notify(notifyId, creatNotification(context));
    }


    private NotificationManager getNotificationManager(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
            notificationManager.createNotificationChannel(creatChan(context, getCHANNEL_ID(), getCHANNEL_NAME()));
        }
        return notificationManager;
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
        builder
//                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(icon!=0?icon:0)
                .setSound(getPlaySound())
                .setVibrate(null)
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
        ;
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon!=0?icon:0));
        return builder;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel creatChan(Context context, String CHANNEL_ID, String CHANNEL_NAME) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//        channel.setBypassDnd(true);    //设置绕过免打扰模式
//        channel.canBypassDnd();       //检测是否绕过免打扰模式
        channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//设置在锁屏界面上显示这条通知
        channel.setDescription("description of this notification");
        channel.setLightColor(Color.GREEN);
        channel.setName(CHANNEL_NAME);
        channel.setShowBadge(true);
//        channel.setVibrationPattern(new long[]{100, 100, 200, 100});
        channel.setVibrationPattern(null);
        channel.enableVibration(false);
        channel.setSound(getPlaySound(), Notification.AUDIO_ATTRIBUTES_DEFAULT);
        return channel;
    }

}
