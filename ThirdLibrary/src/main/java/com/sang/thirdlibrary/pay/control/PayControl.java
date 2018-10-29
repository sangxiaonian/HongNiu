package com.sang.thirdlibrary.pay.control;

import android.app.Activity;

import com.sang.thirdlibrary.pay.entiy.PayBean;
import com.sang.thirdlibrary.pay.unionpay.UnionPayClient;

/**
 * 作者： ${PING} on 2018/9/25.
 */
public class PayControl {

    public interface IPayClient{
        void pay(Activity activity,PayBean bean);
        IPayClient setDebug(boolean isDebug);
    }

}
