package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2019/3/8.
 */
public class FinanceQueryNiuDetailBean {

    /**
     * amount : 0.09
     * createTime : 2018-12-05 00:00:00
     * datestr : 2018-12-05
     */

    private double amount;
    private String createTime;
    private String datestr;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDatestr() {
        return datestr;
    }

    public void setDatestr(String datestr) {
        this.datestr = datestr;
    }
}
