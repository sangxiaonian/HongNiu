package com.hongniu.freight.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.hongniu.freight.R;
import com.hongniu.freight.widget.VistogramView;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者： ${PING} on 2018/8/9.
 */
public class BillHeadHolder extends PeakHolder {
    private TextView tvDes;
    private String title;
    private VistogramView vist;
    private int currentX;
    private boolean hideVisDes;
    private List<List<VistogramView.VistogramBean>> datas;
    private int type;
    private View des;


    public BillHeadHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.bill_item_head_expend);
        datas = new ArrayList<>();

    }


    @Override
    public void initView(View itemView, int position) {
        super.initView(itemView, position);
        tvDes = itemView.findViewById(R.id.tv_des);
        vist = itemView.findViewById(R.id.vistorgram);
        vist = itemView.findViewById(R.id.vistorgram);
        des = itemView.findViewById(R.id.ll_vis_des);
        if (title != null) {
            tvDes.setText(title);
        }

        vist.setDatas(datas);

        if (type == 0) {
            des.setVisibility(  View.GONE );
            tvDes.setText("收入明细");
        }else if (type==1){
            tvDes.setText("运费支出明细");
            itemView.findViewById(R.id.ll_left).setVisibility(View.GONE);
        }else {
            tvDes.setText("运费支出明细");
        }

    }




    public void setDatas(List<List<VistogramView.VistogramBean>> datas) {
        this.datas.clear();
        if (datas != null) {
            this.datas.addAll(datas);
        }
        if (vist != null) {
            vist.setDatas(datas);
        }
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



    public void setType(int type) {
        this.type = type;
    }
}
