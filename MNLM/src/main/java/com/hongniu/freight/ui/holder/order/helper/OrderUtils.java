package com.hongniu.freight.ui.holder.order.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.order.CustomOrderButtonClick;
import com.hongniu.freight.ui.holder.order.helper.control.OrderButtonClickListener;

import java.util.Map;

/**
 * 作者：  on 2020/2/8.
 */
public class OrderUtils {


    protected static TextView getListButton(Context context, int type, String msg) {
        TextView button = new TextView(context);
        button.setGravity(Gravity.CENTER);
        button.setText(msg);
        button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        if (type == 0) {
            button.setBackgroundResource(R.drawable.ovl_5_eeeeee);
            button.setTextColor(context.getResources().getColor(R.color.color_of_040000));
        } else {
            button.setBackgroundResource(R.drawable.ovl_5_e50000);
            button.setTextColor(Color.WHITE);

        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DeviceUtils.dip2px(context, 72), DeviceUtils.dip2px(context, 28));

        button.setLayoutParams(params);
        return button;
    }

    protected static TextView getDetailButton(Context context, int type, String msg) {
        TextView button = new TextView(context);
        button.setGravity(Gravity.CENTER);
        button.setText(msg);
        button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        if (type == 0) {
            button.setBackgroundResource(R.drawable.ovl_5_eeeeee);
            button.setTextColor(context.getResources().getColor(R.color.color_of_040000));
        } else {
            button.setBackgroundResource(R.drawable.ovl_5_e50000);
            button.setTextColor(Color.WHITE);

        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, DeviceUtils.dip2px(context, 49));
        params.weight = 1;
        button.setLayoutParams(params);
        return button;
    }

    public static void addButton(ViewGroup ll_bt, final OrderInfoBean data, Map<String, Integer> status, OrderButtonClickListener orderButtonClickListener) {
        addButton(ll_bt, data, status, false, orderButtonClickListener);
    }

    public static void addButton(ViewGroup ll_bt, final OrderInfoBean data, Map<String, Integer> status, boolean fill, OrderButtonClickListener orderButtonClickListener) {
        ll_bt.removeAllViews();
        if (CollectionUtils.isEmpty(status)) {
            ll_bt.setVisibility(View.GONE);
        } else {
            ll_bt.setVisibility(View.VISIBLE);
            for (final String s : status.keySet()) {
                TextView button =fill?
                        OrderUtils.getDetailButton(ll_bt.getContext(), status.get(s), s)
                        :
                        OrderUtils.getListButton(ll_bt.getContext(), status.get(s), s)
                        ;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (orderButtonClickListener != null) {
                            new CustomOrderButtonClick(orderButtonClickListener).performClick(s, data);
                        }
                    }
                });
                ll_bt.addView(button);
            }
        }
    }


}
