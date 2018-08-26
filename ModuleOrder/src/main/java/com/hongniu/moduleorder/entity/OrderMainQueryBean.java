package com.hongniu.moduleorder.entity;

/**
 * 作者： ${PING} on 2018/8/17.
 */
public class OrderMainQueryBean {

    /**
     * false	number	页数，默认1
     */
    private int pageNum;
    /**
     * false	number	每页条数，默认20
     */
    private int pageSize;
    /**
     * false	string	订单状态(2待发货，3配送中，4已到达,5已收货)
     */
    private String queryStatus;
    /**
     * false	string	是否付运费(1是，0否)
     */
    private String hasFreight;
    /**
     * false	string	我的身份（3-货主/1-车主/2-司机）
     */
    private int userType;
    /**
     * false	string	发车日期(today-今天 tomorrow-明天 thisweek-本周 nextweek-下周)
     */
    private String deliveryDate;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }

    public String getHasFreight() {
        return hasFreight;
    }

    public void setHasFreight(String hasFreight) {
        this.hasFreight = hasFreight;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}

