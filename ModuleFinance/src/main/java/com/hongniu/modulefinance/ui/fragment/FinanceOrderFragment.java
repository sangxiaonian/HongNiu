package com.hongniu.modulefinance.ui.fragment;

import android.content.Context;
import android.graphics.Color;
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
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.control.OnHideChangeListener;
import com.hongniu.modulefinance.entity.QueryExpendBean;
import com.hongniu.modulefinance.entity.QueryExpendResultBean;
import com.hongniu.modulefinance.event.FinanceEvent;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.hongniu.modulefinance.ui.adapter.FinanceExpendHeadHolder;
import com.hongniu.modulefinance.ui.adapter.FinanceOrderAdapter;
import com.sang.common.recycleview.RecycleViewSupportEmpty;
import com.sang.common.recycleview.adapter.XAdapter;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.sang.common.widget.VistogramView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

/**
 * 作者： ${PING} on 2018/8/7.
 * 财务收入模块
 */
@Route(path = ArouterParamsFinance.fragment_finance_order)
public class FinanceOrderFragment extends RefrushFragmet<OrderDetailBean> {


    private FinanceExpendHeadHolder headHolder;
    private QueryExpendBean bean = new QueryExpendBean();
    private int totalCount;
    private float totalMoney;
    /**
     * 1表示运费，2表示保费。0在搜索收入时
     */
    private int type;
    private OnHideChangeListener hidechangeListener;


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_finance_order, null);

        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();

        headHolder = new FinanceExpendHeadHolder(getContext(), rv);

        RecycleViewSupportEmpty empty = null;
        if (rv instanceof RecycleViewSupportEmpty) {
            empty = (RecycleViewSupportEmpty) rv;
        }
        if (type == 1) {
            headHolder.setTitle("运费支出明细");
            if (empty != null) {
                empty.setEmptyView(R.mipmap.icon_cw_240, "暂无支出");
            }
        } else if (type == 2) {
            if (empty != null) {
                empty.setEmptyView(R.mipmap.icon_cw_240, "暂无支出");
            }
            headHolder.setTitle("保费支出明细");
        } else {
            if (empty != null) {
                empty.setEmptyView(R.mipmap.icon_cw_240, "暂无收入");
            }
            headHolder.setTitle("收入明细");
            headHolder.hideVisDes();
        }
        adapter.addHeard(0, headHolder);
//        headHolder.showGuide(getActivity());

    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        type = args.getInt(Param.TRAN);
        bean.setFeeType(type);
        switch (args.getInt(Param.TRAN)) {
            case 0://仅仅是收入
                bean.setFinanceType(2);
                break;
            case 1://运费
            case 2://保费
                bean.setFinanceType(1);
                break;
        }
    }

    @Override
    protected Observable<CommonBean<PageBean<OrderDetailBean>>> getListDatas() {
        bean.setPageNum(currentPage);
        return HttpFinanceFactory.queryFinance(bean)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<CommonBean<PageBean<OrderDetailBean>>, CommonBean<PageBean<OrderDetailBean>>>() {
                    @Override
                    public CommonBean<PageBean<OrderDetailBean>> apply(CommonBean<PageBean<OrderDetailBean>> pageBeanCommonBean) throws Exception {
                        if (pageBeanCommonBean != null && pageBeanCommonBean.getData() != null) {
                            PageBean<OrderDetailBean> data = pageBeanCommonBean.getData();
                            totalCount = data.getTotal();
                            totalMoney = data.getTotalMoney();
                        }
                        return pageBeanCommonBean;
                    }
                })

                ;
    }

    @Override
    public void onTaskSuccess() {
        super.onTaskSuccess();
        if (hidechangeListener != null) {
            hidechangeListener.onFragmentShow(isHidden(), totalCount, totalMoney);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof OnHideChangeListener) {
            this.hidechangeListener = (OnHideChangeListener) getParentFragment();
        }
    }

    @Override
    protected XAdapter<OrderDetailBean> getAdapter(final List<OrderDetailBean> datas) {
        return new FinanceOrderAdapter(getContext(), datas);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidechangeListener != null) {
            hidechangeListener.onFragmentShow(hidden, totalCount, totalMoney);
        }
    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEndEvent(final FinanceEvent.SelectMonthEvent event) {
        if (event != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM月");
            String data = format.format(event.date);
            if (data.startsWith("0")) {
                data = data.substring(1);
            }
            String year = ConvertUtils.formatTime(event.date, "yyyy");
            String month = data.substring(0, data.length() - 1);
            Observable<CommonBean<List<QueryExpendResultBean>>> observable = null;
            switch (type) {
                case 0://仅仅是收入
                    observable = HttpFinanceFactory
                            .queryInComeVistogram(year, month);
                    break;
                case 1://运费

                    observable = HttpFinanceFactory
                            .queryExpendVistogramTran(year, month);
                    break;
                case 2://保费

                    observable = HttpFinanceFactory
                            .queryExpendVistogramInsurance(year, month);
                    break;
            }
            bean.setYear(year);
            bean.setMonth(month);
            queryData(true);
            if (observable != null) {
                observable
                        .subscribe(new NetObserver<List<QueryExpendResultBean>>(this) {
                            @Override
                            public void doOnSuccess(List<QueryExpendResultBean> data) {
                                List<List<VistogramView.VistogramBean>> current = new ArrayList<>();
                                if (!data.isEmpty()) {
                                    for (QueryExpendResultBean datum : data) {
                                        List<VistogramView.VistogramBean> list = new ArrayList<>();
                                        for (int i = 0; i < datum.getCosts().size(); i++) {
                                            if (i % 2 == 0) {
                                                //线上支付
                                                list.add(new VistogramView.VistogramBean(Color.parseColor("#F06F28"), datum.getCosts().get(0).getMoney(), datum.getCostDate()));

                                            } else {
                                                //线下支付
                                                list.add(new VistogramView.VistogramBean(Color.parseColor("#007AFF"), datum.getCosts().get(1).getMoney(), datum.getCostDate()));

                                            }
                                        }

                                        current.add(list);
                                    }
                                }
                                headHolder.setDatas(current);
                                if (current.size() > 0) {
                                    String s = ConvertUtils.formatTime(event.date, "yyyy-MM");
                                    for (List<VistogramView.VistogramBean> debugData : current) {
                                        if (debugData != null && debugData.size() > 0) {
                                            if (debugData.get(0).xMark.equals(s)) {
                                                headHolder.setCurrentX(current.indexOf(debugData));
                                            }
                                        }
                                    }
                                }

                            }
                        });
            }
        }
    }
}
