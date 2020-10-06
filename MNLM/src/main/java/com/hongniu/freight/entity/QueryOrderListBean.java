package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/2.
 */
public class QueryOrderListBean {
  private int  userType;//	true	number	用户类型
  private String  status;//	订单状态 1:待支付 2:待接单 3:差额支付中 4:待派车 5:找车中 6:待发车 7:运输中 8:已到达 9:已收货
  private String  searchText;//	false	string	查询条件
  private int  pageNum;//	false	number	页数 默认1
  private int  pageSize;//	false	number	每页条数 默认20

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
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
