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


    /**
     * true	string	1:微型货车; 2:轻型货车; 3:中型货车; 4:重型货车'
      */
    private String vehicleSize	;
    /**
     * true	string	货车的总重 ,单位：吨。
      */
    private String vehicleLoad	;
    /**
     * true	string	货车的载重 ,单位：吨。
      */
    private String vehicleWeight	;
    /**
     * true	string	货车的最大长度, 单位：米。
      */
    private String vehicleLength	;
    /**
     * true	string	货车的最大宽度,单位：米。
      */
    private String vehicleWidth	;
    /**
     * true	string	货车的高度，单位：米。
      */
    private String vehicleHeight	;
    /**
     * true	string	货车的轴数
      */
    private String vehicleAxis	;


    public String getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(String vehicleSize) {
        this.vehicleSize = vehicleSize;
    }

    public String getVehicleLoad() {
        return vehicleLoad;
    }

    public void setVehicleLoad(String vehicleLoad) {
        this.vehicleLoad = vehicleLoad;
    }

    public String getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(String vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    public String getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(String vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    public String getVehicleWidth() {
        return vehicleWidth;
    }

    public void setVehicleWidth(String vehicleWidth) {
        this.vehicleWidth = vehicleWidth;
    }

    public String getVehicleHeight() {
        return vehicleHeight;
    }

    public void setVehicleHeight(String vehicleHeight) {
        this.vehicleHeight = vehicleHeight;
    }

    public String getVehicleAxis() {
        return vehicleAxis;
    }

    public void setVehicleAxis(String vehicleAxis) {
        this.vehicleAxis = vehicleAxis;
    }

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
