package com.hongniu.baselibrary.widget.order;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hongniu.baselibrary.R;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.sang.common.widget.dialog.inter.DialogControl;

/**
 * 作者： ${PING} on 2018/8/9.
 */
public class OrderDetailDialog implements DialogControl.IBottomDialog {

    private final Dialog dialog;
    private final OrderDetailItem item;
    private OrderDetailBean ordetail;

    public OrderDetailDialog(Context context) {
        item = new OrderDetailItem(context);
//        item.setDebug();
//        item.hideButton(true);

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(item);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
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

        if (ordetail != null) {

            item.setInfor(ordetail);
        }

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
     * 设置底部控件样式
     *
     * @param btBottomString      文字
     * @param btRightColor        颜色
     * @param bottomClickListener 点击事件
     */
    @Override
    public void setBtBottom(String btBottomString, int btRightColor, DialogControl.OnButtonBottomClickListener bottomClickListener) {

    }

    /**
     * 设置控件样式
     *
     * @param btTopString      文字
     * @param btLeftColor      颜色
     * @param topClickListener 点击事件
     */
    @Override
    public void setBtTop(String btTopString, int btLeftColor, DialogControl.OnButtonTopClickListener topClickListener) {

    }

    /**
     * 设置标题
     *
     * @param title      标题
     * @param titleColor 颜色
     * @param textSize   大小
     * @param titleBold  是否加粗
     */
    @Override
    public void setTitle(String title, int titleColor, int textSize, boolean titleBold) {

    }

    /**
     * 是否隐藏标题
     *
     * @param hideTitle
     */
    @Override
    public void hideTitle(boolean hideTitle) {

    }

    /**
     * 上部控件背景资源ID
     *
     * @param btTopBgRes 背景资源ID
     */
    @Override
    public void setBtTopBgRes(int btTopBgRes) {

    }

    /**
     * 底部控件背景资源ID
     *
     * @param btBottomBgRes 背景资源ID
     */
    @Override
    public void setBtBottomBgRes(int btBottomBgRes) {

    }

    public void setOrdetail(OrderDetailBean ordetail) {
        this.ordetail = ordetail;
    }

    public void hideButton( ) {
        item.hideButton(true);
    }
    public void hideBottom( ) {
        item.hideBottom(true);

    }


}
