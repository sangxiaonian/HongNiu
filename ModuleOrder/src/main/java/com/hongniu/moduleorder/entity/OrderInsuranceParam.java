package com.hongniu.moduleorder.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2019/9/23.
 * 保费计算数据
 */
public class OrderInsuranceParam implements Parcelable {

    private String cargoName;
    private String price;
    private String insurancePrice;

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(String insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cargoName);
        dest.writeString(this.price);
        dest.writeString(this.insurancePrice);
    }

    public OrderInsuranceParam() {
    }

    protected OrderInsuranceParam(Parcel in) {
        this.cargoName = in.readString();
        this.price = in.readString();
        this.insurancePrice = in.readString();
    }

    public static final Creator<OrderInsuranceParam> CREATOR = new Creator<OrderInsuranceParam>() {
        @Override
        public OrderInsuranceParam createFromParcel(Parcel source) {
            return new OrderInsuranceParam(source);
        }

        @Override
        public OrderInsuranceParam[] newArray(int size) {
            return new OrderInsuranceParam[size];
        }
    };
}
