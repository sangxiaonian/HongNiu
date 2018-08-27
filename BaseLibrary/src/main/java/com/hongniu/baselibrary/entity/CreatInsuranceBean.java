package com.hongniu.baselibrary.entity;

/**
 * 作者： ${桑小年} on 2018/8/18.
 * 努力，为梦长留
 * 创建保单
 */
public class CreatInsuranceBean {

    //订单编号
    public String orderNum;

    //货物价值
    public String goodsValue;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(String goodsValue) {
        this.goodsValue = goodsValue;
    }
}
