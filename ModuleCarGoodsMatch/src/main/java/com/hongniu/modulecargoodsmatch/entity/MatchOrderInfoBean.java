package com.hongniu.modulecargoodsmatch.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者：  on 2019/10/27.
 */
public class MatchOrderInfoBean implements Parcelable {

    private String id;//true	number	订单id
    private String startPrice;//true	number	起步价
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
    private int status;//true	number	订单状态 1:待付款 2:待接单 3:已接单 4:已送达 5:已完成 6:已取消 7已确认收货
    private String driverId;//true	number	司机id
    private String driverName;//true	string	司机名
    private String driverMobile;//true	string	司机手机号
    private String deliveryTime;//true	string	送达时间
    private String endTime;//true	string	完成日期
    private String payTime;//true	string	付款时间
    private int payWay;//true	number	支付方式(0微信,1银联,2线下支付,3支付宝支付,4余额支付,5企业钱包)
    private String deliveryMark;//true	string	送达备注
    private String isRefundRecord;//true	number	是否生成退款记录
    private int isAppraiseRecord;//true	number	是否生成点评记录
    private int isAppraiseDriver;//true	number	是否生成点评记录
    private String createTime;//true	string	创建时间
    private List<String> imageUrls;//true	string	图片url集合
    private int serviceScore;//true	string	服务评分
    private String content;//true	string	点评内容
    private String mileageFareSumInfo;//true	string	点评内容
    private int isAppraise;//true	string	1可以评价 0不可以
    private String driverContent;//true	string	司机对发货人的评价
    private int driverServiceScore;//true	string	司机对发货人的评价

   private double startPlaceLon;//	true	string	开始地经度
   private double startPlaceLat;//	true	string	开始地纬度
   private double destinationLon;//	true	string	目的地经度
   private double destinationLat;//	true	string	目的地纬度
   private String approvedLoad;//	true	string	核定载质量
   private String plateNum;//	true	string	行驶证车牌
   private String grossMass;//	true	string	总质量
  private String vehicleType	;//true	string	行驶证车辆类型
  private String overallDimension	;//true	string	行驶证外廓尺寸
  private String carLength	;//true	string	车长(单位米)
  private String carWidth	;//true	string	车宽
  private String carHeight	;//true	string	车高
  private String vehicleAxleNumber	;//true	string	carInfo

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getOverallDimension() {
        return overallDimension;
    }

    public void setOverallDimension(String overallDimension) {
        this.overallDimension = overallDimension;
    }

    public String getCarLength() {
        return carLength;
    }

    public void setCarLength(String carLength) {
        this.carLength = carLength;
    }

    public String getCarWidth() {
        return carWidth;
    }

    public void setCarWidth(String carWidth) {
        this.carWidth = carWidth;
    }

    public String getCarHeight() {
        return carHeight;
    }

    public void setCarHeight(String carHeight) {
        this.carHeight = carHeight;
    }

    public String getVehicleAxleNumber() {
        return vehicleAxleNumber;
    }

    public void setVehicleAxleNumber(String vehicleAxleNumber) {
        this.vehicleAxleNumber = vehicleAxleNumber;
    }

    public int getIsAppraise() {
        return isAppraise;
    }

    public void setIsAppraise(int isAppraise) {
        this.isAppraise = isAppraise;
    }

    public double getStartPlaceLon() {
        return startPlaceLon;
    }

    public void setStartPlaceLon(double startPlaceLon) {
        this.startPlaceLon = startPlaceLon;
    }

    public double getStartPlaceLat() {
        return startPlaceLat;
    }

    public void setStartPlaceLat(double startPlaceLat) {
        this.startPlaceLat = startPlaceLat;
    }

    public double getDestinationLon() {
        return destinationLon;
    }

    public void setDestinationLon(double destinationLon) {
        this.destinationLon = destinationLon;
    }

    public double getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public String getApprovedLoad() {
        return approvedLoad;
    }

