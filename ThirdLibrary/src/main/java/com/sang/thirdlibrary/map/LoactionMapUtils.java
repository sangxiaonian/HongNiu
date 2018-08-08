package com.sang.thirdlibrary.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.PoiItem;
import com.sang.thirdlibrary.R;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/8.
 */
public class LoactionMapUtils implements AMap.OnMyLocationChangeListener {

    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private AMap.OnMyLocationChangeListener onMyLocationChangeListener;

    public void moveTo(PoiItem data) {
//        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(data.getLatLonPoint().getLatitude(),data.getLatLonPoint().getLatitude()),18,30,0));
        LatLng latLng = new LatLng(data.getLatLonPoint().getLatitude(), data.getLatLonPoint().getLongitude());
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 18, 30, 0));


        aMap.moveCamera(mCameraUpdate);
    }

    public void addMark(BitmapDescriptor bitmap, PoiItem data) {
        LatLng latLng = new LatLng(data.getLatLonPoint().getLatitude(), data.getLatLonPoint().getLongitude());

        List<Marker> markers = aMap.getMapScreenMarkers();
        if (markers != null && markers.size() > 1) {
            Marker marker = markers.get(1);
            marker.setTitle(data.getTitle());
            marker.setPosition(latLng);
        } else {
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);
            markerOption.title(data.getTitle());
            markerOption.draggable(true);//设置Marker可拖动
            markerOption.icon(bitmap);
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果
            aMap.addMarker(markerOption);

        }
    }


    private static class InnerMpa {
        public static LoactionMapUtils utils = new LoactionMapUtils();
    }

    private LoactionMapUtils() {
    }

    public static LoactionMapUtils getInstance() {
        return InnerMpa.utils;
    }

    public LoactionMapUtils initMap(MapView mMapView) {
        aMap = mMapView.getMap();

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.showMyLocation(true);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。


//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
//以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮

        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置

        aMap.setOnMyLocationChangeListener(this);
        return this;

    }

    public void setOnMyLocationChangeListener(AMap.OnMyLocationChangeListener listener) {
        this.onMyLocationChangeListener = listener;
    }

    @Override
    public void onMyLocationChange(Location location) {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        if (onMyLocationChangeListener != null) {
            onMyLocationChangeListener.onMyLocationChange(location);
        }
    }
}
