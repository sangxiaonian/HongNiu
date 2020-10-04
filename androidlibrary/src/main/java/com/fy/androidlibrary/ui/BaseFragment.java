package com.fy.androidlibrary.ui;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fy.androidlibrary.event.BusFactory;
import com.githang.statusbar.StatusBarCompat;

/**
 * 作者： ${桑小年} on 2018/7/30.
 * 努力，为梦长留
 */
public class BaseFragment extends Fragment  {

    protected Context mContext;
    private Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return initView(inflater);

    }

    /**
     * setArguments 在fragment已经添加并且处于后台的情况下不能使用，因此使用此方法进行替代
     * @param bundle
     */
    public void setBundle(Bundle bundle){
        this.bundle=bundle;
    }

    public Bundle getBundle(){
        return bundle;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }
    /**
     * 更改状态颜色
     * @param color          状态栏颜色
     * @param lightStatusBar 状态栏图标颜色深浅 (false白色，true黑色)
     */
    protected void setToolBarColor(int color,boolean lightStatusBar){
        StatusBarCompat.setStatusBarColor(getActivity(),color, lightStatusBar);

    }
    @Override
    public void onStart() {
        super.onStart();
        if (isUseEventBus()) {
            BusFactory.getBus().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isUseEventBus()) {
            BusFactory.getBus().unregister(this);
        }
    }

    protected boolean isUseEventBus() {
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
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }
}
