package com.sang.thirdlibrary.pay;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.sang.common.widget.VericationView;
import com.sang.thirdlibrary.R;


/**
 * 作者： ${PING} on 2018/10/9.
 * 密码键盘
 */
public class PasswordDialog extends Dialog implements View.OnClickListener, VericationView.OnCompleteListener {

    private Dialog dialog;
    private VericationView vercationview;
    private OnPasswordDialogListener listener;
    private String count;
    private TextView tvTitle;
    private String payDes="金额";
    private Context context;


    public PasswordDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        initView(context);
    }




    private void initView(final Context context ) {

        final View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_password, null);
        inflate.findViewById(R.id.img_cancel).setOnClickListener(this);
        inflate.findViewById(R.id.tv_password).setOnClickListener(this);
        vercationview = inflate.findViewById(R.id.vercationview);
        tvTitle = inflate.findViewById(R.id.tv_title);
        vercationview.setListener(this);
        vercationview.setType(1);
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                vercationview.clear();
                vercationview.openSoft();
            }
        });
        dialog.setOnKeyListener(new OnKeyListener() {
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

        dialog.setOnDismissListener(new OnDismissListener() {
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
        tvTitle.setText(String.format("%s%s 元", payDes == null ? "金额" : payDes, TextUtils.isEmpty(count) ? "0" : count));
    }

    public void setPayDes(String payDes) {
        this.payDes=payDes;

    }

    public interface OnPasswordDialogListener{
        /**
         * 取消支付
         * @param dialog
         */
        void onCancle(Dialog dialog);

        /**
         * 密码输入完成
         * @param dialog
         * @param count
         * @param passWord
         */
        void onInputPassWordSuccess(Dialog dialog, String count, String passWord);

        /**
         * 忘记密码
         * @param dialog
         */
        void onForgetPassowrd(Dialog dialog);

    }
}
