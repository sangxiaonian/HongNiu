package com.hongniu.modulefinance.control;

/**
 * 作者： ${PING} on 2018/9/4.
 * 当Fragment和金额发生改变时候调用
 */
public interface OnHideChangeListener {
    void onFragmentShow(boolean hide,int total,float money);

}
