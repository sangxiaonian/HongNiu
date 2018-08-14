package com.hongniu.supply;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.sang.common.utils.SharedPreferencesUtils;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            boolean aBoolean = SharedPreferencesUtils.getInstance().getBoolean(Param.showGuideActicity);
            if (!aBoolean){
                SharedPreferencesUtils.getInstance().putBoolean(Param.showGuideActicity,true);
                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_guide_activity).navigation(mContext);

            }else {
                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_main).navigation(mContext);
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarTitle("");
        handler.sendEmptyMessageDelayed(1, 500);
    }


    @Override
    protected void onDestroy() {

        handler.removeMessages(0);
        handler = null;

        super.onDestroy();

    }
}
