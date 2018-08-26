package com.hongniu.moduleorder.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者： ${桑小年} on 2018/8/26.
 * 努力，为梦长留
 */
public class WxPayBean {




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
}
