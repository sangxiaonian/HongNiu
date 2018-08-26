package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2018/8/23.
 * year		int	年份
 * month	false	int	月份，从1开始，1代表1月，2代表2月以此类推。
 * carNo	false	String	车牌号
 * financeType	true	int	财务类型，0支出和收入；1支出；2收入
 * pageNum	false	int	页面索引
 * pageSize	false	int	页面记录条数
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


    /**
     * false	String	车牌号
     */
    private String carNo	    ;
    /**
     * true	int	财务类型，0支出和收入；1支出；2收入
     */
    private int financeType	;
    /**
     * false	int	页面索引
     */
    private int pageNum	    ;
    /**
     * false	int	页面记录条数
     */
    private int pageSize	    ;


    public QueryExpendBean(String year, String month) {
        this.year = year;
        this.month = month;
    }

    public QueryExpendBean() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public int getFinanceType() {
        return financeType;
    }

    public void setFinanceType(int financeType) {
        this.financeType = financeType;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
