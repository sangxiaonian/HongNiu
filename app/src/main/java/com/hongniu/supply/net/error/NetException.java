package com.hongniu.supply.net.error;

/**
 * 作者： ${桑小年} on 2017/12/3.
 * 努力，为梦长留
 */

public class NetException extends RuntimeException {

    private int errorCode;
    private String errorMSg;

    public NetException(int errorCode, String errorMSg) {
        super(errorMSg);
        this.errorCode = errorCode;
        this.errorMSg = errorMSg;
    }

    public String getErrorCode() {
        return String.valueOf(errorCode);
    }

    public String getErrorMSg() {
        return errorMSg;
    }
}
