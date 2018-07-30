package com.hongniu.baselibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sang.common.net.listener.TaskControl;

import io.reactivex.disposables.Disposable;

/**
 * 作者： ${桑小年} on 2018/7/30.
 * 努力，为梦长留
 */
public class BaseActivity extends AppCompatActivity implements TaskControl.OnTaskListener{

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;

    }

    protected void initView(){}
    protected void initData(){}
    protected void initListener(){}

    @Override
    public void onTaskStart(Disposable d) {

    }

    @Override
    public void onTaskSuccess() {

    }

    @Override
    public void onTaskDetail(float present) {

    }

    @Override
    public void onTaskFail(Throwable e, String code, String msg) {

    }
}
