package com.hongniu.freight.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.widget.dialog.inter.DialogControl;


/**
 * 作者： ${桑小年} on 2018/12/1.
 * 差额设置
 */
public class BalancePayDialog implements DialogControl.IDialog, View.OnClickListener  {
    private View imgCancel;
    private View btSum;
    private Dialog dialog;
    TextView tv_price;
    ItemTextView item_balance_pay;
    OnBalancePayListener onBalancePayListener;

    public BalancePayDialog(@NonNull Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.mnlm_dialog_balance_pay, null);
        imgCancel = inflate.findViewById(R.id.img_cancel);
        tv_price = inflate.findViewById(R.id.tv_price);
        item_balance_pay = inflate.findViewById(R.id.item_balance_pay);
        btSum = inflate.findViewById(R.id.bt_sum);
        initListener();
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(context, 360));
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    private void initListener() {
        imgCancel.setOnClickListener(this);
        btSum.setOnClickListener(this);

    }

    public void setOnBalancePayListener(OnBalancePayListener onBalancePayListener) {
        this.onBalancePayListener = onBalancePayListener;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_cancel) {
            dialog.dismiss();
        } else  if (v.getId() == R.id.bt_sum) {
            if (TextUtils.isEmpty(item_balance_pay.getTextCenter())) {
                ToastUtils.getInstance().show(item_balance_pay.getTextCenterHide());
                return;
            }

            if (onBalancePayListener != null) {
                onBalancePayListener.onEntryClick(this, item_balance_pay.getTextCenter() );
            }

        }
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

    public void setPrice(String changeFloat) {
        tv_price.setText(String.format("当前订单已支付运费：%s元",changeFloat));
    }


    public interface OnBalancePayListener {

        /**
         * 点击确认按钮
         *
         * @param insuranceBuyDialog
         * @param price
         */
        void onEntryClick(BalancePayDialog insuranceBuyDialog, String price );

    }
}
