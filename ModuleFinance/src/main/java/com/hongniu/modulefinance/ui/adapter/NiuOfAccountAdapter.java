package com.hongniu.modulefinance.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.baselibrary.widget.order.OrderDetailDialog;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.control.OnItemClickListener;
import com.hongniu.modulefinance.entity.NiuOfAccountBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/8.
 * 牛币已入账、待入账 Adapter
 */
public class NiuOfAccountAdapter extends XAdapter<NiuOfAccountBean> {

    OnItemClickListener<NiuOfAccountBean> itemClickListener;


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
                tvOrder.setText(String.format(mContext.getString(R.string.car_num),data.getCarNumber())  );
                tvTime.setText(String.format(mContext.getString(R.string.finance_niu_creat_car_time),data.getCreateTime())  );
                tvPrice.setText(data.getIntegral()+ "个");


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener!=null){
                            itemClickListener.onItemClick(position,data);
                        }
                    }
                });
//                ;
            }
        };
    }

    public void setItemClickListener(OnItemClickListener<NiuOfAccountBean> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
