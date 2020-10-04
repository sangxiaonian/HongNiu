package com.hongniu.moduleorder.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hongniu.moduleorder.R;

/**
 * 作者： ${PING} on 2018/8/9.
 * 车牌联想pupWindow
 */
public class PayAleartPop  {
    protected PopupWindow pop;
    private TextView tvContent;
    private View index;


    public PayAleartPop(Context context) {
        pop = new PopupWindow(context);
        setContentView(context);
    }


    private void setContentView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.order_pay_aleart_pup, null);
        tvContent = inflate.findViewById(R.id.tv_content);
        index = inflate.findViewById(R.id.index);
        pop.setContentView(inflate);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new ColorDrawable(0x00000000));
        pop.setOutsideTouchable(true);



    }


    public void setContent(String msg){
        tvContent.setText(msg==null?"":msg);
    }

    public void show(final View view) {
        if (!isShow()) {
            pop.showAsDropDown(view);
        }
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
