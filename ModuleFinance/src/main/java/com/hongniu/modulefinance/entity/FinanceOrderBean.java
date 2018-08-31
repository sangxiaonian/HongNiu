package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2018/8/30.
 *
 * 财务查询获取到的数据
 *
 */
public class FinanceOrderBean {

    /**
     * carId : 200
     * carInfo :
     * carNum : 沪A88888
     * deliveryDate : 2018-08-31 00:00:00
     * departNum : 测试
     * destinationInfo : 108区间
     * destinationLatitude : 31.27858
     * destinationLongitude : 121.459159
     * driverId : 251
     * driverMobile : 15515871516
     * driverName : 车主
     * financeType : 1
     * goodName : 111
     * goodPrice : 800.0
     * goodVolume : 1.0
     * goodWeight : 1.0
     * id : 285
     * money : -0.01
     * orderNum : HN20180831110923122736
     * ownerId : 251
     * ownerMobile : 15515871516
     * ownerName : 车主
     * payTime : 2018-08-31 11:29:26
     * roleType : 3
     * startLatitude : 31.27816
     * startLongitude : 121.45831
     * startPlaceInfo : 易局汇
     * status : 3
     */

    /**
     * 车辆ID
     */
    private String carId;
    /**
     * 车辆信息
     */
    private String carInfo;
    /**
     * 车牌号
     */
    private String carNum;
    /**
     * 发货日期
     */
    private String deliveryDate;
    /**
     * 货物编号
     */
    private String departNum;

    /**
     * 目的地描述
     */
    private String destinationInfo;
    /**
     *
     */
    private double destinationLatitude;
    private double destinationLongitude;
    private String driverId;
    /**
     * 司机手机号
     */
    private String driverMobile;

    /**
     * 司机姓名
     */
    private String driverName;
    private int financeType;
    private String goodName;
    private double goodPrice;
    private double goodVolume;
    private double goodWeight;
    private String id;
    private double money;
    private String orderNum;
    private String ownerId;
    private String ownerMobile;
    private String ownerName;
    private String payTime;
    private int roleType;
    private double startLatitude;
    private double startLongitude;
    private String startPlaceInfo;
    private int status;


    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDepartNum() {
        return departNum;
    }

    public void setDepartNum(String departNum) {
        this.departNum = departNum;
    }

    public String getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(String destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }


    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getFinanceType() {
        return financeType;
    }

    public void setFinanceType(int financeType) {
        this.financeType = financeType;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public double getGoodVolume() {
        return goodVolume;
    }

    public void setGoodVolume(double goodVolume) {
        this.goodVolume = goodVolume;
    }

    public double getGoodWeight() {
        return goodWeight;
    }

    public void setGoodWeight(double goodWeight) {
        this.goodWeight = goodWeight;
    }


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getStartPlaceInfo() {
        return startPlaceInfo;
    }

    public void setStartPlaceInfo(String startPlaceInfo) {
        this.startPlaceInfo = startPlaceInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
