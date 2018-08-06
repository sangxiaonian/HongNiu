package com.sang.common.widget.dialog.builder;

import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.inter.DialogControl;

/**
 * 作者： ${PING} on 2018/8/6.
 */
public class CenterAlertBuilder {
    private String btLeftString = "取消";
    private int btLeftColor;
    private int btLeftBgRes;

    private String btRightString = "确认";
    private int btRightColor;
    private int btRightBgRes;

    //title
    private String title;
    private int textSize;
    private boolean titleBold = false;
    private int titleColor;


    //content
    private String content;
    private int contentTextSize;
    private int contentColor;

    private DialogControl.OnButtonLeftClickListener leftClickListener;
    private DialogControl.OnButtonRightClickListener rightClickListener;
    private boolean canceledOnTouchOutside = true;
    private boolean cancelable = true;

    private boolean hideTitle;
    private boolean hideContent;
    private boolean hideBtLeft;
    private boolean hideBtRight;

    public CenterAlertBuilder setBtLeftBgRes(int btLeftBgRes) {
        this.btLeftBgRes = btLeftBgRes;
        return this;
    }


    public CenterAlertBuilder setBtRightBgRes(int btRightBgRes) {
        this.btRightBgRes = btRightBgRes;
        return this;
    }

    public CenterAlertBuilder hideTitle() {
        hideTitle = true;
        return this;
    }

    public CenterAlertBuilder hideContent() {
        hideContent = true;
        return this;
    }

    public CenterAlertBuilder hideBtLeft() {
        hideBtLeft = true;
        return this;
    }

    public CenterAlertBuilder hideBtRight() {
        hideBtRight = true;
        return this;
    }

    public CenterAlertBuilder setLeftClickListener(DialogControl.OnButtonLeftClickListener leftClickListener) {
        this.leftClickListener = leftClickListener;
        return this;
    }


    public CenterAlertBuilder setRightClickListener(DialogControl.OnButtonRightClickListener rightClickListener) {
        this.rightClickListener = rightClickListener;
        return this;
    }

    public CenterAlertBuilder setBtLeft(String btLeftString) {
        this.btLeftString = btLeftString;
        return this;
    }


    public CenterAlertBuilder setBtLeftColor(String btLeftString) {
        this.btLeftColor = btLeftColor;
        return this;

    }


    public CenterAlertBuilder setBtRight(String btRightString) {
        this.btRightString = btRightString;
        return this;
    }

    public CenterAlertBuilder setBtRightColor(int btRightColor) {
        this.btRightColor = btRightColor;
        return this;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public CenterAlertBuilder setDialogTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置标题颜色
     *
     * @param titleColor
     */
    public CenterAlertBuilder setDialogTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    /**
     * 设置标题大小
     *
     * @param textSize 标题文字大小，单位px
     */
    public CenterAlertBuilder setDialogTitleSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    /**
     * 设置是否加粗 默认加粗
     *
     * @param bold true 加粗，false正常
     */
    public CenterAlertBuilder setDialogTitleBOLD(boolean bold) {
        this.titleBold = bold;
        return this;

    }


    /**
     * 设置内容文字大小
     *
     * @param textSize 文字大小，单位px
     * @return
     */
    public CenterAlertBuilder setDialogContentSize(int textSize) {
        this.contentTextSize = textSize;
        return this;
    }

    public CenterAlertBuilder setDialogContentColor(int contentColor) {
        this.contentColor = contentColor;
        return this;
    }


    public CenterAlertBuilder setDialogContent(String content) {
        this.content = content;
        return this;
    }

    public CenterAlertBuilder setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public CenterAlertBuilder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public DialogControl.ICenterDialog creatDialog(DialogControl.ICenterDialog dialog) {
//        CenterAlertDialog dialog = new CenterAlertDialog(context);
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
        dialog.setBtRightBgRes(btRightBgRes);
        dialog.setBtLeftBgRes(btLeftBgRes);
        return dialog;
    }
}
