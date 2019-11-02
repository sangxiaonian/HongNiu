package com.hongniu.modulecargoodsmatch.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.services.core.PoiItem;

/**
 * 作者：  on 2019/11/2.
 */
public class TranMapBean implements Parcelable {

    PoiItem poiItem;
    String name;
    String phone;
    String address;


    public PoiItem getPoiItem() {
        return poiItem;
    }

    public void setPoiItem(PoiItem poiItem) {
        this.poiItem = poiItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.poiItem, flags);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.address);
    }

    public TranMapBean() {
    }

    protected TranMapBean(Parcel in) {
        this.poiItem = in.readParcelable(PoiItem.class.getClassLoader());
        this.name = in.readString();
        this.phone = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<TranMapBean> CREATOR = new Parcelable.Creator<TranMapBean>() {
        @Override
        public TranMapBean createFromParcel(Parcel source) {
            return new TranMapBean(source);
        }

        @Override
        public TranMapBean[] newArray(int size) {
            return new TranMapBean[size];
        }
    };
}
