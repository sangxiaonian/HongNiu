package com.sang.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.sang.common.R;
import com.sang.common.widget.dialog.inter.DialogControl;

/**
 * 作者： ${PING} on 2018/7/30.
 */

public class BottomAlertDialog
        implements View.OnClickListener, DialogControl.IBottomDialog {
    TextView btTop;
    TextView btBottom;
    TextView tvTitle;
    private DialogControl.OnButtonTopClickListener topClickListener;
    private DialogControl.OnButtonBottomClickListener bottomClickListener;
    private Dialog dialog;

    public BottomAlertDialog(@NonNull Context context) {
        this(context, 0);
    }


    public BottomAlertDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
    }

    private void initView(Context context, int themeResId) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_bottom, null);
        btTop = inflate.findViewById(R.id.bt_top);
        btBottom = inflate.findViewById(R.id.bt_bottom);
        tvTitle = inflate.findViewById(R.id.tv_title);
        dialog = new Dialog(context, themeResId);
        dialog.setContentView(inflate);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));

    }


    public void setBtBottom(String btBottomString, int btRightColor, final DialogControl.OnButtonBottomClickListener bottomClickListener) {
        if (btBottom != null) {
            if (!TextUtils.isEmpty(btBottomString)) {
                btTop.setVisibility(View.VISIBLE);
                if (btRightColor != 0) {
                    btBottom.setTextColor(btRightColor);
                }
                btBottom.setText(btBottomString);
                btBottom.setOnClickListener(this);
            }

        }
        this.bottomClickListener = bottomClickListener;

    }

    public void setBtTop(String btTopString, int btLeftColor, DialogControl.OnButtonTopClickListener topClickListener) {
        if (btTop != null) {
            if (!TextUtils.isEmpty(btTopString)) {

                btTop.setVisibility(View.VISIBLE);
                if (btLeftColor != 0) {
                    btTop.setTextColor(btLeftColor);
                }
                btTop.setOnClickListener(this);
                btTop.setText(btTopString);
            }
        }
        this.topClickListener = topClickListener;
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


    public void setBtTopBgRes(int btTopBgRes) {
        if (btTopBgRes != 0)
            btTop.setBackgroundResource(btTopBgRes);
    }

    public void setBtBottomBgRes(int btBottomBgRes) {
        if (btBottomBgRes != 0)
            btBottom.setBackgroundResource(btBottomBgRes);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_bottom) {
            if (bottomClickListener != null) {
                bottomClickListener.onBottomClick(v, this);
            } else {
                dismiss();
            }

        } else if (i == R.id.bt_top) {
            if (topClickListener != null) {
                topClickListener.onTopClick(v, this);
            } else {
                dismiss();
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

}
