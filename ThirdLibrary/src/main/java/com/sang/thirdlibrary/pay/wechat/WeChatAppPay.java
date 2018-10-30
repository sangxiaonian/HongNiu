package com.sang.thirdlibrary.pay.wechat;

import android.app.Activity;
import android.content.Context;

import com.sang.thirdlibrary.pay.PayConfig;
import com.sang.thirdlibrary.pay.control.PayControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;
import com.sang.thirdlibrary.pay.unionpay.UnionPayClient;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 作者： ${桑小年} on 2018/8/26.
 * 努力，为梦长留
 */
public class WeChatAppPay implements PayControl.IPayClient {
    private boolean isDebug;

    @Override
    public PayControl.IPayClient setDebug(boolean isDebug) {
        this.isDebug=isDebug;
        return this;
    }

    @Override
    public void pay(Activity activity, PayBean data) {
        pay(activity, data.getPartnerId(), data.getPrePayId(), data.getPrepay_id()
                , data.getNonceStr(), data.getTimeStamp(), data.getPaySign()
        );
    }

    /**
     * @param activity
     * @param partnerId    商户号
     * @param prepayId     预支付交易会话ID
     * @param packageValue 固定值
     * @param nonceStr     随机字符串
     * @param timeStamp    时间戳
     * @param sign         sign
     */
    private static void pay(Activity activity, String partnerId, String prepayId, String packageValue, String nonceStr, String timeStamp, String sign) {
        IWXAPI api;
        api = WXAPIFactory.createWXAPI(activity, PayConfig.weChatAppid);
        PayReq request = new PayReq();
        //应用ID
        request.appId = PayConfig.weChatAppid;
        //商户号
        request.partnerId = partnerId;
        //预支付交易会话ID
        request.prepayId = prepayId;
        //固定值
        request.packageValue = packageValue;
        //随机字符串
        request.nonceStr = nonceStr;
        //timestamp
        request.timeStamp = timeStamp;
        //sign
        request.sign = sign;
        api.sendReq(request);

    }


    /**
     * 掉起来小程序
     */
    public static void jumpToXia(Context context, boolean isDebug) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, PayConfig.weChatAppid);// 填对应开发平台移动应用AppId
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "gh_736800cd3405"; // 填小程序原始id（官方实例请填写自己的小程序id）
        req.path = "pages/authorize/authorize?from=app"; //拉起小程序页面的可带参路径，不填默认拉起小程序首页

        if (isDebug) {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 体验版
        } else {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 正式版

        }
        api.sendReq(req);
    }

}
