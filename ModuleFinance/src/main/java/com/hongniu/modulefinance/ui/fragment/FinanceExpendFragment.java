package com.hongniu.modulefinance.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.control.OnHideChangeListener;

/**
 * 作者： ${PING} on 2018/8/7.
 * 财务支出模块
 */
public class FinanceExpendFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener ,OnHideChangeListener {

    private RadioGroup rg;
    private RadioButton rbRight;
    private RadioButton rbLeft;
    private View llEmpty;
    private TextView tv_order_count;
    private TextView tv_order_money;

    private Fragment insruancFragmeng;
    private Fragment tranFragmeng;
    private Fragment currentFragmeng;



    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_finance_expend, null);
        rbRight = inflate.findViewById(R.id.rb_right);
        rbLeft = inflate.findViewById(R.id.rb_left);
        rg = inflate.findViewById(R.id.rg);
        llEmpty = inflate.findViewById(R.id.ll_empty);
        tv_order_count = inflate.findViewById(R.id.tv_order_count);
        tv_order_money = inflate.findViewById(R.id.tv_order_money);

        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();

    }





    @Override
    protected void initListener() {
        super.initListener();
        rg.setOnCheckedChangeListener(this);
        rbLeft.performClick();


    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (currentFragmeng != null) {
            fragmentTransaction.hide(currentFragmeng);
        }
        if (checkedId == R.id.rb_left) {
            if (tranFragmeng == null) {
                tranFragmeng = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsFinance.fragment_finance_order).navigation(getContext());
                Bundle bundle = new Bundle();
                bundle.putInt(Param.TRAN, 1);
                tranFragmeng.setArguments(bundle);
                fragmentTransaction.add(R.id.content, tranFragmeng);
            } else {
                fragmentTransaction.show(tranFragmeng);
            }
            currentFragmeng = tranFragmeng;
        } else if (checkedId == R.id.rb_right) {
            if (insruancFragmeng == null) {
                insruancFragmeng = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsFinance.fragment_finance_order).navigation(getContext());
                Bundle bundle = new Bundle();
                bundle.putInt(Param.TRAN, 2);
                insruancFragmeng.setArguments(bundle);
                fragmentTransaction.add(R.id.content, insruancFragmeng);
            } else {
                fragmentTransaction.show(insruancFragmeng);
            }
            currentFragmeng = insruancFragmeng;
        }

        fragmentTransaction.commitAllowingStateLoss();



    }


    @Override
    public void onFragmentShow(boolean hide, int total, float money) {
        if (!hide) {
            tv_order_count.setText("共支出" + total + "笔，合计");
            tv_order_money.setText("￥" + money);
        }
    }
}
