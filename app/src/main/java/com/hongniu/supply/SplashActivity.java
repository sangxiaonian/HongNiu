package com.hongniu.supply;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.google.gson.Gson;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.supply.net.HttpAppFactory;
import com.sang.common.utils.JLog;
import com.sang.common.utils.SharedPreferencesUtils;
import com.sang.common.widget.guideview.Guide;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0) {
                boolean aBoolean = SharedPreferencesUtils.getInstance().getBoolean(Param.showGuideActicity);
                if (!aBoolean){
                    SharedPreferencesUtils.getInstance().putBoolean(Param.showGuideActicity,true);
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                }else {
                    if (Utils.isLogin()) {
                        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_main).navigation(mContext);
                    }else {
                        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login).navigation(mContext);
                        sendEmptyMessageDelayed(1,500);
                    }
                }
            }else {
                finish();
            }
        }
    };

    private long time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarTitle("");
        handler.sendEmptyMessageDelayed(0, 1500);
        HttpAppFactory.getCarType()
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                       long l= 1500-(SystemClock.currentThreadTimeMillis()-time);
                       l=l>0?l:0;
                        handler.sendEmptyMessageDelayed(0, l+1);
                    }
                })
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        time= SystemClock.currentThreadTimeMillis();

                    }
                })
                .subscribe(new NetObserver<List<CarTypeBean>>(null) {
                    @Override
                    public void doOnSuccess(List<CarTypeBean> data) {
                        SharedPreferencesUtils.getInstance().putString(Param.CAR_TYPE,new Gson().toJson(data));
                    }
                });

    }

    @Override
    protected boolean reciveClose() {
        return true;
    }


    @Override
    public void onTaskFail(Throwable e, String code, String msg) {
        hideLoad();
    }

    @Override
    protected void onDestroy() {

        handler.removeMessages(0);
        handler.removeMessages(1);
        handler = null;

        super.onDestroy();

    }
}
