package com.hongniu.supply.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongniu.supply.R;
import com.sang.common.utils.DeviceUtils;

import java.sql.NClob;


/**
 * 作者： ${PING} on 2018/7/30.
 * 红包Dialog
 */

public class RedDialog implements View.OnClickListener {
    ImageView imgBg;
    ImageView imgCancle;

    private View inflate;
    private Dialog dialog;
    private TextView btScan;
    private TextView tvCount;

    public RedDialog(@NonNull Context context) {
        initView(context, 0);
    }


    public RedDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
    }


    private void initView(Context context, int themeResId) {
        inflate = LayoutInflater.from(context).inflate(R.layout.dialog_red, null);
        imgBg = inflate.findViewById(R.id.img_bg);
        imgCancle = inflate.findViewById(R.id.img_cancle);
        tvCount = inflate.findViewById(R.id.tv_count);
        btScan = inflate.findViewById(R.id.bt_scan);
        tvCount.setVisibility(View.GONE);
        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        imgCancle.setOnClickListener(this);
        btScan.setOnClickListener(this);

    }

    public void setContent(String msg){
        tvCount.setText(TextUtils.isEmpty(msg)?"0.00":msg);
    }

    public void show() {
        dialog.show();

    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_scan) {
            imgBg.setImageResource(R.mipmap.bg_winning);
            ViewGroup.LayoutParams params = imgBg.getLayoutParams();
            params.height= DeviceUtils.dip2px(dialog.getContext(),335);
            imgBg.setLayoutParams(params);
            tvCount.setVisibility(View.VISIBLE);
            btScan.setVisibility(View.GONE);
        } else if (i == R.id.img_cancle) {

            dismiss();

        }
    }


}
