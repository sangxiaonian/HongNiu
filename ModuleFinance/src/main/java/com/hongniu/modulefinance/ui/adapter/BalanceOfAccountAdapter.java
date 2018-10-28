package com.hongniu.modulefinance.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.BalanceOfAccountBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/8.
 * 我的钱包 余额明细Adapter
 */
public class BalanceOfAccountAdapter extends XAdapter<BalanceOfAccountBean> {

    public BalanceOfAccountAdapter(Context context, List<BalanceOfAccountBean> list) {
        super(context, list);
    }

    @Override
    public BaseHolder<BalanceOfAccountBean> initHolder(ViewGroup parent, int viewType) {
        return new BaseHolder<BalanceOfAccountBean>(context, parent, R.layout.finance_item_finance) {
            @Override
            public void initView(View itemView, final int position, final BalanceOfAccountBean data) {
                super.initView(itemView, position, data);
                TextView tvOrder = itemView.findViewById(R.id.tv_title);
                TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                TextView tvTime = itemView.findViewById(R.id.tv_time);
                TextView tvPrice = itemView.findViewById(R.id.tv_price);

                tvOrder.setText(data.getTitle()==null?"":data.getTitle());
                tvCarNum.setText(data.getSubtitle()==null?"":data.getSubtitle());
                tvPrice.setText(data.getAmtStr());
                tvTime.setText((data.getInorexptype()==2?"提现时间：":"收款时间：")+data.getCreateTime());

//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        OrderDetailDialog orderDetailDialog = new OrderDetailDialog(context);
//                        orderDetailDialog.setOrdetail(data);
//                        new BottomAlertBuilder()
//                                .creatDialog(orderDetailDialog)
//                                .show();
//
//                    }
//                });
//                ;
            }
        };
    }





}
