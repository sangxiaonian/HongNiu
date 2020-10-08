package com.sang.thirdlibrary.map;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.fy.androidlibrary.utils.permission.PermissionUtils;
import com.sang.thirdlibrary.map.inter.OnLocationListener;

/**
 * 作者： ${PING} on 2018/8/22.
 * 高德地图定位工具类
 */
public class SingleLocation {


    OnLocationListener onLocationChanged;
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //可在其中解析amapLocation获取相应内容。
            if (aMapLocation.getErrorCode() == 0) {//定位成功
                if (onLocationChanged != null) {
                    onLocationChanged.onSuccessLocation(aMapLocation.getLongitude(),aMapLocation.getLatitude());
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                if (onLocationChanged != null) {
                    onLocationChanged.onFailLocation(aMapLocation.getErrorCode(),aMapLocation.getErrorInfo());
                }

            }
            stopLoaction();
        }
    };
    private AMapLocationClientOption mLocationOption;




    Context context;

    public SingleLocation(Context context) {
        this.context = context;
//初始化定位
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        //设置单次定位
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(false);
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setSensorEnable(true);
    }

    public void stopLoaction() {
        if (mLocationClient != null) {
            mLocationClient.disableBackgroundLocation(true);
            mLocationClient.stopLocation();
            mLocationClient.disableBackgroundLocation(true);
        }
    }

    public void setListener(OnLocationListener listener) {
        this.onLocationChanged = listener;
    }
    public void startLoaction(Activity activity) {

        PermissionUtils.applyMap(activity, new PermissionUtils.onApplyPermission() {
            @Override
            public void hasPermission() {
                /**
                 * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
                 */
                if (null != mLocationClient) {

                    if (onLocationChanged!=null){
                        onLocationChanged.onStartLocation();
                    }

                    mLocationClient.setLocationOption(mLocationOption);
                    //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                    mLocationClient.stopLocation();
                    mLocationClient.startLocation();
                }
            }

            @Override
            public void noPermission() {

            }
        });


    }







}
