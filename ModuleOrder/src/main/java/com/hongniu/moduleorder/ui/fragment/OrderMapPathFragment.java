package com.hongniu.moduleorder.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.navi.view.AmapCameraOverlay;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.trace.TraceLocation;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.control.OrderMapListener;
import com.sang.common.event.BusFactory;
import com.sang.thirdlibrary.map.MapCalculateHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
    private AMap.OnMyLocationChangeListener changeListener;


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
                if (changeListener!=null){
                    changeListener.onMyLocationChange(location);
                }
            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AMap.OnMyLocationChangeListener){
            this.changeListener= (AMap.OnMyLocationChangeListener) context;
        }
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
