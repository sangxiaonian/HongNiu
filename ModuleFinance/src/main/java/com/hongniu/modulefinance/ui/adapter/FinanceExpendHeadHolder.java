package com.hongniu.modulefinance.ui.adapter;

import android.app.Activity;
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
import com.sang.common.widget.guideview.BaseGuide;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/9.
 */
public class FinanceExpendHeadHolder extends PeakHolder {
    private TextView tvDes;
    private String title;
    private VistogramView vist;

    public FinanceExpendHeadHolder(View itemView) {
        super(itemView);
    }

    public FinanceExpendHeadHolder(Context context, int layoutID) {
        super(context, layoutID);
    }

    public FinanceExpendHeadHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.finance_item_head_expend);


        if (Param.isDebug) {
            final List<List<VistogramView.VistogramBean>> debugDatas = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                List<VistogramView.VistogramBean> list = new ArrayList<>();
                int random = ConvertUtils.getRandom(1, 3);

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

    public void showGuide(Activity activity){
        BaseGuide guidexffx = new BaseGuide();
        guidexffx.setMsg("左右滑动，查看每月对比")
                .setView(vist)
                .setHighTargetGraphStyle(0)
                .setActivity(activity)
                .setShowTop(true)
                .setSharedPreferencesKey(Param.FINACNE_HISTOGRAM)
                .showGuide();
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
