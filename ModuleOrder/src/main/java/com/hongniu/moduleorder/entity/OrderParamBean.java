package com.hongniu.moduleorder.entity;

/**
 * 作者： ${桑小年} on 2018/8/18.
 * 努力，为梦长留
 * 订单相关操作参数
 */
public class OrderParamBean  {
    /**
     * true	string	订单ID
     */
    private String id;
    /**
     * 支付方式 0微信支付 1银联支付 2线下支付 3支付宝 4余额
     */
    private int payType=-1;

    /**
     * true	string	订单号
     */
    private String orderNum;
    /**
     * true	string	微信appid
     */
    private String appid;
    /**
     * true	boolean	是否付运费，true=是
     */
    private boolean hasFreight;
    /**
     * true	boolean	是否买保险，true=是
     */
    private boolean hasPolicy;
    /**
     * true	boolean	是否线上支付,false=线下支付
     */
    private boolean onlinePay;
    private String payPassword;


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
        return payType;
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
