package com.hongniu.modulefinance.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.modulefinance.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/7.
 * 财务收入模块
 *
 */
public class FinanceIncomeFragment extends BaseFragment {

    private RecyclerView recycleView;

    XAdapter<String> adapter;
    private List<String> datas;


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_finance, null);
        recycleView = inflate.findViewById(R.id.rv);

        return inflate;
    }

    @Override
    protected void initDataa() {
        super.initDataa();

        datas=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("");
        }


        LinearLayoutManager manager =new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        adapter=new XAdapter<String>(getContext(),datas) {
            @Override
            public BaseHolder<String> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<String>(getContext(),parent,R.layout.finance_item_finance){
                    @Override
                    public void initView(View itemView, int position, String data) {
                        super.initView(itemView, position, data);
                        TextView tvOrder = itemView.findViewById(R.id.tv_order);
                        TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                        TextView tvTime = itemView.findViewById(R.id.tv_time);

                        tvOrder.setText("订单号："+"1212136484");
                        tvCarNum.setText("车牌号："+"沪A125356");
                        tvTime.setText("付费时间："+"2017-7-8");


                    }
                };
            }
        };
        recycleView.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        super.initListener();
    }
}
