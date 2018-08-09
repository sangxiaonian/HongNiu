package com.hongniu.moduleorder.ui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.navi.view.RouteOverLay;
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

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            helper.moveToStart();
        }
    };





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
    protected void initData() {
        super.initData();
        helper = new MapCalculateHelper();
        helper.initMap(getContext(), mapView);
        helper.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (moveToStar){
                    handler.sendEmptyMessageDelayed(0,200);
                }
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


    private boolean moveToStar;

    @Override
    public void calculate(String carNum){
        helper.calculate(carNum);
        helper.moveToStart();
        moveToStar=true;

    }



    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        helper.onDestroy();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }





}
