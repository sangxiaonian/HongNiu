package com.hongniu.freight.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.hongniu.freight.R;

/**
 * 作者：  on 2020/2/12.
 */
public class EmptyHolder extends PeakHolder {
    public EmptyHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_empty);
    }

    private int emptyImg;
    private String emptyMsg;



    public void setEmptyImg(int emptyImg) {
        this.emptyImg = emptyImg;
    }

    public void setEmptyMsg(String emptyMsg) {
        this.emptyMsg = emptyMsg;
    }

    @Override
    public void initView(View itemView, int position) {
        super.initView(itemView, position);
        ImageView img = itemView.findViewById(R.id.img_empty);
        TextView tv = itemView.findViewById(R.id.tv_empty);
        if (emptyImg!=0){
            img.setImageResource(emptyImg);
        }
        if (emptyMsg!=null){
            tv.setText(emptyMsg);
        }
    }
}
