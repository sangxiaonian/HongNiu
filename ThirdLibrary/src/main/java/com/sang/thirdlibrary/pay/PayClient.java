package com.sang.thirdlibrary.pay;

import android.app.Activity;

import com.sang.thirdlibrary.pay.control.PayControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;
import com.sang.thirdlibrary.pay.entiy.PayType;
import com.sang.thirdlibrary.pay.unionpay.UnionPayClient;

/**
 * 作者： ${PING} on 2018/9/25.
 */
public class PayClient implements PayControl.IPayClient {

    private   PayType payType;
    private PayControl.IPayClient client;


    @Override
    public void pay(Activity activity, PayBean bean) {
        if (client != null) {
            client.pay(activity, bean);
        }
    }

    @Override
    public PayControl.IPayClient setDebug(boolean isDebug) {
        client.setDebug(isDebug);
        return this;
    }

    public PayClient(PayControl.IPayClient client) {
        this.client = client;
    }

    public PayClient( PayType payType) {
        this.payType=payType;

    }
}
