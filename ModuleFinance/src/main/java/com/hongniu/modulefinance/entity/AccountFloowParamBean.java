package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2018/10/25.
 */
public class AccountFloowParamBean {

    private int flowtype;//true	string	1余额明细2待入账明细
    private int pageNum;//false	int	页面索引
    private int pageSize;//false	int	页面记录条数
    private String startDate;//false	string	流水开始时间(yyyy-MM-dd)
    private String endDate;//false	string	流水结束时间(yyyy-MM-dd)

    public int getFlowtype() {
        return flowtype;
    }

    public void setFlowtype(int flowtype) {
        this.flowtype = flowtype;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
