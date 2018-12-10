package com.hongniu.moduleorder.ui;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.moduleorder.R;

/**
 * 订单列表页面，此处为首页点击数据后进入的订单列表
 */
@Route(path = ArouterParamOrder.activity_order_order)
public class OrderOrderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_order);
        Fragment fragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation(mContext);
        int intExtra = getIntent().getIntExtra(Param.TRAN,3);
        Bundle bundle=new Bundle();
        bundle.putInt(Param.TRAN,intExtra);
        bundle.putBoolean(Param.TYPE,true);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }


}
