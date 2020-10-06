package com.hongniu.freight.ui.holder.order;

import android.content.Context;
import android.view.ViewGroup;

import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.hongniu.freight.R;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.order.helper.control.OrderButtonClickListener;

/**
 * 作者：  on 2020/2/6.
 */
public class OrderHolderBuider {

    private RoleOrder type;//0 托运人 1 承运人 2 司机
    private ViewGroup parent;
    private Context context;
    OrderButtonClickListener orderButtonClickListener;

    public OrderHolderBuider(Context context) {
        this.context = context;
    }

    public OrderHolderBuider setType(RoleOrder type) {
        this.type = type;
        return this;
    }

    public OrderHolderBuider setParent(ViewGroup parent) {
        this.parent = parent;
        return this;
    }

    public OrderHolderBuider setOrderButtonClickListener(OrderButtonClickListener orderButtonClickListener) {
        this.orderButtonClickListener = orderButtonClickListener;
        return this;
    }

    public BaseHolder<OrderInfoBean> build() {
        OrderBaseHolder holder = new OrderTYRHolder(context, parent);
        holder.setRole(type);
        holder.setOrderButtonClickListener(orderButtonClickListener);
        return holder;
    }
}
