package com.hongniu.modulefinance.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulefinance.R;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.widget.VistogramView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/9.
 */
public class FinanceIncomHeadHolder extends PeakHolder {
    private TextView tvDes;
    private String title;
    private VistogramView vist;

    public FinanceIncomHeadHolder(View itemView) {
        super(itemView);
    }

    public FinanceIncomHeadHolder(Context context, int layoutID) {
        super(context, layoutID);
    }

    public FinanceIncomHeadHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.finance_item_head_incom);


        if (Param.isDebug) {
            final List<List<VistogramView.VistogramBean>> debugDatas = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                List<VistogramView.VistogramBean> list = new ArrayList<>();
                list.add(new VistogramView.VistogramBean(Color.parseColor("#F06F28"), ConvertUtils.getRandom(10000, 15000), (i + 1) + "月"));
                list.add(new VistogramView.VistogramBean(Color.parseColor("#007AFF"), ConvertUtils.getRandom(5000, 8000), (i + 1) + "月"));
                debugDatas.add(list);
            }

            itemView.post(new Runnable() {
                @Override
                public void run() {
                    setDatas(debugDatas);
                }
            })
            ;
        }


    }

    @Override
    public void initView(int position) {
        super.initView(position);
        tvDes = itemView.findViewById(R.id.tv_des);
        vist = itemView.findViewById(R.id.vistorgram);
        if (title != null) {
            tvDes.setText(title);
        }


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

}
