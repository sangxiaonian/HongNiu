package com.hongniu.supply;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            new ArouterUtils().builder(ArouterParamLogin.activity_login).navigation(mContext);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.sendEmptyMessageDelayed(1,500);
    }



}
