package com.hongniu.freight.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.hongniu.freight.config.PayType;

/**
 * 作者：  on 2020/3/7.
 */
public class QueryPayInfoParams implements Parcelable {

   private String orderNum;//	true	string	订单号
   private String orderId;//	true	string	订单号
   private int payType;//	true	string	0微信支付1银联支付3支付宝支付4余额支付 5企业支付
   private PayType type;//	true	string	0微信支付1银联支付3支付宝支付4余额支付 5企业支付
   private String payPassword;//	false	strng	支付密码 (余额支付必填)
   private String appid;//	false	string	应用ID (微信、支付宝必传)
   private int paybusiness;//	true	string	支付业务类型(1订单支付2补款运费支付3补购保险支付)

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getPayType() {
        return payType;
    }

    public PayType getType() {
        return type;
    }

    public void setType(PayType type) {
        this.type = type;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public int getPaybusiness() {
        return paybusiness;
    }

    public void setPaybusiness(int paybusiness) {
        this.paybusiness = paybusiness;
    }

    public QueryPayInfoParams() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderNum);
        dest.writeString(this.orderId);
        dest.writeInt(this.payType);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.payPassword);
        dest.writeString(this.appid);
        dest.writeInt(this.paybusiness);
    }

    protected QueryPayInfoParams(Parcel in) {
        this.orderNum = in.readString();
        this.orderId = in.readString();
        this.payType = in.readInt();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : PayType.values()[tmpType];
        this.payPassword = in.readString();
        this.appid = in.readString();
        this.paybusiness = in.readInt();
    }

    public static final Creator<QueryPayInfoParams> CREATOR = new Creator<QueryPayInfoParams>() {
        @Override
        public QueryPayInfoParams createFromParcel(Parcel source) {
            return new QueryPayInfoParams(source);
        }

        @Override
        public QueryPayInfoParams[] newArray(int size) {
            return new QueryPayInfoParams[size];
        }
    };
}
