package com.hongniu.modulefinance.ui.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.config.Param;
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
import com.hongniu.modulefinance.ui.adapter.FinanceIncomHeadHolder;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.JLog;
import com.sang.common.widget.VistogramView;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;

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
public class FinanceIncomeFragment extends RefrushFragmet<OrderDetailBean> {


    private FinanceIncomHeadHolder headHolder;
    private QueryExpendBean bean=new QueryExpendBean();

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

        headHolder = new FinanceIncomHeadHolder(getContext(), rv);
        adapter.addHeard(0,headHolder);





    }

    @Override
    protected Observable<CommonBean<PageBean<OrderDetailBean>>> getListDatas() {
        bean.setPageNum(currentPage);
        bean.setFinanceType(2);
        return HttpFinanceFactory.queryFinance(bean)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<CommonBean<PageBean<OrderDetailBean>>, CommonBean<PageBean<OrderDetailBean>>>() {
                    @Override
                    public CommonBean<PageBean<OrderDetailBean>> apply(CommonBean<PageBean<OrderDetailBean>> pageBeanCommonBean) throws Exception {
                        if (pageBeanCommonBean != null && pageBeanCommonBean.getData() != null) {
                            PageBean<OrderDetailBean> data = pageBeanCommonBean.getData();
                            int total = data.getTotal();
                            tv_order_count.setText("共支出"+total+"笔，合计");
                            tv_order_money.setText("￥"+data.getTotalMoney());
                        }

                        return pageBeanCommonBean;
                    }
                })

                ;
    }

    @Override
    protected XAdapter<OrderDetailBean> getAdapter(final List<OrderDetailBean> datas) {
        return  new XAdapter<OrderDetailBean>(getContext(), datas) {
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

            bean.setYear(ConvertUtils.formatTime(event.date, "yyyy"));
            bean.setMonth(data.substring(0, data.length() - 1));
            queryData(true);
            HttpFinanceFactory
                    .queryInComeVistogram(ConvertUtils.formatTime(event.date, "yyyy"), data.substring(0, data.length() - 1))
                    .subscribe(new NetObserver<List<QueryExpendResultBean>>(this) {
                        @Override
                        public void doOnSuccess(List<QueryExpendResultBean> data) {
                            List<List<VistogramView.VistogramBean>> current = new ArrayList<>();

                            if (!data.isEmpty()) {
                                for (QueryExpendResultBean datum : data) {
                                    List<VistogramView.VistogramBean> list = new ArrayList<>();
                                    list.add(new VistogramView.VistogramBean(Color.parseColor("#F06F28"), datum.getCosts().get(0).getMoney(), datum.getCostDate()));
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
