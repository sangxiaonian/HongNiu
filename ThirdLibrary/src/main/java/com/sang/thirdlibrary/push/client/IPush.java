package com.sang.thirdlibrary.push.client;

import android.content.Context;

import com.sang.thirdlibrary.push.inter.PlushDealWithMessageListener;
import com.sang.thirdlibrary.push.inter.PlushRegisterListener;

/**
 * 作者： ${PING} on 2018/6/26.
 * 推送的接口，用来约束实现类的功能
 */

public interface IPush<T> {

    /**
     * 注册推送结果监听
     *
     * @param registerListener
     */
    void setPlushRegisterListener(PlushRegisterListener registerListener);

    /**
     * 处理接收到推送消息监听
     *
     * @param dealWithMessageListener 处理接收到推送消息监听
     */
    void setPlushDealWithMessageListener(PlushDealWithMessageListener<T> dealWithMessageListener);

    /**
     * 统计消息点击打开
     *
     * @param context
     * @param message
     */
    void trackMsgClick(Context context, T message);

    /**
     * 统计消息忽略
     *
     * @param context
     * @param message
     */
    void trackMsgDismissed(Context context, T message);

    void onAppStart(Context context);

    void init(Context context, boolean isAgree);

    /**
     * 判断是否支持当前推送模式
     *
     * @param context
     * @return
     */
    boolean isSupport(Context context);
}
