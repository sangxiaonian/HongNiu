package com.sang.thirdlibrary.pay.entiy;

import com.sang.common.event.IBus;

/**
 * 作者： ${PING} on 2018/10/17.
 */
public class PayResult implements IBus.IEvent {

    public final static int SUCCESS=1000;
    public final static int FAIL=2000;
    public final static int CANCEL=3000;

    /**
     * 支付结果 1000 成功 2000失败 3000取消
     */
    public int code;
    public String ms;


    public PayResult(int code) {
        this.code = code;
    }
}
