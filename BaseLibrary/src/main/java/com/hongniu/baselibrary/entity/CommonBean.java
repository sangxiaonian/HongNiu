package com.hongniu.baselibrary.entity;

/**
 * 作者： ${PING} on 2018/8/13.
 * 网络请求返回数据的公共接口
 */
public class CommonBean<T> {
    T data;
    private int code=200;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
