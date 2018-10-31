package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2018/10/8.
 * 余额明细
 */
public class BalanceOfAccountBean {
    /**
     * accountCode : HN000329
     * amount : 1002220.0
     * amtStr : +1000.00
     * carteUserName : zhoujg77
     * createTime : 2018-10-15 15:14:04
     * flowType : 1
     * fundsources : 4
     * fundtype : 5
     * fundtypeStr : 充值
     * id : 6
     * income : 1000.0
     * inorexptype : 1
     * inorexptypeStr : 收入
     * isMe : 1
     * ordernumber : 12312313
     * relPayBillno : 1231231
     * remark : 充值
     * subtitle : 12312313
     * title : 账号充值
     * userName : asdfasdf
     */

    private String amtStr;
    private String createTime;
    private int flowType;
    private int id;
    private int inorexptype;
    private int isMe;
    private String ordernumber;
    private String subtitle;
    private String title;
    /**
     * true	string	提现审核状态 0待审核 1 审核通过 2 审核不通过
     */
    private String reviewState	;
    /**
     * true	string	审核不通过的原因
     */
    private String reviewRemark	;
    /**
     * true	int	提现状态 0生成记录、1已发送、2提现成功、3提现失败
     */
    private String state	        ;


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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAmtStr() {
        return amtStr;
    }

    public void setAmtStr(String amtStr) {
        this.amtStr = amtStr;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getFlowType() {
        return flowType;
    }

    public void setFlowType(int flowType) {
        this.flowType = flowType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInorexptype() {
        return inorexptype;
    }

    public void setInorexptype(int inorexptype) {
        this.inorexptype = inorexptype;
    }


    public int getIsMe() {
        return isMe;
    }

    public void setIsMe(int isMe) {
        this.isMe = isMe;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }







}
