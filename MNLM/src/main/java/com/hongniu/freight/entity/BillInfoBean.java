package com.hongniu.freight.entity;

import com.fy.companylibrary.entity.PageBean;

/**
 * 作者：  on 2020/3/10.
 */
public class BillInfoBean extends PageBean<BillInfoListBean> {

    private int total;
    private double totalMoney;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getTotalMoney() {
        return totalMoney;
    }
}
