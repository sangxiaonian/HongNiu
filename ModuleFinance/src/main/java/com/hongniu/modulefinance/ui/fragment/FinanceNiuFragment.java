package com.hongniu.modulefinance.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.control.OnItemClickListener;
import com.hongniu.modulefinance.entity.BalanceOfAccountBean;
import com.hongniu.modulefinance.entity.NiuOfAccountBean;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.hongniu.modulefinance.ui.adapter.BalanceOfAccountAdapter;
import com.hongniu.modulefinance.ui.adapter.NiuOfAccountAdapter;
import com.sang.common.recycleview.adapter.XAdapter;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/10/8.
 * 牛币相关，待入账，已入账等
 *  1 待入账 2已入账
 *
 */
@Route(path = ArouterParamsFinance.fragment_finance_niu)
public class FinanceNiuFragment extends RefrushFragmet<NiuOfAccountBean> implements OnItemClickListener<NiuOfAccountBean> {

    private int type;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_finance_wallet, null);
        return inflate;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
         type = args.getInt(Param.TRAN);
    }

    @Override
    protected void initData() {
        super.initData();
        queryData(true);
    }

    @Override
    protected Observable<CommonBean<PageBean<NiuOfAccountBean>>> getListDatas() {
        return HttpFinanceFactory.gueryNiuList(currentPage,type);
    }

    @Override
    protected XAdapter<NiuOfAccountBean> getAdapter(List<NiuOfAccountBean> datas) {
        NiuOfAccountAdapter niuOfAccountAdapter = new NiuOfAccountAdapter(getContext(), datas);
        niuOfAccountAdapter.setItemClickListener(this);
        return niuOfAccountAdapter;
    }

    @Override
    public void onItemClick(int position, NiuOfAccountBean niuOfAccountBean) {
        ArouterUtils.getInstance().builder(ArouterParamsFinance.activity_finance_car_order_detail).withString(Param.TRAN,niuOfAccountBean.carNum).navigation(getContext());
    }
}
