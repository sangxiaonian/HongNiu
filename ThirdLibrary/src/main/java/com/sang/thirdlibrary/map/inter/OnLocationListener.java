package com.sang.thirdlibrary.map.inter;

/**
 * 作者：  on 2020/3/8.
 */
public interface OnLocationListener {
    /**
     * 开始定位
     */
    void onStartLocation();

    /**
     * 定位成功
     * @param longitude
     * @param latitude
     */
    void onSuccessLocation(double longitude, double latitude);

    /**
     * 定位失败
     * @param errorCode
     * @param errorInfo
     */
    void onFailLocation(int errorCode, String errorInfo);

}
