package com.sang.thirdlibrary.pay;

import android.app.Activity;
import android.content.Intent;

import com.sang.common.event.BusFactory;
import com.sang.thirdlibrary.pay.ali.AliPay;
import com.sang.thirdlibrary.pay.control.PayControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;
import com.sang.thirdlibrary.pay.entiy.PayType;
import com.sang.thirdlibrary.pay.unionpay.UnionPayClient;
import com.sang.thirdlibrary.pay.wechat.WeChatAppPay;

/**
 * 作者： ${PING} on 2018/9/25.
 */
public class PayClient implements PayControl.IPayClient {

    private   PayType payType;
    private PayControl.IPayClient client;
    private boolean isDebug = false;

    @Override
    public void pay(Activity activity, PayBean bean) {
//        if (client != null) {
//            client.pay(activity, bean);
//        }

        Intent intent =new Intent(activity,PayActivity.class);
        intent.putExtra("payType",payType);
        intent.putExtra("payInfor",bean);
        activity.startActivityForResult(intent,0);

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
//        PayType payType = (PayType) getIntent().getSerializableExtra("payType");
//        bean = getIntent().getParcelableExtra("payInfor");
////        0微信支付1银联支付2线下支付3支付宝支付
//
//        client.pay(this, (PayBean) bean);
//        BusFactory.getBus().register(this);
    }

    public PayClient( PayType payType) {
        this.payType=payType;

    }
}
