package com.hongniu.modulefinance.entity;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/23.
 */
public class QueryExpendResultBean {

    /**
     * costDate : 2017-09
     * costs : [{"payWay":1,"money":0},{"payWay":2,"money":0}]
     */

    private String costDate;
    private List<CostsBean> costs;

    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }

    public List<CostsBean> getCosts() {
        return costs;
    }

    public void setCosts(List<CostsBean> costs) {
        this.costs = costs;
    }

    public static class CostsBean {
        /**
         * payWay : 1
         * money : 0
         */

        private int payWay;
        private int money;

        public int getPayWay() {
            return payWay;
        }

        public void setPayWay(int payWay) {
            this.payWay = payWay;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }
    }
}
