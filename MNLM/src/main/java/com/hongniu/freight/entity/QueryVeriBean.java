package com.hongniu.freight.entity;

/**
 * 作者： ${PING} on 2019/3/8.
 * 查询用户绑定的华夏银行账户信息
 */
public class QueryVeriBean {



    private boolean subAccStatus;//开户状态 true:已开子账户 false:未开


    public boolean isSubAccStatus() {
        return subAccStatus;
    }

    public void setSubAccStatus(boolean subAccStatus) {
        this.subAccStatus = subAccStatus;
    }
}
