package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.widget.RadioIconButton;
import com.hongniu.baselibrary.widget.RadioIconGroup;
import com.hongniu.modulecargoodsmatch.R;
import com.sang.common.utils.SharedPreferencesUtils;

/**
 * @data 2019/10/26
 * @Author PING
 * @Description 车货匹配预下单，估价界面
 */
@Route(path = ArouterParamsMatch.activity_match_estimate_order)
public class MatchEstimateOrderActivity extends BaseActivity implements RadioIconGroup.CheckListener {

    private RadioIconButton ribLeft, ribRight;
    private RadioIconGroup iconGroup;
    private Fragment currentFragment, ownerFragment, driverFragment;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_estimate_order);
        setToolbarTitle("明珠城配");
        setToolbarSrcRight("我的订单");
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance()
                        .builder(ArouterParamsMatch.activity_match_my_order)
                        .withInt(Param.TYPE, type)
                        .navigation(mContext);


            }
        });
        initView();
        initData();
        initListener();
          type = SharedPreferencesUtils.getInstance().getInt(Param.MATCHTYPE);
        if (type==1){
            ribRight.performClick();
        }else {
            ribLeft.performClick();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        ribLeft = findViewById(R.id.rib_left);
        ribRight = findViewById(R.id.rib_right);
        iconGroup = findViewById(R.id.rib_group);
    }

    @Override
    protected void initListener() {
        super.initListener();
        iconGroup.setCheckListener(this);
    }

    /**
     * 选择更改的时候
     *
     * @param group
     * @param iconButton
     */
    @Override
    public void onCheck(RadioIconGroup group, RadioIconButton iconButton) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        if (iconButton.getId() == R.id.rib_left) {
            type=0;
            if (ownerFragment == null) {
                ownerFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsMatch.fragment_match_owner_find_car).navigation();
                transaction.add(R.id.content, ownerFragment);
            } else {

                transaction.show(ownerFragment);
            }
            currentFragment = ownerFragment;

        } else if (iconButton.getId() == R.id.rib_right) {
            type=1;
            if (driverFragment == null) {
                driverFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsMatch.fragment_match_driver_order_receiving).navigation();
                transaction.add(R.id.content, driverFragment);
            } else {
                transaction.show(driverFragment);
            }
            currentFragment = driverFragment;
        }
        SharedPreferencesUtils.getInstance().putInt(Param.MATCHTYPE,type);
        transaction.commit();
    }
}
