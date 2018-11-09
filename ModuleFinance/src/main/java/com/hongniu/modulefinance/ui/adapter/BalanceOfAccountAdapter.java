package com.hongniu.modulefinance.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.baselibrary.widget.order.OrderDetailDialog;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.control.OnItemClickListener;
import com.hongniu.modulefinance.entity.BalanceOfAccountBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/8.
 * 我的钱包 余额明细Adapter
 */
public class BalanceOfAccountAdapter extends XAdapter<BalanceOfAccountBean> {

    public BalanceOfAccountAdapter(Context context, List<BalanceOfAccountBean> list) {
        super(context, list);
    }
    OnItemClickListener<BalanceOfAccountBean> listener;

    public void setListener(OnItemClickListener<BalanceOfAccountBean> listener) {
        this.listener = listener;
    }

    @Override
    public BaseHolder<BalanceOfAccountBean> initHolder(ViewGroup parent, int viewType) {
        return new BaseHolder<BalanceOfAccountBean>(context, parent, R.layout.finance_item_finance) {
            @Override
            public void initView(View itemView, final int position, final BalanceOfAccountBean data) {
                super.initView(itemView, position, data);
                TextView tvTitle = itemView.findViewById(R.id.tv_title);
                TextView tvSubTitle = itemView.findViewById(R.id.tv_car_num);
                TextView tvTime = itemView.findViewById(R.id.tv_time);
                TextView tvPrice = itemView.findViewById(R.id.tv_price);

                tvSubTitle.setText((data.getSubtitle() == null ? "" : data.getSubtitle()));
                tvTitle.setText(data.getTitle() == null ? "" : data.getTitle());
                tvPrice.setText(data.getAmtStr());
                tvTime.setText(data.getSubheading() +"："+ data.getCreateTime());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener!=null){
                            listener.onItemClick(position,data);
                        }
                    }
                });
            }
        };
    }


}
