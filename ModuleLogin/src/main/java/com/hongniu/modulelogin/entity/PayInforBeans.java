package com.hongniu.modulelogin.entity;

/**
 * 作者： ${PING} on 2018/9/17.
 * 收款方式的信息
 */
public class PayInforBeans {
    /**
     *  true	String 银行名称
      */
    private String  bankName	  ;
    /**
     * true	String	开户行名称
      */
    private String  depositBank;
    /**
     * true	String	帐户名称/持卡人姓名
      */
    private String  accountName;
    /**
     *  true	String 银行卡号
      */
    private String  cardNo	  ;

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
