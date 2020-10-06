package com.hongniu.freight.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2020/6/6.
 * 查看轨迹站点相关信息
 */
public class AppPathStationBean implements Parcelable {


    private String time;
    private String info;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeString(this.info);
    }

    public AppPathStationBean() {
    }

    protected AppPathStationBean(Parcel in) {
        this.time = in.readString();
        this.info = in.readString();
    }

    public static final Parcelable.Creator<AppPathStationBean> CREATOR = new Parcelable.Creator<AppPathStationBean>() {
        @Override
        public AppPathStationBean createFromParcel(Parcel source) {
            return new AppPathStationBean(source);
        }

        @Override
        public AppPathStationBean[] newArray(int size) {
            return new AppPathStationBean[size];
        }
    };
}
