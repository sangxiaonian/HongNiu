package com.hongniu.moduleorder.entity;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class OrderCreatParamBean {

    protected String id;

    /**
     * true	string	发车编号
     */
    protected String departNum;
    /**
     * true	string	开始地点x坐标
     */
    protected String startLatitude;
    /**
     * true	string	开始地点y坐标
     */
    protected String startLongitude;
    /**
     * true	string	开始地点描述
     */
    protected String startPlaceInfo;
    /**
     * true	string	目的地x坐标
     */
    protected String destinationLatitude;
    /**
     * true	string	目的地y坐标
     */
    protected String destinationLongitude;
    /**
     * true	string	目的地描述
     */
    protected String destinationInfo;
    /**
     * true	string	发货日期（字符串，格式YYYY-MM-dd）
     */
    protected String deliveryDate;
    /**
     * true	string	货物名称
     */
    protected String goodName;
    /**
     * true	string	货物体积 (方)
     */
    protected String goodVolume;
    /**
     * true	string	货物质量(吨)
     */
    protected String goodWeight;
    /**
     * true	string	配送金额
     */
    protected String money;
    /**
     * true	string	车牌号
     */
    protected String carNum;
    /**
     * false	string	车辆类型
     */
    protected String carInfo;
    /**
     * true	string	车主姓名
     */
    protected String ownerName;
    /**
     * true	string	车主手机号
     */
    protected String ownerMobile;
    /**
     * true	string	司机名称
     */
    protected String driverName;
    /**
     * true	string	司机手机号
     */
    protected String driverMobile;
    /**
     * false	string	支付方式(0微信,1银联,2线下支付)
     */
    protected String payWay;
    private List<String> goodsImageUrls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getGoodsImages() {
        return goodsImageUrls;
    }

    public void setGoodsImages(List<String> goodsImages) {
        this.goodsImageUrls = goodsImages;
    }

    public String getDepartNum() {
        return departNum;
    }

    public void setDepartNum(String departNum) {
        this.departNum = departNum;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getStartPlaceInfo() {
        return startPlaceInfo;
    }

    public void setStartPlaceInfo(String startPlaceInfo) {
        this.startPlaceInfo = startPlaceInfo;
    }

    public String getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(String destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public String getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(String destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public String getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(String destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodVolume() {
        return goodVolume;
    }

    public void setGoodVolume(String goodVolume) {
        this.goodVolume = goodVolume;
    }

    public String getGoodWeight() {
        return goodWeight;
    }

    public void setGoodWeight(String goodWeight) {
        this.goodWeight = goodWeight;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile;
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

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
}
