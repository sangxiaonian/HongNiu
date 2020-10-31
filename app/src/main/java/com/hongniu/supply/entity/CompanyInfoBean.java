package com.hongniu.supply.entity;

/**
 * 作者：  on 2020/10/31.
 */

public class CompanyInfoBean {


    /**
     * androidPackage : com.hongniu.freight
     * apiUrl : http://47.104.130.110:9080/wlhyapi
     * companyName : 山东远恒供应链管理有限公司
     * iOSPackage : com.hongniu.huoyun
     * logoUrl : https://statichongniu.oss-cn-qingdao.aliyuncs.com/mnlmlogo.png
     * name : 木牛流马
     * subAppCode : mnlm
     */

    private String androidPackage;
    private String apiUrl;
    private String companyName;
    private String iOSPackage;
    private String logoUrl;
    private String name;
    private String subAppCode;


    public String getAndroidPackage() {
        return androidPackage;
    }

    public void setAndroidPackage(String androidPackage) {
        this.androidPackage = androidPackage;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getiOSPackage() {
        return iOSPackage;
    }

    public void setiOSPackage(String iOSPackage) {
        this.iOSPackage = iOSPackage;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubAppCode() {
        return subAppCode;
    }

    public void setSubAppCode(String subAppCode) {
        this.subAppCode = subAppCode;
    }
}
