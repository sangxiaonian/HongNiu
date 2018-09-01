package com.hongniu.modulefinance.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.widget.order.OrderDetailDialog;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.FinanceOrderBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/30.
 */
public class FinanceOrderAdapter extends XAdapter<OrderDetailBean> {
    public FinanceOrderAdapter(Context context, List<OrderDetailBean> list) {
        super(context, list);
    }
    OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public BaseHolder<OrderDetailBean> initHolder(ViewGroup parent, int viewType) {
        return new BaseHolder<OrderDetailBean>(context, parent, R.layout.finance_item_finance) {
            @Override
            public void initView(View itemView, final int position, final OrderDetailBean data) {
                super.initView(itemView, position, data);
                TextView tvOrder = itemView.findViewById(R.id.tv_order);
                TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                TextView tvTime = itemView.findViewById(R.id.tv_time);
                TextView tvPrice = itemView.findViewById(R.id.tv_price);

                tvOrder.setText("订单号：" + (data.getOrderNum() == null ? "" : data.getOrderNum()));
                tvCarNum.setText("车牌号码：" + (data.getCarNum() == null ? "" : data.getCarNum()));
                tvTime.setText("付费时间：" + data.getPayTime()==null?"":data.getPayTime());
//
                String money = data.getMoney();
                int financeType = data.getFinanceType();
                StringBuffer buffer = new StringBuffer();
                if (financeType == 1) {//支出

                } else if (financeType == 2) {//收入
                    buffer.append("+");
                } else {//所有情况

                }
                buffer.append(money);
                tvPrice.setText(buffer.toString().trim());


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderDetailDialog orderDetailDialog = new OrderDetailDialog(context);
                        orderDetailDialog.setOrdetail(data);
                        new BottomAlertBuilder()
                                .creatDialog(orderDetailDialog)
                                .show();

                    }
                });
                ;
            }
        };
    }

    public interface OnItemClickListener{
        void itemClick(int position,FinanceOrderBean data);
    }
}
