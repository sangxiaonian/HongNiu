package com.hongniu.baselibrary.entity;

import com.sang.thirdlibrary.pay.PayConfig;

/**
 * 作者： ${桑小年} on 2018/8/18.
 * 努力，为梦长留
 * 订单相关操作参数
 */
public class PayParam {
    /**
     * true	string	订单ID
     */
    private String id;
    /**
     * 支付方式 0微信支付 1银联支付 2线下支付3 支付宝支付 4余额支付 5企业支付
     */
    private Integer payType=-1;

    /**
     * true	string	订单号
     */
    private String orderNum;
    /**
     * true	string	微信appid
     */
    private String appid =PayConfig.weChatAppid;
    /**
     * true	boolean	是否付运费，true=是
     */
    private Boolean hasFreight;
    /**
     * true	boolean	是否买保险，true=是
     */
    private Boolean hasPolicy;
    /**
     * true	boolean	是否线上支付,false=线下支付
     */
    private Boolean onlinePay;
    private String payPassword;

    /**
     * 被保险人ID
     */
    private String insuranceUserId;

   private Integer paybusiness	;//true	string	1订单支付2代收货款和代收运费支付3意向金支付
   private String matchingId	;//true	long	车货匹配抢单记录id
   private Integer freightPayClass	;//true	number	运费支付类型1:现付 2:到付 3:回付
   private String receiptName	;//false	string	收货人姓名 到付时必填
   private String receiptMobile	;//false	string	收货人手机 到付时必填


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPaybusiness() {
        return paybusiness;
    }

    public void setPaybusiness(int paybusiness) {
        this.paybusiness = paybusiness;
    }

    public String getMatchingId() {
        return matchingId;
    }

    public void setMatchingId(String matchingId) {
        this.matchingId = matchingId;
    }

    public Integer getFreightPayClass() {
        return freightPayClass;
    }

    public void setFreightPayClass(Integer freightPayClass) {
        this.freightPayClass = freightPayClass;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public String getReceiptMobile() {
        return receiptMobile;
    }

    public void setReceiptMobile(String receiptMobile) {
        this.receiptMobile = receiptMobile;
    }

    public String getInsuranceUserId() {
        return insuranceUserId;
    }

    public void setInsuranceUserId(String insuranceUserId) {
        this.insuranceUserId = insuranceUserId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getOrderId() {
        return id
                ;
    }

    public int getPayType() {
        return payType==null?0:payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public void setOrderId(String orderId) {
        this.id = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }



    public boolean isHasFreight() {
        return hasFreight;
    }

    public void setHasFreight(boolean hasFreight) {
        this.hasFreight = hasFreight;
    }

    public boolean isHasPolicy() {
        return hasPolicy;
    }

    public void setHasPolicy(boolean hasPolicy) {
        this.hasPolicy = hasPolicy;
    }

    public boolean isOnlinePay() {
        return onlinePay;
    }

    public void setOnlinePay(boolean onlinePay) {
        this.onlinePay = onlinePay;
    }
}
