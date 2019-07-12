package com.hongniu.modulecargoodsmatch.ui.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.sang.common.recycleview.holder.BaseHolder;

/**
 * 作者： ${PING} on 2019/7/12.
 * 车货匹配列表使用的holder
 */
public class MatchGoodsListHolder extends BaseHolder<GoodsOwnerInforBean> {


    private String id;//用户的id
    private int type;//0  匹配列表 1我的发布
    OnClickButtonListener clickButtonListener;

    public MatchGoodsListHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_match_my_record);
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setClickButtonListener(OnClickButtonListener clickButtonListener) {
        this.clickButtonListener = clickButtonListener;
    }

    @Override
    public void initView(View itemView, final int position, final GoodsOwnerInforBean data) {
        super.initView(itemView, position, data);
        final TextView bt_left = itemView.findViewById(R.id.bt_left);
        final TextView bt_right = itemView.findViewById(R.id.bt_right);
        TextView tvTitle = itemView.findViewById(R.id.tv_title);
        TextView tvTime = itemView.findViewById(R.id.tv_time);
        TextView tv_start_point = itemView.findViewById(R.id.tv_start_point);
        TextView tv_end_point = itemView.findViewById(R.id.tv_end_point);
        TextView tv_goods = itemView.findViewById(R.id.tv_goods);
        TextView tv_price = itemView.findViewById(R.id.tv_price);
        TextView tv_remark = itemView.findViewById(R.id.tv_remark);
        TextView tv1 = itemView.findViewById(R.id.tv1);
        TextView tv_state = itemView.findViewById(R.id.tv_state);
        tv1.setVisibility(View.GONE);
        tv_state.setVisibility(View.VISIBLE);
        tv_price.setVisibility(View.GONE);

        String statusName = getStatusNameByType(data, type);
        tv_state.setText(statusName);
        String userName = data.userId.equals(id) ? "你" : data.userName == null ? "" : data.userName;
        tvTitle.setText(String.format("%s正在寻找%s%s", userName, TextUtils.isEmpty(data.carType) ? "车辆" : data.carType, data.carLength == null ? "" : String.format("（%s米）", data.carLength)));
        tvTime.setText(String.format("需要发货时间：%s", data.startTime));
        tv_start_point.setText(String.format("发货地：%s", data.startPlaceInfo));
        tv_end_point.setText(String.format("收货地：%s", data.destinationInfo));
        tv_goods.setText(String.format("货物名：%s", data.goodsSourceDetail));
        tv_remark.setVisibility(TextUtils.isEmpty(data.remark) ? View.GONE : View.VISIBLE);
        tv_remark.setText(String.format("货主备注：%s", data.remark == null ? "" : data.remark));
        bt_left.setText(getBtLeftNameByType());
        bt_right.setText(getBtRightNameByType(data));
        bt_left.setVisibility(isShowBtLeft(data, type) ? View.VISIBLE : View.GONE);
        bt_right.setVisibility((isShowBtLRight(data, type)) ? View.VISIBLE : View.GONE);
        bt_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickButtonListener != null) {
                    clickButtonListener.onClickLeft(bt_left.getText().toString().trim(),position, data);
                }
            }
        });
        bt_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickButtonListener != null) {
                    clickButtonListener.onClickRight(bt_right.getText().toString().trim(),position, data);
                }

            }
        });

    }

    private String getStatusNameByType(GoodsOwnerInforBean data, int type) {
        String statusName;
        switch (data.status) {
            case 0:
                statusName = "待接单";
                break;
            case 1:
                statusName = "待确认";
                break;
            case 2:
                statusName = "已下单";
                break;
            case 3:
                statusName = "运输中";
                break;
            case 4:
                statusName = "已完成";
                break;
            case 5:
                statusName = "已失效";
                break;
            default:
                statusName = "";
        }
        return statusName;
    }

    private boolean isShowBtLeft(GoodsOwnerInforBean bean, int type) {
        boolean show = false;
        if (type == 0) {
            show = bean.status == 0 || bean.status == 1;
        } else if (type == 1) {
            show = bean.status == 1 || bean.status == 0;
        }
        return show;
    }

    private boolean isShowBtLRight(GoodsOwnerInforBean data, int type) {
        boolean show = false;
        if (type == 0) {
            show = (data.status == 0 || data.status == 1);
        } else if (type == 1) {
            show = data.status != 5;
        }
        return show;
    }

    private String getBtLeftNameByType() {
        String result;
        switch (type) {
            case 0:
                result = "联系货主";
                break;
            case 1:
                result = "删除发布";
                break;

            default:
                result = "";
        }
        return result;
    }

    private String getBtRightNameByType(GoodsOwnerInforBean data) {
        String result;
        switch (type) {
            case 0:
                result = !id.equals(data.userId) ? "我要接单" : "接单信息";
                break;
            case 1:
                result = "接单信息";
                break;

            default:
                result = "";
        }
        return result;
    }


    public interface OnClickButtonListener {
        /**
         * 点击左侧按钮
         *
         * @param btName
         * @param position
         * @param bean
         */
        void onClickLeft(String btName, int position, GoodsOwnerInforBean bean);

        /**
         * 点击右侧按钮
         *
         * @param btName
         * @param position
         * @param bean
         */
        void onClickRight(String btName, int position, GoodsOwnerInforBean bean);
    }


}
