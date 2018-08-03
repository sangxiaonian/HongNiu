package com.hongniu.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sang.common.event.BusFactory;
import com.sang.common.net.listener.TaskControl;

import io.reactivex.disposables.Disposable;

/**
 * 作者： ${桑小年} on 2018/7/30.
 * 努力，为梦长留
 */
public class BaseFragment extends Fragment implements TaskControl.OnTaskListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return initView(inflater);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDataa();
        initListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getUseEventBus()){
            BusFactory.getBus().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getUseEventBus()){
            BusFactory.getBus().unregister(this);
        }
    }

    protected boolean getUseEventBus(){
        return false;
    }

    private View initView(LayoutInflater inflater) {
        return null;
    }



    private void initListener() {

    }

    private void initDataa() {
    }

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
