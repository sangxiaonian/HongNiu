package com.hongniu.modulefinance.entity;

import com.hongniu.baselibrary.entity.OrderDetailBean;

import java.util.List;
import java.util.Map;

/**
 * 作者： ${桑小年} on 2018/10/28.
 * 努力，为梦长留
 */
public class FinanceQueryCarDetailMap {


    private String dates;
    private  List<OrderDetailBean> list;

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public List<OrderDetailBean> getList() {
        return list;
    }

    public void setList(List<OrderDetailBean> list) {
        this.list = list;
    }
}
