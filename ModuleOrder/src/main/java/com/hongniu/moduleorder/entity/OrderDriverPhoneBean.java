package com.hongniu.moduleorder.entity;

/**
 * 作者： ${桑小年} on 2018/9/2.
 * 努力，为梦长留
 */
public class OrderDriverPhoneBean {
    private String driverMobile;
    private String mobile;
    private String userName;

    public OrderDriverPhoneBean(String mobile, String userName) {
        this.mobile = mobile;
        this.userName = userName;
    }

    public OrderDriverPhoneBean() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    @Override
    public String toString() {
        return   mobile==null?"":mobile;

    }
}
