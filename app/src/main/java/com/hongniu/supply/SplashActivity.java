package com.hongniu.supply;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.sang.common.utils.JLog;
import com.sang.common.utils.SharedPreferencesUtils;
import com.sang.common.widget.guideview.Guide;

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
                    ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_main).navigation(mContext);
                }
//                sendEmptyMessageDelayed(1,500);
            }else {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarTitle("");
        handler.sendEmptyMessageDelayed(0, 1500);
    }

    @Override
    protected boolean reciveClose() {
        return true;
    }



    @Override
    protected void onDestroy() {

        handler.removeMessages(0);
        handler.removeMessages(1);
        handler = null;

        super.onDestroy();

    }
}
