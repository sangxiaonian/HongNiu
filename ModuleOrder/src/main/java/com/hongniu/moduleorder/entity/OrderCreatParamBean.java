package com.hongniu.moduleorder.entity;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class OrderCreatParamBean {
    /**
     * true	string	发车编号
     */
    protected String  departNum	          ;
    /**
     * true	string	开始地点x坐标
     */
    protected double startLatitude;
    /**
     * true	string	开始地点y坐标
     */
    protected double startLongitude;
    /**
     * true	string	开始地点描述
     */
    protected String startPlaceInfo;
    /**
     * true	string	目的地x坐标
     */
    protected double destinationLatitude;
    /**
     * true	string	目的地y坐标
     */
    protected double destinationLongitude;
    /**
     * true	string	目的地描述
     */
    protected String  destinationInfo	  ;
    /**
     * true	string	发货日期（字符串，格式YYYY-MM-dd）
     */
    protected String  deliverydateStr	  ;
    /**
     * true	string	货物名称
     */
    protected String  goodName	          ;
    /**
     * true	string	货物体积 (方)
     */
    protected String  goodvolume	      ;
    /**
     * true	string	货物质量(吨)
     */
    protected String  goodweight	      ;
    /**
     * true	string	配送金额
     */
    protected String  money             ;
    /**
     * true	string	车牌号
     */
    protected String  carnum	          ;
    /**
     * false	string	车辆类型
     */
    protected String  carInfo	          ;
    /**
     * true	string	车主姓名
     */
    protected String  userName	          ;
    /**
     * true	string	车主手机号
     */
    protected String  userPhone	          ;
    /**
     * true	string	司机名称
     */
    protected String  drivername	      ;
    /**
     * true	string	司机手机号
     */
    protected String  drivermobile	      ;
    /**
     * false	string	支付方式(0微信,1银联,2线下支付)
     */
    protected String  payWay	          ;

    public String getDepartNum() {
        return departNum;
    }

    public void setDepartNum(String departNum) {
        this.departNum = departNum;
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

    public String getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(String destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public String getDeliverydateStr() {
        return deliverydateStr;
    }

    public void setDeliverydateStr(String deliverydateStr) {
        this.deliverydateStr = deliverydateStr;
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
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

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
}
