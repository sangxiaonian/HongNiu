package com.hongniu.modulefinance.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.modulefinance.R;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.widget.VistogramView;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/9.
 */
public class FinanceIncomHeadHolder extends PeakHolder {
    private TextView tvDes;
    private String title;
    private VistogramView vist;
    private int currentX;

    public FinanceIncomHeadHolder(View itemView) {
        super(itemView);
    }

    public FinanceIncomHeadHolder(Context context, int layoutID) {
        super(context, layoutID);
    }

    public FinanceIncomHeadHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.finance_item_head_incom);

    }

    @Override
    public void initView(int position) {
        super.initView(position);
        tvDes = itemView.findViewById(R.id.tv_des);
        vist = itemView.findViewById(R.id.vistorgram);
        tvDes.setText(title == null ? "" : title);


    }

    public void setTitle(String title) {
        this.title = title;
        if (tvDes != null && title != null) {
            tvDes.setText(title);
        }
    }

    public void setDatas(List<List<VistogramView.VistogramBean>> datas) {
        vist.setDatas(datas);
    }

    public void setCurrentX(int i) {
        this.currentX = i;
        itemView.post(new Runnable() {
            @Override
            public void run() {
                vist.setCurrentSelect(currentX);
            }
        });


    }
}
