package com.hongniu.supply.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hongniu.baselibrary.arouter.ArouterParamFestivity;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.supply.R;
import com.sang.common.utils.ToastUtils;

/**
 * 作者： ${PING} on 2018/11/23.
 */
@Route(path = ArouterParamsApp.fragment_home_fragment)
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    TextView tv_balance;
    Button bt_wallet;
    ViewGroup ll_search;
    ViewGroup ll_cargo;
    ViewGroup ll_car;
    ViewGroup ll_driver;
    ViewGroup card_policy;
    ViewGroup card_yongjin;
    ViewGroup card_etc;

    @Override
    protected View initView(LayoutInflater inflater) {

        View inflate = inflater.inflate(R.layout.fragment_home_fragment, null);
        tv_balance = inflate.findViewById(R.id.tv_balance);
        bt_wallet = inflate.findViewById(R.id.bt_wallet);
        ll_search = inflate.findViewById(R.id.ll_search);
        ll_cargo = inflate.findViewById(R.id.ll_cargo);
        ll_car = inflate.findViewById(R.id.ll_car);
        ll_driver = inflate.findViewById(R.id.ll_driver);
        card_policy = inflate.findViewById(R.id.card_policy);
        card_yongjin = inflate.findViewById(R.id.card_yongjin);
        card_etc = inflate.findViewById(R.id.card_etc);

        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.color_new_light), false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.color_new_light), false);
        }
    }


    @Override
    protected void initListener() {
        super.initListener();
//        tv_balance.setOnClickListener(this);
        bt_wallet.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        ll_cargo.setOnClickListener(this);
        ll_car.setOnClickListener(this);
        ll_driver.setOnClickListener(this);
        card_policy.setOnClickListener(this);
        card_yongjin.setOnClickListener(this);
        card_etc.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_wallet:
                ArouterUtils.getInstance().builder(ArouterParamsFinance.activity_finance_wallet).navigation(getContext());
                break;
            case R.id.ll_search:
                ToastUtils.getInstance().show("搜索");
                break;
            case R.id.ll_cargo:
                ToastUtils.getInstance().show("货主");
                break;
            case R.id.ll_car:
                ToastUtils.getInstance().show("车主");
                break;
            case R.id.ll_driver:
                ToastUtils.getInstance().show("司机");
                break;
            case R.id.card_policy:
                ToastUtils.getInstance().show("保险");
                break;
            case R.id.card_yongjin:
                ArouterUtils.getInstance().builder(ArouterParamFestivity.activity_festivity_home).navigation(getContext());

                break;
            case R.id.card_etc:
                ToastUtils.getInstance().show("近期上线");
                break;
        }
    }
}
