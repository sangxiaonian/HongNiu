package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2018/8/30.
 *
 * 财务查询获取到的数据
 *
 */
public class FinanceOrderBean {

    /**
     * id : 269
     * orderNum : HN20180830111830250766
     * carNum : 沪A888888
     * payTime : 2018-08-30 14:23:46
     * money : -0.01
     * roleType : 3
     * financeType : 1
     */

    private int id;
    private String orderNum;
    private String carNum;
    private String payTime;
    private double money;
    private int roleType;
    private int financeType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public int getFinanceType() {
        return financeType;
    }

    public void setFinanceType(int financeType) {
        this.financeType = financeType;
    }
}
