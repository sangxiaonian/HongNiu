package com.hongniu.baselibrary.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.baselibrary.R;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.widget.dialog.ListBottomDialog;

/**
 * 作者： ${PING} on 2018/9/14.
 * 我的收款方式弹出的Dialog，微信和银联收款方式
 */
public class PayWaysDialog  extends ListBottomDialog<String> {


    public PayWaysDialog(Context context) {
        super(context);
    }

    @Override
    public BaseHolder<String> getBaseHolder(Context context, ViewGroup parent) {
        return new BaseHolder<String>(context,parent, R.layout.item_pay_chose){
            @Override
            public void initView(View itemView, final int position, final String data) {
                super.initView(itemView, position, data);
                TextView tv = itemView.findViewById(R.id.tv);
                tv.setText(data==null?"":data);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (entryClickListener!=null){
                            entryClickListener.onEntryClick(dialog,position,data);
                        }
                    }
                });
            }
        };
    }

    @Override
    public PeakHolder getCancleHolder(Context context, ViewGroup parent) {
        return new PeakHolder(context,parent,R.layout.item_pay_cancle){
            @Override
            public void initView(int position) {
                super.initView(position);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bottomListener!=null){
                            bottomListener.onClick(v);
                        }else {
                            dimiss();
                        }
                    }
                });
            }
        };
    }
}
