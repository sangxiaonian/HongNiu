package com.hongniu.baselibrary.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2020/10/31.
 */

public class CompanyInfoBean implements Parcelable {


    /**
     * androidPackage : com.hongniu.freight
     * apiUrl : http://47.104.130.110:9080/wlhyapi
     * companyName : 山东远恒供应链管理有限公司
     * iOSPackage : com.hongniu.huoyun
     * logoUrl : https://statichongniu.oss-cn-qingdao.aliyuncs.com/mnlmlogo.png
     * name :
     * subAppCode : mnlm
     */

    private String androidPackage;
    private String apiUrl;
    private String companyName;
    private String iOSPackage;
    private String logoUrl;
    private String name;
    private String subAppCode;
    private int status;//	true	string	1上线 2未上线
    private String contacts;//	true	string	联系人
    private String contactsphone;//	true	string	联系人电话


    protected CompanyInfoBean(Parcel in) {
        androidPackage = in.readString();
        apiUrl = in.readString();
        companyName = in.readString();
        iOSPackage = in.readString();
        logoUrl = in.readString();
        name = in.readString();
        subAppCode = in.readString();
        status = in.readInt();
        contacts = in.readString();
        contactsphone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(androidPackage);
        dest.writeString(apiUrl);
        dest.writeString(companyName);
        dest.writeString(iOSPackage);
        dest.writeString(logoUrl);
        dest.writeString(name);
        dest.writeString(subAppCode);
        dest.writeInt(status);
        dest.writeString(contacts);
        dest.writeString(contactsphone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CompanyInfoBean> CREATOR = new Creator<CompanyInfoBean>() {
        @Override
        public CompanyInfoBean createFromParcel(Parcel in) {
            return new CompanyInfoBean(in);
        }

        @Override
        public CompanyInfoBean[] newArray(int size) {
            return new CompanyInfoBean[size];
        }
    };

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsphone() {
        return contactsphone;
    }

    public void setContactsphone(String contactsphone) {
        this.contactsphone = contactsphone;
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
