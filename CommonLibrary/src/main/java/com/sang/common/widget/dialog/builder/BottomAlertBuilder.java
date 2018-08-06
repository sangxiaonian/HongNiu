package com.sang.common.widget.dialog.builder;

import com.sang.common.widget.dialog.inter.DialogControl;

public   class BottomAlertBuilder {

        private String btTopString="确认";
        private int btTopColor;

        private String btBottomString="取消";
        private int btBottomColor;

        //title
        private String title;
        private int textSize;
        private boolean titleBold = false;
        private int titleColor;



        private DialogControl.OnButtonBottomClickListener bottomClickListener;
        private DialogControl.OnButtonTopClickListener topClickListener;
        private boolean canceledOnTouchOutside = true;
        private boolean cancelable = true;
        private int btBottomBgRes;
        private int btTopBgRes;


        public BottomAlertBuilder setBtTopBgRes(int btLeftBgRes) {
            this.btTopBgRes = btLeftBgRes;
            return this;
        }


        public BottomAlertBuilder setBtBottomBgRes(int btRightBgRes) {
            this.btBottomBgRes = btRightBgRes;
            return this;
        }



        public BottomAlertBuilder setBottomClickListener(DialogControl.OnButtonBottomClickListener bottomClickListener) {
            this.bottomClickListener = bottomClickListener;
            return this;
        }


        public BottomAlertBuilder setTopClickListener(DialogControl.OnButtonTopClickListener topClickListener) {
            this.topClickListener = topClickListener;
            return this;
        }

        public BottomAlertBuilder setbtTopString(String btTopString) {
            this.btTopString = btTopString;
            return this;
        }


        public BottomAlertBuilder setBtTopColor(String btLeftString) {
            this.btTopColor = btTopColor;
            return this;

        }


        public BottomAlertBuilder setbtBottomString(String btBottomString) {
            this.btBottomString = btBottomString;
            return this;
        }

        public BottomAlertBuilder setBtBottomColor(int btBottomColor) {
            this.btBottomColor = btBottomColor;
            return this;
        }

        /**
         * 设置标题
         *
         * @param title
         */
        public BottomAlertBuilder setDialogTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置标题颜色
         *
         * @param titleColor
         */
        public BottomAlertBuilder setDialogTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        /**
         * 设置标题大小
         *
         * @param textSize 标题文字大小，单位px
         */
        public BottomAlertBuilder setDialogTitleSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * 设置是否加粗 默认加粗
         *
         * @param bold true 加粗，false正常
         */
        public BottomAlertBuilder setDialogTitleBOLD(boolean bold) {
            this.titleBold = bold;
            return this;

        }



        public BottomAlertBuilder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public BottomAlertBuilder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public DialogControl.IBottomDialog creatDialog(DialogControl.IBottomDialog dialog) {
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