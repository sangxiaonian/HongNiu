package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.H5Config;

/**
 * @data 2020/2/10
 * @Author PING
 * @Description 关于我们页面
 */
@Route(path = ArouterParamMNLM.activity_about_us)

public class AboutUsActivity extends CompanyBaseActivity implements View.OnClickListener {
    private TextView tv_privacy;
    private TextView tv_agreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_about_us);
        setWhitToolBar("关于我们");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();

        tv_privacy = findViewById(R.id.tv_privacy);
        tv_agreement = findViewById(R.id.tv_agreement);
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_privacy.setOnClickListener(this);
                tv_agreement.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tv_privacy){
            H5Config h5Config = new H5Config(tv_privacy.getText().toString(), Param.hongniu_privacy, true);
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(mContext);

        }else  if (v.getId()==R.id.tv_agreement) {

            H5Config h5Config = new H5Config(tv_agreement.getText().toString(), Param.agreement, true);
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(mContext);

        }
    }
}
