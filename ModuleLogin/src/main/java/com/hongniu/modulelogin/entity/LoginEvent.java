package com.hongniu.modulelogin.entity;

import com.hongniu.baselibrary.entity.CarInforBean;
import com.sang.common.event.IBus;

/**
 * 作者： ${PING} on 2018/8/3.
 */
public class LoginEvent {


    /**
     * 新增、修改车辆信息
     */
    public static class  CarEvent implements IBus.IEvent{
        //消息类型 0 新增车辆 1 修改车辆
        public int type;
        public CarInforBean bean;

        public CarEvent(int type) {
            this.type = type;
        }
    }


    /**
     * 更新信息
     */
    public static class UpdateEvent implements IBus.IEvent{

    }

}
