package com.hongniu.freight.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2020/2/5.
 * 订单详情/列表数据
 */
public class OrderInfoBean implements Parcelable {

    private int status;
    private String id;//	true	number	订单id
    private String orderNum;//	true	number	订单号
    private long createTime;//	true	string	下单时间
    private String startPlaceInfo;//	true	string	发货地
    private String destinationInfo;//	true	string	收货地

    private String shipperName;//	true	string	发货人姓名
    private String shipperMobile;//	true	string	发货人手机号码
    private String receiverName;//	true	string	收货人姓名
    private String receiverMobile;//	true	string	收货人手机号码
    private String departureTime;//	false	string	发货时间 null表示立即发货
    private String goodName;//	true	string	货物名称
    private String goodVolume;//	true	number	货物体积(立方)
    private String goodWeight;//	true	number	货物重量(千克)
    private double money;//	true	number	运费
    private int insurance;//	true	number	是否购买保险 1表示是 0表示否
    private int payPolicyState;//	 	string	保费 0未支付1支付成功
    private int hasReceiptImage;//	 	string	是否有回单 0无 1有
    private double policyMoney;//	false	number	保险支付金额
    private String insureUsername;//	false	string	被保险人
    private String insureIdnumber;//	false	string	被保险人证件号码
    private String carNum;//	false	string	车牌号码
    private String ownerName;//	false	stringstring	承运人姓名
    private String ownerMobile;//	false		承运人手机
    private double balanceMoney;//	false		运费差额
    private String policyNum;//	false	string	保单号
    private String companyName;//	false	string	保险公司简称
    private String policyInfo;//	false	string	保单信息
    private String cartype;//	false	string	车辆类型
    private double realMoney;//": 实际运费
    private String driverId;//": null,司机id
    private String driverName;//": null,司机名
    private String driverMobile;//": null,司机手机号

   private String userrId;//	true	number	托运人id
   private String userName;//	true	number	托运人姓名
   private String userMobile;//	true	number	托运人手机号
    private int userEvaluateState;//": 托运人评价状态 1:已评价 0:未评价
    private int owenrEvaluateState;//": 承运人评价状态 1:已评价 0:未评价
    private int driverEvaluateState;//":司机评价状态 1:已评价 0:未评价
    private String  endCountrySubdivisionCode;//"重点点行政区划代码
    private String   startCountrySubdivisionCode;//":起点行政区划代码
    private String   cargoTypeClassificationInfo;//":起点行政区划代码
    private String   cargoTypeClassificationCode;//":起点行政区划代码


    private String userId;//": 268,
    private double startPlaceLon;//": 121.40225,
    private double startPlaceLat;//": 31.311806,
    private double destinationLon;//": 121.401128,
    private double destinationLat;//": 31.310851,
    private String remark;//": null,
    private double totalMoney;//": 1,
    private String payTime;//": null,
    private String payWay;//": null,
    private String isRefundRecord;//": null,
    private String hasFreight;//": 1,
    private String freightPayClass;//": 1,
    private String goodPrice;//": 0,
    private String insuranceUserId;//": //被保险人id,
    private String isDel;//": 0,
    private String companyAccountId;//": null,
    private String ownerId;//": null,

    private String verifyUserId;//": null,
    private String verifyFailCause;//": null,
    private String orderTakingUserId;//": null,
    private String orderTakingTime;//": null,
    private String carId;//": null,
    private String carInfo;//": null,
    private String departTime;//": null,
    private String arrivedTime;//": null,
    private String receiptTime;//": null,
    private String longitude;//": null,
    private String latitude;//": null,

    public String getCargoTypeClassificationInfo() {
        return cargoTypeClassificationInfo;
    }

    public void setCargoTypeClassificationInfo(String cargoTypeClassificationInfo) {
        this.cargoTypeClassificationInfo = cargoTypeClassificationInfo;
    }

    public String getCargoTypeClassificationCode() {
        return cargoTypeClassificationCode;
    }

    public void setCargoTypeClassificationCode(String cargoTypeClassificationCode) {
        this.cargoTypeClassificationCode = cargoTypeClassificationCode;
    }

    public int getHasReceiptImage() {
        return hasReceiptImage;
    }

    public void setHasReceiptImage(int hasReceiptImage) {
        this.hasReceiptImage = hasReceiptImage;
    }

    public String getUserrId() {
        return userrId;
    }

