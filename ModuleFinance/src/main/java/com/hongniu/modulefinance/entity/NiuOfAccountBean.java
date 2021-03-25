package com.hongniu.modulefinance.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者： ${PING} on 2018/10/8.
 * 牛贝相关的数据，待入账，已入账
 */
public class NiuOfAccountBean implements Parcelable {


    /**
     * contact : 周
     * id : 4
     * integralStr : +1个
     * mobile : 17602153410
     * name : 周
     * rongToken : kilEjAWD08N6G3tvdnSAfSQnPbxE0G8DcEDeVienvtLNT9cffugAks/nvUDXLk8j3CQbra1YTx0vl/VehQF+sw==
     */

    private String contact;
    private String id;
    private String rongId;
    private String integralStr;
    private String mobile;
    private String name;
    private String rongToken;
    private String totalAmt;//总收益
    private String yesterdayAmt;//昨日收益

    public NiuOfAccountBean() {
    }

    protected NiuOfAccountBean(Parcel in) {
        contact = in.readString();
        id = in.readString();
        integralStr = in.readString();
        mobile = in.readString();
        name = in.readString();
        rongToken = in.readString();
        totalAmt = in.readString();
        yesterdayAmt = in.readString();
    }

    public static final Creator<NiuOfAccountBean> CREATOR = new Creator<NiuOfAccountBean>() {
        @Override
        public NiuOfAccountBean createFromParcel(Parcel in) {
            return new NiuOfAccountBean(in);
        }

        @Override
        public NiuOfAccountBean[] newArray(int size) {
            return new NiuOfAccountBean[size];
        }
    };

    public String getRongId() {
        return rongId;
    }

    public void setRongId(String rongId) {
        this.rongId = rongId;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getYesterdayAmt() {
        return yesterdayAmt;
    }

    public void setYesterdayAmt(String yesterdayAmt) {
        this.yesterdayAmt = yesterdayAmt;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntegralStr() {
        return integralStr;
    }

    public void setIntegralStr(String integralStr) {
        this.integralStr = integralStr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRongToken() {
        return rongToken;
    }

    public void setRongToken(String rongToken) {
        this.rongToken = rongToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contact);
        dest.writeString(id);
        dest.writeString(integralStr);
        dest.writeString(mobile);
        dest.writeString(name);
        dest.writeString(rongToken);
        dest.writeString(totalAmt);
        dest.writeString(yesterdayAmt);
    }
}
