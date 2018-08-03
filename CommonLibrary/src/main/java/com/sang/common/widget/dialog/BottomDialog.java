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
import android.widget.TextView;

import com.sang.common.R;

/**
 * 作者： ${PING} on 2018/7/30.
 */

public class BottomDialog extends Dialog implements View.OnClickListener {
    TextView btTop;
    TextView btBottom;
    TextView tvTitle;
    private OnButtonTopClickListener topClickListener;
    private OnButtonBottomClickListener bottomClickListener;
    public BottomDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }


    public BottomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_bottom, null);
        btTop = inflate.findViewById(R.id.bt_top);
        btBottom = inflate.findViewById(R.id.bt_bottom);
        tvTitle = inflate.findViewById(R.id.tv_title);
        setContentView(inflate);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setWindowAnimations(R.style.dialog_ani);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }


    private void setBtBottom(String btBottomString, int btRightColor, final OnButtonBottomClickListener bottomClickListener) {
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

    private void setBtTop(String btTopString, int btLeftColor, OnButtonTopClickListener topClickListener) {
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


    private void setTitle(String title, int titleColor, int textSize, boolean titleBold) {
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
        if (btTopBgRes!=0)
        btTop.setBackgroundResource(btTopBgRes);
    }
    public void setBtBottomBgRes(int btBottomBgRes) {
        if (btBottomBgRes!=0)
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
            }else {
                dismiss();
            }

        } else if (i == R.id.bt_top) {
            if (topClickListener != null) {
                topClickListener.onTopClick(v, this);
            }else {
                dismiss();
            }

        }
    }



    public interface OnButtonBottomClickListener {
        void onBottomClick(View view, Dialog dialog);
    }

    public interface OnButtonTopClickListener {
        void onTopClick(View view, Dialog dialog);
    }


    public static class Builder {

        private String btTopString="确认";
        private int btTopColor;

        private String btBottomString="取消";
        private int btBottomColor;

        //title
        private String title;
        private int textSize;
        private boolean titleBold = false;
        private int titleColor;



        private OnButtonBottomClickListener bottomClickListener;
        private OnButtonTopClickListener topClickListener;
        private boolean canceledOnTouchOutside = true;
        private boolean cancelable = true;
        private int btBottomBgRes;
        private int btTopBgRes;


        public Builder setBtTopBgRes(int btLeftBgRes) {
            this.btTopBgRes = btLeftBgRes;
            return this;
        }


        public Builder setBtBottomBgRes(int btRightBgRes) {
            this.btBottomBgRes = btRightBgRes;
            return this;
        }



        public Builder setBottomClickListener(OnButtonBottomClickListener bottomClickListener) {
            this.bottomClickListener = bottomClickListener;
            return this;
        }


        public Builder setTopClickListener(OnButtonTopClickListener topClickListener) {
            this.topClickListener = topClickListener;
            return this;
        }

        public Builder setbtTopString(String btTopString) {
            this.btTopString = btTopString;
            return this;
        }


        public Builder setBtTopColor(String btLeftString) {
            this.btTopColor = btTopColor;
            return this;

        }


        public Builder setbtBottomString(String btBottomString) {
            this.btBottomString = btBottomString;
            return this;
        }

        public Builder setBtBottomColor(int btBottomColor) {
            this.btBottomColor = btBottomColor;
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



        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public BottomDialog creatDialog(Context context) {
            BottomDialog dialog = new BottomDialog(context);
            dialog.setTitle(title, titleColor, textSize, titleBold);
            dialog.setBtTop(btTopString, btTopColor, topClickListener);
            dialog.setBtBottom(btBottomString, btBottomColor, bottomClickListener);
            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            dialog.setBtBottomBgRes(btBottomBgRes);
            dialog.setBtTopBgRes(btTopBgRes);
            return dialog;
        }

    }


}
