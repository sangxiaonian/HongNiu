package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/7.
 * 订单状态
 */
public class OrderStatusBean {
   private int orderState;//	true	number	订单状态  1:待支付 2:待接单 3:差额支付中 4:待派车 5:找车中 6:待发车 7:运输中 8:已到达 9:已收货
   private boolean havePolicy;//	true	string	是否生成保单 true /false
   private String policyNo;//	fasle	string	保单号码
   private String downloadUrl;//	false	string	保单下载地址
   private String policyUrl;//	false	string	保单查询链接
   private int payPolicyState;//	true	string	保费 0未支付1支付成功
   private int balanceFreightStatus;//	差额运费支付状态: 0待支付 1支付成功

    private String errormsg;//	false	string	投保失败返回结果
   private int freightStatus;//	true	number	运费支付状态: 0待支付 1支付成功

    public int getBalanceFreightStatus() {
        return balanceFreightStatus;
    }

    public void setBalanceFreightStatus(int balanceFreightStatus) {
        this.balanceFreightStatus = balanceFreightStatus;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public boolean isHavePolicy() {
        return havePolicy;
    }

    public void setHavePolicy(boolean havePolicy) {
        this.havePolicy = havePolicy;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPolicyUrl() {
        return policyUrl;
    }

    public void setPolicyUrl(String policyUrl) {
        this.policyUrl = policyUrl;
    }

    public int getPayPolicyState() {
        return payPolicyState;
    }

    public void setPayPolicyState(int payPolicyState) {
        this.payPolicyState = payPolicyState;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public int getFreightStatus() {
        return freightStatus;
    }

    public void setFreightStatus(int freightStatus) {
        this.freightStatus = freightStatus;
    }
}
