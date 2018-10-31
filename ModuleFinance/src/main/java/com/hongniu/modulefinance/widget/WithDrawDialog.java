package com.hongniu.modulefinance.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.hongniu.baselibrary.widget.WithDrawProgress;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.BalanceOfAccountBean;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.widget.dialog.inter.DialogControl;

/**
 * 作者： ${PING} on 2018/10/9.
 * 添加新的提现到账方式
 */
public class WithDrawDialog implements DialogControl.IDialog, View.OnClickListener {

    private View imgCancel ;//取消按钮
    private Dialog dialog;

    OnAddNewPayWayListener listener;
    private TextView tv_start_title;
    private TextView tv_start_time  ;
    private TextView tv_center_title;
    private TextView tv_center_time ;
    private TextView tv_bottom_title;
    private TextView tv_bottom_time ;
    private TextView tv_money       ;
    private TextView tv_withdraw    ;
    private WithDrawProgress progress;

    public interface OnAddNewPayWayListener {

        void onAddUnipay(DialogControl.IDialog dialog);

        void onAddWechat(DialogControl.IDialog dialog);

        void onAddAli(DialogControl.IDialog dialog);

    }

    public WithDrawDialog(@NonNull Context context) {
        this(context, 0);
    }


    public WithDrawDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
    }

    private void initView(Context context, int themeResId) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_withdraw, null);
        imgCancel = inflate.findViewById(R.id.img_cancel);
        tv_start_title = inflate.findViewById(R.id.tv_start_title);
        tv_start_time   = inflate.findViewById(R.id.tv_start_time);
        tv_center_title = inflate.findViewById(R.id.tv_center_title);
        tv_center_time  = inflate.findViewById(R.id.tv_center_time);
        tv_bottom_title = inflate.findViewById(R.id.tv_bottom_title);
        tv_bottom_time  = inflate.findViewById(R.id.tv_bottom_time);
        tv_money         = inflate.findViewById(R.id.tv_money);
        tv_withdraw      = inflate.findViewById(R.id.tv_withdraw);
        progress      = inflate.findViewById(R.id.progress);


        imgCancel.setOnClickListener(this);




        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(context, 360));
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }




    public void setData(BalanceOfAccountBean balanceOfAccountBean){

    }


    @Override
    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    @Override
    public void show() {
        dialog.show();
    }

    @Override
    public boolean isShowing() {
        return dialog.isShowing();
    }

    @Override
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
        if (v.getId() == R.id.img_cancel) {
            dismiss();
        } else if (v.getId() == R.id.ali) {
            if (listener != null) {
                listener.onAddAli(this);
            }
        } else if (v.getId() == R.id.wechat) {
            if (listener != null) {
                listener.onAddWechat(this);
            }
        } else if (v.getId() == R.id.unipay) {
            if (listener != null) {
                listener.onAddUnipay(this);
            }
        }
    }
}
