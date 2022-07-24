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
    private String isdirect;//		添加承运人司机一起下单1表示是 0或空表示否
    private String ownerCompanyAccountId;//	承运人企业帐号id（4.15查询信息
    private String carInfo;//": null,

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
    private String departTime;//": null,
    private String arrivedTime;//": null,
    private String receiptTime;//": null,
    private String longitude;//": null,
    private String latitude;//": null,

    private String userRongId;//	false	string	托运人融云id
    private String ownerRongId;//	false	string	车主融云id
    private String driverRongId;//	false	string	司机融云id

    private String goodsTypes;    //false	string	货物类型
    private String loadingMethods; //false	string	装载方式
    private String transportMethods; //false	string	运输方式
    private String packingMethods; //false	string	打包方式
    private String policyType; //false	string	险种类型

    protected OrderInfoBean(Parcel in) {
        status = in.readInt();
        id = in.readString();
        orderNum = in.readString();
        createTime = in.readLong();
        startPlaceInfo = in.readString();
        destinationInfo = in.readString();
        shipperName = in.readString();
        shipperMobile = in.readString();
        receiverName = in.readString();
        receiverMobile = in.readString();
        departureTime = in.readString();
        goodName = in.readString();
        goodVolume = in.readString();
        goodWeight = in.readString();
        money = in.readDouble();
        insurance = in.readInt();
        payPolicyState = in.readInt();
        hasReceiptImage = in.readInt();
        policyMoney = in.readDouble();
        insureUsername = in.readString();
        insureIdnumber = in.readString();
        carNum = in.readString();
        ownerName = in.readString();
        ownerMobile = in.readString();
        balanceMoney = in.readDouble();
        policyNum = in.readString();
        companyName = in.readString();
        policyInfo = in.readString();
        cartype = in.readString();
        realMoney = in.readDouble();
        driverId = in.readString();
        driverName = in.readString();
        driverMobile = in.readString();
        userrId = in.readString();
        userName = in.readString();
        userMobile = in.readString();
        userEvaluateState = in.readInt();
        owenrEvaluateState = in.readInt();
        driverEvaluateState = in.readInt();
        endCountrySubdivisionCode = in.readString();
        startCountrySubdivisionCode = in.readString();
        cargoTypeClassificationInfo = in.readString();
        cargoTypeClassificationCode = in.readString();
        isdirect = in.readString();
        ownerCompanyAccountId = in.readString();
        carInfo = in.readString();
        userId = in.readString();
        startPlaceLon = in.readDouble();
        startPlaceLat = in.readDouble();
        destinationLon = in.readDouble();
        destinationLat = in.readDouble();
        remark = in.readString();
        totalMoney = in.readDouble();
        payTime = in.readString();
        payWay = in.readString();
        isRefundRecord = in.readString();
        hasFreight = in.readString();
        freightPayClass = in.readString();
        goodPrice = in.readString();
        insuranceUserId = in.readString();
        isDel = in.readString();
        companyAccountId = in.readString();
        ownerId = in.readString();
        verifyUserId = in.readString();
        verifyFailCause = in.readString();
        orderTakingUserId = in.readString();
        orderTakingTime = in.readString();
        carId = in.readString();
        departTime = in.readString();
        arrivedTime = in.readString();
        receiptTime = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        userRongId = in.readString();
        ownerRongId = in.readString();
        driverRongId = in.readString();
        goodsTypes = in.readString();
        loadingMethods = in.readString();
        transportMethods = in.readString();
        packingMethods = in.readString();
        policyType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(id);
        dest.writeString(orderNum);
        dest.writeLong(createTime);
        dest.writeString(startPlaceInfo);
        dest.writeString(destinationInfo);
        dest.writeString(shipperName);
        dest.writeString(shipperMobile);
        dest.writeString(receiverName);
        dest.writeString(receiverMobile);
        dest.writeString(departureTime);
        dest.writeString(goodName);
        dest.writeString(goodVolume);
        dest.writeString(goodWeight);
        dest.writeDouble(money);
        dest.writeInt(insurance);
        dest.writeInt(payPolicyState);
        dest.writeInt(hasReceiptImage);
        dest.writeDouble(policyMoney);
        dest.writeString(insureUsername);
        dest.writeString(insureIdnumber);
        dest.writeString(carNum);
        dest.writeString(ownerName);
        dest.writeString(ownerMobile);
        dest.writeDouble(balanceMoney);
        dest.writeString(policyNum);
        dest.writeString(companyName);
        dest.writeString(policyInfo);
        dest.writeString(cartype);
        dest.writeDouble(realMoney);
        dest.writeString(driverId);
        dest.writeString(driverName);
        dest.writeString(driverMobile);
        dest.writeString(userrId);
        dest.writeString(userName);
        dest.writeString(userMobile);
        dest.writeInt(userEvaluateState);
        dest.writeInt(owenrEvaluateState);
        dest.writeInt(driverEvaluateState);
        dest.writeString(endCountrySubdivisionCode);
        dest.writeString(startCountrySubdivisionCode);
        dest.writeString(cargoTypeClassificationInfo);
        dest.writeString(cargoTypeClassificationCode);
        dest.writeString(isdirect);
        dest.writeString(ownerCompanyAccountId);
        dest.writeString(carInfo);
        dest.writeString(userId);
        dest.writeDouble(startPlaceLon);
        dest.writeDouble(startPlaceLat);
        dest.writeDouble(destinationLon);
        dest.writeDouble(destinationLat);
        dest.writeString(remark);
        dest.writeDouble(totalMoney);
        dest.writeString(payTime);
        dest.writeString(payWay);
        dest.writeString(isRefundRecord);
        dest.writeString(hasFreight);
        dest.writeString(freightPayClass);
        dest.writeString(goodPrice);
        dest.writeString(insuranceUserId);
        dest.writeString(isDel);
        dest.writeString(companyAccountId);
        dest.writeString(ownerId);
        dest.writeString(verifyUserId);
        dest.writeString(verifyFailCause);
        dest.writeString(orderTakingUserId);
        dest.writeString(orderTakingTime);
        dest.writeString(carId);
        dest.writeString(departTime);
        dest.writeString(arrivedTime);
        dest.writeString(receiptTime);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(userRongId);
        dest.writeString(ownerRongId);
        dest.writeString(driverRongId);
        dest.writeString(goodsTypes);
        dest.writeString(loadingMethods);
        dest.writeString(transportMethods);
        dest.writeString(packingMethods);
        dest.writeString(policyType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderInfoBean> CREATOR = new Creator<OrderInfoBean>() {
        @Override
        public OrderInfoBean createFromParcel(Parcel in) {
            return new OrderInfoBean(in);
        }

        @Override
        public OrderInfoBean[] newArray(int size) {
            return new OrderInfoBean[size];
        }
    };

    public String getGoodsTypes() {
        return goodsTypes;
    }

    public void setGoodsTypes(String goodsTypes) {
        this.goodsTypes = goodsTypes;
    }

    public String getLoadingMethods() {
        return loadingMethods;
    }

    public void setLoadingMethods(String loadingMethods) {
        this.loadingMethods = loadingMethods;
    }

    public String getTransportMethods() {
        return transportMethods;
    }

    public void setTransportMethods(String transportMethods) {
        this.transportMethods = transportMethods;
    }

    public String getPackingMethods() {
        return packingMethods;
    }

    public void setPackingMethods(String packingMethods) {
        this.packingMethods = packingMethods;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
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

    public String getUserRongId() {
        return userRongId;
    }

    public void setUserRongId(String userRongId) {
        this.userRongId = userRongId;
    }

    public String getOwnerRongId() {
        return ownerRongId;
    }

    public void setOwnerRongId(String ownerRongId) {
        this.ownerRongId = ownerRongId;
    }

    public String getDriverRongId() {
        return driverRongId;
    }

    public void setDriverRongId(String driverRongId) {
        this.driverRongId = driverRongId;
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

}
