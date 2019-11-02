package com.hongniu.modulecargoodsmatch.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderInfoBean;
import com.hongniu.modulecargoodsmatch.ui.holder.MatchOrderInfoHolder;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 *@data  2019/10/27
 *@Author PING
 *@Description
 *
 * 货主找车
 */
@Route(path = ArouterParamsMatch.fragment_match_driver_order_receiving)
public class MatchDriverOrderRecevingFragment extends RefrushFragmet<MatchOrderInfoBean> implements MatchOrderInfoHolder.MatchOrderItemClickListener {
    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_match_driver_order_receiving, null, false);
    }

    @Override
    protected void initData() {
        super.initData();
        queryData(true);

    }

    @Override
    protected void initListener() {
        super.initListener();
    }
    @Override
    protected Observable<CommonBean<PageBean<MatchOrderInfoBean>>> getListDatas() {
        CommonBean<PageBean<MatchOrderInfoBean>> commonBean=new CommonBean<>();
        commonBean.setCode(200);
        PageBean<MatchOrderInfoBean> pageBean = new PageBean<>();
        ArrayList<MatchOrderInfoBean> infoBeans = new ArrayList<>();
        pageBean.setList(infoBeans);
        commonBean.setData(pageBean);
        for (int i = 0; i < 10; i++) {
            infoBeans.add(new MatchOrderInfoBean());
        }
        return Observable.just(commonBean);
    }

    @Override
    protected XAdapter<MatchOrderInfoBean> getAdapter(List<MatchOrderInfoBean> datas) {
        return new XAdapter<MatchOrderInfoBean>(getContext(),datas) {
            @Override
            public BaseHolder<MatchOrderInfoBean> initHolder(ViewGroup parent, int viewType) {
                MatchOrderInfoHolder infoHolder = new MatchOrderInfoHolder(getContext(), parent);
                infoHolder.setListener(MatchDriverOrderRecevingFragment.this);
                return infoHolder;
            }
        };
    }

    @Override
    public void onBtClick(int position, MatchOrderInfoBean infoHolder) {
        ToastUtils.getInstance().show("我要接單");
    }

    @Override
    public void onItemClick(int position, MatchOrderInfoBean data) {
        ToastUtils.getInstance().show("订单详情");

    }
}