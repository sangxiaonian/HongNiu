package com.sang.common.recycleview.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sang.common.R;

/**
 * 作者： ${桑小年} on 2018/8/18.
 * 努力，为梦长留
 */
public class EmptyHolder extends PeakHolder {

    ImageView img;
    TextView tv;
    private int imgRes;
    private String msg;

    public EmptyHolder(View itemView) {
        super(itemView);
    }

    public EmptyHolder(Context context, int layoutID) {
        super(context, layoutID);
    }

    public EmptyHolder(Context context, ViewGroup parent, int layoutID) {
        super(context, parent, layoutID);
    }

    @Override
    public void initView(int position) {
        super.initView(position);
        img = itemView.findViewById(R.id.img_empty);
        tv = itemView.findViewById(R.id.tv_empty);
        if (imgRes != 0) {
            img.setImageResource(imgRes);
        }

        tv.setText(msg == null ? "" : msg);
    }

    public void setEmptyInfor(int imgRes, String msg) {
        this.imgRes = imgRes;
        this.msg = msg;
    }
}
