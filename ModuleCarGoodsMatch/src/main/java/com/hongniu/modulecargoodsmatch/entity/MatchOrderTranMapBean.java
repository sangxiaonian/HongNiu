package com.hongniu.modulecargoodsmatch.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.services.core.PoiItem;

/**
 * 作者：  on 2019/11/2.
 */
public class MatchOrderTranMapBean implements Parcelable {

    TranMapBean start;
    TranMapBean end;
    String time;
    MatchCarTypeInfoBean carTypeInfoBean;

    public TranMapBean getStart() {
        return start;
    }

    public void setStart(TranMapBean start) {
        this.start = start;
    }

    public TranMapBean getEnd() {
        return end;
    }

    public void setEnd(TranMapBean end) {
        this.end = end;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MatchCarTypeInfoBean getCarTypeInfoBean() {
        return carTypeInfoBean;
    }

    public void setCarTypeInfoBean(MatchCarTypeInfoBean id) {
        this.carTypeInfoBean = id;
    }

    public MatchOrderTranMapBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.start, flags);
        dest.writeParcelable(this.end, flags);
        dest.writeString(this.time);
        dest.writeParcelable(this.carTypeInfoBean, flags);
    }

    protected MatchOrderTranMapBean(Parcel in) {
        this.start = in.readParcelable(TranMapBean.class.getClassLoader());
        this.end = in.readParcelable(TranMapBean.class.getClassLoader());
        this.time = in.readString();
        this.carTypeInfoBean = in.readParcelable(MatchCarTypeInfoBean.class.getClassLoader());
    }

    public static final Creator<MatchOrderTranMapBean> CREATOR = new Creator<MatchOrderTranMapBean>() {
        @Override
        public MatchOrderTranMapBean createFromParcel(Parcel source) {
            return new MatchOrderTranMapBean(source);
        }

        @Override
        public MatchOrderTranMapBean[] newArray(int size) {
            return new MatchOrderTranMapBean[size];
        }
    };
}
