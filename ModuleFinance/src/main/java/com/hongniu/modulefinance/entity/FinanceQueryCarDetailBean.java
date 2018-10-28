package com.hongniu.modulefinance.entity;

import com.hongniu.baselibrary.entity.OrderDetailBean;

/**
 * 作者： ${桑小年} on 2018/10/28.
 * 努力，为梦长留
 */
public class FinanceQueryCarDetailBean  {
    private int type;
    private String time;
    private OrderDetailBean bean;



    public FinanceQueryCarDetailBean(int type, String time, OrderDetailBean bean) {
        this.type = type;
        this.time = time;
        this.bean = bean;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public OrderDetailBean getBean() {
        return bean;
    }

    public void setBean(OrderDetailBean bean) {
        this.bean = bean;
    }
}
