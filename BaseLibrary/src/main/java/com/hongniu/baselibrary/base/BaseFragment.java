package com.hongniu.baselibrary.base;

import android.content.Context;
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
public class BaseFragment extends Fragment implements TaskControl.OnTaskListener {
    protected TaskControl.OnTaskListener taskListener;
    protected Disposable d;

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
        initData();
        initListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getUseEventBus()) {
            BusFactory.getBus().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getUseEventBus()) {
            BusFactory.getBus().unregister(this);
        }
    }

    protected boolean getUseEventBus() {
        return false;
    }

    protected View initView(LayoutInflater inflater) {
        return null;
    }


    protected void initListener() {

    }

    protected void initData() {
    }

    @Override
    public void onTaskStart(Disposable d) {
        this.d = d;
        if (taskListener != null)
            taskListener.onTaskStart(d);
    }

    @Override
    public void onTaskSuccess() {
        if (taskListener != null)
            taskListener.onTaskSuccess();
    }

    @Override
    public void onTaskDetail(float present) {
        if (taskListener != null)
            taskListener.onTaskDetail(present);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TaskControl.OnTaskListener) {
            this.taskListener = (TaskControl.OnTaskListener) context;
        }
    }

    @Override
    public void onTaskFail(Throwable e, String code, String msg) {
        if (taskListener != null)
            taskListener.onTaskFail(e, code, msg);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (d != null) {
            d.dispose();
        }
    }
}
