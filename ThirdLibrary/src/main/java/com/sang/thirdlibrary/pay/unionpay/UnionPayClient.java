package com.sang.thirdlibrary.pay.unionpay;

import android.app.Activity;

import com.unionpay.UPPayAssistEx;

/**
 * 作者： ${PING} on 2018/9/25.
 */
public class UnionPayClient {

    /**
     * @param activity
     * @param tn    账单号
     */
    public static void pay(Activity activity,String tn) {

        String serverMode = "01";//测试环境
//        String serverMode = "00";//正式环境

        UPPayAssistEx.startPay (activity, null, null, tn, serverMode);
    }


}
