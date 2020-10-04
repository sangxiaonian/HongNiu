package com.fy.androidlibrary.widget.recycle.control;

/**
 * 作者： ${PING} on 2017/10/17.
 */

public class RecycleControl {

    public interface OnItemClickListener<T>{
        void onItemClick(int position, T date);
    }

}
