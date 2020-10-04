package com.fy.androidlibrary.net.error;

import android.os.NetworkOnMainThreadException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

public class ExceptionEngine {

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static String handleException(Throwable e) {
        String msg = "网络异常";
        if (e instanceof NetException) {             //自定义异常
            msg=((NetException) e).getErrorMSg();
        } else if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    msg = "网络错误";  //均视为网络错误
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            msg = "数据解析错误";            //均视为解析错误
        } else if (e instanceof ConnectException) {
            msg = "服务器连接失败";
        } else if (e instanceof SocketException) {
            msg = "服务器连接失败";
        } else if (e instanceof SSLHandshakeException) {
            msg = "签名证书异常";
        } else if (e instanceof IllegalArgumentException) {
            msg = "非法参数异常";
        } else if (e instanceof UnknownHostException) {
            msg = "网络异常，无法连接到服务器";
        } else if (e instanceof NetworkOnMainThreadException) {
            msg = "请勿在主线程进行网络请求";
        } else if (e instanceof SocketTimeoutException) {
            msg = "服务器连接超时,请稍后再试";
        } else {
            msg = e.getMessage();
        }
        return msg;
    }

    public static int handleExceptionCode(Throwable e) {
        int code= 1000;
        if (e instanceof NetException) {             //自定义异常
            code=((NetException) e).getErrorCode();
        } else if (e instanceof HttpException) {             //HTTP错误
            code=((HttpException) e).code();

        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
//            msg = "数据解析错误";            //均视为解析错误
        } else if (e instanceof ConnectException) {
//            msg = "服务器连接失败";
        } else if (e instanceof SocketException) {
//            msg = "服务器连接失败";
        } else if (e instanceof SSLHandshakeException) {
//            msg = "签名证书异常";
        } else if (e instanceof IllegalArgumentException) {
//            msg = "非法参数异常";
        } else if (e instanceof UnknownHostException) {
//            msg = "网络异常，无法连接到服务器";
        } else if (e instanceof NetworkOnMainThreadException) {
//            msg = "请勿在主线程进行网络请求";
        } else {
//            msg = e.getMessage();
        }
        return code;
    }
}