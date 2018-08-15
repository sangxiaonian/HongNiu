package com.hongniu.modulelogin.entity;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class LoginCarListBean {

    /**
     * pageNum : 1
     * pageSize : 20
     * total : 3
     * pages : 1
     * list : [{"id":128,"carNumber":"沪A888888","carcode":null,"carType":5,"carOwnerId":251,"userId":251,"createTime":1534340606000,"state":0,"cartypename":"前四后六","contactName":"测试","contactMobile":"15766464644"},{"id":127,"carNumber":"157664","carcode":null,"carType":null,"carOwnerId":258,"userId":251,"createTime":1534337226000,"state":1,"cartypename":null,"contactName":null,"contactMobile":null},{"id":126,"carNumber":"沪1264949","carcode":null,"carType":null,"carOwnerId":256,"userId":251,"createTime":1534337080000,"state":1,"cartypename":null,"contactName":null,"contactMobile":null}]
     */

    private int pageNum;
    private int pageSize;
    private int total;
    private int pages;
    private List<LoginCarInforBean> list;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<LoginCarInforBean> getList() {
        return list;
    }

    public void setList(List<LoginCarInforBean> list) {
        this.list = list;
    }
}
