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
 * 余额明细Adapter
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
                TextView tvOrder = itemView.findViewById(R.id.tv_order);
                TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                TextView tvTime = itemView.findViewById(R.id.tv_time);
                TextView tvPrice = itemView.findViewById(R.id.tv_price);

                if (position % 2 == 0) {

                    tvOrder.setText("订单号：" + "D18282819192");
                    tvCarNum.setText("车牌号码：" + "皖B555555");
                    tvTime.setText("收款时间：" + "2018-06-30 23:30:53");
                    tvPrice.setText("+" + "2000");
                } else {
                    tvOrder.setText("账户提现");
                    tvCarNum.setText("提现账户：" + "工商银行 2737 **** **23");
                    tvTime.setText("提现时间：" + "2018-06-30 23:30:53");
                    tvPrice.setText("-" + "12000");
                }

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
