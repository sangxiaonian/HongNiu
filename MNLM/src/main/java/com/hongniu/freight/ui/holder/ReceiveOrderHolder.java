package com.hongniu.freight.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.hongniu.freight.R;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.order.helper.OrderHelper;
import com.hongniu.freight.ui.holder.order.helper.control.HelperControl;

/**
 * 作者：  on 2020/3/14.
 */
public class ReceiveOrderHolder extends BaseHolder<OrderInfoBean> {


    private RoleOrder role;

    public ReceiveOrderHolder(Context context, ViewGroup parent, RoleOrder order) {
        super(context, parent, R.layout.item_receive_order);
        this.role = order;
    }

    @Override
    public void initView(View itemView, int position, final OrderInfoBean data) {
        super.initView(itemView, position, data);
        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        itemView.setLayoutParams(params);
        TextView tvTitle = itemView.findViewById(R.id.tv_title);
        TextView tv_time = itemView.findViewById(R.id.tv_time);
        TextView tv_start_address = itemView.findViewById(R.id.tv_start_address);
        TextView tv_end_address = itemView.findViewById(R.id.tv_end_address);
        CommonUtils.setText(tv_time, ConvertUtils.formatTime(data.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        CommonUtils.setText(tv_start_address, data.getStartPlaceInfo());
        CommonUtils.setText(tv_end_address, data.getDestinationInfo());
        CommonUtils.setText(tvTitle, data.getCartype());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArouterUtils.getInstance().builder(ArouterParamApp.activity_order_detail)
                        .withParcelable(Param.TRAN, data)
                        .withSerializable(Param.TYPE, role)
                        .navigation(mContext);
            }
        });

    }
}
