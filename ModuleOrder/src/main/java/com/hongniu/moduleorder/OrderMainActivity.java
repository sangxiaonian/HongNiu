package com.hongniu.moduleorder;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.moduleorder.widget.OrderMainTitlePop;
import com.sang.common.utils.JLog;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;

/**
 * 订单中心主页
 */
@Route(path = ArouterParamOrder.activity_order_main)
public class OrderMainActivity extends BaseActivity implements SwitchTextLayout.OnSwitchListener, OrderMainTitlePop.OnOrderMainClickListener, OnPopuDismissListener {

    private SwitchTextLayout switchTitle;
    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;

    private OrderMainTitlePop titlePop;


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
        titlePop = new OrderMainTitlePop(this);
        switchTitle = findViewById(R.id.switch_title);
        switchLeft = findViewById(R.id.switch_left);
        switchRight = findViewById(R.id.switch_right);
    }

    @Override
    protected void initListener() {
        super.initListener();
        switchTitle.setListener(this);
        switchRight.setListener(this);
        switchLeft.setListener(this);
        titlePop.setListener(this);
        titlePop.setOnDismissListener(this);
    }

    @Override
    public void onOpen(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout);

        titlePop.show(switchTitle);
    }

    @Override
    public void onClose(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout);
        titlePop.dismiss();
    }


    private void changeState(View view) {
        if (view.getId() == R.id.switch_left) {
            switchLeft.setSelect(true);
            switchRight.setSelect(false);
            switchRight.closeSwitch();

        } else if (view.getId() == R.id.switch_right) {
            switchRight.setSelect(true);
            switchLeft.setSelect(false);
            switchLeft.closeSwitch();
        }
    }

    /**
     * 顶部角色类型被选中点击的时候
     *
     * @param popu
     * @param position
     */
    @Override
    public void onMainClick(OrderMainTitlePop popu, int position) {
        popu.dismiss();
        changeStaff(position);
    }

    /**
     * 切换用户角色
     *
     * @param position
     */
    private void changeStaff(int position) {
        switch (position) {
            case 0:
                switchTitle.setTitle("我是货主");
                break;
            case 1:
                switchTitle.setTitle("我是车主");
                break;
            case 2:
                switchTitle.setTitle("我是司机");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (titlePop.isShow()) {
            titlePop.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Popu dimiss 监听
     *
     * @param popu   当前popu
     * @param target 目标View
     */
    @Override
    public void onPopuDismsss(BasePopu popu, View target) {
        if (target instanceof SwitchTextLayout){
            ((SwitchTextLayout) target).closeSwitch();
        }
    }
}
