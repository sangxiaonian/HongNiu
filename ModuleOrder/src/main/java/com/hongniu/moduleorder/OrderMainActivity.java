package com.hongniu.moduleorder;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.sang.common.utils.JLog;
import com.sang.common.widget.SwitchTextLayout;

/**
 * 订单中心主页
 */
@Route(path = ArouterParamOrder.activity_order_main)
public class OrderMainActivity extends BaseActivity implements SwitchTextLayout.OnSwitchListener {

    private SwitchTextLayout switchTitle;
    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        switchTitle=findViewById(R.id.switch_title);
        switchLeft=findViewById(R.id.switch_left);
        switchRight=findViewById(R.id.switch_right);
    }

    @Override
    protected void initListener() {
        super.initListener();
        switchTitle.setListener(this);
        switchRight.setListener(this);
        switchLeft.setListener(this);
    }

    @Override
    public void onOpen(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout);
    }

    @Override
    public void onClose(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout);
    }


    private void changeState(View view){
        if (view.getId()==R.id.switch_left){
            switchLeft.setSelect(true);
            JLog.i("-----switch_left------");
            switchRight.setSelect(false);
            switchRight.closeSwitch();

        }else if (view.getId()==R.id.switch_right){
            switchRight.setSelect(true);
            JLog.i("-----switch_right------");
            switchLeft.setSelect(false);
            switchLeft.closeSwitch();
        }
    }

}
