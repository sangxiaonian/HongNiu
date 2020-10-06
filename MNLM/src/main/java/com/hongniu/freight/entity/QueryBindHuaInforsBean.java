package com.hongniu.freight.entity;

/**
 * 作者： ${PING} on 2019/3/8.
 * 查询用户绑定的华夏银行账户信息
 */
public class QueryBindHuaInforsBean {


    /**
     * othBankPayeeSubAccName : 上海木牛流马管理有限公司
     * bankCardNum : 6222226446646646446
     * othBankPayeeSubAccSetteName : 华夏银行北京分行
     * othBankPayeeSubAcc : 99110250001001723572
     */

    private String othBankPayeeSubAccName;
    private String bankCardNum;
    private String othBankPayeeSubAccSetteName;
    private String othBankPayeeSubAcc;
    private boolean subAccStatus;//开户状态 true:已开子账户 false:未开


    public String getOthBankPayeeSubAccName() {
        return othBankPayeeSubAccName;
    }

    public void setOthBankPayeeSubAccName(String othBankPayeeSubAccName) {
        this.othBankPayeeSubAccName = othBankPayeeSubAccName;
    }

    public boolean isSubAccStatus() {
        return subAccStatus;
    }

    public void setSubAccStatus(boolean subAccStatus) {
        this.subAccStatus = subAccStatus;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public String getOthBankPayeeSubAccSetteName() {
        return othBankPayeeSubAccSetteName;
    }

    public void setOthBankPayeeSubAccSetteName(String othBankPayeeSubAccSetteName) {
        this.othBankPayeeSubAccSetteName = othBankPayeeSubAccSetteName;
    }

    public String getOthBankPayeeSubAcc() {
        return othBankPayeeSubAcc;
    }

    public void setOthBankPayeeSubAcc(String othBankPayeeSubAcc) {
        this.othBankPayeeSubAcc = othBankPayeeSubAcc;
    }
}
