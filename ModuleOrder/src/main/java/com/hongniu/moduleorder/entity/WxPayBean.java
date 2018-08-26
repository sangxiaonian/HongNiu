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
    /**
     * 签名
     */
    private String paySign;

}
