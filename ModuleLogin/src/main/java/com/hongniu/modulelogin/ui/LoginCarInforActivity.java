package com.hongniu.modulelogin.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.modulelogin.R;

/**
 * 车辆新增、修改界面
 */
@Route(path = ArouterParamLogin.activity_car_infor)
public class LoginCarInforActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_car_infor);
        setToolbarTitle("修改车辆");
    }
}
