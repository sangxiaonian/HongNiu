package com.sang.common.utils;

import android.content.Context;
import android.view.View;

import com.sang.common.toast.IToast;
import com.sang.common.toast.ToastCenter;
import com.sang.common.toast.ToastNormal;
import com.sang.common.toast.ToastSuccess;


public class ToastUtils implements IToast {

    private static Context mContext;

    IToast toast;
    IToast toastSuccess;
    IToast toastCenter;
    IToast toastNormal;

    public static class InnerToast {
        public static ToastUtils toast = new ToastUtils();
    }

    public enum ToastType {
        SUCCESS, CENTER, NORMAL
    }


    public void init(Context context) {
        mContext = context;
        toastSuccess = new ToastSuccess(context);
        toastCenter = new ToastCenter(context);
        toastNormal = new ToastNormal(context);
    }


    public static ToastUtils getInstance() {
        return InnerToast.toast;
    }


    public ToastUtils makeToast(ToastType type) {
        switch (type) {
            case SUCCESS:
                toast = toastSuccess;
                break;
            case CENTER:
                toast = toastCenter;
                break;
            case NORMAL:
                toast = toastNormal;
                break;
            default:
                toast = toastNormal;
                break;
        }
        return this;
    }

    private ToastUtils() {

    }


    @Override
    public void setShowTime(int time) {
        toast.setShowTime(time);
    }

    /**
     * 设置不同的View
     *
     * @param view
     */
    @Override
    public void setView(View view) {
        toast.setView(view);
    }

    /**
     * 设置显示的文字
     *
     * @param text
     */
    @Override
    public void setText(String text) {
        toast.setText(text);
    }

    @Override
    public void show(String msg) {
        toast.show(msg);
    }

    @Override
    public void show(int msg) {
        toast.show(msg);
    }

    @Override
    public void show(Context context, int msg) {
        toast.show(context, msg);
    }

    @Override
    public void show(Context context, String msg) {
        toast.show(context, msg);
    }


}