package com.hongniu.modulefinance.ui.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.widget.order.OrderDetailDialog;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.QueryExpendBean;
import com.hongniu.modulefinance.entity.QueryExpendResultBean;
import com.hongniu.modulefinance.event.FinanceEvent;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.hongniu.modulefinance.ui.adapter.FinanceExpendHeadHolder;
import com.sang.common.recycleview.RecycleViewSupportEmpty;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.widget.VistogramView;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/8/7.
 * 财务支出模块
 */
public class FinanceExpendFragment extends RefrushFragmet<OrderDetailBean> implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rg;
    private RadioButton rbRight;
    private RadioButton rbLeft;
    private View llEmpty;


    private FinanceExpendHeadHolder headHolder;

    List<QueryExpendResultBean> vistogramTran;//运费
    List<QueryExpendResultBean> vistogramInsurance;//保费
    private Date date;//目前显示的数据
    private QueryExpendBean bean = new QueryExpendBean();

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_finance_expend, null);
        rbRight = inflate.findViewById(R.id.rb_right);
        rbLeft = inflate.findViewById(R.id.rb_left);
        rg = inflate.findViewById(R.id.rg);
        llEmpty = inflate.findViewById(R.id.ll_empty);

        return inflate;
    }

    @Override
    protected void initData() {
//        if (rv instanceof RecycleViewSupportEmpty){
//            ((RecycleViewSupportEmpty) rv).setEmptyView(llEmpty);
//        }
        super.initData();
        vistogramTran = new ArrayList<>();
        vistogramInsurance = new ArrayList<>();
        headHolder = new FinanceExpendHeadHolder(getContext(), rv);
        adapter.addHeard(0,headHolder);

        queryData(true);

    }


    @Override
    protected Observable<CommonBean<PageBean<OrderDetailBean>>> getListDatas() {
        bean.setPageNum(currentPage);
        bean.setFinanceType(1);

        return HttpFinanceFactory.queryFinance(bean);
    }

    @Override
    protected XAdapter<OrderDetailBean> getAdapter(List<OrderDetailBean> datas) {
        return   new XAdapter<OrderDetailBean>(getContext(), datas) {
            @Override
            public BaseHolder<OrderDetailBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<OrderDetailBean>(getContext(), parent, R.layout.finance_item_finance) {
                    @Override
                    public void initView(View itemView, int position, final OrderDetailBean data) {
                        super.initView(itemView, position, data);
                        TextView tvOrder = itemView.findViewById(R.id.tv_order);
                        TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        TextView tvPrice = itemView.findViewById(R.id.tv_price);

                        tvOrder.setText("订单号：" + (data.getOrderNum()==null?"":data.getOrderNum()));
                        tvCarNum.setText("车牌号码：" + (data.getCarnum()==null?"":data.getCarnum()));
                        tvTime.setText("付费时间：" + (data.getPayTime()==0?"":ConvertUtils.formatTime(data.getPayTime(),"yyyy-MM-dd HH:mm:ss")));
                        tvPrice.setText("1200.0");
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                OrderDetailDialog orderDetailDialog = new OrderDetailDialog(getContext());
                                orderDetailDialog.setOrdetail(data);
                                new BottomAlertBuilder()
                                        .setDialogTitle(getString(R.string.login_car_entry_deleted))
                                        .creatDialog(orderDetailDialog)
                                        .show();
                            }
                        });

                    }
                };
            }
        };
    }

    @Override
    protected void initListener() {
        super.initListener();
        rg.setOnCheckedChangeListener(this);
        rbLeft.performClick();

        rv.post(new Runnable() {
            @Override
            public void run() {
                headHolder.showGuide(getActivity());
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_left) {
            headHolder.setTitle("运费支出明细");
            insruance = false;
        } else if (checkedId == R.id.rb_right) {
            headHolder.setTitle("保费支出明细");
            insruance = true;
        }


        changeSelect();

    }


    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    private boolean insruance;//是否是保费支出

    public void changeSelect() {
        if (date == null) {
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("MM月");
        String data = format.format(date);
        if (data.startsWith("0")) {
            data = data.substring(1);
        }
        Observable<CommonBean<List<QueryExpendResultBean>>> observable;

        bean.setYear(ConvertUtils.formatTime(date, "yyyy"));
        bean.setMonth(data.substring(0, data.length() - 1));


        if (insruance) {
            if (vistogramInsurance.isEmpty()) {
                observable = HttpFinanceFactory
                        .queryExpendVistogramInsurance(ConvertUtils.formatTime(date, "yyyy"), data.substring(0, data.length() - 1));
            } else {
                CommonBean<List<QueryExpendResultBean>> commonBean = new CommonBean<>();
                commonBean.setData(vistogramInsurance);
                commonBean.setCode(200);
                observable = Observable.just(commonBean);
            }
        } else {
            if (vistogramTran.isEmpty()) {
                observable = HttpFinanceFactory
                        .queryExpendVistogramTran(ConvertUtils.formatTime(date, "yyyy"), data.substring(0, data.length() - 1));
            } else {
                CommonBean<List<QueryExpendResultBean>> commonBean = new CommonBean<>();
                commonBean.setData(vistogramTran);
                commonBean.setCode(200);
                observable = Observable.just(commonBean);
            }
        }
        observable.subscribe(new NetObserver<List<QueryExpendResultBean>>(this) {
            @Override
            public void doOnSuccess(List<QueryExpendResultBean> data) {
                List<List<VistogramView.VistogramBean>> current = new ArrayList<>();
                if (insruance) {
                    if (vistogramInsurance.isEmpty()) {
                        vistogramInsurance.addAll(data);
                    }
                } else {
                    if (vistogramTran.isEmpty()) {
                        vistogramTran.addAll(data);
                    }
                }
                if (!data.isEmpty()) {
                    for (QueryExpendResultBean datum : data) {
                        List<VistogramView.VistogramBean> list = new ArrayList<>();
                        for (QueryExpendResultBean.CostsBean costsBean : datum.getCosts()) {
                            if (costsBean.getPayWay() == 1) {
                                //线上支付
                                list.add(new VistogramView.VistogramBean(Color.parseColor("#F06F28"), datum.getCosts().get(0).getMoney(), datum.getCostDate()));
                            } else if (costsBean.getPayWay() == 2) {
                                //线下支付
                                list.add(new VistogramView.VistogramBean(Color.parseColor("#007AFF"), datum.getCosts().get(1).getMoney(), datum.getCostDate()));
                            }

                        }
                        current.add(list);
                    }
                }
                headHolder.setDatas(current);
                if (current.size() > 0) {
                    String s = ConvertUtils.formatTime(date, "yyyy-MM");
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


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEndEvent(final FinanceEvent.SelectMonthEvent event) {
        if (event != null) {
            this.date = event.date;
            vistogramTran.clear();
            vistogramInsurance.clear();
            changeSelect();

        }
    }


}
