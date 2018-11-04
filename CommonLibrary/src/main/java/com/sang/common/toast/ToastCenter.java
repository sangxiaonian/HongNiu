package com.sang.common.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * 作者： ${PING} on 2018/8/3.
 */
public class ToastCenter implements IToast {


    private Toast toast = null;
    private Context context;


    public ToastCenter(Context context) {
        this.context = context;
        toast = creatToast(context);
    }

    private Toast creatToast(Context context) {
        Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        //设置显示时间
        toast.setDuration(Toast.LENGTH_SHORT);
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
        toast.setText(text);
    }

    @Override
    public void show(String msg) {
        toast.setText(msg == null ? "" : msg);
        toast.show();
    }

    @Override
    public void show(int msg) {
        setText(msg == 0 ? "" : context.getString(msg));
        toast.show();
    }

    @Override
    public void show() {
        show("");
    }

    @Override
    public void show(Context context, int msg) {
        Toast toast = creatToast(context);
        toast.setText(msg);
        toast.show();

    }

    @Override
    public void show(Context context, String msg) {
        Toast toast = creatToast(context);
        toast.setText(msg);
        toast.show();
    }
}
