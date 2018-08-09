package com.hongniu.moduleorder.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.NaviLatLng;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.moduleorder.R;
import com.sang.thirdlibrary.map.NavigationUtils;

/**
 * 导航使用的Activity，提供导航和轨迹绘制功能
 */
@Route(path = ArouterParamOrder.activity_order_navigation)
public class OrderNavigationActivity extends BaseActivity {

    private AMapNaviView mAMapNaviView;
    private NavigationUtils navigationUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_navigation);
        setToolbarTitle("查看轨迹");
        navigationUtils = new NavigationUtils(this);
        navigationUtils.setDebug();

        initView();
        mAMapNaviView.onCreate(savedInstanceState);


        mAMapNaviView.setAMapNaviViewListener(navigationUtils);
        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setTilt(0);
        mAMapNaviView.setViewOptions(options);


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


}
