package com.hongniu.modulelogin.entity;

/**
 * 作者： ${PING} on 2019/3/8.
 */
public class LoginBlindBlankParams {

    /**
     * 收款方式  (微信0,银行卡1)
      */
    private String type;
    /**
     * 选中银行id
      */
    private String bankId;
    /**
     * 0出入金
      */
    private String linkAccountType;
    /**
     * 开户名
      */
    private String accountName;
    /**
     * 身份证号
      */
    private String idnumber;
    /**
     * 银行卡号
      */
    private String cardNo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getLinkAccountType() {
        return linkAccountType;
    }

    public void setLinkAccountType(String linkAccountType) {
        this.linkAccountType = linkAccountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