    public void setUserrId(String userrId) {
        this.userrId = userrId;
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


    public String getEndCountrySubdivisionCode() {
        return endCountrySubdivisionCode;
    }

    public void setEndCountrySubdivisionCode(String endCountrySubdivisionCode) {
        this.endCountrySubdivisionCode = endCountrySubdivisionCode;
    }

    public String getStartCountrySubdivisionCode() {
        return startCountrySubdivisionCode;
    }

    public void setStartCountrySubdivisionCode(String startCountrySubdivisionCode) {
        this.startCountrySubdivisionCode = startCountrySubdivisionCode;
    }

    public void setRealMoney(double realMoney) {
        this.realMoney = realMoney;
    }

    public int getPayPolicyState() {
        return payPolicyState;
    }

    public void setPayPolicyState(int payPolicyState) {
        this.payPolicyState = payPolicyState;
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

    public String getPolicyInfo() {
        return policyInfo;
    }

    public void setPolicyInfo(String policyInfo) {
        this.policyInfo = policyInfo;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(double balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
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

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getIsRefundRecord() {
        return isRefundRecord;
    }

    public void setIsRefundRecord(String isRefundRecord) {
        this.isRefundRecord = isRefundRecord;
    }

    public String getHasFreight() {
        return hasFreight;
    }

    public void setHasFreight(String hasFreight) {
        this.hasFreight = hasFreight;
    }

    public String getFreightPayClass() {
        return freightPayClass;
    }

    public void setFreightPayClass(String freightPayClass) {
        this.freightPayClass = freightPayClass;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getInsuranceUserId() {
        return insuranceUserId;
    }

    public void setInsuranceUserId(String insuranceUserId) {
        this.insuranceUserId = insuranceUserId;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getCompanyAccountId() {
        return companyAccountId;
    }

    public void setCompanyAccountId(String companyAccountId) {
        this.companyAccountId = companyAccountId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getVerifyUserId() {
        return verifyUserId;
    }

    public void setVerifyUserId(String verifyUserId) {
        this.verifyUserId = verifyUserId;
    }

    public String getVerifyFailCause() {
        return verifyFailCause;
    }

    public void setVerifyFailCause(String verifyFailCause) {
        this.verifyFailCause = verifyFailCause;
    }

    public String getOrderTakingUserId() {
        return orderTakingUserId;
    }

    public void setOrderTakingUserId(String orderTakingUserId) {
        this.orderTakingUserId = orderTakingUserId;
    }

    public String getOrderTakingTime() {
        return orderTakingTime;
    }

    public void setOrderTakingTime(String orderTakingTime) {
        this.orderTakingTime = orderTakingTime;
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

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public double getRealMoney() {
        return realMoney;
    }



    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getUserEvaluateState() {
        return userEvaluateState;
    }

    public void setUserEvaluateState(int userEvaluateState) {
        this.userEvaluateState = userEvaluateState;
    }

    public int getOwenrEvaluateState() {
        return owenrEvaluateState;
    }

    public void setOwenrEvaluateState(int owenrEvaluateState) {
        this.owenrEvaluateState = owenrEvaluateState;
    }

    public int getDriverEvaluateState() {
        return driverEvaluateState;
    }

    public void setDriverEvaluateState(int driverEvaluateState) {
        this.driverEvaluateState = driverEvaluateState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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


    public int getInsurance() {
        return insurance;
    }

    public void setInsurance(int insurance) {
        this.insurance = insurance;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getPolicyMoney() {
        return policyMoney;
    }

    public void setPolicyMoney(double policyMoney) {
        this.policyMoney = policyMoney;
    }

    public String getInsureUsername() {
        return insureUsername;
    }

    public void setInsureUsername(String insureUsername) {
        this.insureUsername = insureUsername;
    }

    public String getInsureIdnumber() {
        return insureIdnumber;
    }

    public void setInsureIdnumber(String insureIdnumber) {
        this.insureIdnumber = insureIdnumber;
    }

    public String getCarNum() {
        return carNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OrderInfoBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.id);
        dest.writeString(this.orderNum);
        dest.writeLong(this.createTime);
        dest.writeString(this.startPlaceInfo);
        dest.writeString(this.destinationInfo);
        dest.writeString(this.shipperName);
        dest.writeString(this.shipperMobile);
        dest.writeString(this.receiverName);
        dest.writeString(this.receiverMobile);
        dest.writeString(this.departureTime);
        dest.writeString(this.goodName);
        dest.writeString(this.goodVolume);
        dest.writeString(this.goodWeight);
        dest.writeDouble(this.money);
        dest.writeInt(this.insurance);
        dest.writeInt(this.payPolicyState);
        dest.writeInt(this.hasReceiptImage);
        dest.writeDouble(this.policyMoney);
        dest.writeString(this.insureUsername);
        dest.writeString(this.insureIdnumber);
        dest.writeString(this.carNum);
        dest.writeString(this.ownerName);
        dest.writeString(this.ownerMobile);
        dest.writeDouble(this.balanceMoney);
        dest.writeString(this.policyNum);
        dest.writeString(this.companyName);
        dest.writeString(this.policyInfo);
        dest.writeString(this.cartype);
        dest.writeDouble(this.realMoney);
        dest.writeString(this.driverId);
        dest.writeString(this.driverName);
        dest.writeString(this.driverMobile);
        dest.writeString(this.userrId);
        dest.writeString(this.userName);
        dest.writeString(this.userMobile);
        dest.writeInt(this.userEvaluateState);
        dest.writeInt(this.owenrEvaluateState);
        dest.writeInt(this.driverEvaluateState);
        dest.writeString(this.endCountrySubdivisionCode);
        dest.writeString(this.startCountrySubdivisionCode);
        dest.writeString(this.cargoTypeClassificationInfo);
        dest.writeString(this.cargoTypeClassificationCode);
        dest.writeString(this.userId);
        dest.writeDouble(this.startPlaceLon);
        dest.writeDouble(this.startPlaceLat);
        dest.writeDouble(this.destinationLon);
        dest.writeDouble(this.destinationLat);
        dest.writeString(this.remark);
        dest.writeDouble(this.totalMoney);
        dest.writeString(this.payTime);
        dest.writeString(this.payWay);
        dest.writeString(this.isRefundRecord);
        dest.writeString(this.hasFreight);
        dest.writeString(this.freightPayClass);
        dest.writeString(this.goodPrice);
        dest.writeString(this.insuranceUserId);
        dest.writeString(this.isDel);
        dest.writeString(this.companyAccountId);
        dest.writeString(this.ownerId);
        dest.writeString(this.verifyUserId);
        dest.writeString(this.verifyFailCause);
        dest.writeString(this.orderTakingUserId);
        dest.writeString(this.orderTakingTime);
        dest.writeString(this.carId);
        dest.writeString(this.carInfo);
        dest.writeString(this.departTime);
        dest.writeString(this.arrivedTime);
        dest.writeString(this.receiptTime);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
    }

    protected OrderInfoBean(Parcel in) {
        this.status = in.readInt();
        this.id = in.readString();
        this.orderNum = in.readString();
        this.createTime = in.readLong();
        this.startPlaceInfo = in.readString();
        this.destinationInfo = in.readString();
        this.shipperName = in.readString();
        this.shipperMobile = in.readString();
        this.receiverName = in.readString();
        this.receiverMobile = in.readString();
        this.departureTime = in.readString();
        this.goodName = in.readString();
        this.goodVolume = in.readString();
        this.goodWeight = in.readString();
        this.money = in.readDouble();
        this.insurance = in.readInt();
        this.payPolicyState = in.readInt();
        this.hasReceiptImage = in.readInt();
        this.policyMoney = in.readDouble();
        this.insureUsername = in.readString();
        this.insureIdnumber = in.readString();
        this.carNum = in.readString();
        this.ownerName = in.readString();
        this.ownerMobile = in.readString();
        this.balanceMoney = in.readDouble();
        this.policyNum = in.readString();
        this.companyName = in.readString();
        this.policyInfo = in.readString();
        this.cartype = in.readString();
        this.realMoney = in.readDouble();
        this.driverId = in.readString();
        this.driverName = in.readString();
        this.driverMobile = in.readString();
        this.userrId = in.readString();
        this.userName = in.readString();
        this.userMobile = in.readString();
        this.userEvaluateState = in.readInt();
        this.owenrEvaluateState = in.readInt();
        this.driverEvaluateState = in.readInt();
        this.endCountrySubdivisionCode = in.readString();
        this.startCountrySubdivisionCode = in.readString();
        this.cargoTypeClassificationInfo = in.readString();
        this.cargoTypeClassificationCode = in.readString();
        this.userId = in.readString();
        this.startPlaceLon = in.readDouble();
        this.startPlaceLat = in.readDouble();
        this.destinationLon = in.readDouble();
        this.destinationLat = in.readDouble();
        this.remark = in.readString();
        this.totalMoney = in.readDouble();
        this.payTime = in.readString();
        this.payWay = in.readString();
        this.isRefundRecord = in.readString();
        this.hasFreight = in.readString();
        this.freightPayClass = in.readString();
        this.goodPrice = in.readString();
        this.insuranceUserId = in.readString();
        this.isDel = in.readString();
        this.companyAccountId = in.readString();
        this.ownerId = in.readString();
        this.verifyUserId = in.readString();
        this.verifyFailCause = in.readString();
        this.orderTakingUserId = in.readString();
        this.orderTakingTime = in.readString();
        this.carId = in.readString();
        this.carInfo = in.readString();
        this.departTime = in.readString();
        this.arrivedTime = in.readString();
        this.receiptTime = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
    }

    public static final Creator<OrderInfoBean> CREATOR = new Creator<OrderInfoBean>() {
        @Override
        public OrderInfoBean createFromParcel(Parcel source) {
            return new OrderInfoBean(source);
        }

        @Override
        public OrderInfoBean[] newArray(int size) {
            return new OrderInfoBean[size];
        }
    };
}
