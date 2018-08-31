package com.sang.thirdlibrary.map;

import android.content.Context;
import android.location.Location;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;

/**
 * 作者： ${PING} on 2018/8/22.
 * 高德地图定位工具类
 */
public class MapUtils implements AMap.OnMyLocationChangeListener, AMap.OnCameraChangeListener {

    private AMap aMap;
    private OnMapChangeListener mapListener;
    public MapUtils() {

    }

    public void setMapListener(OnMapChangeListener mapListener) {
        this.mapListener = mapListener;
    }

    public void init(Context context, AMap aMap) {
        this.aMap=aMap;
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.showMyLocation(false);


        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


        aMap.setOnCameraChangeListener(this);
        aMap.setOnMyLocationChangeListener(this);
    }

    /**
     * 地图视角移动到指定位置
     */
    public void moveTo(double latitude,double longitude){
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 17, 30, 30));
        aMap.moveCamera(mCameraUpdate);

    }

    @Override
    public void onMyLocationChange(Location location) {
        if (mapListener !=null){
            mapListener.loactionChangeListener(location.getLatitude(),location.getLongitude());
        }
    }




    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (mapListener !=null){
            mapListener.onCameraChange(cameraPosition.target.latitude,cameraPosition.target.longitude);
        }
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if (mapListener !=null){
            mapListener.onCameraChangeFinish(cameraPosition.target.latitude,cameraPosition.target.longitude);
        }
    }

    public interface OnMapChangeListener{
        /**
         * 定位改变监听
         */
        void loactionChangeListener(double latitude, double longitude);

        /**
         * 地图移动变化
         */
        void onCameraChange(double latitude, double longitude);

        /**
         * 地图移动完成
         */
        void onCameraChangeFinish(double latitude, double longitude);


    }




}
