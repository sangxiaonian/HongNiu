package com.fy.androidlibrary.control;

/**
 * 作者：  on 2019/11/13.
 */
public interface ParallaxListener {

    /**
     * 下拉拖动
     * @param distance 拖动的距离
     */
    void onDrag(float distance);

    /**
     *  执行回复动画
     * @param currentDistance 当前的距离
     */
    void onUpDataAnimation(float currentDistance);
}
