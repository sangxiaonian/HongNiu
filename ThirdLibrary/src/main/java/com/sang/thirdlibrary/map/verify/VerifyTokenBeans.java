package com.sang.thirdlibrary.map.verify;

/**
 * 作者：  on 2020/3/1.
 */
public class VerifyTokenBeans {

    private int isAuthen;//	true	number	是否已识别(0 未识别 1 已识别)
    private String bizSeqNo;//	true	string	业务流水号
    private String orderNo;//	true	string	合作方订单号
    private String faceId;//	true	string	faceId
    private String nonceTicket;//	true	string	NONCE ticket
    private String nonceStr;//	true	string	生成签名的随机数
    private String sign;//	true	string	签名
    private String userID;//	用户自身的UserID


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getIsAuthen() {
        return isAuthen;
    }

    public void setIsAuthen(int isAuthen) {
        this.isAuthen = isAuthen;
    }

    public String getBizSeqNo() {
        return bizSeqNo;
    }

    public void setBizSeqNo(String bizSeqNo) {
        this.bizSeqNo = bizSeqNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getNonceTicket() {
        return nonceTicket;
    }

    public void setNonceTicket(String nonceTicket) {
        this.nonceTicket = nonceTicket;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
