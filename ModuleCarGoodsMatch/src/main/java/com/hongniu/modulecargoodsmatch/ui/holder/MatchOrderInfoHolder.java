package com.hongniu.modulecargoodsmatch.ui.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderInfoBean;
import com.hongniu.modulecargoodsmatch.utils.MatchOrderListHelper;
import com.sang.common.recycleview.holder.BaseHolder;

/**
 * 作者：  on 2019/10/27.
 */
public class MatchOrderInfoHolder extends BaseHolder<MatchOrderInfoBean> {

    MatchOrderItemClickListener listener;

    private int type;//角色类型 0 2 司机 1货主

    MatchOrderListHelper helper;

    public MatchOrderInfoHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.match_item_driver_order_receiving);
        helper = new MatchOrderListHelper();
    }


    @Override
    public void initView(View itemView, final int position, final MatchOrderInfoBean data) {
        super.initView(itemView, position, data);


        helper.setType(type)
                .setState(data.getStatus());


        TextView tv_state = itemView.findViewById(R.id.tv_state);
        TextView tv_state_des = itemView.findViewById(R.id.tv_state_des);
        TextView tv_car_type = itemView.findViewById(R.id.tv_car_type);
        TextView tv_start_address = itemView.findViewById(R.id.tv_start_address);
        TextView tv_end_address = itemView.findViewById(R.id.tv_end_address);
        TextView tv_price = itemView.findViewById(R.id.tv_price);
        final TextView bt_sub = itemView.findViewById(R.id.bt_sub);
        final TextView bt_sub_white = itemView.findViewById(R.id.bt_sub_white);

        tv_car_type.setText(TextUtils.isEmpty(data.getCartypeName()) ? "" : data.getCartypeName());
        tv_start_address.setText(TextUtils.isEmpty(data.getStartPlaceInfo()) ? "" : data.getStartPlaceInfo());
        tv_end_address.setText(TextUtils.isEmpty(data.getDestinationInfo()) ? "" : data.getDestinationInfo());
        tv_price.setText(TextUtils.isEmpty(data.getEstimateFare()) ? "" : ("￥" + data.getEstimateFare()));
        tv_state.setText(helper.getStateDes());
        tv_state.setBackgroundColor(helper.getStateColor(mContext));
        tv_state_des.setText("发货人正在等待接单…");

        bt_sub.setVisibility(TextUtils.isEmpty(helper.getButtonRed()) ? View.GONE : View.VISIBLE);
        bt_sub_white.setVisibility(TextUtils.isEmpty(helper.getButtonWhite()) ? View.GONE : View.VISIBLE);
        bt_sub.setText(helper.getButtonRed());
        bt_sub_white.setText(helper.getButtonWhite());

        bt_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onBtClick(position, data, type,bt_sub.getText().toString().trim());
                }
            }
        });
        bt_sub_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onBtClick(position, data, type,bt_sub_white.getText().toString().trim());
                }
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position, data);
                }
            }
        });
    }

    public void setListener(MatchOrderItemClickListener listener) {
        this.listener = listener;
    }

    public void setType(int type) {
        this.type = type;
    }

    public interface MatchOrderItemClickListener {
        void onBtClick(int position, MatchOrderInfoBean infoHolder, int type, String btState);

        void onItemClick(int position, MatchOrderInfoBean data);
    }


    private String getState(int state) {
        String msg;
        switch (state) {
            case 1:
                msg = "待付款";
                break;
            case 2:
                msg = "待接单";
                break;
            case 3:
                msg = "已接单";
                break;
            case 4:
                msg = "已送达";
                break;
            case 5:
                msg = "已完成";
                break;
            case 6:
                msg = "已取消";
                break;
            default:
                msg = "异常";
        }
        return msg;
    }


    private int getStateColor(int state) {
        int color;
        switch (state) {
            case 1:
                color = mContext.getResources().getColor(R.color.color_of_e83515);
                break;
            case 2:
                color = mContext.getResources().getColor(R.color.color_of_e83515);
                break;
            case 3:
                color = mContext.getResources().getColor(R.color.color_of_52c41a);
                break;
            case 4://"已送达"
            case 6://"已取消"
            case 5://"已完成"

                color = mContext.getResources().getColor(R.color.color_of_797c8b);

                break;

            default:
                color = mContext.getResources().getColor(R.color.color_of_797c8b);
        }
        return color;
    }


}
