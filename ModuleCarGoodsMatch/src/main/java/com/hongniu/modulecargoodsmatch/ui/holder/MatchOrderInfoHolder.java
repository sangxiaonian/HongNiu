package com.hongniu.modulecargoodsmatch.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderInfoBean;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;

import org.w3c.dom.Text;

/**
 * 作者：  on 2019/10/27.
 */
public class MatchOrderInfoHolder extends BaseHolder<MatchOrderInfoBean> {

    MatchOrderItemClickListener listener;
    public MatchOrderInfoHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.match_item_driver_order_receiving);
    }


    @Override
    public void initView(View itemView, final int position, final MatchOrderInfoBean data) {
        super.initView(itemView, position, data);
        TextView tv_state = itemView.findViewById(R.id.tv_state);
        TextView tv_state_des = itemView.findViewById(R.id.tv_state_des);
        TextView tv_car_type = itemView.findViewById(R.id.tv_car_type);
        TextView tv_start_address = itemView.findViewById(R.id.tv_start_address);
        TextView tv_price = itemView.findViewById(R.id.tv_price);
        TextView bt_sub = itemView.findViewById(R.id.bt_sub);
        bt_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onBtClick(position,data);
                }
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemClick(position,data);
                }
            }
        });
    }

    public void setListener(MatchOrderItemClickListener listener) {
        this.listener = listener;
    }

    public interface MatchOrderItemClickListener {
        void onBtClick(int position, MatchOrderInfoBean infoHolder);

        void onItemClick(int position, MatchOrderInfoBean data);
    }
}
