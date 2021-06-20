package com.hongniu.moduleorder.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.services.core.PoiItem;

/**
 * 作者：  on 2019/11/2.
 */
public class OrderTranMapBean implements Parcelable {

    PoiItem poiItem;
    String name;
    String phone;
    String address;
    String addressDetail;

    protected OrderTranMapBean(Parcel in) {
        poiItem = in.readParcelable(PoiItem.class.getClassLoader());
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        addressDetail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(poiItem, flags);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(addressDetail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderTranMapBean> CREATOR = new Creator<OrderTranMapBean>() {
        @Override
        public OrderTranMapBean createFromParcel(Parcel in) {
            return new OrderTranMapBean(in);
        }

        @Override
        public OrderTranMapBean[] newArray(int size) {
            return new OrderTranMapBean[size];
        }
    };

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

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public OrderTranMapBean() {
    }

}
