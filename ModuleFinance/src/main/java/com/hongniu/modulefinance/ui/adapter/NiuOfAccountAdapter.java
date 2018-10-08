package com.hongniu.modulefinance.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.NiuOfAccountBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/8.
 * 牛币已入账、待入账 Adapter
 */
public class NiuOfAccountAdapter extends XAdapter<NiuOfAccountBean> {

    public NiuOfAccountAdapter(Context context, List<NiuOfAccountBean> list) {
        super(context, list);
    }

    @Override
    public BaseHolder<NiuOfAccountBean> initHolder(ViewGroup parent, int viewType) {
        return new BaseHolder<NiuOfAccountBean>(context, parent, R.layout.finance_item_finance) {
            @Override
            public void initView(View itemView, final int position, final NiuOfAccountBean data) {
                super.initView(itemView, position, data);
                TextView tvOrder = itemView.findViewById(R.id.tv_title);
                TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                TextView tvTime = itemView.findViewById(R.id.tv_time);
                TextView tvPrice = itemView.findViewById(R.id.tv_price);
                tvCarNum.setVisibility(View.GONE);
                tvOrder.setText("车牌号码：" + "皖B555555");
                tvTime.setText("创建车辆时间：" + "2018-06-30 23:30:53");
                tvPrice.setText("1" + "个");


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
