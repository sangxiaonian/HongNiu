package com.hongniu.modulefinance.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.modulefinance.R;
import com.sang.common.recycleview.holder.PeakHolder;

/**
 * 作者： ${PING} on 2018/8/9.
 */
public class FinanceExpendHeadHolder extends PeakHolder {
    private TextView tvDes;
    private String title;

    public FinanceExpendHeadHolder(View itemView) {
        super(itemView);
    }

    public FinanceExpendHeadHolder(Context context, int layoutID) {
        super(context, layoutID);
    }

    public FinanceExpendHeadHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.finance_item_head_expend);
    }

    @Override
    public void initView(int position) {
        super.initView(position);
          tvDes = itemView.findViewById(R.id.tv_des);
          if (title!=null){
              tvDes.setText(title);
          }
    }

    public void setTitle(String title) {
        this.title = title;
        if (tvDes!=null&&title!=null){
            tvDes.setText(title);
        }
    }
}
