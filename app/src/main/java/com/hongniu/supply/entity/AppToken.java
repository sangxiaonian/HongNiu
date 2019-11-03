package com.hongniu.supply.entity;

/**
 * 作者：  on 2019/11/3.
 */
public class AppToken {
   private String deviceType	;//true	string	登录设备类型(ios或 android)
   private String deviceTokens	;//true	string	友盟token

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceTokens() {
        return deviceTokens;
    }

    public void setDeviceTokens(String deviceTokens) {
        this.deviceTokens = deviceTokens;
    }
}
