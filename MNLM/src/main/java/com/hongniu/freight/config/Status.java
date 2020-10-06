package com.hongniu.freight.config;

/**
 * 作者：  on 2020/2/8.
 * 订单状态数据
 */
public enum Status {
//1:待支付 2:待接单 3:差额支付中 4:待派车 5:找车中 6:待发车 7:运输中 8:已到达 9:已收货
    All(0,"全部"),
    WAITE_PAY(1,"待支付"),
    WAITE_RECEIVING_ORDER(2,"待接单"),
    WAITE_PAY_BALANCE(3,"差额支付中"),
    WAITE_CAR(4,"待派车"),
    FIND_CAR(5,"找车中"),
    WAITE_DEPART_NO_INSURANCE(6,"待发车"),
//    WAITE_DEPART_INSURANCE(7,"待发车(已买保险)"),
    IN_TRANSIT(7,"运输中"),
    ARRIVE(8,"已到达"),
    RECEIVING(9,"已收货"),
    AUDIT_WAITE(10,"待审核"),
    AUDIT_FAIL(11,"审核失败"),

    ;

    int status;
    String name;

    Status(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
