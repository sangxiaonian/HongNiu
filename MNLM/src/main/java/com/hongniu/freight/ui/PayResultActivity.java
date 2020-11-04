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
import com.hongniu.freight.entity.OrderInfoBean;

/**
 * @data 2020/3/9
 * @Author PING
 * @Description 平台员工支付成功
 */
@Route(path = ArouterParamMNLM.activity_pay_result)
public class PayResultActivity extends CompanyBaseActivity implements View.OnClickListener {
    private TextView bt1;
    private TextView bt2;
    OrderInfoBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWhitToolBar("");
        setContentView(R.layout.mnlm_activity_pay_result);
        bean=getIntent().getParcelableExtra(Param.TRAN);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
    }

    @Override
    protected void initListener() {
        super.initListener();
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.bt1 == v.getId()) {
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_assign_order)
                    .withString(Param.TRAN, bean.getId())
                    .navigation(mContext);
            finish();
        } else if (R.id.bt2 == v.getId()) {
            ArouterUtils.getInstance()
                    .builder(ArouterParamMNLM.activity_order_find_car)
                    .withString(Param.TRAN, bean.getId())
                    .navigation(mContext);
            finish();
        }
    }
}