    public void setApprovedLoad(String approvedLoad) {
        this.approvedLoad = approvedLoad;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getGrossMass() {
        return grossMass;
    }

    public void setGrossMass(String grossMass) {
        this.grossMass = grossMass;
    }

    public String getDriverContent() {
        return driverContent;
    }

    public void setDriverContent(String driverContent) {
        this.driverContent = driverContent;
    }

    public int getDriverServiceScore() {
        return driverServiceScore;
    }

    public void setDriverServiceScore(int driverServiceScore) {
        this.driverServiceScore = driverServiceScore;
    }

    public int getIsAppraiseDriver() {
        return isAppraiseDriver;
    }

    public void setIsAppraiseDriver(int isAppraiseDriver) {
        this.isAppraiseDriver = isAppraiseDriver;
    }



    public int isAppraise() {
        return isAppraise;
    }

    public void setAppraise(int appraise) {
        isAppraise = appraise;
    }

    public String getMileageFareSumInfo() {
        return mileageFareSumInfo;
    }

    public void setMileageFareSumInfo(String mileageFareSumInfo) {
        this.mileageFareSumInfo = mileageFareSumInfo;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }



    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(int serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getIsAppraiseRecord() {
        return isAppraiseRecord;
    }

    public void setIsAppraiseRecord(int isAppraiseRecord) {
        this.isAppraiseRecord = isAppraiseRecord;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public MatchOrderInfoBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.startPrice);
        dest.writeString(this.orderNum);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.userMobile);
        dest.writeString(this.departureTime);
        dest.writeString(this.startPlaceInfo);
        dest.writeString(this.destinationInfo);
        dest.writeString(this.shipperName);
        dest.writeString(this.shipperMobile);
        dest.writeString(this.receiverName);
        dest.writeString(this.receiverMobile);
        dest.writeString(this.remark);
        dest.writeString(this.distance);
        dest.writeString(this.cartypeId);
        dest.writeString(this.cartypeName);
        dest.writeString(this.estimateFare);
        dest.writeInt(this.status);
        dest.writeString(this.driverId);
        dest.writeString(this.driverName);
        dest.writeString(this.driverMobile);
        dest.writeString(this.deliveryTime);
        dest.writeString(this.endTime);
        dest.writeString(this.payTime);
        dest.writeInt(this.payWay);
        dest.writeString(this.deliveryMark);
        dest.writeString(this.isRefundRecord);
        dest.writeInt(this.isAppraiseRecord);
        dest.writeString(this.createTime);
        dest.writeStringList(this.imageUrls);
        dest.writeInt(this.serviceScore);
        dest.writeString(this.content);
        dest.writeString(this.mileageFareSumInfo);
        dest.writeInt(this.isAppraise);
    }

    protected MatchOrderInfoBean(Parcel in) {
        this.id = in.readString();
        this.startPrice = in.readString();
        this.orderNum = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.userMobile = in.readString();
        this.departureTime = in.readString();
        this.startPlaceInfo = in.readString();
        this.destinationInfo = in.readString();
        this.shipperName = in.readString();
        this.shipperMobile = in.readString();
        this.receiverName = in.readString();
        this.receiverMobile = in.readString();
        this.remark = in.readString();
        this.distance = in.readString();
        this.cartypeId = in.readString();
        this.cartypeName = in.readString();
        this.estimateFare = in.readString();
        this.status = in.readInt();
        this.driverId = in.readString();
        this.driverName = in.readString();
        this.driverMobile = in.readString();
        this.deliveryTime = in.readString();
        this.endTime = in.readString();
        this.payTime = in.readString();
        this.payWay = in.readInt();
        this.deliveryMark = in.readString();
        this.isRefundRecord = in.readString();
        this.isAppraiseRecord = in.readInt();
        this.createTime = in.readString();
        this.imageUrls = in.createStringArrayList();
        this.serviceScore = in.readInt();
        this.content = in.readString();
        this.mileageFareSumInfo = in.readString();
        this.isAppraise = in.readInt();
    }

    public static final Creator<MatchOrderInfoBean> CREATOR = new Creator<MatchOrderInfoBean>() {
        @Override
        public MatchOrderInfoBean createFromParcel(Parcel source) {
            return new MatchOrderInfoBean(source);
        }

        @Override
        public MatchOrderInfoBean[] newArray(int size) {
            return new MatchOrderInfoBean[size];
        }
    };
}
