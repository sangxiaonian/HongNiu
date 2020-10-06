package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/8.
 * 发布找车信息
 */
public class OrderDispathCarParams {
   private String orderId;//	true	number	订单ID
   private String realMoney;//	true	number	实际运费
   private String carId;//	true	number	车辆id
   private String driverName;//	true	string	司机姓名
   private String driverMobile;//	true	string	司机手机号

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

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }
}
