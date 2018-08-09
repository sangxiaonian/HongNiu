package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.widget.order.OrderDetailItem;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.control.OrderMapListener;
import com.hongniu.moduleorder.ui.fragment.OrderMapPathFragment;
import com.sang.common.event.BusFactory;

@Route(path = ArouterParamOrder.activity_order_map_path)
public class OrderMapPathActivity extends BaseActivity {

    private OrderMapListener helper;

    public boolean checkPath;//是否是查看轨迹

    private OrderDetailItem item;
    private Button bt;


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
        item.setDebug();


        bt = findViewById(R.id.bt);

    }


    @Override
    protected void initData() {
        super.initData();
        if (checkPath) {
            setToolbarTitle("查看轨迹");
            bt.setVisibility(View.GONE);
        } else {
            item.setVisibility(View.GONE);
            setToolbarTitle("查看路线");
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_navigation).navigation(mContext);
                OrderEvent.MapNavigationEvent event = new OrderEvent.MapNavigationEvent();
                event.setStart(31.275837, 121.457689);
                event.setEnd(31.315814, 121.393459);
                BusFactory.getBus().postSticky(event);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        helper.setStartMarker(31.275837, 121.457689, "");
        helper.setEndtMarker(31.315814, 121.393459, null);
        helper.calculate("");
    }
}
