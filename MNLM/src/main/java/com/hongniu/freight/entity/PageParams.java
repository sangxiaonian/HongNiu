package com.hongniu.freight.entity;

import com.fy.companylibrary.config.Param;

/**
 * 作者：  on 2020/3/3.
 */
public class PageParams {
  protected int pageNum;//	false	string	页数，默认1
  protected int pageSize= Param.PAGE_SIZE;//	false	string	每页条数，默认20

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
