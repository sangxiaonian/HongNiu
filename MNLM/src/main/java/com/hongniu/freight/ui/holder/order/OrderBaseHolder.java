package com.hongniu.freight.ui.holder.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.order.helper.OrderHelper;
import com.hongniu.freight.ui.holder.order.helper.OrderUtils;
import com.hongniu.freight.ui.holder.order.helper.control.OrderButtonClickListener;

import java.util.Map;

/**
 * 作者：  on 2020/2/6.
 */
public class OrderBaseHolder extends BaseHolder<OrderInfoBean> {

    private OrderButtonClickListener orderButtonClickListener;
    RoleOrder role=  RoleOrder.SHIPPER;
    public OrderBaseHolder(Context context, ViewGroup parent, int layoutID ) {
        super(context, parent, layoutID);
    }

    public void setOrderButtonClickListener(OrderButtonClickListener orderButtonClickListener) {
        this.orderButtonClickListener = orderButtonClickListener;
    }

    public void setRole(RoleOrder role) {
        this.role = role;
    }

    @Override
    public void initView(View itemView, int position, final OrderInfoBean data) {
        super.initView(itemView, position, data);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_order_detail)
                        .withParcelable(Param.TRAN,data)
                        .withSerializable(Param.TYPE,role)
                        .navigation(mContext);
            }
        });
    }
    protected  void addButton(  ViewGroup ll_bt, final OrderInfoBean data, Map<String, Integer> status) {
        OrderUtils.addButton( ll_bt,data,status,orderButtonClickListener);

    }
}
