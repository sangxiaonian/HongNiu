package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.R;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CARGO_OWNER;

/**
 * @data 2018/10/12
 * @Author PING
 * @Description 订单搜索结果页面
 */
@Route(path = ArouterParamOrder.activity_order_search_result)
public class OrderSearchResultActivity extends BaseActivity {


    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search_result);

        initView();
        initData();
        initListener();


    }

    @Override
    protected void initView() {
        super.initView();
        fragment= (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_search).navigation();
        OrderDetailItemControl.RoleState roleState = (OrderDetailItemControl.RoleState) getIntent().getSerializableExtra(Param.TRAN);
        if (roleState == null) {
            roleState = CARGO_OWNER;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(Param.TRAN, roleState);
        fragment.setArguments(bundle);
    }

    @Override
    protected void initData() {
        super.initData();
        setToolbarTitle(getIntent().getStringExtra(Param.TITLE));
        getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment).commit();
    }
}
