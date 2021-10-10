package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.ModuleBaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.utils.clickevent.ClickEventParams;
import com.hongniu.baselibrary.utils.clickevent.ClickEventUtils;
import com.hongniu.moduleorder.R;

/**
 * 订单列表页面，此处为首页点击数据后进入的订单列表
 */
@Route(path = ArouterParamOrder.activity_order_order)
public class OrderOrderActivity extends ModuleBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_order);
        Fragment fragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation(mContext);
        int intExtra = getIntent().getIntExtra(Param.TRAN, 3);
        Bundle bundle = new Bundle();
        bundle.putInt(Param.TRAN, intExtra);
        bundle.putBoolean(Param.TYPE, true);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();

        findViewById(R.id.img_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickEventUtils.getInstance().onClick(ClickEventParams.菜单栏_下单);
                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_create).navigation(mContext);
            }
        });
    }


}
