package com.hongniu.modulecargoodsmatch.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderInfoBean;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.hongniu.modulecargoodsmatch.ui.holder.MatchOrderInfoHolder;
import com.hongniu.modulecargoodsmatch.utils.MatchOrderListHelper;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2019/10/27
 * @Author PING
 * @Description 货主找车
 */
@Route(path = ArouterParamsMatch.fragment_match_driver_order_receiving)
public class MatchDriverOrderRecevingFragment extends RefrushFragmet<MatchOrderInfoBean> implements MatchOrderInfoHolder.MatchOrderItemClickListener {
    private int type;//角色类型   0 2 司机 1货主

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            type = args.getInt(Param.TYPE);
        }
    }

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
        if (type == 0) {
            return HttpMatchFactory.queryDriverOrder(currentPage);
        } else {
            return HttpMatchFactory.queryMyOrder(currentPage, type);
        }

    }

    @Override
    protected XAdapter<MatchOrderInfoBean> getAdapter(List<MatchOrderInfoBean> datas) {
        return new XAdapter<MatchOrderInfoBean>(getContext(), datas) {
            @Override
            public BaseHolder<MatchOrderInfoBean> initHolder(ViewGroup parent, int viewType) {
                MatchOrderInfoHolder infoHolder = new MatchOrderInfoHolder(getContext(), parent);
                infoHolder.setListener(MatchDriverOrderRecevingFragment.this);
                infoHolder.setType(type);
                return infoHolder;
            }
        };
    }

    @Override
    public void onBtClick(int position, MatchOrderInfoBean infoHolder, int type, String btState) {

        if (MatchOrderListHelper.CANCLE_CAR.equals(btState)) {
            _cancleCar(infoHolder);
        } else if (MatchOrderListHelper.PAY.equals(btState)) {

        } else if (MatchOrderListHelper.CONTACT_DRIVER.equals(btState)) {
            CommonUtils.call(getContext(), infoHolder.getDriverMobile());
        } else if (MatchOrderListHelper.EVALUATE_DRIVER.equals(btState)) {

        } else if (MatchOrderListHelper.RECEIVE_ORDER.equals(btState)) {
            _receiveOrder(infoHolder);
        } else if (MatchOrderListHelper.ENTRY_ARRIVE.equals(btState)) {

        }


    }

    /**
     *@data  2019/11/2
     *@Author PING
     *@Description
     *
     * 我要接单
     */
    private void _receiveOrder(final MatchOrderInfoBean infoHolder) {
        CenterAlertBuilder builder = Utils.creatDialog(getContext(), "确定接单？", "接单后，即可与货主取得联系", "放弃接单", "确定接单");
        builder
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog cdialog) {
                        cdialog.dismiss();
                        HttpMatchFactory
                                .receiverOrder(infoHolder.getId())
                                .subscribe(new NetObserver<Object>(MatchDriverOrderRecevingFragment.this) {
                                    @Override
                                    public void doOnSuccess(Object data) {
                                        queryData(true, true);
                                    }

                                });

                    }
                })
                .creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }

    /**
     * 取消找车
     *
     * @param infoHolder
     */
    private void _cancleCar(final MatchOrderInfoBean infoHolder) {
        CenterAlertBuilder builder = Utils.creatDialog(getContext(), "确认要取消找车？", "取消后，您可在“我的找车”里查看记录", "我再想想", "取消找车");
        builder
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog cdialog) {
                        cdialog.dismiss();
                        HttpMatchFactory
                                .cancleCar(infoHolder.getId())
                                .subscribe(new NetObserver<Object>(MatchDriverOrderRecevingFragment.this) {
                                    @Override
                                    public void doOnSuccess(Object data) {
                                        queryData(true, true);
                                    }

                                });

                    }
                })
                .creatDialog(new CenterAlertDialog(getContext()))
                .show();

    }

    @Override
    public void onItemClick(int position, MatchOrderInfoBean data) {
        ArouterUtils.getInstance()
                .builder(ArouterParamsMatch.activity_match_order_detail)
                .withInt(Param.TYPE,type==1?0:1)
                .withParcelable(Param.TRAN,data)
                .navigation(getContext());
    }
}
