package com.sang.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.sang.common.R;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.widget.VericationView;
import com.sang.common.widget.dialog.inter.DialogControl;

/**
 * 作者： ${PING} on 2018/10/9.
 * 密码键盘
 */
public class PasswordDialog implements DialogControl.IDialog, View.OnClickListener, VericationView.OnCompleteListener {

    private Dialog dialog;
    private VericationView vercationview;
    private OnPasswordDialogListener listener;
    private String count;
    private TextView tvTitle;
    private String payDes="金额";

    public PasswordDialog(@NonNull Context context) {
        this(context, 0);
    }


    public PasswordDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
    }

    private void initView(final Context context, int themeResId) {

        final View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_password, null);
        inflate.findViewById(R.id.img_cancel).setOnClickListener(this);
        inflate.findViewById(R.id.tv_password).setOnClickListener(this);
        vercationview = inflate.findViewById(R.id.vercationview);
        tvTitle = inflate.findViewById(R.id.tv_title);
        vercationview.setListener(this);
        vercationview.setType(1);
        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                vercationview.clear();
                vercationview.openSoft();
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_BACK){
                    if (listener!=null){
                        listener.onCancle(PasswordDialog.this);
                    }
                    return true;
                }
                return false;
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface d) {
                vercationview.closeSoft();
                dialog.onBackPressed();

            }
        });

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

    public void setListener(OnPasswordDialogListener listener) {
        this.listener = listener;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tv_password){
            if (listener!=null){
                listener.onForgetPassowrd(this);
            }
            dismiss();
            DeviceUtils.hideSoft(dialog.getWindow().getCurrentFocus());
        }else if (v.getId()==R.id.img_cancel){
            if (listener!=null){
                listener.onCancle(this);
            }
            dismiss();
        }
    }

    @Override
    public void onComplete(String content) {
        if (listener!=null){
            listener.onInputPassWordSuccess(this,count,content);
        }
    }

    /**
     * 设置显示的提现金额
     * @param count
     */
    public void setCount(final String count) {
        this.count = count;
        tvTitle.post(new Runnable() {
            @Override
            public void run() {
                tvTitle.setText((payDes==null?"金额":payDes)+(TextUtils.isEmpty(count)?"0":count) +" 元");
            }
        });

    }

    public void setPayDes(String payDes) {
        this.payDes=payDes;

    }

    public interface OnPasswordDialogListener{
        /**
         * 取消支付
         * @param dialog
         */
        void onCancle(DialogControl.IDialog dialog);

        /**
         * 密码输入完成
         * @param dialog
         * @param count
         * @param passWord
         */
        void onInputPassWordSuccess(DialogControl.IDialog dialog, String count, String passWord);

        /**
         * 忘记密码
         * @param dialog
         */
        void onForgetPassowrd(DialogControl.IDialog dialog);

    }
}
