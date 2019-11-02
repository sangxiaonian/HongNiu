package com.hongniu.modulecargoodsmatch.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2019/11/1.
 */
public class MatchCreateOrderParams implements Parcelable {

 private String  departureTime	;//false	string	取货时间 null表示立即取货
 private String  startPlaceInfo	;//true	string	发货地描述
 private String  startPlaceLon	;//true	number	发货地经度
 private String  startPlaceLat	;//true	number	发货地纬度
 private String  destinationInfo	;//true	string	收货地描述
 private String  destinationLon	;//true	number	收货地经度
 private String  destinationLat	;//true	number	收货地纬度
 private String  cartypeId	;//true	number	车辆类型id
 private String  cartype	;//true	number	车辆类型id
 private String  shipperName	;//false	string	发货人姓名
 private String  shipperMobile	;//false	string	发货人手机号码
 private String  receiverName	;//false	string	收货人姓名
 private String  receiverMobile	;//false	string	收货人手机号码
 private String  remark	;//false	string	备注

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
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

    public String getCartypeId() {
        return cartypeId;
    }

    public void setCartypeId(String cartypeId) {
        this.cartypeId = cartypeId;
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

    public MatchCreateOrderParams() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.departureTime);
        dest.writeString(this.startPlaceInfo);
        dest.writeString(this.startPlaceLon);
        dest.writeString(this.startPlaceLat);
        dest.writeString(this.destinationInfo);
        dest.writeString(this.destinationLon);
        dest.writeString(this.destinationLat);
        dest.writeString(this.cartypeId);
        dest.writeString(this.cartype);
        dest.writeString(this.shipperName);
        dest.writeString(this.shipperMobile);
        dest.writeString(this.receiverName);
        dest.writeString(this.receiverMobile);
        dest.writeString(this.remark);
    }

    protected MatchCreateOrderParams(Parcel in) {
        this.departureTime = in.readString();
        this.startPlaceInfo = in.readString();
        this.startPlaceLon = in.readString();
        this.startPlaceLat = in.readString();
        this.destinationInfo = in.readString();
        this.destinationLon = in.readString();
        this.destinationLat = in.readString();
        this.cartypeId = in.readString();
        this.cartype = in.readString();
        this.shipperName = in.readString();
        this.shipperMobile = in.readString();
        this.receiverName = in.readString();
        this.receiverMobile = in.readString();
        this.remark = in.readString();
    }

    public static final Creator<MatchCreateOrderParams> CREATOR = new Creator<MatchCreateOrderParams>() {
        @Override
        public MatchCreateOrderParams createFromParcel(Parcel source) {
            return new MatchCreateOrderParams(source);
        }

        @Override
        public MatchCreateOrderParams[] newArray(int size) {
            return new MatchCreateOrderParams[size];
        }
    };
}
