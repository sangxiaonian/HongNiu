package com.hongniu.baselibrary.event;

import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.sang.common.event.IBus;

/**
 * 作者： ${PING} on 2018/8/27.
 * 对于模块间交互的事件
 * */
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
}
