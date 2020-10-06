package com.hongniu.freight.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2020/2/10.
 * 车辆信息
 */
public class CarInfoBean implements Parcelable {

   private String  id;//	true	string	主键
   private String  carNumber;//	true	string	车牌号
   private String  carcode;//	true	string	车架号
   private String  carTypeId;//	true	string	车辆类型id
   private String  carType;//	true	string	类型名称
   private String  carColorId;//	true	string	车辆牙博士id
   private String  carColor;//	true	string	车辆颜色
   private String  vehicleModel;//	false	string	品牌车型
   private String  name;//	true	string	承运人姓名
   private String  mobile;//	true	string	承运人手机号
   private String  faceVImageUrl;//	true	string	正面行驶证照片url
   private String  fullFaceVImageUrl;//	true	string	正面行驶证照片url
   private String  backVImageUrl;//	true	string	背面行驶证照片url
   private String  fullBackVImageUrl;//	true	string	背面行驶证照片url
   private int  isIdentity;//	false	string	车辆是否认证 0否 1是
   private int  identityStatus;//	false	string	车辆当前认证状态  0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
   private int  transportStatus;//	0 正常 1运输中

    public int getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(int transportStatus) {
        this.transportStatus = transportStatus;
    }

    public String getFullFaceVImageUrl() {
        return fullFaceVImageUrl;
    }

    public void setFullFaceVImageUrl(String fullFaceVImageUrl) {
        this.fullFaceVImageUrl = fullFaceVImageUrl;
    }

    public String getFullBackVImageUrl() {
        return fullBackVImageUrl;
    }

    public void setFullBackVImageUrl(String fullBackVImageUrl) {
        this.fullBackVImageUrl = fullBackVImageUrl;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarColorId() {
        return carColorId;
    }

    public void setCarColorId(String carColorId) {
        this.carColorId = carColorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarcode() {
        return carcode;
    }

    public void setCarcode(String carcode) {
        this.carcode = carcode;
    }

    public String getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(String carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFaceVImageUrl() {
        return faceVImageUrl;
    }

    public void setFaceVImageUrl(String faceVImageUrl) {
        this.faceVImageUrl = faceVImageUrl;
    }

    public String getBackVImageUrl() {
        return backVImageUrl;
    }

    public void setBackVImageUrl(String backVImageUrl) {
        this.backVImageUrl = backVImageUrl;
    }

    public int getIsIdentity() {
        return isIdentity;
    }

    public void setIsIdentity(int isIdentity) {
        this.isIdentity = isIdentity;
    }

    public int getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(int identityStatus) {
        this.identityStatus = identityStatus;
    }

    public static Creator<CarInfoBean> getCREATOR() {
        return CREATOR;
    }

    public CarInfoBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.carNumber);
        dest.writeString(this.carcode);
        dest.writeString(this.carTypeId);
        dest.writeString(this.carType);
        dest.writeString(this.carColorId);
        dest.writeString(this.carColor);
        dest.writeString(this.vehicleModel);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.faceVImageUrl);
        dest.writeString(this.fullFaceVImageUrl);
        dest.writeString(this.backVImageUrl);
        dest.writeString(this.fullBackVImageUrl);
        dest.writeInt(this.isIdentity);
        dest.writeInt(this.identityStatus);
        dest.writeInt(this.transportStatus);
    }

    protected CarInfoBean(Parcel in) {
        this.id = in.readString();
        this.carNumber = in.readString();
        this.carcode = in.readString();
        this.carTypeId = in.readString();
        this.carType = in.readString();
        this.carColorId = in.readString();
        this.carColor = in.readString();
        this.vehicleModel = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.faceVImageUrl = in.readString();
        this.fullFaceVImageUrl = in.readString();
        this.backVImageUrl = in.readString();
        this.fullBackVImageUrl = in.readString();
        this.isIdentity = in.readInt();
        this.identityStatus = in.readInt();
        this.transportStatus = in.readInt();
    }

    public static final Creator<CarInfoBean> CREATOR = new Creator<CarInfoBean>() {
        @Override
        public CarInfoBean createFromParcel(Parcel source) {
            return new CarInfoBean(source);
        }

        @Override
        public CarInfoBean[] newArray(int size) {
            return new CarInfoBean[size];
        }
    };
}
