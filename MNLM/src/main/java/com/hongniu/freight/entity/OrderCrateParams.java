package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/2.
 * 创建订单 我要发货
 */
public class OrderCrateParams {
    private String id;//	true	string	订单id
    private String startPlaceInfo;//	true	string	开始地描述
    private String startPlaceLon;//	true	number	开始地经度
    private String startPlaceLat;//	true	number	开始地纬度
    private String destinationInfo;//	true	string	目的地描述
    private String destinationLon;//	true	number	目的地经度
    private String destinationLat;//	true	number	目的地纬度
    private String shipperName;//	true	string	发货人姓名
    private String shipperMobile;//	true	string	发货人手机号码
    private String receiverName;//	true	string	收货人姓名
    private String receiverMobile;//	true	string	收货人手机号码
    private String departureTime;//	false	string	发货时间 null表示立即发货
    private String goodName;//	true	string	货物名称
    private String goodVolume;//	true	number	货物体积(立方)
    private String goodWeight;//	true	number	货物重量(千克)
    private String money;//	true	number	运费
    private int freightPayClass;//	true	number	运费支付类型1:在线支付 2:到付
    private int insurance;//	true	number	是否购买保险 1表示是 0表示否
    private String insuranceUserId;//	false	number	被保险人id insurance=1时必填
    private String goodPrice;//	false	number	货物价格 insurance=1时必填
    private String cargoTypeClassificationCode;//	货物分类代码
    private String isdirect;//		添加承运人司机一起下单1表示是 0或空表示否
    private String ownerCompanyAccountId;//	承运人企业帐号id（4.15查询信息
    private String ownerId;//	承运人)id（4.15查询信息
    private String ownerName;//	承运人姓名（4.15查询信息
    private String ownerMobile;//	承运人手机号（4.15查询信息
    private String driverId;//司机id（1.32查询信息
    private String driverName;//司机名（1.32查询信息
    private String driverMobile;//司机手机号（1.32查询信息
    private String carId;//车辆id
    private String carInfo;//车辆类型
    private String carNum;//车牌号

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
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

    public String getIsdirect() {
        return isdirect;
    }

    public void setIsdirect(String isdirect) {
        this.isdirect = isdirect;
    }

    public String getOwnerCompanyAccountId() {
        return ownerCompanyAccountId;
    }

    public void setOwnerCompanyAccountId(String ownerCompanyAccountId) {
        this.ownerCompanyAccountId = ownerCompanyAccountId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
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

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
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

    public int getFreightPayClass() {
        return freightPayClass;
    }

    public void setFreightPayClass(int freightPayClass) {
        this.freightPayClass = freightPayClass;
    }

    public int getInsurance() {
        return insurance;
    }

    public String getCargoTypeClassificationCode() {
        return cargoTypeClassificationCode;
    }

    public void setCargoTypeClassificationCode(String cargoTypeClassificationCode) {
        this.cargoTypeClassificationCode = cargoTypeClassificationCode;
    }

    public void setInsurance(int insurance) {
        this.insurance = insurance;
    }

    public String getInsuranceUserId() {
        return insuranceUserId;
    }

    public void setInsuranceUserId(String insuranceUserId) {
        this.insuranceUserId = insuranceUserId;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGoodPrice(String goodPrice) {

        this.goodPrice = goodPrice;
    }
}
