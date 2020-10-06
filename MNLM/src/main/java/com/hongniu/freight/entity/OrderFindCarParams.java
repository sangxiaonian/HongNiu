package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/8.
 * 发布找车信息
 */
public class OrderFindCarParams {
    private String orderId;//	true	number	订单ID
    private String realMoney;//	true	number	实际运费
    private String cartypeId;//	true	number	车辆类型id

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(String realMoney) {
        this.realMoney = realMoney;
    }

    public String getCartypeId() {
        return cartypeId;
    }

    public void setCartypeId(String cartypeId) {
        this.cartypeId = cartypeId;
    }
}
