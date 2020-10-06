package com.hongniu.freight.ui.holder.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.hongniu.freight.R;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.order.helper.OrderHelper;
import com.hongniu.freight.ui.holder.order.helper.control.HelperControl;
import com.hongniu.freight.utils.Utils;

import java.util.Map;

/**
 * 作者：  on 2020/2/6.
 * 托运人
 */
class OrderTYRHolder extends OrderBaseHolder {


    public OrderTYRHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_tyr_order);
    }

    @Override
    public void initView(View itemView, int position, OrderInfoBean data) {
        super.initView(itemView, position, data);
        TextView tvTitle = itemView.findViewById(R.id.tv_title);
        TextView tv_time = itemView.findViewById(R.id.tv_time);
        TextView tv_tag = itemView.findViewById(R.id.tv_tag);
        TextView tv_start_address = itemView.findViewById(R.id.tv_start_address);
        TextView tv_end_address = itemView.findViewById(R.id.tv_end_address);
        ViewGroup ll_bt = itemView.findViewById(R.id.ll_bt);
        Group bottom_group = itemView.findViewById(R.id.bottom_group);

        HelperControl helper = new OrderHelper(role)
                .setInsurance(data.getPayPolicyState() == 1)
                .setHasReceiptImage(data.getHasReceiptImage()==1)

                .setStatus(data.getStatus());

        CommonUtils.setText(tv_time, ConvertUtils.formatTime(data.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
        CommonUtils.setText(tv_start_address, data.getStartPlaceInfo());
        CommonUtils.setText(tv_end_address, data.getDestinationInfo());
        tv_tag.setText(role.getName());
        tv_tag.setVisibility(role== RoleOrder.PLATFORM?View.GONE:View.VISIBLE);
        tvTitle.setText(helper.getStatus().getName());

        Map<String, Integer> buttons = helper.getButtons(data.getStatus());
        Utils.fliter(role,data, buttons);

        //控制底部button
        addButton( ll_bt, data, buttons);
        bottom_group.setVisibility(CollectionUtils.isEmpty(buttons)?View.GONE:View.VISIBLE);
    }



}
