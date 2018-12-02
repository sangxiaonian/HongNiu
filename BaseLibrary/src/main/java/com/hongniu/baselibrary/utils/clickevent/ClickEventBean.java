package com.hongniu.baselibrary.utils.clickevent;

import java.util.List;

/**
 * 作者： ${桑小年} on 2018/12/2.
 * 努力，为梦长留
 */
public class ClickEventBean {
    /**
     *  true	string	系统类型必须是： ios、android   、miniprogram的一种
     */
    private String systemType	   ;
    /**
     *  false	string	系统版本号
     */
    private String systemVersion	   ;
    /**
     * 	false	string	系统版本号
     */
    private String systemOsVersion;
    /**
     *  false	string	手机型号
     */
    private String systemModel	   ;
    /**
     *  false	string	系统名称
     */
    private String systemName	   ;
    /**
     *  false	string	app版本号
     */
    private String appVersion	   ;
    /**
     *  false	string	错误日志
     */
    private String crashlog	       ;
    /**
     *  false	array
     */
    private List<ItemClickEventBean> events	       ;

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getSystemOsVersion() {
        return systemOsVersion;
    }

    public void setSystemOsVersion(String systemOsVersion) {
        this.systemOsVersion = systemOsVersion;
    }

    public String getSystemModel() {
        return systemModel;
    }

    public void setSystemModel(String systemModel) {
        this.systemModel = systemModel;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getCrashlog() {
        return crashlog;
    }

    public void setCrashlog(String crashlog) {
        this.crashlog = crashlog;
    }

    public List<ItemClickEventBean> getEvents() {
        return events;
    }

    public void setEvents(List<ItemClickEventBean> events) {
        this.events = events;
    }
}
