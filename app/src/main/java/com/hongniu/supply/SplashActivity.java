package com.hongniu.supply;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.companylibrary.manager.PrivacyManger;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.ModuleBaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.supply.manager.ThirdManager;
import com.hongniu.supply.weight.RuleAlertDialog;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

public class SplashActivity extends ModuleBaseActivity implements RuleAlertDialog.IDialog {

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
                        ArouterUtils.getInstance().builder(ArouterParamsApp.activity_main).navigation(mContext);
                    } else {
                        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login).navigation(mContext);
                    }
                }
                sendEmptyMessageDelayed(1, 500);
            } else {
                finish();
            }
        }
    };

    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getIntent() != null) {
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        }
        setToolbarTitle("");
        boolean rule = PrivacyManger.INSTANCE.isAgreePrivacy();
        if (rule) {
            jump2Next();
        } else {
            new RuleAlertDialog(this, this).show();
        }


    }

    private void jump2Next() {
        if (Utils.isLogin()) {
            HttpAppFactory.getRoleType()
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
                            super.onError(e);
                            EventBus.getDefault().postSticky(new RoleTypeBean());
                            long l = 1500 - (SystemClock.currentThreadTimeMillis() - time);
                            handler.sendEmptyMessageDelayed(0, l > 10 ? l : 1);
                        }

                        @Override
                        public void onComplete() {
                            super.onComplete();
                            long l = 1500 - (SystemClock.currentThreadTimeMillis() - time);
                            handler.sendEmptyMessageDelayed(0, l > 10 ? l : 1);
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
    public void onTaskFail(Throwable e, int code, String msg) {
        hideLoad();
    }

    @Override
    protected void onDestroy() {

        if (handler != null) {
            handler.removeMessages(0);
            handler.removeMessages(1);
            handler = null;
        }


        super.onDestroy();

    }

    @Override
    public void onClickReportAlert(boolean isPositive) {
        if (isPositive) {
            PrivacyManger.INSTANCE.setAgreePrivacy(true);
            ThirdManager.INSTANCE.init(this, BuildConfig.DEBUG);
            jump2Next();
        } else {
            finish();
        }
    }
}
