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
     * false	string	我的身份（3-货主/1-车主/2-司机）
     */
    private int userType;
    /**
     * false	string	发车日期(today-今天 tomorrow-明天 thisweek-本周 nextweek-下周)
     */
    private String deliveryDateType;

    /**
     * 搜索内容
     */
    private String searchText;
    /**
     * 起始发车日期
     */
    private String deliveryDateStart;
    /**
     * 结束发车日期
     */
    private String deliveryDateEnd;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getDeliveryDateStart() {
        return deliveryDateStart;
    }

    public void setDeliveryDateStart(String deliveryDateStart) {
        this.deliveryDateStart = deliveryDateStart;
    }

    public String getDeliveryDateEnd() {
        return deliveryDateEnd;
    }

    public void setDeliveryDateEnd(String deliveryDateEnd) {
        this.deliveryDateEnd = deliveryDateEnd;
    }

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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getDeliveryDateType() {
        return deliveryDateType;
    }

    public void setDeliveryDateType(String deliveryDateType) {
        this.deliveryDateType = deliveryDateType;
    }
}

