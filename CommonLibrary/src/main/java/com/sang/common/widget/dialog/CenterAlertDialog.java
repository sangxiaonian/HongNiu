package com.sang.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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

public class CenterAlertDialog implements View.OnClickListener, DialogControl.ICenterDialog {
    TextView btLeft;
    TextView btRight;
    TextView tvTitle;
    TextView tvContent;
    private DialogControl.OnButtonRightClickListener rightClickListener;
    private DialogControl.OnButtonLeftClickListener leftClickListener;
    private View line;
    private View inflate;
    private Dialog dialog;

    public CenterAlertDialog(@NonNull Context context) {
        initView(context, 0);
    }


    public CenterAlertDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
    }


    private void initView(Context context, int themeResId) {
        inflate = LayoutInflater.from(context).inflate(R.layout.dialog_center, null);
        btLeft = inflate.findViewById(R.id.btn_left);
        btRight = inflate.findViewById(R.id.btn_right);
        tvTitle = inflate.findViewById(R.id.tv_title);
        tvContent = inflate.findViewById(R.id.tv_content);
        line = inflate.findViewById(R.id.img_line);
        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    public void setBtRight(String btRightString, int btRightColor, final DialogControl.OnButtonRightClickListener rightClickListener) {
        if (btRight != null) {
            if (TextUtils.isEmpty(btRightString)) {
                hideBtRight(true);
            } else {
                btLeft.setVisibility(View.VISIBLE);
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
        if (btLeft != null) {
            if (TextUtils.isEmpty(btLeftString)) {
                hideBtLeft(true);
            } else {
                btLeft.setVisibility(View.VISIBLE);
                if (btLeftColor != 0) {
                    btLeft.setTextColor(btLeftColor);
                }
                btLeft.setOnClickListener(this);
                btLeft.setText(btLeftString);
            }
        }
        this.leftClickListener = leftClickListener;
    }

    public void setContent(String content, int contentColor, int contentTextSize) {
        if (tvContent != null) {
            if (TextUtils.isEmpty(content)) {
                tvContent.setVisibility(View.GONE);
            } else {
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText(content);
                if (contentTextSize > 0) {
                    tvContent.setTextSize(contentTextSize);
                }

                if (contentColor != 0) {
                    tvContent.setTextColor(contentTextSize);
                }
            }
        }
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
        if (hideContent) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setVisibility(View.VISIBLE);
        }
    }

    public void hideBtLeft(boolean hideBtLeft) {
        if (hideBtLeft) {
            btLeft.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            btLeft.setVisibility(View.VISIBLE);
            if (btRight.getVisibility() == View.VISIBLE) {
                line.setVisibility(View.VISIBLE);
            } else {
                line.setVisibility(View.GONE);
            }
        }
    }

    public void hideBtRight(boolean hideBtRight) {
        if (hideBtRight) {
            btRight.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            btRight.setVisibility(View.VISIBLE);
            if (btLeft.getVisibility() == View.VISIBLE) {
                line.setVisibility(View.VISIBLE);
            } else {
                line.setVisibility(View.GONE);
            }
        }
    }

    public void setBtLeftBgRes(int btLeftBgRes) {
        if (btLeftBgRes != 0)
            btLeft.setBackgroundResource(btLeftBgRes);
    }

    public void setBtRightBgRes(int btRightBgRes) {
        if (btRightBgRes != 0)
            btRight.setBackgroundResource(btRightBgRes);
    }

    @Override
    public void setbtSize(int btSize) {
        if (btSize>0){
            btLeft.setTextSize(btSize);
            btRight.setTextSize(btSize);
        }
    }

    @Override
    public void setDialogSize(int width, int height) {
        if (width!=0&&height!=0) {
//            ViewGroup.LayoutParams params = inflate.getLayoutParams();
//            if (params != null) {
//                params.width = width;
//                params.height = height;
//            } else {
//                params = new ViewGroup.LayoutParams(width, height);
//            }
//            inflate.setLayoutParams(params);
            dialog.getWindow().setLayout(width, height);

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
            }else {
                dismiss();
            }

        } else if (i == R.id.btn_right) {
            if (rightClickListener != null) {
                rightClickListener.onRightClick(v, this);
            }else {
                dismiss();
            }

        }
    }


}
