package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.moduleorder.R;
import com.sang.common.utils.ToastUtils;


/**
 * 订单创建结果 传入boolean 参数 Param.tran true 成功，false 失败
 */
@Route(path = ArouterParamOrder.activity_insurance_creat_result)
public class OrderInsuranceResultActivity extends BaseActivity implements View.OnClickListener {

    private boolean success;
    private Button btCheck,btFinish;
    private TextView tvInsurance,tvInsuranceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_insurance_result);
        setToolbarTitle("");
        success= getIntent().getBooleanExtra(Param.TRAN, false);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        btCheck=findViewById(R.id.bt_check);
        btFinish=findViewById(R.id.bt_finish);
        tvInsurance=findViewById(R.id.tv_insurance);
        tvInsuranceState=findViewById(R.id.tv_insurance_state);
    }

    @Override
    protected void initData() {
        super.initData();
        btCheck.setVisibility(success? View.VISIBLE:View.GONE);
        tvInsurance.setVisibility(success? View.VISIBLE:View.GONE);
        tvInsurance.setText("保单号"+"1546846544654");
        tvInsuranceState.setText(success?R.string.order_insurance_creat_success:R.string.order_insurance_creat_fail);

    }

    @Override
    protected void initListener() {
        btCheck.setOnClickListener(this);
        btFinish.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_finish) {
            ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_main).navigation(mContext);
        } else if (i == R.id.bt_check) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看保单");
        }
    }
}
