package com.sang.common.toast;

import android.content.Context;
import android.view.View;

/**
 * 作者： ${PING} on 2018/8/3.
 * Toast 的实现类，用来设置不同的实现
 */
public interface IToast {

    void setShowTime(int time);

    /**
     * 设置不同的View
     * @param view
     */
    void setView(View view);

    /**
     * 设置显示的文字
     * @param text
     */
    void setText(String text);


    void show(String msg);
    void show(int msg);
    void show();
    void show(Context context,int msg);
    void show(Context context,String msg);
}
