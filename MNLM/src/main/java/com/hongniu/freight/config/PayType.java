package com.hongniu.freight.config;

/**
 * 作者：  on 2020/3/7.
 */
public enum PayType {



    WEICHAT(0,"微信支付"),
    ALIPAY(3,"支付宝支付"),
    BALANCE(4,"余额支付"),
    COMPANY(5,"企业支付"),
    COMPANY_APPLY(5,"企业支付"),//企业申请支付
    UNIONPAY(1,"银联支付"),
    ;

    int payType;
    String name;

    PayType(int payType, String name) {
        this.payType = payType;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPayType() {
        return payType;
    }
}
