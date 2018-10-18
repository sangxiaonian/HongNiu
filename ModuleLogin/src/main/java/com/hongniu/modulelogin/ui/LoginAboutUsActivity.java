package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.H5Config;
import com.hongniu.modulelogin.R;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.common.widget.ItemView;

@Route(path = ArouterParamLogin.activity_about_us)
public class LoginAboutUsActivity extends BaseActivity implements View.OnClickListener {

    private ItemView itemTermsOfService;
    private ItemView itemAobutHongNiu;
    private TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_about_us);
        setToolbarTitle(getString(R.string.login_about_us));
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        itemTermsOfService = findViewById(R.id.item_terms_of_service);
        itemAobutHongNiu = findViewById(R.id.item_about_hongniu);
        tvVersion = findViewById(R.id.tv_version);
    }

    @Override
    protected void initData() {
        super.initData();
        try {
            itemAobutHongNiu.setTextRight(DeviceUtils.getVersionName(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        itemTermsOfService.setOnClickListener(this);
        itemAobutHongNiu.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.item_about_hongniu) {
        } else if (v.getId() == R.id.item_terms_of_service) {
            H5Config h5Config=new H5Config(getString(R.string.hongniu_agreement),Param.hongniu_agreement,true);
            ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(mContext);

        }

    }
}
