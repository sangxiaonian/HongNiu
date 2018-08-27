package com.hongniu.baselibrary.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者： ${桑小年} on 2018/8/27.
 * 努力，为梦长留
 */
public class OrderCreatBean implements Parcelable{

    /**
     * policyUrl : http://testing.mypicc.com.cn/t/cc/nuieiu
     * downloadUrl : http://partnertest.mypicc.com.cn/ecooperation/policydownload/downloadurl.do?platfromcodes=CPI000458&policyNo=41D6D7BFE353F40D00A2ACE71DC68483FFB44904CD081E3389C8539545A075AF&insuredID=A0B37373E3B519199DFE22BD3003962077FEDFBBA071041E9F1FF905927DAA0A&flag=Y
     * policyNo : PYDG20183713Q000E02103
     */

    private String policyUrl;
    private String downloadUrl;
    private String policyNo;

    protected OrderCreatBean(Parcel in) {
        policyUrl = in.readString();
        downloadUrl = in.readString();
        policyNo = in.readString();
    }

    public OrderCreatBean() {
    }

    public static final Creator<OrderCreatBean> CREATOR = new Creator<OrderCreatBean>() {
        @Override
        public OrderCreatBean createFromParcel(Parcel in) {
            return new OrderCreatBean(in);
        }

        @Override
        public OrderCreatBean[] newArray(int size) {
            return new OrderCreatBean[size];
        }
    };

    public String getPolicyUrl() {
        return policyUrl;
    }

    public void setPolicyUrl(String policyUrl) {
        this.policyUrl = policyUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(policyUrl);
        dest.writeString(downloadUrl);
        dest.writeString(policyNo);
    }
}
