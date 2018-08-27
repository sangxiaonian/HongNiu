package com.sang.thirdlibrary.pay.wechat;

import android.content.Context;
import android.util.Log;

import com.sang.thirdlibrary.pay.PayConfig;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者： ${桑小年} on 2018/8/26.
 * 努力，为梦长留
 */
public class WeChatAppPay {

    /**
     *
     * @param context
     * @param partnerId  商户号
     * @param prepayId 预支付交易会话ID
     * @param packageValue 预支付交易会话ID
     * @param nonceStr  随机字符串
     * @param timeStamp 时间戳
     * @param sign     sign
     */
    public static void pay(Context context, String partnerId, String prepayId,String packageValue, String nonceStr, String timeStamp, String sign){
        IWXAPI api;

        api = WXAPIFactory.createWXAPI(context, PayConfig.weChatAppid);
        PayReq request = new PayReq();
        //应用ID
        request.appId = PayConfig.weChatAppid;
        //商户号
        request.partnerId = partnerId;
        //预支付交易会话ID
        request.prepayId= prepayId;
        //固定值
        request.packageValue = packageValue;
        //随机字符串
        request.nonceStr= nonceStr;
        //timestamp
        request.timeStamp= timeStamp;
        //sign
        request.sign= sign;
        api.sendReq(request);

    }


}
