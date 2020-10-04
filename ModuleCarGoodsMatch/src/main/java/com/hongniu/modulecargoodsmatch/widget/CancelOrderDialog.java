package com.hongniu.modulecargoodsmatch.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hongniu.modulecargoodsmatch.R;
import com.fy.androidlibrary.toast.ToastUtils;

/**
 * 作者：  on 2019/11/3.
 * 取消订单
 */
public class CancelOrderDialog {
    protected Context context;
    protected Dialog dialog;
    protected Display display;

    protected CancelEntryClickListener entryClickListener;


    EditText etRemark;
    Button button;
    private String subTitleInfo;
    private TextView tv_describe;
    private View img_cancel;
    private RadioButton rbLeft;
    private RadioButton rbRight;

    public CancelOrderDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        builder();
    }


    public CancelOrderDialog setEntryClickListener(CancelEntryClickListener entryClickListener) {
        this.entryClickListener = entryClickListener;
        return this;
    }


    /**
     * 初始化布局
     *
     * @return
     */
    public CancelOrderDialog builder() {
        // 获取Dialog布局
        final View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_cancel_order, null);
        etRemark = view.findViewById(R.id.et_remark);
        button = view.findViewById(R.id.bt_sum);
        tv_describe = view.findViewById(R.id.tv_describe);
        img_cancel = view.findViewById(R.id.img_cancel);
        rbLeft = view.findViewById(R.id.rb_left);
        rbRight = view.findViewById(R.id.rv_right);

        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dimiss();
            }
        });

        setSubTitle(subTitleInfo);

        rbLeft.performClick();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etRemark.getText().toString().length() <= 0) {
                    ToastUtils.getInstance().show(etRemark.getHint().toString());
                    return;
                }
                dimiss();
                if (entryClickListener != null) {
                    entryClickListener.OnCancelOrderClick(!rbLeft.isSelected(), etRemark.getText().toString().trim());
                }
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(view);
        setBottomLayout(display.getWidth(), display.getWidth() * 5 / 4);
        return this;
    }


    /**
     * 设置 dialog 位于屏幕底部，并且设置出入动画
     */
    protected void setBottomLayout(int width, int height) {
        Window win = dialog.getWindow();
        if (win != null) {
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = width;
            lp.height = height;
            win.setAttributes(lp);
            // dialog 布局位于底部
            win.setGravity(Gravity.BOTTOM);
            // 设置进出场动画
        }
    }


    public CancelOrderDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public CancelOrderDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }


    public void show( ) {
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (KeyEvent.ACTION_UP == event.getAction()) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dimiss();
                    }
                }
                return false;
            }
        });
        dialog.show();

    }


    public void dimiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void setSubTitle(String subTitleInfo) {
        this.subTitleInfo = subTitleInfo;
        if (tv_describe != null && subTitleInfo != null) {
            tv_describe.setText(subTitleInfo);
        }
    }

    public interface CancelEntryClickListener {
        /**
         *
         * @param isDriver true 司机原因
         * @param trim
         */
        void OnCancelOrderClick(boolean isDriver, String trim);
    }


}
