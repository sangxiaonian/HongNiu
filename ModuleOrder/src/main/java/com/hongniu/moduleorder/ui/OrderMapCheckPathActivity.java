package com.hongniu.moduleorder.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceListener;
import com.amap.api.trace.TraceLocation;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.widget.order.OrderDetailItem;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.control.OrderMapListener;
import com.hongniu.moduleorder.ui.fragment.OrderMapPathFragment;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看轨迹
 */
@Route(path = ArouterParamOrder.activity_map_check_path)
public class OrderMapCheckPathActivity extends BaseActivity implements TraceListener {


    private MapView mapView;

    private OrderDetailItem item;
    private AMap aMap;
    private LBSTraceClient mTraceClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_check_path);
        initView();
        initData();
        initListener();
        mapView.onCreate(savedInstanceState);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void initView() {
        super.initView();
        mapView = findViewById(R.id.map);
        aMap = mapView.getMap();
        item = findViewById(R.id.item_order);
        item.setDebug();
        item.hideButton(true);


    }


    @Override
    protected void initData() {
        super.initData();
        setToolbarTitle("查看轨迹");
        mTraceClient = LBSTraceClient.getInstance(this);

    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderEvent.CheckPathEvent event) {
        if (event != null &&event.getBean()!=null) {
            OrderDetailBean bean = event.getBean();
            List<TraceLocation> locations = event.getLocations();
            if (locations!=null){
                showLoad();

                mTraceClient.queryProcessedTrace(0,locations, LBSTraceClient.TYPE_AMAP,this);
            }
        }
        BusFactory.getBus().removeStickyEvent(event);
    }

    public void moveTo(double latitude,double longitude){
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 12, 30, 0));
        aMap.moveCamera(mCameraUpdate);
    }


    @Override
    public void onRequestFailed(int i, String s) {
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("轨迹绘制失败："+s);
        hideLoad();
    }

    @Override
    public void onTraceProcessing(int i, int i1, List<LatLng> list) {

    }

    @Override
    public void onFinished(int i, List<LatLng> list, int i1, int i2) {
        hideLoad();
        moveTo(list.get(0).latitude,list.get(0).longitude);
        mapView.getMap().addPolyline(new PolylineOptions().
                        addAll(list)
                    .width(10).color(Color.argb(255, 1, 1, 1))
        );
    }
}
