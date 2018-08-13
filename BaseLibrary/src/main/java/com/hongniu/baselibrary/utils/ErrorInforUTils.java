package com.hongniu.baselibrary.utils;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public class ErrorInforUTils {

    public static String getErrorInfor(int code) {
        String msg = "未知错误";
        switch (code) {
            case 200:// 成功;
                msg = "成功";
                break;
            case 400:// 警告，详细信息见msg;
                msg = "警告，详细信息见msg";
                break;
            case 401:// 用户不存在（openid或token不存在或错误）;
                msg = "用户不存在（openid或token不存在或错误";
                break;
            case 402:// 参数不能为空;
                msg = "参数不能为空";
                break;
            case 500:// 服务器繁忙;
                msg = "服务器繁忙";
                break;
            case 300:// 业务逻辑提示，详细信息见msg;
                msg = "业务逻辑提示，详细信息见msg";

                break;
            case 301:// openid不存在;

                msg = "openid不存在";
                break;
            case 302:// 手机号不存在;

                msg = "手机号不存在";
                break;
            case 303:// 用户冻结;

                msg = "用户冻结";
                break;
            case 304:// 用户审核中;

                msg = "用户审核中";
                break;

        }
        return msg;
    }

}
