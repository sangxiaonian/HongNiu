package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.modulecargoodsmatch.R;
import com.sang.common.utils.ToastUtils;

/**
 * @data 2019/5/12
 * @Author PING
 * @Description 我要抢单页面
 */
@Route(path = ArouterParams.activity_match_grap_single)
public class MatchGrapSingleActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvCarNum;
    private TextView tvCarType;
    private EditText etPrice;
    private Button btSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_grap_single);
        setToolbarTitle("XX的车货匹配");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        tvCarNum = findViewById(R.id.tv_car_num);
        tvCarType = findViewById(R.id.tv_car_type);
        btSubmit = findViewById(R.id.bt_sum);
        etPrice = findViewById(R.id.et_price);
    }

    @Override
    protected void initData() {
        super.initData();
        tvCarNum.setText("沪A12234");
        tvCarType.setText("后八轮");
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSubmit.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_sum){
            ToastUtils.getInstance().show("提交");
        }
    }
}
