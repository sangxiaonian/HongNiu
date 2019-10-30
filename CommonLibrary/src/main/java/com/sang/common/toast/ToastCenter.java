package com.sang.common.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sang.common.R;


/**
 * 作者： ${PING} on 2018/8/3.
 */
public class ToastCenter implements IToast {


    private Toast toast = null;
    private Context context;
    private long lastTime;
    private View inflate;


    public ToastCenter(Context context) {
        this.context=context;
        toast = creatToast(context);
    }

    private Toast creatToast(Context context ) {
        Toast toast = new Toast(context);
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

    }

    @Override
    public void show(String msg) {

        long curr = System.currentTimeMillis();
        if (curr - lastTime > 2000) {
            inflate = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
            ((TextView) inflate.findViewById(R.id.tv_title)).setText(msg);
            toast.setView(inflate);
            toast.show();
            lastTime = curr;
        }else if (inflate!=null){
            ((TextView) inflate.findViewById(R.id.tv_title)).setText(msg);
        }
    }

    @Override
    public void show(int msg) {
        show(msg == 0 ? "" : context.getString(msg));
    }

    @Override
    public void show() {
        show("操作成功");
    }

    @Override
    public void show(Context context, int msg) {

        show(context,msg == 0 ? "" : context.getString(msg));
    }

    @Override
    public void show(Context context, String msg) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        ((TextView) inflate.findViewById(R.id.tv_title)).setText(msg);
        Toast toast = creatToast(context);
        toast.show();
    }
}
