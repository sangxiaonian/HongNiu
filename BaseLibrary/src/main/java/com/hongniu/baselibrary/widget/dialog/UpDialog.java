package com.hongniu.baselibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hongniu.baselibrary.R;
import com.sang.common.widget.dialog.inter.DialogControl;

/**
 * 作者： ${PING} on 2018/7/30.
 */

public class UpDialog implements View.OnClickListener, DialogControl.ICenterDialog {
    TextView btRight;
    TextView tvTitle;
    private DialogControl.OnButtonRightClickListener rightClickListener;
    private DialogControl.OnButtonLeftClickListener leftClickListener;

    private Dialog dialog;
    private boolean canceledOnTouchOutside;
    private LinearLayout llClose;

    public UpDialog(@NonNull Context context) {
        initView(context, 0);
    }


    public UpDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
    }


    private void initView(Context context, int themeResId) {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
          llClose = (LinearLayout) v.findViewById(R.id.llClose);
        tvTitle = (TextView) v.findViewById(R.id.tvInfo);
        btRight = (TextView) v.findViewById(R.id.tvUpdate);


        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    public void setBtRight(String btRightString, int btRightColor, final DialogControl.OnButtonRightClickListener rightClickListener) {
        if (btRight != null) {
            if (TextUtils.isEmpty(btRightString)) {
                hideBtRight(true);
            } else {
                btRight.setVisibility(View.VISIBLE);
                if (btRightColor != 0) {
                    btRight.setTextColor(btRightColor);
                }
                btRight.setText(btRightString);
                btRight.setOnClickListener(this);
            }

        }
        this.rightClickListener = rightClickListener;

    }

    public void setBtLeft(String btLeftString, int btLeftColor, DialogControl.OnButtonLeftClickListener leftClickListener) {

        this.leftClickListener = leftClickListener;
    }

    public void setContent(String content, int contentColor, int contentTextSize) {

    }

    public void setTitle(String title, int titleColor, int textSize, boolean titleBold) {
        if (tvTitle != null) {
            if (TextUtils.isEmpty(title)) {
                hideTitle(true);
            } else {
                hideTitle(false);
                tvTitle.setText(title);
                if (textSize > 0) {
                    tvTitle.setTextSize(textSize);
                }
                if (titleColor != 0) {
                    tvTitle.setTextColor(titleColor);
                }
                if (titleBold) {
                    tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else {
                    tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            }
        }
    }

    public void hideTitle(boolean hideTitle) {
        if (hideTitle) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
        }
    }

    public void hideContent(boolean hideContent) {

    }

    public void hideBtLeft(boolean hideBtLeft) {

    }

    public void hideBtRight(boolean hideBtRight) {
        if (hideBtRight) {
            btRight.setVisibility(View.GONE);
        } else {
            btRight.setVisibility(View.VISIBLE);

        }
    }

    public void setBtLeftBgRes(int btLeftBgRes) {

    }

    public void setBtRightBgRes(int btRightBgRes) {
        if (btRightBgRes != 0)
            btRight.setBackgroundResource(btRightBgRes);
    }

    @Override
    public void setbtSize(int btSize) {
        if (btSize > 0) {
            btRight.setTextSize(btSize);
        }
    }

    @Override
    public void setDialogSize(int width, int height) {
        if (width != 0 && height != 0) {

            dialog.getWindow().setLayout(width, height);

        }
    }

    private boolean cancelable;

    @Override
    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        this.cancelable=cancelable;

    }

    @Override
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        this.canceledOnTouchOutside=canceledOnTouchOutside;
    }

    @Override
    public void show() {
        dialog.show();
        if (!cancelable&&!canceledOnTouchOutside){
            llClose.setVisibility(View.GONE);
        }

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
        int i = v.getId();
        if (i == R.id.btn_left) {
            if (leftClickListener != null) {
                leftClickListener.onLeftClick(v, this);
            } else {
                dismiss();
            }

        } else if (i == R.id.tvUpdate) {
            if (rightClickListener != null) {
                rightClickListener.onRightClick(v, this);
            } else {
                dismiss();
            }

        }
    }


}
