package com.hongniu.baselibrary.entity;

import com.sang.common.event.IBus;

/**
 * 作者： ${PING} on 2019/5/22.
 */
public class PayOrderInfor implements IBus.IEvent{
    public String orderID;//订单ID
    public float money;//运费金额
    public boolean insurance;//是否购买保险，true购买
    public String orderNum;//订单号
    public String receiptName;//收货人姓名
    public String receiptMobile;//收货人手机号
}
