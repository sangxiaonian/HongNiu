package com.hongniu.moduleorder.ui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderMapListener;
import com.sang.thirdlibrary.map.MapCalculateHelper;

/**
 * 作者： ${PING} on 2018/8/9.
 */
public class OrderMapPathFragment extends BaseFragment implements OrderMapListener {

    private MapView mapView;
    private MapCalculateHelper helper;





    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_order_map_path, null);
        mapView = inflate.findViewById(R.id.map);

        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);

    }



    @Override
    protected void initDataa() {
        super.initDataa();
        helper = new MapCalculateHelper();
        helper.initMap(getContext(), mapView);

        helper.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

            }
        });
    }

    @Override
    public void setStartMarker(double latitude, double longitude, String title) {
        helper.setStartMarker(latitude,longitude,title);
        helper.moveTo(latitude,longitude);
    }

    @Override
    public void setEndtMarker(double latitude, double longitude, String title) {
        helper.setEndMarker(latitude,longitude,title);
        helper.moveTo(latitude,longitude);

    }

    @Override
    public void calculate(String carNum){
        helper.calculate(carNum);
    }


    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        helper.onDestroy();

    }





}
