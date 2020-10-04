package com.fy.androidlibrary.control;

/**
 * 作者：  on 2019/11/7.
 */
public interface ScrollPercentChangeListener {

    /**
     * 滑动监听
     * @param scrollX
     * @param scrollY
     * @param countX   设置X的总的滑动距离
     * @param countY   设置Y的总的滑动距离
     * @param perX      百分比
     * @param perY
     */
    void onScrollPercentChange(float scrollX, float scrollY, float countX, float countY, float perX, float perY);

}
