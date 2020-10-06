package com.fy.companylibrary.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fy.androidlibrary.widget.recycle.control.RecycleControl;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.companylibrary.R;


/**
 * 作者： ${PING} on 2019/3/3.
 */
public class BottomListDialog extends ListBaseDialog<String> {
    public BottomListDialog(Context context) {
        super(context);
    }

    RecycleControl.OnItemClickListener<String> entryClickListener;

    public void setEntryClickListener(RecycleControl.OnItemClickListener<String> entryClickListener) {
        this.entryClickListener = entryClickListener;
    }

    @Override
    public BaseHolder<String> getBaseHolder(Context context, ViewGroup parent) {
        return new BaseHolder<String>(context, parent, R.layout.item_bottom_item) {
            @Override
            public void initView(View itemView, final int position, final String data) {
                super.initView(itemView, position, data);
                TextView title = itemView.findViewById(R.id.title);
                title.setText(data == null ? "" : data);
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dimiss();
                        if (entryClickListener != null) {
                            entryClickListener.onItemClick(position, data);
                        }
                    }
                });
            }
        };
    }

    @Override
    public PeakHolder getCancleHolder(Context context, ViewGroup parent) {
        return new PeakHolder(context, parent, R.layout.item_bottom_item_cancle) {
            @Override
            public void initView(View itemView, int position) {
                itemView.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bottomListener != null) {
                            bottomListener.onClick(v);
                        } else {
                            dimiss();
                        }
                    }


                });
            }
        };
    }
}