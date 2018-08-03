package com.sang.common.toast;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sang.common.R;
import com.sang.common.utils.DeviceUtils;

/**
 * 作者： ${PING} on 2018/8/3.
 */
public class ToastSuccess implements IToast {


    private Toast toast = null;
    private Context context;
    private View inflate;


    public ToastSuccess(Context context) {
        this.context=context;
        inflate = LayoutInflater.from(context).inflate(R.layout.layout_toast_success, null);
        toast = creatToast(context, inflate);
    }

    private Toast creatToast(Context context, View inflate) {
        Toast toast = new Toast(context);
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        //设置显示时间
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(inflate);
        return toast;
    }

    @Override
    public void setShowTime(int time) {
        toast.setDuration(time);
    }

    /**
     * 设置不同的View
     *
     * @param view 一定要有ID 为 tv_title，为title
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
        ((TextView) inflate.findViewById(R.id.tv_title)).setText(text);
    }

    @Override
    public void show(String msg) {
        setText(msg == null ? "" : msg);
        toast.show();
    }

    @Override
    public void show(int msg) {
        setText(msg == 0 ? "" : context.getString(msg));
        toast.show();
    }

    @Override
    public void show() {
        toast.show();
    }

    @Override
    public void show(Context context, int msg) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_toast_success, null);
        Toast toast = creatToast(context, inflate);
        ((TextView) inflate.findViewById(R.id.tv_title)).setText(msg);
        toast.show();
    }

    @Override
    public void show(Context context, String msg) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_toast_success, null);
        Toast toast = creatToast(context, inflate);
        ((TextView) inflate.findViewById(R.id.tv_title)).setText(msg);
        toast.show();
    }
}
