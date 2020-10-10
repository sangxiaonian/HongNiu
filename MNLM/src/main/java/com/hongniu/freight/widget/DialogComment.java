package com.hongniu.freight.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hongniu.freight.R;


/**
 * 作者： ${PING} on 2018/7/30.
 */

public class DialogComment extends Dialog implements View.OnClickListener {
    TextView btLeft;
    TextView btRight;
    TextView tvTitle;
    TextView tvContent;
    private OnButtonRightClickListener rightClickListener;
    private OnButtonLeftClickListener leftClickListener;
    private View line;

    public DialogComment(@NonNull Context context) {
        super(context);
        initView(context);
    }


    public DialogComment(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.mnlm_dialog_alert, null);
        btLeft = inflate.findViewById(R.id.btn_left);
        btRight = inflate.findViewById(R.id.btn_right);
        tvTitle = inflate.findViewById(R.id.tv_title);
        tvContent = inflate.findViewById(R.id.tv_content);
        line = inflate.findViewById(R.id.img_line);
        setContentView(inflate);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        btLeft.setOnClickListener(this);
        btRight.setOnClickListener(this);

    }


    private DialogComment setBtRight(String btRightString, int btRightColor, final OnButtonRightClickListener rightClickListener) {
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
        return this;

    }

    private DialogComment setBtLeft(String btLeftString, int btLeftColor, OnButtonLeftClickListener leftClickListener) {
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
        return this;

    }

    private DialogComment setContent(String content, int contentColor, int contentTextSize) {
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
        return this;

    }

    private DialogComment setTitle(String title, int titleColor, int textSize, boolean titleBold) {
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
        return this;

    }

    public DialogComment setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
            tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
        return this;

    }

    public DialogComment hideTitle(boolean hideTitle) {
        if (hideTitle) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
        }
        return this;

    }

    public DialogComment hideContent(boolean hideContent) {
        if (hideContent) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setVisibility(View.VISIBLE);
        }
        return this;

    }

    public DialogComment hideBtLeft(boolean hideBtLeft) {
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
        return this;

    }

    public DialogComment hideBtRight(boolean hideBtRight) {
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
        return this;

    }

    private DialogComment setContentAlign(int textAlignment) {
        if (tvContent != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                tvContent.setGravity(textAlignment);
            }
        }
        return this;

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

        } else if (i == R.id.btn_right) {
            if (rightClickListener != null) {
                rightClickListener.onRightClick(v, this);
            } else {
                dismiss();
            }

        }
    }

    public DialogComment setBtLeft(String left) {
        if (btLeft != null) {
            btLeft.setText(left == null ? "" : left);
        }
        return this;
    }

    public DialogComment setBtRight(String msgRight) {
        if (btRight != null) {
            btRight.setText(msgRight == null ? "" : msgRight);
        }
        return this;
    }

    public DialogComment setTvContent(TextView tvContent) {
        this.tvContent = tvContent;
        return this;
    }

    public DialogComment setRightClickListener(OnButtonRightClickListener rightClickListener) {
        this.rightClickListener = rightClickListener;
        return this;
    }

    public DialogComment setLeftClickListener(OnButtonLeftClickListener leftClickListener) {
        this.leftClickListener = leftClickListener;
        return this;
    }

    public interface OnButtonLeftClickListener {
        void onLeftClick(View view, Dialog dialog);
    }

    public interface OnButtonRightClickListener {
        void onRightClick(View view, Dialog dialog);
    }


    public static class Builder {

        private String btLeftString;
        private int btLeftColor;
        private String btRightString;
        private int btRightColor;

        //title
        private String title;
        private int textSize;
        private boolean titleBold = false;
        private int titleColor;


        //content
        private String content;
        private int contentTextSize;
        private int contentColor;

        private OnButtonLeftClickListener leftClickListener;
        private OnButtonRightClickListener rightClickListener;
        private boolean canceledOnTouchOutside = true;
        private boolean cancelable = true;

        private boolean hideTitle;
        private boolean hideContent = true;
        private boolean hideBtLeft;
        private boolean hideBtRight;

        public Builder() {

        }

        public Builder hideTitle() {
            hideTitle = true;
            return this;
        }

        public Builder hideContent() {
            hideContent = true;
            return this;
        }

        public Builder showContent(boolean show) {
            hideContent = !show;
            return this;
        }

        public Builder hideBtLeft() {
            hideBtLeft = true;
            return this;
        }

        public Builder hideBtRight() {
            hideBtRight = true;
            return this;
        }

        public Builder setLeftClickListener(OnButtonLeftClickListener leftClickListener) {
            this.leftClickListener = leftClickListener;
            return this;
        }


        public Builder setRightClickListener(OnButtonRightClickListener rightClickListener) {
            this.rightClickListener = rightClickListener;
            return this;
        }

        public Builder setBtLeft(String btLeftString) {
            this.btLeftString = btLeftString;
            return this;
        }


        public Builder setBtLeftColor(int btLeftColor) {
            this.btLeftColor = btLeftColor;
            return this;

        }


        public Builder setBtRight(String btRightString) {
            this.btRightString = btRightString;
            return this;
        }



        public Builder setBtRightColor(int btRightColor) {
            this.btRightColor = btRightColor;
            return this;
        }

        /**
         * 设置标题
         *
         * @param title
         */
        public Builder setDialogTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置标题颜色
         *
         * @param titleColor
         */
        public Builder setDialogTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        /**
         * 设置标题大小
         *
         * @param textSize 标题文字大小，单位px
         */
        public Builder setDialogTitleSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * 设置是否加粗 默认加粗
         *
         * @param bold true 加粗，false正常
         */
        public Builder setDialogTitleBOLD(boolean bold) {
            this.titleBold = bold;
            return this;

        }

        /**
         * 设置内容文字大小
         *
         * @param textSize 文字大小，单位px
         * @return
         */
        public Builder setDialogContentSize(int textSize) {
            this.contentTextSize = textSize;
            return this;
        }

        public Builder setDialogContentColor(int contentColor) {
            this.contentColor = contentColor;
            return this;
        }


        public Builder setDialogContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public DialogComment creatDialog(Context context) {
            DialogComment dialog = new DialogComment(context);
            dialog.setTitle(title, titleColor, textSize, titleBold);
            dialog.setContent(content, contentColor, contentTextSize);
            dialog.setBtLeft(btLeftString, btLeftColor, leftClickListener);
            dialog.setBtRight(btRightString, btRightColor, rightClickListener);
            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            dialog.hideTitle(hideTitle);
            dialog.hideContent(hideContent);
            dialog.hideBtLeft(hideBtLeft);
            dialog.hideBtRight(hideBtRight);

            return dialog;
        }

    }


}
