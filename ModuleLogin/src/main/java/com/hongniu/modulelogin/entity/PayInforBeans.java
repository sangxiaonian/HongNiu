package com.hongniu.modulelogin.entity;

/**
 * 作者： ${PING} on 2018/9/17.
 * 收款方式的信息
 */
public class PayInforBeans {

    /**
     * 是否是默认支付方式   1默认，0非默认
     */
    private String id;

    /**
     * true	String 银行名称
     */
    private String bankName;
    /**
     * true	String	开户行名称
     */
    private String depositBank;
    /**
     * true	String	帐户名称/持卡人姓名
     */
    private String accountName;
    /**
     * true	String 银行卡号
     */
    private String cardNo;

    /**
     * 是否是默认支付方式   1默认，0非默认
     */
    private int isDefault;

    /**
     * 支付方式 1微信支付，0银联支付  （此参数为个人写入，方便区别，非接口返回参数）
     */
    private int payWays;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public int getPayWays() {
        return payWays;
    }

    public void setPayWays(int payWays) {
        this.payWays = payWays;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
