package com.hongniu.baselibrary.event;

import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.sang.common.event.IBus;

/**
 * 作者： ${PING} on 2018/8/27.
 * 对于模块间交互的事件
 */
public class Event {
    /**
     * 创建保单
     */
    public static class CraetInsurance implements IBus.IEvent {
        CreatInsuranceBean bean;
        /**
         * 是否需要创建保单
         */
        public boolean isCreatInsurance;

        public CraetInsurance(CreatInsuranceBean bean) {
            this.bean = bean;
        }

        public CreatInsuranceBean getBean() {
            return bean;
        }
    }

    /**
     * 更新个人信息
     */
    public static class UpPerson implements IBus.IEvent {
    }

    /**
     * APP进入后台
     */
    public static class OnBackground implements IBus.IEvent {
        //App是否在前台
        public boolean onFront;

        public OnBackground(boolean onFront) {
            this.onFront = onFront;
        }

        public OnBackground() {
        }
    }

    /**
     * 更新位置信息
     */
    public static class UpLoaction implements IBus.IEvent {
        public double latitude;
        public double longitude;
        public long movingTime;
        public float speed;
        public float bearing;

        public UpLoaction(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    /**
     * 更新个人信息
     */
    public static class UpRoale implements IBus.IEvent {
        public OrderDetailItemControl.RoleState roleState;

        public UpRoale(OrderDetailItemControl.RoleState roleState) {
            this.roleState = roleState;
        }
    }
}
