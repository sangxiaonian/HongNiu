package com.sang.thirdlibrary.pay.unionpay;

import android.app.Activity;

import com.sang.thirdlibrary.pay.control.PayControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;
import com.unionpay.UPPayAssistEx;

/**
 * 作者： ${PING} on 2018/9/25.
 */
public class UnionPayClient implements PayControl.IPayClient {


    private boolean isDebug;

    @Override
    public void pay(Activity activity, PayBean bean) {
        String serverMode;
        if (isDebug) {
            serverMode = "01";//测试环境
        } else {
            serverMode = "00";//正式环境
        }
        UPPayAssistEx.startPay(activity, null, null, bean.getTn(), serverMode);
    }

    @Override
    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }
}
