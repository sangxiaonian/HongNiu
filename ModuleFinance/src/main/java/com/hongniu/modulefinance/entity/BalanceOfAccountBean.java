package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2018/10/8.
 * 余额明细
 */
public class BalanceOfAccountBean {


    /**
     * id : 103
     * accountCode : HN000329
     * userName : asdfasdf
     * amount : 1000.0
     * fundtype : 5
     * inorexptype : 1
     * createTime : 2018-10-25 18:44:12
     * carteUserName : zhoujg77
     * relPayBillno : 1231231
     * relAccountCode : null
     * relAccountName : null
     * remark : 充值
     * ref1 : null
     * ref2 : null
     * ref3 : null
     * ref4 : null
     * inorexptypeStr : 收入
     * income : 1000.0
     * expend : null
     * surplus : null
     * fundtypeStr : 充值
     * fundsources : 4
     * ordernumber : 12312313
     */

    private int id;
    private String accountCode;
    private String userName;
    private double amount;
//  流水来源  1支付2提现3转账4退款5充值
    private int fundtype;
    private int inorexptype;
    private String createTime;
    private String carteUserName;
    private String relPayBillno;
    private Object relAccountCode;
    private Object relAccountName;
    private String remark;
    private String ref1;
    private Object ref2;
    private Object ref3;
    private Object ref4;
    private String inorexptypeStr;
    private double income;
    private Object expend;
    private Object surplus;
    private String fundtypeStr;
    private int fundsources;
    private String ordernumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getFundtype() {
        return fundtype;
    }

    public void setFundtype(int fundtype) {
        this.fundtype = fundtype;
    }

    public int getInorexptype() {
        return inorexptype;
    }

    public void setInorexptype(int inorexptype) {
        this.inorexptype = inorexptype;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCarteUserName() {
        return carteUserName;
    }

    public void setCarteUserName(String carteUserName) {
        this.carteUserName = carteUserName;
    }

    public String getRelPayBillno() {
        return relPayBillno;
    }

    public void setRelPayBillno(String relPayBillno) {
        this.relPayBillno = relPayBillno;
    }

    public Object getRelAccountCode() {
        return relAccountCode;
    }

    public void setRelAccountCode(Object relAccountCode) {
        this.relAccountCode = relAccountCode;
    }

    public Object getRelAccountName() {
        return relAccountName;
    }

    public void setRelAccountName(Object relAccountName) {
        this.relAccountName = relAccountName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRef1() {
        return ref1;
    }

    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }

    public Object getRef2() {
        return ref2;
    }

    public void setRef2(Object ref2) {
        this.ref2 = ref2;
    }

    public Object getRef3() {
        return ref3;
    }

    public void setRef3(Object ref3) {
        this.ref3 = ref3;
    }

    public Object getRef4() {
        return ref4;
    }

    public void setRef4(Object ref4) {
        this.ref4 = ref4;
    }

    public String getInorexptypeStr() {
        return inorexptypeStr;
    }

    public void setInorexptypeStr(String inorexptypeStr) {
        this.inorexptypeStr = inorexptypeStr;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Object getExpend() {
        return expend;
    }

    public void setExpend(Object expend) {
        this.expend = expend;
    }

    public Object getSurplus() {
        return surplus;
    }

    public void setSurplus(Object surplus) {
        this.surplus = surplus;
    }

    public String getFundtypeStr() {
        return fundtypeStr;
    }

    public void setFundtypeStr(String fundtypeStr) {
        this.fundtypeStr = fundtypeStr;
    }

    public int getFundsources() {
        return fundsources;
    }

    public void setFundsources(int fundsources) {
        this.fundsources = fundsources;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }


}
