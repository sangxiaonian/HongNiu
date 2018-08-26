package com.sang.thirdlibrary.pay.wechat;

import android.content.Context;

import com.sang.thirdlibrary.pay.PayConfig;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 作者： ${桑小年} on 2018/8/26.
 * 努力，为梦长留
 */
public class WeChatAppPay {

    public static void pay(Context context){
        IWXAPI api;

        api = WXAPIFactory.createWXAPI(context, PayConfig.weChatAppid);

        PayReq request = new PayReq();
        //应用ID
        request.appId = PayConfig.weChatAppid;
        //商户号
//        request.partnerId = "1900000109";
        //预支付交易会话ID
        request.prepayId= "1101000000140415649af9fc314aa427";
        //固定值
        request.packageValue = "Sign=WXPay";
        //随机字符串
        request.nonceStr= "1101000000140429eb40476f8896f4c9";
        //timestamp
        request.timeStamp= "1398746574";
        //sign
        request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
        api.sendReq(request);
    }


}
