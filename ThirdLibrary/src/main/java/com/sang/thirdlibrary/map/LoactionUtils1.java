package com.sang.thirdlibrary.map;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 作者： ${PING} on 2018/8/22.
 * 高德地图定位工具类
 */
public class LoactionUtils1 {


    private AMapLocationListener listener;
    //连续定位
    private AMapLocationClientOption mLocationOption;
    //单次定位
    private AMapLocationClientOption singleOption;

    //是否是单次定位
    private boolean isSingle;


    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //可在其中解析amapLocation获取相应内容。
            if (aMapLocation.getErrorCode() == 0) {//定位成功
                if (listener != null) {
                    listener.onLocationChanged(aMapLocation);
                }


            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }

        }
    };

    public void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
    }


    private static class InnerClass {
        public static LoactionUtils1 utils = new LoactionUtils1();
    }

    public static LoactionUtils1 getInstance() {
        return InnerClass.utils;
    }

    private LoactionUtils1() {

    }

    public void setListener(AMapLocationListener listener) {
        this.listener = listener;
    }

    public void init(Context context) {

        //初始化定位
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        mLocationClient.setLocationListener(mLocationListener);
        //设置定位回调监听
        singleOption = creatOption(true);
        mLocationOption = creatOption(false);
    }

    /**
     * 设置时间间隔
     *
     * @param time
     */
    public void setInterval(long time) {
        if (mLocationOption != null) {
            mLocationOption.setInterval(time);
            startLoaction();
        }
    }

    private void setSingle(boolean isSingle) {
        this.isSingle = isSingle;
    }


    public void startLoaction() {
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(isSingle ? singleOption : mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }

    private AMapLocationClientOption creatOption(boolean isSingle) {

        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(isSingle);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Sport);
        return mLocationOption;
    }


}
