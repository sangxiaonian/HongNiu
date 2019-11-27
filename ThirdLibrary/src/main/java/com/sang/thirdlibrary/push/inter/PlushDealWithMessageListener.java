package com.sang.thirdlibrary.push.inter;

import android.content.Context;

/**
 * 作者： ${PING} on 2018/6/26.
 * 处理获取到推送消息时候，自定义处理消息信息
 */

public interface PlushDealWithMessageListener<T>  {

    void dealMessage(Context context, T message);



}
