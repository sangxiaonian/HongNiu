package com.hongniu.baselibrary.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者： ${桑小年} on 2018/8/18.
 * 努力，为梦长留
 * 创建保单
 */
public class CreatInsuranceBean implements Parcelable {

    //订单编号
    public String orderNum;

    //货物价值
    public String goodsValue;
    public String orderID;



    public CreatInsuranceBean() {
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(String goodsValue) {
        this.goodsValue = goodsValue;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    protected CreatInsuranceBean(Parcel in) {
        orderNum = in.readString();
        goodsValue = in.readString();
        orderID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderNum);
        dest.writeString(goodsValue);
        dest.writeString(orderID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreatInsuranceBean> CREATOR = new Creator<CreatInsuranceBean>() {
        @Override
        public CreatInsuranceBean createFromParcel(Parcel in) {
            return new CreatInsuranceBean(in);
        }

        @Override
        public CreatInsuranceBean[] newArray(int size) {
            return new CreatInsuranceBean[size];
        }
    };
}
