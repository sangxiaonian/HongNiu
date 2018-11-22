package com.hongniu.baselibrary.entity;

import java.io.Serializable;

/**
 * 作者： ${PING} on 2018/10/29.
 */
public class QueryOrderStateBean implements Serializable{

    /**
     *  true	number	订单状态0待支付2支付成功待发车3运输中
      */
    private int orderState	   ;

    /**
     * 保单支付状态 0未支付 1 已经支付
     */
    private int payPolicyState	   ;
    /**
     * 	true	string	是否生成保单 true /false
      */
    private boolean havePolicy;



    /**
     *  fasle	string	保单号码
      */
    private String policyNo	   ;
    /**
     * 	false	string	保单下载地址
      */
    private String downloadUrl;
    /**
     * 	false	string	保单查询链接
      */
    private String policyUrl ;


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

    public int getPayPolicyState() {
        return payPolicyState;
    }

    public void setPayPolicyState(int payPolicyState) {
        this.payPolicyState = payPolicyState;
    }

    public void setPolicyUrl(String policyUrl) {

        this.policyUrl = policyUrl;
    }
}
