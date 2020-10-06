package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/13.
 */
public class AppraiseParams {
 private String orderId;//	true	number	订单id
 private int userType;//	true	number	评价用户身份 1:托运人 2:实际承运人 3:司机
 private int goodsScore;//	false	number	托运人评分
 private int staffScore;//	true	number	平台员工评分
 private int ownerScore;//	false	number	实际承运人评分
 private int driverScore;//	false	number	司机评分
 private String content;//	false	string	评价内容

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getGoodsScore() {
        return goodsScore;
    }

    public void setGoodsScore(int goodsScore) {
        this.goodsScore = goodsScore;
    }

    public int getStaffScore() {
        return staffScore;
    }

    public void setStaffScore(int staffScore) {
        this.staffScore = staffScore;
    }

    public int getOwnerScore() {
        return ownerScore;
    }

    public void setOwnerScore(int ownerScore) {
        this.ownerScore = ownerScore;
    }

    public int getDriverScore() {
        return driverScore;
    }

    public void setDriverScore(int driverScore) {
        this.driverScore = driverScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
