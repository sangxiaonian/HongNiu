package com.hongniu.baselibrary.entity;

import com.fy.androidlibrary.event.IBus;

/**
 * 开始或者停止用户信息上传的数据
 */
public class UpLoactionEvent implements IBus.IEvent {
    public String cardID;
    public String orderID;
    public double destinationLatitude;
    public double destinationLongitude;

    public boolean start;//true 开始，false 停止

}
