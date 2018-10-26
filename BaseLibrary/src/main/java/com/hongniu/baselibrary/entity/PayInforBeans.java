package com.hongniu.baselibrary.entity;

/**
 * 作者： ${PING} on 2018/9/17.
 * 收款方式的信息
 */
public class PayInforBeans {



    private String encryptedData;
    private String iv;
    private String code;
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
    private int type;

  private String  openId	;//true	String	微信openid
  private String  wxNickName;//
    // 	true	String	微信昵称


    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
