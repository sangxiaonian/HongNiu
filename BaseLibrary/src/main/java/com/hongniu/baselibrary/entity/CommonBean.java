package com.hongniu.baselibrary.entity;

/**
 * 作者：  on 2019/10/30.
 *
 * 请求通用
 */
public class CommonBean<T> {

    //200：成功；401：用户不存在（openid或token不存在或错误）
    private int code;

    //错误原因
    private String msg;

    //具体信息
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
