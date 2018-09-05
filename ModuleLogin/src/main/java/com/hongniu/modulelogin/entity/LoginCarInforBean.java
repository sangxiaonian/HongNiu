package com.hongniu.modulelogin.entity;

/**
 * 作者： ${PING} on 2018/8/15.
 * 新增修改车辆
 */
public class LoginCarInforBean {


    /**
     * true	string	车牌号
     */
    private String carNumber;


    /**
     * true	string	车辆类型id
     */
    private String carType;

    /**
     * true	long	车主id
     */
    private String carOwnerId;

    /**
     * true	string	车辆联系人姓名
     */
    private String contactName;

    /**
     * true	string	车辆联系人手机号
     */
    private String contactMobile;

    private String id;
    private String cartypename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public String getCartypename() {
        return cartypename;
    }

    public void setCartypename(String cartypename) {
        this.cartypename = cartypename;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }



    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarOwnerId() {
        return carOwnerId;
    }

    public void setCarOwnerId(String carOwnerId) {
        this.carOwnerId = carOwnerId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }
}
