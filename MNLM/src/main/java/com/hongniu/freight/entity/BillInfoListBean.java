package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/2/12.
 * 账单列表数据
 */
public class BillInfoListBean {
  private String id;//	false	int	流水id
  private String accountCode;//	fasle	string	会员代码
  private String orderNum;//	fasle	string	会员代码
  private String userName;//	fasle	string	会员名称
  private double amount;//	false	double	流水发生金额
  private int fundtype;//	false	int	流水来源，1支付2提现3转账4退款5充值
  private int inorexptype;//	false	int	1收入2支出
  private String createTime;//	false	string	流水发生时间
  private String carteUserName;//	false	string	流水创建人
  private String relAccountCode;//	false	string	流水关联会员代码  微信提现openid  银行卡提现银行号码
  private String relAccountName;//	false	string	流水关联会员名称  微信提现昵称  银行卡提现银行名称
  private String remark;//	false	string	备注
  private String ordernumber;//	false	string	关联订单号
  private String fundsources;//	false	string	支付来源，1保费2运费3保费加运费4返现5积分兑换6邀请返现
  private String flowType;//	true	string	流水类型1余额流水 2 提现流水
  private String reviewState;//	true	string	提现审核状态 0待审核 1 审核通过 2 审核不通过
  private String reviewRemark;//	true	string	审核不通过的原因
  private int state;//	true	int	提现状态 0生成记录、1已发送、2提现成功、3提现失败
  private String ref1;//	true	string	提现方式0微信 1银行卡
  private String title;//	true	string	显示标题
  private String subtitle;//	true	string	副标题
  private String subheading;//	true	string	时间标题
  private String amtStr;//	true	string	金额
  private String isMe;//	true	string	是否可以显示订单 1是 2否 3不可点击
  private String reviewTime;//	true	string	审核时间
  private String successTime;//	true	string	到账时间
  private String carNum;//	true	string	车牌号
  private String payTime;//	true	string	支付时间
  private String money;//	true	string	支付时间
  private int financeType;//	true	string	支付时间

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getFinanceType() {
        return financeType;
    }

    public void setFinanceType(int financeType) {
        this.financeType = financeType;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getRelAccountCode() {
        return relAccountCode;
    }

    public void setRelAccountCode(String relAccountCode) {
        this.relAccountCode = relAccountCode;
    }

    public String getRelAccountName() {
        return relAccountName;
    }

    public void setRelAccountName(String relAccountName) {
        this.relAccountName = relAccountName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getFundsources() {
        return fundsources;
    }

    public void setFundsources(String fundsources) {
        this.fundsources = fundsources;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getReviewState() {
        return reviewState;
    }

    public void setReviewState(String reviewState) {
        this.reviewState = reviewState;
    }

    public String getReviewRemark() {
        return reviewRemark;
    }

    public void setReviewRemark(String reviewRemark) {
        this.reviewRemark = reviewRemark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRef1() {
        return ref1;
    }

    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public String getAmtStr() {
        return amtStr;
    }

    public void setAmtStr(String amtStr) {
        this.amtStr = amtStr;
    }

    public String getIsMe() {
        return isMe;
    }

    public void setIsMe(String isMe) {
        this.isMe = isMe;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }
}
