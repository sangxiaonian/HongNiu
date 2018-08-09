package com.hongniu.moduleorder.control;

/**
 * 作者： ${PING} on 2018/8/9.
 */
public interface OrderMapListener {
    void setStartMarker(double latitude, double longitude, String title);

    void setEndtMarker(double latitude, double longitude, String title);

    void calculate(String carNum);
}
