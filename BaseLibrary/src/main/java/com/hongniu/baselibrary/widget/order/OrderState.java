package com.hongniu.baselibrary.widget.order;

//当前所处的支付状态
public enum OrderState {
    WAITE_PAY,//待支付
    WAITE_START_NO_INSURANCE,//待发车(未购买保险)
    WAITE_START,//待发车(已买保险)
    IN_TRANSIT,//运输中
    HAS_ARRIVED,//已到达
    RECEIPT,//已收货

}