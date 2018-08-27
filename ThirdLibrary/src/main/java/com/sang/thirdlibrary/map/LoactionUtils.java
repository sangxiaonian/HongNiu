package com.sang.thirdlibrary.map;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.amap.api.maps.model.LatLng;
import com.sang.common.utils.ToastUtils;

/**
 * 作者： ${PING} on 2018/8/22.
 * 高德地图定位工具类
 */
public class LoactionUtils {


    private LatLng latLng;


    private  AMapLocationListener listener;

    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //可在其中解析amapLocation获取相应内容。
            if (aMapLocation.getErrorCode() == 0) {//定位成功
                latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                if (listener!=null){
                    listener.onLocationChanged(aMapLocation);
                }


            } else {
                latLng = null;
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }

        }
    };
    private AMapLocationClientOption mLocationOption;

    public void onDestroy() {
        if (mLocationClient!=null) {
            mLocationClient.onDestroy();
        }
    }

    public void upInterval(float v) {

        float dis = v / 1000;
        if (dis<10){
            setInterval(3000);
        }else if (dis<50){
            setInterval(10*1000);
        }else if (dis<200){
            setInterval(30*1000);
        }else {
            setInterval(60*1000);
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
        this.context=context;

//初始化定位
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(false);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Sport);
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


    public void startLoaction() {
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
//启动定位
    }


    /**
     * 计算当前位置距离终点位置的距离
     * @param latitude  终点坐标
     * @param longitude 终点坐标
     */
    public float caculeDis(double latitude, double longitude) {
        DPoint startLatlng =new DPoint();
        DPoint endLatlng =new DPoint();
        if (latLng!=null){
            startLatlng.setLatitude(latLng.latitude);
            startLatlng.setLongitude(latLng.longitude);

        }else {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("获取当前位置失败，请打开GPS");
            return 0;
        }
        endLatlng.setLatitude(latitude);
        endLatlng.setLongitude(longitude);
       return CoordinateConverter.calculateLineDistance(startLatlng,endLatlng)/1000;

    }

    /**
     * 两坐标之间的距离 单位米
     * @param startLatitude
     * @param startLongitude
     * @param endLatitude
     * @param endLongitude
     * @return
     */
    public float caculeDis(double startLatitude, double startLongitude, double endLatitude,
                          double endLongitude){
        DPoint startLatlng =new DPoint();
        DPoint endLatlng =new DPoint();
        startLatlng.setLatitude(startLatitude);
        startLatlng.setLongitude(startLongitude);
        endLatlng.setLatitude(endLatitude);
        endLatlng.setLongitude(endLongitude);
        return CoordinateConverter.calculateLineDistance(startLatlng,endLatlng);
    }


}
