package com.hongniu.moduleorder.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.moduleorder.R;

/**
 * 创建订单
 */
@Route(path = ArouterParamOrder.activity_order_create)
public class OrderCreatOrderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creat_order);
        setToolbarTitle(getString(R.string.order_create_order));
    }
}
