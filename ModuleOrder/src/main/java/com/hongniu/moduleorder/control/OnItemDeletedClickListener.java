package com.hongniu.moduleorder.control;

/**
 * 作者： ${PING} on 2018/10/23.
 */
public interface OnItemDeletedClickListener<T> {

    /**
     * 条目被点击
     * @param position
     * @param t
     */
    void onItemDeletedClick(int position, T t);
}
