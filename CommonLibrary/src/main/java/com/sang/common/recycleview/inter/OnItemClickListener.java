package com.sang.common.recycleview.inter;

/**
 * 作者： ${PING} on 2018/10/23.
 */
public interface OnItemClickListener<T> {

    /**
     * 条目被点击
     * @param position
     * @param t
     */
    void onItemClick(int position,T t);
}
