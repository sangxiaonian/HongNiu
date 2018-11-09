package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2018/10/26.
 * 提现方式参数
 */
public class BalanceWithDrawBean {
    /**
     * true	double	提现金额
      */
    private String amount	    ;
    /**
     * true	string	支付密码
      */
    private String payPassword	;
    /**
     * true	long	提现方式id
      */
    private String refundId	    ;

    public BalanceWithDrawBean(String amount, String payPassword, String refundId) {
        this.amount = amount;
        this.payPassword = payPassword;
        this.refundId = refundId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }
}
