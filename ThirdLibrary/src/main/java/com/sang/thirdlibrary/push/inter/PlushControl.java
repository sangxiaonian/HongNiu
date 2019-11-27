package com.sang.thirdlibrary.push.inter;

/**
 * 作者： ${PING} on 2017/11/15.
 */

public class PlushControl {

    /**
     * 推送注册监听接口
     * @param <T>
     */
    public interface RegisterListener<T> {
        /**
         * 推送注册成功
         *
         * @param data
         */
        void onSuccess(String data);

        /**
         * 推送注册失败
         *
         * @param s
         * @param s1
         */
        void onFailure(String s, String s1);

        /**
         * 系统消息推送之后,对自定义行为的处理解析
         *
         * @param message
         */
        void dealWithCustomAction(T message);
        /**
         * 系统消息推送之后,对自定义消息的处理解析
         *
         * @param message
         */
        void dealWithCustomMessage(T message);

    }
}
