package com.hongniu.moduleorder.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderMapListener;
import com.hongniu.moduleorder.ui.fragment.OrderMapPathFragment;

@Route(path = ArouterParamOrder.activity_order_map_path)
public class OrderMapPathActivity extends BaseActivity {

    private OrderMapListener helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_calculate);
        setToolbarTitle("查看轨迹");

        OrderMapPathFragment fragment = new OrderMapPathFragment();
        helper=fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();

    }


}
