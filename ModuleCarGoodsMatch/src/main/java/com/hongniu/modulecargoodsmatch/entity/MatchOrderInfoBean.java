package com.hongniu.modulecargoodsmatch.entity;

/**
 * 作者：  on 2019/10/27.
 */
public class MatchOrderInfoBean {


    private String id;//true	number	订单id
    private String orderNum;//true	string	订单号
    private String userId;//true	string	用户id
    private String userName;//true	string	用户名
    private String userMobile;//true	string	用户手机号码
    private String departureTime;//true	string	发车时间
    private String startPlaceInfo;//true	string	开始地描述
    private String destinationInfo;//true	string	目的地描述
    private String shipperName;//true	string	发货人姓名
    private String shipperMobile;//true	string	发货人手机号码
    private String receiverName;//true	string	收货人姓名
    private String receiverMobile;//true	string	收货人手机号码
    private String remark;//true	string	备注
    private String distance;//true	number	距离 单位公里
    private String cartypeId;//true	number	车辆类型id
    private String cartypeName;//true	string	车辆类型名称
    private String estimateFare;//true	number	预估运费
    private int status;//true	number	订单状态 1:待付款 2:待接单 3:已接单 4:已送达 5:已完成 6:已取消
    private String driverId;//true	number	司机id
    private String driverName;//true	string	司机名
    private String driverMobile;//true	string	司机手机号
    private String deliveryTime;//true	string	送达时间
    private String endTime;//true	string	完成日期
    private String payTime;//true	string	付款时间
    private int payWay;//true	number	支付方式(0微信,1银联,2线下支付,3支付宝支付,4余额支付,5企业钱包)
    private String deliveryMark;//true	string	送达备注
    private String isRefundRecord;//true	number	是否生成退款记录
    private String isAppraiseRecord;//true	number	是否生成点评记录
    private String createTime;//true	string	创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

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

    public String getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(String destinationInfo) {
        this.destinationInfo = destinationInfo;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCartypeId() {
        return cartypeId;
    }

    public void setCartypeId(String cartypeId) {
        this.cartypeId = cartypeId;
    }

    public String getCartypeName() {
        return cartypeName;
    }

    public void setCartypeName(String cartypeName) {
        this.cartypeName = cartypeName;
    }

    public String getEstimateFare() {
        return estimateFare;
    }

    public void setEstimateFare(String estimateFare) {
        this.estimateFare = estimateFare;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public String getDeliveryMark() {
        return deliveryMark;
    }

    public void setDeliveryMark(String deliveryMark) {
        this.deliveryMark = deliveryMark;
    }

    public String getIsRefundRecord() {
        return isRefundRecord;
    }

    public void setIsRefundRecord(String isRefundRecord) {
        this.isRefundRecord = isRefundRecord;
    }

    public String getIsAppraiseRecord() {
        return isAppraiseRecord;
    }

    public void setIsAppraiseRecord(String isAppraiseRecord) {
        this.isAppraiseRecord = isAppraiseRecord;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
