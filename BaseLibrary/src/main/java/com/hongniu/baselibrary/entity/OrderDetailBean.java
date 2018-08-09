package com.hongniu.baselibrary.entity;

import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;

/**
 * 作者： ${PING} on 2018/8/7.
 * 订单详情相关数据
 */
public class OrderDetailBean {

    /**
     * 主键
     */
    private String id;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * true string 订单状态 -1退款 2待发货 3配送中 4到货 5已收货
     */
    private int status;

    /**
     * true string 出发地x坐标
     */
    private String stratPlaceX;

    /**
     * true string 出发地y坐标
     */
    private String stratPlaceY;

    /**
     * true string 出发地描述
     */
    private String stratPlaceInfo;

    /**
     * true string 目的地描述
     */
    private String destinationInfo;

    /**
     * true string 目的地x坐标
     */
    private String destinationX;
    /**
     * true string 目的地y坐标
     */
    private String destinationY;

    /**
     * true string 创建日期
     */
    private String creationDate;

    /**
     * true string 发货日期
     */
    private String deliverydate;

    /**
     * true string 完成日期
     */
    private String endTime;

    /**
     * true string 下单人id
     */
    private String userId;
    /**
     * true string 车主的姓名(非下单人)
     */
    private String userName;

    /**
     * true string 车主的电话(非下单人)
     */
    private String userPhone;

    /**
     * true string 司机的id
     */
    private String userIdSend;
    /**
     * true string 司机的姓名
     */
    private String drivername;

    /**
     * true string 司机的手机号
     */
    private String drivermobile;

    /**
     * true string 车辆id
     */
    private String carId;
    /**
     * true string 车辆类型
     */
    private String carInfo;

    /**
     * true string 车牌号
     */
    private String carnum;

    /**
     * true number 运费，单位元
     */
    private String money;

    /**
     * true string 货物名称
     */
    private String goodName;
    /**
     * true number 货物体积，单位方
     */
    private String goodvolume;

    /**
     * true number 货物质量，单位吨
     */
    private String goodweight;
    /**
     * true number 货物价值，单位元
     */
    private String goodPrice;

    /**
     * true boolean 是否购买保险，true=是
     */
    private boolean insurance;

    /**
     * true boolean 是否付运费，true=是
     */
    private boolean hasFreight;

    /**
     * true string 发车编号
     */
    private String departNum;

    /**
     * 订单状态
     */
    private OrderDetailItemControl.OrderState orderState;

    public OrderDetailItemControl.OrderState getOrderState() {
        if (orderState == null) {
            // 订单状态 -1退款 1待支付 2待发货 3配送中 4到货 5已收货
//            WAITE_PAY,//待支付
//                    WAITE_START_NO_INSURANCE,//待发车(未购买保险)
//                    WAITE_START,//待发车(已买保险)
//                    IN_TRANSIT,//运输中
//                    HAS_ARRIVED,//已到达
//                    RECEIPT,//已收货
            switch (getStatus()) {
                case  -1 :

                    break;
                case  1 :
                    orderState= OrderDetailItemControl.OrderState.WAITE_PAY;
                    break;
                case 2 :

                    orderState = OrderDetailItemControl.OrderState.WAITE_START;

                    break;
                case 3:
                    orderState =OrderDetailItemControl. OrderState.IN_TRANSIT;

                    break;
                case 4:
                    orderState = OrderDetailItemControl.OrderState.HAS_ARRIVED;

                    break;
                case 5:
                    orderState = OrderDetailItemControl.OrderState.RECEIPT;
                    break;

            }
        }
        return orderState;
    }

    public void setOrderState(OrderDetailItemControl.OrderState orderState) {

        this.orderState = orderState;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public boolean isHasFreight() {
        return hasFreight;
    }

    public void setHasFreight(boolean hasFreight) {
        this.hasFreight = hasFreight;
    }

    public String getStratPlaceX() {
        return stratPlaceX;
    }

    public void setStratPlaceX(String stratPlaceX) {
        this.stratPlaceX = stratPlaceX;
    }

    public String getStratPlaceY() {
        return stratPlaceY;
    }

    public void setStratPlaceY(String stratPlaceY) {
        this.stratPlaceY = stratPlaceY;
    }

    public String getStratPlaceInfo() {
        return stratPlaceInfo;
    }

    public void setStratPlaceInfo(String stratPlaceInfo) {
        this.stratPlaceInfo = stratPlaceInfo;
    }

    public String getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(String destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public String getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(String destinationX) {
        this.destinationX = destinationX;
    }

    public String getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(String destinationY) {
        this.destinationY = destinationY;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserIdSend() {
        return userIdSend;
    }

    public void setUserIdSend(String userIdSend) {
        this.userIdSend = userIdSend;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getDrivermobile() {
        return drivermobile;
    }

    public void setDrivermobile(String drivermobile) {
        this.drivermobile = drivermobile;
    }

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

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodvolume() {
        return goodvolume;
    }

    public void setGoodvolume(String goodvolume) {
        this.goodvolume = goodvolume;
    }

    public String getGoodweight() {
        return goodweight;
    }

    public void setGoodweight(String goodweight) {
        this.goodweight = goodweight;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }


    public String getDepartNum() {
        return departNum;
    }

    public void setDepartNum(String departNum) {
        this.departNum = departNum;
    }
}