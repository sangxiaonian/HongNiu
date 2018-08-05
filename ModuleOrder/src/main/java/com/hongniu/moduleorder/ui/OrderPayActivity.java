package com.hongniu.moduleorder.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.moduleorder.R;

/**
 * 订单支付界面
 */
@Route(path = ArouterParamOrder.activity_order_pay)
public class OrderPayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        setToolbarDarkTitle("支付订单");
    }
}
