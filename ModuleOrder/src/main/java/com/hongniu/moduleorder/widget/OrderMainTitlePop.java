package com.hongniu.moduleorder.widget;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.hongniu.moduleorder.R;
import com.sang.common.widget.popu.BasePopu;

/**
 * 作者： ${PING} on 2018/8/1.
 */
public class OrderMainTitlePop extends BasePopu implements View.OnClickListener {


    public interface OnBackClickListener{
        void onBackPressed();
    }
    OnBackClickListener onBackClickListener;

    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    public OrderMainTitlePop(Context context) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.order_main_title_pop, null);
        inflate.findViewById(R.id.ll_left).setOnClickListener(this);
        inflate.findViewById(R.id.ll_center).setOnClickListener(this);
        inflate.findViewById(R.id.ll_right).setOnClickListener(this);
        View viewById = inflate.findViewById(R.id.view_out);
        viewById.setOnClickListener(this);

        setContentView(inflate);
        viewById.setFocusable(true);
        viewById.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode== KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    if (onBackClickListener!=null){
                        onBackClickListener.onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });

    }

    OnOrderMainClickListener listener;

    public void setListener(OnOrderMainClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_left) {
            if (listener != null) {
                listener.onMainClick(this,3);
            }

        } else if (i == R.id.ll_center) {
            if (listener != null) {
                listener.onMainClick(this,1);
            }

        } else if (i == R.id.ll_right) {
            if (listener != null) {
                listener.onMainClick(this,2);
            }

        }else if (i == R.id.view_out) {
            dismiss();
        }
    }

    public interface OnOrderMainClickListener{

        /**
         * 顶部角色类型被选中点击的时候
         * @param popu
         * @param position
         */
        void onMainClick(OrderMainTitlePop popu ,int position);
    }
}
