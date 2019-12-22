package com.hongniu.supply.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.hongniu.supply.R;
import com.sang.common.recycleview.holder.PeakHolder;

/**
 * 作者：  on 2019/12/22.
 */
public class HeadImageHolder extends PeakHolder {
    private View.OnClickListener onClickListener;
    private View view_more;

    public HeadImageHolder(Context context, ViewGroup parent ) {
        super(context, parent, R.layout.item_home_head_image);
    }

    @Override
    public void initView(int position) {
        super.initView(position);
        view_more = itemView.findViewById(R.id.view_more);
        view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null){
                    onClickListener.onClick(v);
                }
            }
        });
    }
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
