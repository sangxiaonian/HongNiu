package com.hongniu.baselibrary.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @data 2022/7/13$
 * @Author PING
 * @Description
 */
public class PolicyCaculParam implements Parcelable {
    private String id;//true	number	订单id
    private String goodPrice;//true	number	货物价值，单位元
    private String goodsTypes;//true	string	货物类型
    private String loadingMethods;//true	string	装载方式
    private String transportMethods;//true	string	运输方式
    private String packingMethods;//true	string	打包方式
    private String policyType;//true	string	险种类型
    private String policyPrice;//true	string	险种类型

    public PolicyCaculParam() {
    }


    protected PolicyCaculParam(Parcel in) {
        id = in.readString();
        goodPrice = in.readString();
        goodsTypes = in.readString();
        loadingMethods = in.readString();
        transportMethods = in.readString();
        packingMethods = in.readString();
        policyType = in.readString();
        policyPrice = in.readString();
    }

    public static final Creator<PolicyCaculParam> CREATOR = new Creator<PolicyCaculParam>() {
        @Override
        public PolicyCaculParam createFromParcel(Parcel in) {
            return new PolicyCaculParam(in);
        }

        @Override
        public PolicyCaculParam[] newArray(int size) {
            return new PolicyCaculParam[size];
        }
    };

    public String getPolicyPrice() {
        if (policyPrice != null && policyPrice.startsWith(".")) {
            return "0"+policyPrice;
        }
        return policyPrice;
    }

    public void setPolicyPrice(String policyPrice) {
        this.policyPrice = policyPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

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
        dest.writeString(id);
        dest.writeString(goodPrice);
        dest.writeString(goodsTypes);
        dest.writeString(loadingMethods);
        dest.writeString(transportMethods);
        dest.writeString(packingMethods);
        dest.writeString(policyType);
        dest.writeString(policyPrice);
    }
}
