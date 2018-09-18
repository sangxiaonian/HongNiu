package com.sang.thirdlibrary.map;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sang.common.utils.JLog;
import com.sang.thirdlibrary.R;
import com.sang.thirdlibrary.map.utils.ErrorInfo;

/**
 * 作者： ${PING} on 2018/8/22.
 * 高德地图定位工具类
 */
public class LoactionUtils {

    private final int NOTIFYID = 2001;

    private AMapLocationListener listener;
    private int errorCode = Integer.MAX_VALUE;

    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {


            if (listener != null) {
                listener.onLocationChanged(aMapLocation);
            }

            //可在其中解析amapLocation获取相应内容。
            if (aMapLocation.getErrorCode() == 0) {//定位成功
                if (aMapLocation.getErrorCode() != errorCode) {
                    JLog.i("----------------");
                    mLocationClient.enableBackgroundLocation(NOTIFYID, buildNotification(context, context.getString(R.string.app_name) + "正在为您提供定位服务"));
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                if (aMapLocation.getErrorCode() != errorCode) {
                    mLocationClient.enableBackgroundLocation(NOTIFYID, buildNotification(context, ErrorInfo.getLoactionError(aMapLocation.getErrorCode())));
                }

            }
            errorCode = aMapLocation.getErrorCode();

        }
    };
    private AMapLocationClientOption mLocationOption;

    public void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }

    public void upInterval(float v) {
        JLog.e("更改频率："+v);
        float dis = v / 1000;
        if (dis < 10) {
            setInterval(3000);
        } else if (dis < 50) {
            setInterval(10 * 1000);
        } else if (dis < 200) {
            setInterval(30 * 1000);
        } else {
            setInterval(60 * 1000);
        }


    }

    private static class InnerClass {
        public static LoactionUtils utils = new LoactionUtils();
    }

    public static LoactionUtils getInstance() {
        return InnerClass.utils;
    }

    private LoactionUtils() {

    }

    public void setListener(AMapLocationListener listener) {
        this.listener = listener;
    }

    Context context;

    public void init(Context context) {
        this.context = context;
//初始化定位
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(false);
        mLocationOption.setDeviceModeDistanceFilter(10);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(false);
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setSensorEnable(true);
    }

    /**
     * 设置时间间隔
     *
     * @param time
     */
    public void setInterval(long time) {
        mLocationOption.setInterval(time);
        startLoaction();
    }

    public void stopLoaction() {
        if (mLocationClient != null) {
            mLocationClient.disableBackgroundLocation(true);
            mLocationClient.stopLocation();
            mLocationClient.disableBackgroundLocation(true);
        }
    }


    public void startLoaction() {
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        if (null != mLocationClient) {
            mLocationClient.enableBackgroundLocation(NOTIFYID, buildNotification(context, context.getString(R.string.app_name) + "正在为您提供定位服务"));

            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        JLog.i("开始定位");
    }


    //创建一个前台引用，用来显示数据
    private static final String NOTIFICATION_CHANNEL_NAME = "地图定位数据";
    private NotificationManager notificationManager = null;
    boolean isCreateChannel = false;

    @SuppressLint("NewApi")
    public Notification buildNotification(Context context, String loactionError) {

        Notification.Builder builder = null;
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            if (null == notificationManager) {
                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            String channelId = context.getPackageName();
            if (!isCreateChannel) {
                @SuppressLint("WrongConstant")
                NotificationChannel notificationChannel = new NotificationChannel(channelId,
                        NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_MAX);
                notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
                notificationChannel.setLightColor(Color.BLUE); //小圆点颜色
                notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                notificationChannel.enableVibration(true);//是否开启振动
                notificationChannel.setVibrationPattern(null);
                notificationChannel.setSound(null, null);
                notificationManager.createNotificationChannel(notificationChannel);
                isCreateChannel = true;
            }
            builder = new Notification.Builder(context.getApplicationContext(), channelId);
        } else {
            builder = new Notification.Builder(context.getApplicationContext());
        }
        builder.setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.app_logo)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(loactionError == null ? "" : loactionError)
                .setWhen(System.currentTimeMillis())

        ;
        //被点击
        Intent intent = new Intent();
        intent.setClass(context, MapClickReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        builder.setContentIntent(PendingIntent.getBroadcast(context, 1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT));


        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            return builder.getNotification();
        }


        return notification;
    }


}
