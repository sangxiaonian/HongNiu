package com.fy.androidlibrary.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fy.androidlibrary.R;
import com.fy.androidlibrary.event.BusFactory;
import com.githang.statusbar.StatusBarCompat;

/**
 * @data 2019/10/28
 * @Author PING
 * @Description 所有Activity的基础类，切勿在此Activity内做任何特殊逻辑处理，所有逻辑必须具有通用性，任何Activity皆可以通用
 * 如果有特殊逻辑需要处理，继承此Activity基础上进行处理
 */
public class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void setOrientation(int orientation){
        setRequestedOrientation(orientation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isUseEventBus()) {
            BusFactory.getBus().register(this);
        }
    }

    /**
     * 更改状态颜色
     * @param color          状态栏颜色
     * @param lightStatusBar 状态栏图标颜色深浅
     */
    protected void setToolBarColor(int color,boolean lightStatusBar){
        StatusBarCompat.setStatusBarColor(this,color, lightStatusBar);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isUseEventBus()) {
            BusFactory.getBus().unregister(this);
        }
    }

    protected boolean isUseEventBus() {
        return false;
    }

    protected void initView(){}
    protected void initData(){}
    protected void initListener(){}

}
