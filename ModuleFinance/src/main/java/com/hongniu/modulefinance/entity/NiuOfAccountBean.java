package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2018/10/8.
 * 牛贝相关的数据，待入账，已入账
 */
public class NiuOfAccountBean {


    /**
     * carNumber : 沪A99999
     * createTime : 2018-09-25 21:30:00
     * integral : 1
     * relPayBillno : HN20180925212959725083
     */

    private String carNumber;
    private String createTime;
    private int integral;
    private String relPayBillno;

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getRelPayBillno() {
        return relPayBillno;
    }

    public void setRelPayBillno(String relPayBillno) {
        this.relPayBillno = relPayBillno;
    }
}
