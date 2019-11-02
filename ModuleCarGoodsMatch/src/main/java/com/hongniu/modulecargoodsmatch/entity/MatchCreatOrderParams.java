package com.hongniu.modulecargoodsmatch.entity;

/**
 * 作者：  on 2019/11/2.
 */
public class MatchCreatOrderParams {

    String departureTime;//false	string	取货时间 null表示立即取货
    String startPlaceInfo;//true	string	发货地描述
    String startPlaceLon;//true	number	发货地经度
    String startPlaceLat;//true	number	发货地纬度
    String destinationInfo;//true	string	收货地描述
    String destinationLon;//true	number	收货地经度
    String destinationLat;//true	number	收货地纬度
    String cartypeId;//true	number	车辆类型id
    String shipperName;//false	string	发货人姓名
    String shipperMobile;//false	string	发货人手机号码
    String receiverName;//false	string	收货人姓名
    String receiverMobile;//false	string	收货人手机号码
    String remark;//false	string	备注

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getStartPlaceInfo() {
        return startPlaceInfo;
    }

    public void setStartPlaceInfo(String startPlaceInfo) {
        this.startPlaceInfo = startPlaceInfo;
    }

    public String getStartPlaceLon() {
        return startPlaceLon;
    }

    public void setStartPlaceLon(String startPlaceLon) {
        this.startPlaceLon = startPlaceLon;
    }

    public String getStartPlaceLat() {
        return startPlaceLat;
    }

    public void setStartPlaceLat(String startPlaceLat) {
        this.startPlaceLat = startPlaceLat;
    }

    public String getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(String destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public String getDestinationLon() {
        return destinationLon;
    }

    public void setDestinationLon(String destinationLon) {
        this.destinationLon = destinationLon;
    }

    public String getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(String destinationLat) {
        this.destinationLat = destinationLat;
    }

    public String getCartypeId() {
        return cartypeId;
    }

    public void setCartypeId(String cartypeId) {
        this.cartypeId = cartypeId;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperMobile() {
        return shipperMobile;
    }

    public void setShipperMobile(String shipperMobile) {
        this.shipperMobile = shipperMobile;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
