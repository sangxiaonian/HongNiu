package com.sang.common.widget.popu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.sang.common.R;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;

/**
 * 作者： ${PING} on 2018/8/1.
 */
public class BasePopu {

    protected PopupWindow pop;
    protected OnPopuDismissListener dismissListener;
    protected View tragetView;

    public BasePopu(Context context) {
        pop = new PopupWindow(context);
    }

    public void setContentView(View view) {
        pop.setContentView(view);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new ColorDrawable(0x00000000));
        pop.setOutsideTouchable(false);
        pop.setAnimationStyle(R.style.pop_ani);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (dismissListener != null) {
                    dismissListener.onPopuDismsss(BasePopu.this, tragetView);
                }
            }
        });


    }

    public void setOnDismissListener(OnPopuDismissListener listener){
        dismissListener=listener;
    }

    public void show(View view) {
        tragetView=view;
        pop.showAsDropDown(view);
    }

    public void dismiss() {
        if (pop.isShowing()) {
            pop.dismiss();
        }
    }

    public boolean isShow() {
        return pop.isShowing();
    }



}
