package com.hongniu.modulefinance.ui.fragment;

import android.app.usage.UsageEvents;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.widget.order.OrderDetailDialog;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.event.FinanceEvent;
import com.hongniu.modulefinance.ui.adapter.FinanceExpendHeadHolder;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.widget.VistogramView;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;
import com.sang.common.widget.guideview.BaseGuide;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/7.
 * 财务支出模块
 */
public class FinanceExpendFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    private RecyclerView recycleView;
    private RadioGroup rg;
    private RadioButton rbRight;
    private RadioButton rbLeft;

    XAdapter<String> adapter;
    private List<String> datas;
    private FinanceExpendHeadHolder headHolder;

    List<List<VistogramView.VistogramBean>>  debugDatas;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_finance_expend, null);
        recycleView = inflate.findViewById(R.id.rv);
        rbRight = inflate.findViewById(R.id.rb_right);
        rbLeft = inflate.findViewById(R.id.rb_left);
        rg = inflate.findViewById(R.id.rg);

        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("");
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        adapter = new XAdapter<String>(getContext(), datas) {
            @Override
            public BaseHolder<String> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<String>(getContext(), parent, R.layout.finance_item_finance) {
                    @Override
                    public void initView(View itemView, int position, String data) {
                        super.initView(itemView, position, data);
                        TextView tvOrder = itemView.findViewById(R.id.tv_order);
                        TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        TextView tvPrice = itemView.findViewById(R.id.tv_price);

                        tvOrder.setText("订单号：" + "1212136484");
                        tvCarNum.setText("车牌号码：" + "沪A125356");
                        tvTime.setText("付费时间：" + "2017-7-8");
                        tvPrice.setText("1200.0");
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new BottomAlertBuilder()

                                        .setDialogTitle(getString(R.string.login_car_entry_deleted))
                                        .creatDialog(new OrderDetailDialog(getContext()))
                                        .show();
                            }
                        });

                    }
                };
            }
        };
        headHolder = new FinanceExpendHeadHolder(getContext(), recycleView);
        adapter.addHeard(headHolder);

        debugDatas = new ArrayList<>();
        if (Param.isDebug) {
            for (int i = 0; i < 12; i++) {
                List<VistogramView.VistogramBean> list = new ArrayList<>();
                list.add(new VistogramView.VistogramBean(Color.parseColor("#F06F28"), ConvertUtils.getRandom(10000, 15000), (i + 1) + "月"));
                list.add(new VistogramView.VistogramBean(Color.parseColor("#007AFF"), ConvertUtils.getRandom(5000, 8000), (i + 1) + "月"));
                debugDatas.add(list);
            }

            recycleView.post(new Runnable() {
                @Override
                public void run() {
                    headHolder.setDatas(debugDatas);
                }
            })
            ;
        }

        recycleView.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        super.initListener();
        rg.setOnCheckedChangeListener(this);
        rbLeft.performClick();

        recycleView.post(new Runnable() {
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
        } else if (checkedId == R.id.rb_right) {
            headHolder.setTitle("保费支出明细");

        }
    }


    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEndEvent(FinanceEvent.SelectMonthEvent event) {
        if (event != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM月");
            String data = format.format(event.date);
            if (data.startsWith("0")){
               data= data.substring(1);
            }
            if (debugDatas!=null&&debugDatas.size()>0){
                for (List<VistogramView.VistogramBean> debugData : debugDatas) {
                    if (debugData!=null&&debugData.size()>0){
                        if (debugData.get(0).xMark.equals(data)){
                            headHolder.setCurrentX(debugDatas.indexOf(debugData));
                        }
                    }
                }
            }
        }
    }


}
