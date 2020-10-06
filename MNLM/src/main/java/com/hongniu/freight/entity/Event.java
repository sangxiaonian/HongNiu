package com.hongniu.freight.entity;

import com.fy.androidlibrary.event.IBus;

/**
 * 作者：  on 2020/3/7.
 */
public class Event {
    /**
     *@data  2020/3/7
     *@Author PING
     *@Description
     * 更新订单列表数据
     *
     */
    public static class UpDateOrder {

    }

    /**
     * 更新聊天消息的fragment
     */
    public static class UpChactFragment  {

        public UpChactFragment( ) {
        }
    }
    /**
     * 开始或者停止用户信息上传的数据
     */
    public static class UpLoactionEvent implements IBus.IEvent {
        public String cardID;
        public String orderID;
        public double destinationLatitude;
        public double destinationLongitude;

        public boolean start;//true 开始，false 停止

    }

}
