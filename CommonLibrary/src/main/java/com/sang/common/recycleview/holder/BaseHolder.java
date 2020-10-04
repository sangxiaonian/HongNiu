package com.sang.common.recycleview.holder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者： ${PING} on 2017/8/29.
 */

public class BaseHolder<T> extends RecyclerView.ViewHolder {


    public Context mContext;
    /**
     * holder 的根View
     */
    protected View rootView;

    public BaseHolder(View rootView) {
        super(rootView);
        this.rootView = rootView;
    }

    public BaseHolder(Context context,ViewGroup parent,  int layoutID) {
        this(LayoutInflater.from(context).inflate(layoutID, parent, false));
        this.mContext = context;
    }

    public BaseHolder(Context context, int layoutID) {
        this(LayoutInflater.from(context).inflate(layoutID, null, false));
        this.mContext = context;
    }

    /**
     * 获取holer 的根View
     *
     * @return
     */
    public View getRootView() {
        return rootView;
    }

    /**
     * 初始化数据
     *
     * @param itemView 根View
     * @param position 在RecycleView中的位置
     * @param data     当前的数据
     */
    public void initView(View itemView, int position, T data) {

    }
}
