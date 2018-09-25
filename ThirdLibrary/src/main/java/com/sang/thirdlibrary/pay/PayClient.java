package com.sang.thirdlibrary.pay;

import android.app.Activity;

import com.sang.thirdlibrary.pay.control.PayControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;

/**
 * 作者： ${PING} on 2018/9/25.
 */
public class PayClient implements PayControl.IPayClient {

    private PayControl.IPayClient client;
    private boolean isDebug = false;

    @Override
    public void pay(Activity activity, PayBean bean) {
        if (client != null) {
            client.pay(activity, bean);
        }
    }

    @Override
    public void setDebug(boolean isDebug) {
        client.setDebug(isDebug);
    }

//    private static class InnerClass{
//        private static   PayClient client=new PayClient();
//    }
//
//    public static PayClient getClient(){
//        return InnerClass.client;
//    }

    public PayClient(PayControl.IPayClient client) {
        this.client = client;
    }


}
