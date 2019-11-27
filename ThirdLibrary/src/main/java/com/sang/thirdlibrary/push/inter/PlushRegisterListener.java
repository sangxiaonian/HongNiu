package com.sang.thirdlibrary.push.inter;

/**
 * 作者： ${PING} on 2018/6/26.
 */

public interface PlushRegisterListener  {
    /**
     * 推送注册成功
     *
     * @param data
     */
    void onSuccess(String data);

    /**
     * 推送注册失败
     *
     * @param code  失败码
     * @param errorReson 失败原因
     */
    void onFailure(String code, String errorReson);
}
