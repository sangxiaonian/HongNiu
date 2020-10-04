package com.hongniu.modulefinance.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.control.OnHideChangeListener;

/**
 * 作者： ${PING} on 2018/8/7.
 * 财务收入模块
 */
public class FinanceIncomeFragment extends BaseFragment implements OnHideChangeListener {


    private TextView tv_order_count;
    private TextView tv_order_money;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_finance_incom, null);
        tv_order_count = inflate.findViewById(R.id.tv_order_count);
        tv_order_money = inflate.findViewById(R.id.tv_order_money);

        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        Fragment tranFragmeng = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsFinance.fragment_finance_order).navigation(getContext());
        Bundle bundle = new Bundle();
        bundle.putInt(Param.TRAN, 0);
        tranFragmeng.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.content, tranFragmeng).commit();
    }


    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    public void onFragmentShow(boolean hide, int total, float money) {
        if (!hide) {
            tv_order_count.setText("共收入" + total + "笔，合计");
            tv_order_money.setText("￥" + money);
        }
    }

}
