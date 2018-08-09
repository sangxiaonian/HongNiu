package com.sang.thirdlibrary.map;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.text.TextUtils;
import android.widget.TextView;

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
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.PoiItem;
import com.sang.thirdlibrary.R;
import com.sang.thirdlibrary.map.voice.TTSController;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/8.
 * <p>
 * 算路使用的 Helper
 */
public class BaseMapHelper implements AMap.OnMyLocationChangeListener {

    private Marker mStartMarker;
    private Marker mEndMarker;

    protected AMap aMap;
    protected MyLocationStyle myLocationStyle;
    protected UiSettings mUiSettings;//定义一个UiSettings对象
    protected AMap.OnMyLocationChangeListener onMyLocationChangeListener;

    //起点坐标
    protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    //终点坐标
    protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
    protected List<NaviLatLng> mWayPointList=new ArrayList<>();//途经地点
    protected Context mContext;


    public BaseMapHelper initMap(Context context,MapView mMapView) {
        mContext=context;
        aMap = mMapView.getMap();
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.showMyLocation(true);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。

        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
        aMap.setOnMyLocationChangeListener(this);
        mStartMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.start))));
        mEndMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.end))));
        return this;

    }


    /**
     * 移动到指定位置
     * @param latitude
     * @param longitude
     */
    public void moveTo(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 18, 30, 0));
        aMap.moveCamera(mCameraUpdate);
    }

    public void addMark(BitmapDescriptor bitmap, PoiItem data) {
        LatLng latLng = new LatLng(data.getLatLonPoint().getLatitude(), data.getLatLonPoint().getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(data.getTitle());
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(bitmap);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        aMap.addMarker(markerOption);
    }


    public MarkerOptions creatMark(BitmapDescriptor bitmap, String title) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(bitmap);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        return markerOption;
    }

    public void onDestroy() {
        sList.clear();
        mWayPointList.clear();
        eList.clear();
    }


    public void setStartMarker(double latitude, double longitude) {
        setStartMarker( latitude,longitude,"");
    }

    public void setStartMarker(double latitude, double longitude, String title) {
        mStartMarker.setPosition(new LatLng( latitude,longitude));
        sList.clear();
        sList.add(new NaviLatLng(latitude,longitude));
        if (!TextUtils.isEmpty(title)){
            mStartMarker.setTitle(title);
        }
    }

    public void setEndMarker(double latitude, double longitude, String title) {
        mEndMarker.setPosition(new LatLng(latitude, longitude));
        eList.clear();
        eList.add(new NaviLatLng(latitude,longitude));
        if (!TextUtils.isEmpty(title)){
            mEndMarker.setTitle(title);
        }
    }

    public void setEndMarker(double latitude, double longitude) {
        setEndMarker( latitude,longitude,"");
    }


    public void setWayPointList(List<NaviLatLng> mWayPointList) {
        this.mWayPointList = mWayPointList;
    }

    /**
     * 设置当前计算起点坐标
     *
     * @param latitude
     * @param longitude
     */
    public void setStartPoint(double latitude, double longitude) {
        NaviLatLng latLng = new NaviLatLng(latitude, longitude);
        sList.clear();
        sList.add(latLng);
    }

    /**
     * 添加当前计算起点坐标
     *
     * @param latitude
     * @param longitude
     */
    public void addStartPoint(double latitude, double longitude) {
        NaviLatLng latLng = new NaviLatLng(latitude, longitude);
        sList.add(latLng);
    }


    /**
     * 设置当前计算终点坐标
     *
     * @param latitude
     * @param longitude
     */
    public void setEndtPoint(double latitude, double longitude) {
        NaviLatLng latLng = new NaviLatLng(latitude, longitude);
        eList.clear();
        eList.add(latLng);
    }

    /**
     * 添加当前计算终点坐标
     *
     * @param latitude
     * @param longitude
     */
    public void addEdntPoint(double latitude, double longitude) {
        NaviLatLng latLng = new NaviLatLng(latitude, longitude);
        eList.add(latLng);
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
