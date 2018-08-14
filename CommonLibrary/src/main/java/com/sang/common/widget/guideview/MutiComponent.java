package com.sang.common.widget.guideview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sang.common.R;


/**
 * Created by binIoter on 16/6/17.
 */
public class MutiComponent implements Component {

    protected String msg;
    protected View targView;
    protected View.OnClickListener onClickListener;
    private int layoutID= R.layout.item_guide;


    public void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }

    @Override
    public View getView(LayoutInflater inflater) {
        final View ll =  inflater.inflate(layoutID, null);

        TextView infors = (TextView) ll.findViewById(R.id.tv_infors);
        if (msg != null) {
            infors.setText(msg);
        }
        ll.findViewById(R.id.tv_know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }

            }
        });
        return ll;
    }

    @Override
    public int getAnchor() {
        return Component.ANCHOR_TOP;
    }

    @Override
    public int getFitPosition() {
        return Component.FIT_CENTER;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return -20;
    }

    public void setTargView(View targView) {
        this.targView = targView;
    }

    public void setInfor(String msg) {
        this.msg = msg;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
