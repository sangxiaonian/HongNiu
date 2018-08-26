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


//        String content =" {\"appid\":\"wxb4ba3c02aa476ea1\",\"partnerid\":\"1900006771\",\"package\":\"Sign=WXPay\",\"noncestr\":\"11511274716659fa530bd6b67bb2e2f7\",\"timestamp\":1535291201,\"prepayid\":\"wx26214641750437b4b95148054181284069\",\"sign\":\"F345DBA9A0514933C11019718B85E03B\"}";
//
//        try {
//            JSONObject json = new JSONObject(content);
//            if(null != json && !json.has("retcode") ){
//                PayReq req = new PayReq();
//                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
//                req.appId			= json.getString("appid");
//                req.partnerId		= json.getString("partnerid");
//                req.prepayId		= json.getString("prepayid");
//                req.nonceStr		= json.getString("noncestr");
//                req.timeStamp		= json.getString("timestamp");
//                req.packageValue	= json.getString("package");
//                req.sign			= json.getString("sign");
//                req.extData			= "app data"; // optional
//
//
////                showToast("正常调起支付");
//                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//                api.sendReq(req);
//            }else{
//                Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
////                showToast("返回错误"+json.getString("retmsg"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


    }


}
