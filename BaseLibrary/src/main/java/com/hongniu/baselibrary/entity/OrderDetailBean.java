package com.hongniu.baselibrary.entity;

import android.text.TextUtils;

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
     * true string 出发地纬度坐标
     */
    private double startLatitude;

    /**
     * true string 出发地经度坐标
     */
    private double startLongitude;

    /**
     * true string 出发地描述
     */
    private String startPlaceInfo;

    /**
     * true string 目的地描述
     */
    private String destinationInfo;

    /**
     * true string 目的地x坐标 目的地纬度坐标
     */
    private double destinationLatitude;
    /**
     * true string 目的地y坐标 经度坐标
     */
    private double destinationLongitude;

    /**
     * true string 创建日期
     */
    private String creationDate;

    /**
     * true string 发货日期
     */
    private long deliverydate;

    /**
     * true string 完成日期
     */
    private String endTime;

    /**
     * true string 下单人id
     */
    private String userId;
    /**
     * 货主电话
     */
    private String ownerPhone;
    /**
     * 货主姓名
     */
    private String ownerName;
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
     * true	string	发货日期（字符串，格式YYYY-MM-dd）
     */
    protected String deliverydateStr;
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
     * 付费时间
     */
    private long payTime;
    /**
     * 订单状态
     */
    private OrderDetailItemControl.OrderState orderState;


    /**
     * 当前订单所在位置
     */
    private double latitude;
    private double longitude;

    /**
     * false	string	支付方式(0微信,1银联,2线下支付)
     */
    protected String payWay;


    /**
     * false	string	保单号
     */
    private String policyNum;
    /**
     * false	string	保险公司简称
     */
    private String companyName;
    /**
     * false	number	保费
     */
    private String policyMoney;
    /**
     * false	string	保单信息
     */
    private String policyInfo;

    /**
     * 财务类型 1支持 2收入 0 无类型
     */
    private int financeType;

    /**
     * 角色类似 1车主 2司机 3 货主
     */
    private int roleType;

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public int getFinanceType() {
        return financeType;
    }

    public void setFinanceType(int financeType) {
        this.financeType = financeType;
    }

    public String getPolicyNum() {
        return policyNum;
    }

    public void setPolicyNum(String policyNum) {
        this.policyNum = policyNum;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPolicyMoney() {
        return policyMoney;
    }

    public void setPolicyMoney(String policyMoney) {
        this.policyMoney = policyMoney;
    }

    public String getPolicyInfo() {
        return policyInfo;
    }

    public void setPolicyInfo(String policyInfo) {
        this.policyInfo = policyInfo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDeliverydateStr() {
        return deliverydateStr;
    }

    public void setDeliverydateStr(String deliverydateStr) {
        this.deliverydateStr = deliverydateStr;
    }

    public String getPayWay() {
        return payWay;
    }

    //获取支付方式的描述
    public String getPayWayDes() {
        if (TextUtils.isEmpty(payWay)) {
            return "";
        } else if (payWay.equals("0")) {
            return "微信";
        } else if (payWay.equals("1")) {
            return "银联";
        } else if (payWay.equals("2")) {
            return "线下支付";
        } else {
            return "";
        }

    }


    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public OrderDetailItemControl.OrderState getOrderState() {
        // 订单状态 -1退款 1待支付 2待发货 3配送中 4到货 5已收货
//            WAITE_PAY,//待支付
//                    WAITE_START_NO_INSURANCE,//待发车(未购买保险)
//                    WAITE_START,//待发车(已买保险)
//                    IN_TRANSIT,//运输中
//                    HAS_ARRIVED,//已到达
//                    RECEIPT,//已收货
        switch (getStatus()) {
            case -1:
                orderState = OrderDetailItemControl.OrderState.REFUND;
                break;
            case 1:
                orderState = OrderDetailItemControl.OrderState.WAITE_PAY;
                break;
            case 2:
                if (hasFreight) {
                    orderState = OrderDetailItemControl.OrderState.WAITE_START;
                } else {
                    orderState = OrderDetailItemControl.OrderState.WAITE_PAY;
                }

                break;
            case 3:
                orderState = OrderDetailItemControl.OrderState.IN_TRANSIT;

                break;
            case 4:
                orderState = OrderDetailItemControl.OrderState.HAS_ARRIVED;

                break;
            case 5:
                orderState = OrderDetailItemControl.OrderState.RECEIPT;
                break;
            default:
                orderState = OrderDetailItemControl.OrderState.UNKNOW;
                break;

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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public long getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(long deliverydate) {
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
