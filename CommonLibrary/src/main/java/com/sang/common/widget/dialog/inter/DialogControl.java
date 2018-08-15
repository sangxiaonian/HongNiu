package com.sang.common.widget.dialog.inter;

import android.app.Dialog;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;

import com.sang.common.widget.dialog.CenterAlertDialog;

/**
 * 作者： ${PING} on 2018/8/6.
 */
public class DialogControl {

    /**
     * Bottom dialog 点击底部按钮
     */
    public interface OnButtonBottomClickListener {
        void onBottomClick(View view, IBottomDialog dialog);
    }

    /**
     * Bottom dialog 点击上部按钮
     */
    public interface OnButtonTopClickListener {
        void onTopClick(View view, IBottomDialog dialog);
    }


    public interface OnButtonLeftClickListener {
        void onLeftClick(View view, ICenterDialog dialog);
    }

    public interface OnButtonRightClickListener {
        void onRightClick(View view, ICenterDialog dialog);
    }


    public interface IDialog{
        void setCancelable(boolean cancelable);

        void setCanceledOnTouchOutside(boolean canceledOnTouchOutside);

        void show();

        boolean isShowing();

        void dismiss();
    }


    /**
     * 中间显示弹出
     */
    public interface ICenterDialog extends IDialog{

        /**
         * 设置右侧按钮
         *
         * @param btRightString      左侧文字
         * @param btRightColor       文字颜色
         * @param rightClickListener 点击监听
         */
        void setBtRight(String btRightString, int btRightColor, final OnButtonRightClickListener rightClickListener);

        /**
         * 设置做侧按钮
         *
         * @param btLeftString      左侧文字
         * @param btLeftColor       文字颜色
         * @param leftClickListener 点击监听
         */
        void setBtLeft(String btLeftString, int btLeftColor, OnButtonLeftClickListener leftClickListener);

        /**
         * 设置内容
         *
         * @param content         文字
         * @param contentColor    颜色
         * @param contentTextSize 字体大小
         */
        void setContent(String content, int contentColor, int contentTextSize);

        /**
         * 设置标题
         *
         * @param title      标题
         * @param titleColor 标题颜色
         * @param textSize   标题大小
         * @param titleBold  是否加粗
         */
        void setTitle(String title, int titleColor, int textSize, boolean titleBold);

        /**
         * 隐藏标题
         *
         * @param hideTitle true 隐藏
         */
        void hideTitle(boolean hideTitle);

        /**
         * 隐藏内容
         *
         * @param hideContent true 隐藏
         */
        void hideContent(boolean hideContent);

        /**
         * 隐藏左侧按钮
         *
         * @param hideBtLeft true 隐藏
         */
        void hideBtLeft(boolean hideBtLeft);

        /**
         * 隐藏右侧按钮
         *
         * @param hideBtRight true 隐藏
         */
        void hideBtRight(boolean hideBtRight);

        /**
         * 设置左侧按钮背景
         *
         * @param btLeftBgRes 背景资源ID
         */
        void setBtLeftBgRes(int btLeftBgRes);

        /**
         * 设置右侧按钮背景
         *
         * @param btRightBgRes 背景资源ID
         */
        void setBtRightBgRes(int btRightBgRes);

        void setbtSize(int btSize);

        void setDialogSize(int width, int height);
    }

    public interface IBottomDialog extends IDialog{
        /**
         * 设置底部控件样式
         * @param btBottomString    文字
         * @param btRightColor      颜色
         * @param bottomClickListener 点击事件
         */
         void setBtBottom(String btBottomString, int btRightColor, final DialogControl.OnButtonBottomClickListener bottomClickListener);

        /**
         * 设置控件样式
         * @param btTopString 文字
         * @param btLeftColor 颜色
         * @param topClickListener 点击事件
         */
         void setBtTop(String btTopString, int btLeftColor, DialogControl.OnButtonTopClickListener topClickListener);


        /**
         * 设置标题
         * @param title      标题
         * @param titleColor 颜色
         * @param textSize   大小
         * @param titleBold  是否加粗
         */
         void setTitle(String title, int titleColor, int textSize, boolean titleBold);

        /**
         * 是否隐藏标题
         * @param hideTitle
         */
         void hideTitle(boolean hideTitle);

        /**
         * 上部控件背景资源ID
         * @param btTopBgRes 背景资源ID
         */
         void setBtTopBgRes(int btTopBgRes);

        /**
         * 底部控件背景资源ID
         * @param btBottomBgRes 背景资源ID
         */
         void setBtBottomBgRes(int btBottomBgRes);
    }
}
