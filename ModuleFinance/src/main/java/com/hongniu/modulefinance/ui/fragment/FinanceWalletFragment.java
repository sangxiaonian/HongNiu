package com.hongniu.modulefinance.ui.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.widget.order.OrderDetailDialog;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.control.OnItemClickListener;
import com.hongniu.modulefinance.entity.BalanceOfAccountBean;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.hongniu.modulefinance.ui.adapter.BalanceOfAccountAdapter;
import com.hongniu.modulefinance.widget.WithDrawDialog;
import com.sang.common.recycleview.adapter.XAdapter;
import com.fy.androidlibrary.toast.ToastUtils;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/10/8.
 * type 1余额明细 2待入账明细
 */
@Route(path = ArouterParamsFinance.fragment_finance_wallet)
public class FinanceWalletFragment extends RefrushFragmet<BalanceOfAccountBean> implements OnItemClickListener<BalanceOfAccountBean> {

    /**
     * 类型
     */
    private int type;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_finance_wallet, null);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void onStart() {
        super.onStart();
        queryData(true);
    }


    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        type = args.getInt(Param.TRAN);
    }

    @Override
    protected Observable<CommonBean<PageBean<BalanceOfAccountBean>>> getListDatas() {
        return HttpFinanceFactory.gueryBananceOfAccount(type, currentPage);
    }

    @Override
    protected XAdapter<BalanceOfAccountBean> getAdapter(List<BalanceOfAccountBean> datas) {
        BalanceOfAccountAdapter balanceOfAccountAdapter = new BalanceOfAccountAdapter(getContext(), datas);
        balanceOfAccountAdapter.setListener(this);
        return balanceOfAccountAdapter;
    }

    @Override
    public void onItemClick(int position, BalanceOfAccountBean balanceOfAccountBean) {
        if (balanceOfAccountBean.getFlowType() == 1) {//订单
            if (balanceOfAccountBean.getIsMe() == 1) {
//

                HttpAppFactory.queryOrderDetail(null, balanceOfAccountBean.getOrdernumber(), balanceOfAccountBean.getId())
                        .subscribe(new NetObserver<OrderDetailBean>(this) {
                            @Override
                            public void doOnSuccess(OrderDetailBean data) {
                                OrderDetailDialog orderDetailDialog = new OrderDetailDialog(getContext());
                                if (type == 2) {
                                    data.setRoleType(1);
                                }
                                orderDetailDialog.setOrdetail(data);
                                orderDetailDialog.hideButton();
                                new BottomAlertBuilder()
                                        .creatDialog(orderDetailDialog)
                                        .show();
                            }
                        });


            } else if (balanceOfAccountBean.getIsMe() == 3) {//提现完成
                ToastUtils.getInstance().show("提现已到账");
            } else {
                ToastUtils.getInstance().show("此为好友订单不能查看明细信息！");

            }

        } else {//提现
            WithDrawDialog dialog = new WithDrawDialog(getContext());
            dialog.setData(balanceOfAccountBean);
            dialog.show();
//            ToastUtils.getInstance().show("提现");
        }
    }
}
