package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2019/3/8.
 * 查询用户绑定的华夏银行账户信息
 */
public class QueryBindHuaInforsBean {


    /**
     * othBankPayeeSubAccName : 上海泓牛供应链管理有限公司
     * bankCardNum : 6222226446646646446
     * othBankPayeeSubAccSetteName : 华夏银行北京分行
     * othBankPayeeSubAcc : 99110250001001723572
     */

    private String othBankPayeeSubAccName;
    private String bankCardNum;
    private String othBankPayeeSubAccSetteName;
    private String othBankPayeeSubAcc;

    private String transacRemark;//	true	string	转账必填交易附言
    private String transacExplain;//	true	string	转账说明


    public String getTransacRemark() {
        return transacRemark;
    }

    public void setTransacRemark(String transacRemark) {
        this.transacRemark = transacRemark;
    }

    public String getTransacExplain() {
        return transacExplain;
    }

    public void setTransacExplain(String transacExplain) {
        this.transacExplain = transacExplain;
    }

    public String getOthBankPayeeSubAccName() {
        return othBankPayeeSubAccName;
    }

    public void setOthBankPayeeSubAccName(String othBankPayeeSubAccName) {
        this.othBankPayeeSubAccName = othBankPayeeSubAccName;
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
