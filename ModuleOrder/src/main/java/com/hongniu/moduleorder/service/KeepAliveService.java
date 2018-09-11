package com.hongniu.moduleorder.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.hongniu.moduleorder.OrderMainActivity;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.utils.LoactionUpUtils;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.NotificationUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 作者： ${PING} on 2018/9/10.
 */
public class KeepAliveService extends Service {

    private MyBinder myBinder = new MyBinder() ;
    private LoactionUpUtils upLoactionUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        JLog.e( "onCreate") ;
        //设置前台Service
    }
    private void setForeGroundService() {
        NotificationUtils notification = new NotificationUtils();
        notification.setTitle(getString(R.string.app_name));
        notification.setContent("正在为您记录轨迹服务");
        notification.setIcon(R.mipmap.app_logo);
        Intent resultIntent = new Intent(this, OrderMainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(OrderMainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        notification.setClickIntent(this,resultPendingIntent);
        //前台 service
        startForeground(15,notification.creatNotification(this));
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JLog.e( "onStartCommand") ;

        setForeGroundService();

        BusFactory.getBus().register(this);
        return START_STICKY;
    }

    //开始或停止记录用户位置信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartLoactionMessage(OrderEvent.UpLoactionEvent event) {
        if (event != null) {
            //如果有正在运输中的订单，则此时获取到用户的位置信息
            if (event.start) {//开始记录数据
                if (upLoactionUtils != null) {
                    upLoactionUtils.onDestroy();
                }


                JLog.i("-------服务接收到运输相关信息-----");

                upLoactionUtils = new LoactionUpUtils();
                upLoactionUtils.setOrderInfor(event.orderID, event.cardID, event.destinationLatitude, event.destinationLongitude);
            } else {
                if (upLoactionUtils != null) {
                    upLoactionUtils.onDestroy();
                }
            }
        }
    }







    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JLog.e( "onDestroy");
        stopForeground(true) ;
    }

    class MyBinder extends Binder {
        public void processTask(){
            JLog.e( "操作一些任务") ;
        }
    }



}
