package com.hongniu.moduleorder.entity;

/**
 * 作者： ${桑小年} on 2018/9/2.
 * 努力，为梦长留
 */
public class OrderDriverPhoneBean {
    private String driverMobile;
    private String mobile;
    private String contact;


    public OrderDriverPhoneBean() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
