package com.hongniu.modulefinance.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.BalanceOfAccountBean;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.hongniu.modulefinance.ui.adapter.BalanceOfAccountAdapter;
import com.sang.common.recycleview.adapter.XAdapter;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/10/8.
 * 余额明细/待入账明细
 */
@Route(path = ArouterParamsFinance.fragment_finance_wallet)
public class FinanceWalletFragment extends RefrushFragmet<BalanceOfAccountBean>{

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_finance_wallet, null);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        queryData(true);
    }

    @Override
    protected Observable<CommonBean<PageBean<BalanceOfAccountBean>>> getListDatas() {
        return HttpFinanceFactory.gueryBananceOfAccount();
    }

    @Override
    protected XAdapter<BalanceOfAccountBean> getAdapter(List<BalanceOfAccountBean> datas) {
        return new BalanceOfAccountAdapter(getContext(),datas);
    }
}
