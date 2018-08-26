package com.hongniu.supply;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.supply.net.HttpAppFactory;
import com.sang.common.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                boolean aBoolean = SharedPreferencesUtils.getInstance().getBoolean(Param.showGuideActicity);
                if (!aBoolean) {
                    SharedPreferencesUtils.getInstance().putBoolean(Param.showGuideActicity, true);
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                } else {
                    if (Utils.isLogin()) {
                        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_main).navigation(mContext);
                    } else {
                        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login).navigation(mContext);
                        sendEmptyMessageDelayed(1, 500);
                    }
                }
            } else {
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
        if (Utils.isLogin()) {
            HttpAppFactory.getRoleType()
                    .doFinally(new Action() {
                        @Override
                        public void run() throws Exception {
                            long l = 1500 - (SystemClock.currentThreadTimeMillis() - time);
                            handler.sendEmptyMessageDelayed(0, l > 0 ? l : 1);
                        }
                    })
                    .subscribe(new NetObserver<RoleTypeBean>(null) {

                        @Override
                        public void onSubscribe(Disposable d) {
                            super.onSubscribe(d);
                            time = SystemClock.currentThreadTimeMillis();
                        }

                        @Override
                        public void doOnSuccess(RoleTypeBean data) {
                            EventBus.getDefault().postSticky(data);
                        }

                        @Override
                        public void onError(Throwable e) {
                            EventBus.getDefault().postSticky(new RoleTypeBean());
                        }
                    });
        } else {
            EventBus.getDefault().postSticky(new RoleTypeBean());
            handler.sendEmptyMessageDelayed(0, 1500);

        }


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
