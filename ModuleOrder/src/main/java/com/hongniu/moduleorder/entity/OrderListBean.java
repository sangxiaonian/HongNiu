package com.hongniu.moduleorder.entity;

/**
 * 作者： ${PING} on 2018/8/17.
 */
public class OrderListBean extends OrderCreatParamBean {


    /**
     * true	string	订单号
     */
    private String orderNum;
    /**
     * true	string	订单状态 - 2待发货 3配送中 4到货 5已收货
     */
    private String status;

    /**
     * true	string	创建日期
     */
    private String creationDate;
    /**
     * true	string	发货日期
     */
    private String deliverydate;
    /**
     * true	string	完成日期
     */
    private String endTime;
    /**
     * true	string	下单人id
     */
    private String userId;

    /**
     * true	string	司机的id
     */
    private String userIdSend;
    /**
     * true	string	司机的姓名
     */

    /**
     * true	string	车辆id
     */
    private String carId;

    /**
     * true	number	货物价值，单位元
     */
    private float goodPrice;
    /**
     * true	boolean	是否购买保险，true=是
     */
    private String insurance;
    /**
     * true	boolean	是否付运费，true=是
     */
    private String hasFreight;









    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIdSend() {
        return userIdSend;
    }

    public void setUserIdSend(String userIdSend) {
        this.userIdSend = userIdSend;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public float getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(float goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getHasFreight() {
        return hasFreight;
    }

    public void setHasFreight(String hasFreight) {
        this.hasFreight = hasFreight;
    }
}
