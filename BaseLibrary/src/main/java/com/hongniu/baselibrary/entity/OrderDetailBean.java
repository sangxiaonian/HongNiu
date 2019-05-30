package com.hongniu.baselibrary.entity;

import com.google.gson.annotations.SerializedName;
import com.hongniu.baselibrary.widget.order.CommonOrderUtils;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;

import java.security.PublicKey;
import java.util.List;

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
     * <p>
     * 最新订单状态：-1 已退款 0 待支付 1待发车 2待发货 3配送中 4到货 5已收货 20 已取消
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
    private String deliveryDate;

    /**
     * true string 完成日期
     */
    private String endTime;


    /**
     * 货主电话
     */
    private String userMobile;
    /**
     * 货主姓名
     */
    private String userName;

    /**
     * 货主聊天ID
     */
    private String userId;


    /**
     * true string 车主的姓名(非下单人)
     */
    private String ownerName;

    /**
     * true string 车主的电话(非下单人)
     */
    private String ownerMobile;


    /**
     * 车主聊天ID
     */
    private String ownerId;


    /**
     * true string 司机的聊天id
     */
    private String driverId;
    /**
     * true string 司机的姓名
     */
    private String driverName;

    /**
     * true string 司机的手机号
     */
    private String driverMobile;


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
    private String carNum;

    /**
     * true number 运费，单位元
     */
    private float money;

    /**
     * true string 货物名称
     */
    private String goodName;
    /**
     * true number 货物体积，单位方
     */
    private String goodVolume;

    /**
     * true number 货物质量，单位吨
     */
    private String goodWeight;
    /**
     * true number 货物价值，单位元
     */
    private String goodPrice;

    /**
     * true boolean 是否购买保险，true=是
     */
    private boolean insurance;

    /**
     * 保费的支付方式
     */
    private String policyPayWay;

    /**
     * 是否有货单
     */
    private boolean hasGoodsImage;
    /**
     * 是否有回单
     */
    private boolean hasReceiptImage;


    /**
     * true string 发车编号
     */
    private String departNum;
    /**
     * 付费时间
     */
    private String payTime;


    /**
     * 当前订单所在位置
     */
    private double latitude;
    private double longitude;

    /**
     * false	string	支付方式(0微信,1银联,2线下支付 3 支付宝 4余额支付，5企业余额
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
    private float policyMoney;
    /**
     * false	string	保单信息
     */
    private String policyInfo;

    /**
     * 财务类型 1支持 2收入 0 无类型
     */
    private int financeType;

    /**
     * 角色类似 1车主 2司机 3 货主,4 收货人
     */
    @SerializedName(value = "roleType", alternate = {"role"})
    private int roleType;
    /**
     * 角色类似 1车主 2司机 3 货主,4 收货人
     */
    private int userType;

    /**
     * 货单信息
     */
    private List<UpImgData> goodsImages;
    /**
     * 订单被拒原因
     */
    private String verifyFailCause;

    /**
     * true	number	代收货款:0:否 1:是
     */
    private int replaceState;    //
    /**
     * false	number	货款金额 replaceState=1 时必填
     */
    private float paymentAmount;//
    /**
     * false	string	收货人姓名 replaceState=1 时必填
     */
    private String receiptName;  //
    /**
     * false	string	收货人手机 replaceState=1 时必填
     */
    private String receiptMobile;//
    /**
     * false	string	货源单号(车货匹配货主下单时使用)
     */
    private String gsNum;        //
   public int freightPayClass;	//true	number	运费支付类型1:现付 2:到付 3:回付
   public String  freightPayWayStr;//	true	string	运费支付方式描述

    /**
     * 运费支付状态: 0待支付 1支付成功 2支付失败
     */
    public int freightStatus;
    /**
     * false	number	货款状态: 0待支付 1支付成功 2支付失败
     */
    public int  paymentStatus	;

    public String getVerifyFailCause() {
        return verifyFailCause;
    }

    public void setVerifyFailCause(String verifyFailCause) {
        this.verifyFailCause = verifyFailCause;
    }

    public int getStatus() {
        return status;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<UpImgData> getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(List<UpImgData> goodsImages) {
        this.goodsImages = goodsImages;
    }

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

    public float getPolicyMoney() {
        return policyMoney;
    }

    public void setPolicyMoney(float policyMoney) {
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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }


    public String getPayWay() {
        return payWay;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isHasGoodsImage() {
        return hasGoodsImage;
    }

    public void setHasGoodsImage(boolean hasGoodsImage) {
        this.hasGoodsImage = hasGoodsImage;
    }

    public boolean isHasReceiptImage() {
        return hasReceiptImage;
    }

    public void setHasReceiptImage(boolean hasReceiptImage) {
        this.hasReceiptImage = hasReceiptImage;
    }

    //获取支付方式的描述
    public String getPayWayDes() {

        return CommonOrderUtils.getPayWay(payWay);


    }

    public String getPolicyPayWay() {
        return policyPayWay;
    }

    public void setPolicyPayWay(String policyPayWay) {
        this.policyPayWay = policyPayWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public OrderDetailItemControl.OrderState getOrderState() {

        return CommonOrderUtils.getStatus(status);

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


    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
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

    public int getReplaceState() {
        return replaceState;
    }

    public void setReplaceState(int replaceState) {
        this.replaceState = replaceState;
    }

    public float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public String getReceiptMobile() {
        return receiptMobile;
    }

    public void setReceiptMobile(String receiptMobile) {
        this.receiptMobile = receiptMobile;
    }

    public String getGsNum() {
        return gsNum;
    }

    public void setGsNum(String gsNum) {
        this.gsNum = gsNum;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
