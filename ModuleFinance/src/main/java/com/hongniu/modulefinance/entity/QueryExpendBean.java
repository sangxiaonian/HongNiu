package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2018/8/23.
 */
public class QueryExpendBean {
    /**
     * true	int	年份
     */
    private String year;
    /**
     * true	int	月份，从1开始，1代表1月，2代表2月以此类推。
     */
    private String month;

    public QueryExpendBean(String year, String month) {
        this.year = year;
        this.month = month;
    }


}
