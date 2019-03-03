package com.hongniu.baselibrary.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.baselibrary.R;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.widget.dialog.ListBottomDialog;

/**
 * 作者： ${PING} on 2019/3/3.
 */
public class BottomListDialog extends ListBottomDialog<String> {
    public BottomListDialog(Context context) {
        super(context);
    }

    @Override
    public BaseHolder<String> getBaseHolder(Context context, ViewGroup parent) {
        return new BaseHolder<String>(context,parent, R.layout.item_bottom_item){
            @Override
            public void initView(View itemView, final int position, final String data) {
                super.initView(itemView, position, data);
                TextView title = itemView.findViewById(R.id.title);
                title.setText(data==null?"":data);
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (entryClickListener!=null) {
                            entryClickListener.onEntryClick(dialog,position,data);
                        }
                    }
                });
            }
        };
    }

    @Override
    public PeakHolder getCancleHolder(Context context, ViewGroup parent) {
        return new PeakHolder(context,parent,R.layout.item_bottom_item_cancle){
            @Override
            public void initView(int position) {
                super.initView(position);
                itemView.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bottomListener!=null) {
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
