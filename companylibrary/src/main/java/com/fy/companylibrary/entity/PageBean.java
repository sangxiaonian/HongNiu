package com.fy.companylibrary.entity;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/17.
 */
public class PageBean<T> {

    protected int dataCount;
    protected int currentPage;
    protected List<T> list;


    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
