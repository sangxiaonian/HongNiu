package com.hongniu.freight.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.freight.widget.dialog.RuleAlertDialog;

public class SplashActivity extends CompanyBaseActivity implements RuleAlertDialog.IDialog {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            LoginInfo loginInfo = InfoUtils.getLoginInfo();
            PersonInfor myInfo = InfoUtils.getMyInfo();
            if (loginInfo != null) {
                ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_main)
                        .navigation(mContext);
            } else {
                ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_login)
                        .navigation(mContext);
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_splash);
        //切换url
        setWhitToolBar("");
        boolean rule = SharedPreferencesUtils.getInstance().getBoolean("RULE");
        if (rule){
            handler.sendEmptyMessageDelayed(0, 500);

        }else {
            new RuleAlertDialog(this,this).show();
        }
    }

    @Override
    public void onClickReportAlert(boolean isPositive) {
        SharedPreferencesUtils.getInstance().putBoolean("RULE",true);
        handler.sendEmptyMessage(0);
    }
}
