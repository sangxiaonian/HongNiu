package com.hongniu.modulefinance.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hongniu.modulefinance.R;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.sang.common.widget.dialog.inter.DialogControl;

/**
 * 作者： ${PING} on 2018/10/9.
 * 添加新的提现到账方式
 */
public class CreatAccountDialog implements DialogControl.IDialog, View.OnClickListener {

    private View imgCancel, unipay, wechat, ali;//取消按钮
    private Dialog dialog;

    OnAddNewPayWayListener listener;

    public interface OnAddNewPayWayListener {

        void onAddUnipay(DialogControl.IDialog dialog);

        void onAddWechat(DialogControl.IDialog dialog);

        void onAddAli(DialogControl.IDialog dialog);

    }

    public CreatAccountDialog(@NonNull Context context) {
        this(context, 0);
    }


    public CreatAccountDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
        initData(context);
    }

    private void initView(Context context, int themeResId) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_account_add, null);
        imgCancel = inflate.findViewById(R.id.img_cancel);

        unipay = inflate.findViewById(R.id.unipay);
        wechat = inflate.findViewById(R.id.wechat);
        ali = inflate.findViewById(R.id.ali);
        imgCancel.setOnClickListener(this);
        ali.setOnClickListener(this);
        wechat.setOnClickListener(this);
        unipay.setOnClickListener(this);

        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(context, 360));
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    private void initData(Context context) {
        unipay.setVisibility(View.GONE);
        ali.setVisibility(View.GONE);

    }

    public void setListener(OnAddNewPayWayListener listener) {
        this.listener = listener;
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
