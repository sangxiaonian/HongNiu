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

    public CreatInsuranceBean() {
    }

    public CreatInsuranceBean(Parcel in) {
        orderNum = in.readString();
        goodsValue = in.readString();
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

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderNum);
        dest.writeString(goodsValue);
    }
}
