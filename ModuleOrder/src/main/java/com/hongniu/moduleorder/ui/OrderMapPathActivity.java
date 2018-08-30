package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CloseActivityEvent;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.widget.order.OrderDetailItem;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.control.OrderMapListener;
import com.hongniu.moduleorder.ui.fragment.OrderMapPathFragment;
import com.sang.common.event.BusFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

@Route(path = ArouterParamOrder.activity_order_map_path)
public class OrderMapPathActivity extends BaseActivity {

    private OrderMapListener helper;

    public boolean checkPath;//是否是查看轨迹

    private OrderDetailItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_calculate);
        checkPath = getIntent().getBooleanExtra(Param.TRAN, true);
        initView();
        initData();
        initListener();

    }

    @Override
    protected void initView() {
        super.initView();
        OrderMapPathFragment fragment = new OrderMapPathFragment();
        helper = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        item = findViewById(R.id.item_order);
        item.hideButton(true);


    }


    @Override
    protected void initData() {
        super.initData();
        setToolbarTitle("查看轨迹");

    }

    @Override
    protected void initListener() {
        super.initListener();

    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}
