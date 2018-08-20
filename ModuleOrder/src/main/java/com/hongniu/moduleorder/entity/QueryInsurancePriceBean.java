package com.hongniu.moduleorder.entity;

/**
 * 作者： ${桑小年} on 2018/8/18.
 * 努力，为梦长留
 * 根据货物金额查询计算费
 */
public class QueryInsurancePriceBean {

   private String orderId	 ;//   true	number	订单id
   private String goodPrice	 ;// true	number	货物价值，单位元，不能超过200万

    public QueryInsurancePriceBean(String orderId, String goodPrice) {
        this.orderId = orderId;
        this.goodPrice = goodPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }
}
