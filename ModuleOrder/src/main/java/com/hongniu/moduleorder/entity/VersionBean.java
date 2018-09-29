package com.hongniu.moduleorder.entity;

/**
 * 作者： ${PING} on 2018/9/18.
 */
public class VersionBean {
    /**
     * true	number	APP类型，字典：1 苹果，2 安卓
      */
    private int   type	        ;
    /**
     * false	number	版本号
      */
    private String   versionCode 	;
    /**
     * true	string	版本说明
      */
    private String   versionName	;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
