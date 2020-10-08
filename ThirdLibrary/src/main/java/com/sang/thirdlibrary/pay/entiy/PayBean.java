package com.sang.thirdlibrary.pay.entiy;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 作者： ${桑小年} on 2018/8/26.
 * 努力，为梦长留
 */
public class PayBean implements Parcelable {


    /**
     * true	string	时间
     */
    private String timeStamp;
    /**
     * true	string	随机数
     */
    private String nonceStr;
    /**
     * true	string	prepay_id=XXXXXXX
     */
    @SerializedName(value = "package", alternate = {"prepay_id"})
    private String prepay_id;
    /**
     * true	string	加签类型
     */
    private String signType;

    private String prePayId;
    /**
     * 商户号
     */
    private String partnerId;
    /**
     * 签名
     */
    private String paySign;

    /**
     * 银联支付的账单号
     */
    private String tn;
    private String code;
    private String msg;


    /*支付宝的信息*/
    private String orderInfo;

     //------------------------微信支付相关数据---------------------------//
    private String orderNum;//	true	string	订单号

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid;
    }

    //------------------------密码支付---------------------------//
    private String flowid;//	true	string	流水号flowid


    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPrePayId() {
        return prePayId;
    }

    public void setPrePayId(String prePayId) {
        this.prePayId = prePayId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.timeStamp);
        dest.writeString(this.nonceStr);
        dest.writeString(this.prepay_id);
        dest.writeString(this.signType);
        dest.writeString(this.prePayId);
        dest.writeString(this.partnerId);
        dest.writeString(this.paySign);
        dest.writeString(this.tn);
        dest.writeString(this.code);
        dest.writeString(this.msg);
        dest.writeString(this.orderInfo);
        dest.writeString(this.orderNum);
        dest.writeString(this.flowid);
    }

    public PayBean() {
    }

    protected PayBean(Parcel in) {
        this.timeStamp = in.readString();
        this.nonceStr = in.readString();
        this.prepay_id = in.readString();
        this.signType = in.readString();
        this.prePayId = in.readString();
        this.partnerId = in.readString();
        this.paySign = in.readString();
        this.tn = in.readString();
        this.code = in.readString();
        this.msg = in.readString();
        this.orderInfo = in.readString();
        this.orderNum = in.readString();
        this.flowid = in.readString();
    }

    public static final Creator<PayBean> CREATOR = new Creator<PayBean>() {
        @Override
        public PayBean createFromParcel(Parcel source) {
            return new PayBean(source);
        }

        @Override
        public PayBean[] newArray(int size) {
            return new PayBean[size];
        }
    };
}
