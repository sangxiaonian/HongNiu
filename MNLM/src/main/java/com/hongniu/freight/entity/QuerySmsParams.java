package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/2/23.
 */
public class QuerySmsParams {
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 校验码，（手机号+秘钥）进行MD5加密
     */
    private String code;
    private String checkCode;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
