package com.hongniu.moduleorder.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.sang.thirdlibrary.map.MapControl;
import com.sang.thirdlibrary.map.NavigationUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 导航使用的Activity，提供导航和轨迹绘制功能
 */
@Route(path = ArouterParamOrder.activity_order_navigation)
public class OrderNavigationActivity extends BaseActivity implements MapControl.OnMapInitListener {

    private AMapNaviView mAMapNaviView;
    private NavigationUtils navigationUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_navigation);
        setToolbarTitle("查看轨迹");


        initView();
        mAMapNaviView.onCreate(savedInstanceState);
        navigationUtils = new NavigationUtils(this);
        navigationUtils.setListener(this);
        navigationUtils.setDebug();

        mAMapNaviView.setAMapNaviViewListener(navigationUtils);

        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setTilt(0);
        mAMapNaviView.setViewOptions(options);


    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEndEvent(OrderEvent.MapNavigationEvent event) {
        if (event != null && event.options != null) {
//            RouteOverLay options1 = event.options;
//            options1.addToMap();

//            options.setRouteOverlayOptions(options1.getRouteOverlayOptions());



        }
    }


    @Override
    protected boolean getUseEventBus() {
        return true;
    }


    @Override
    protected void initView() {
        super.initView();
        mAMapNaviView = findViewById(R.id.navi_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
        navigationUtils.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();
        navigationUtils.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
        navigationUtils.onDestroy();
    }


    @Override
    public void onInitSuccess() {

    }

    @Override
    public void onCalculateRouteSuccess() {
        navigationUtils.startNavi();
    }
}
