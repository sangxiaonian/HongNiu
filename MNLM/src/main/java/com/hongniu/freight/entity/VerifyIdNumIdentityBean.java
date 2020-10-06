package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/1.
 * 身份证认证信息(为空则无数据)
 */
public class VerifyIdNumIdentityBean {
    private String name;//	false	string	姓名
    private String email;//	false	string	邮箱
    private String idnumber;//	false	string	身份证
    private String idnumberFaceImageUrl;//	false	string	身份证正面照片url
    private String idnumberBackImageUrl;//	false	string	身份证背面照片url(相对路径)
    private String idnumIsIdentity;//	false	string	身份证是否认证 0否 1是
    private String faceDLImageUrl;//false	string	驶证正面照片url
    private String backDLImageUrl;//false	string	驶证反面照片url
    private String dLIsIdentity;//false	string	驾驶证是否认证 0否 1是

    public String getFaceDLImageUrl() {
        return faceDLImageUrl;
    }

    public void setFaceDLImageUrl(String faceDLImageUrl) {
        this.faceDLImageUrl = faceDLImageUrl;
    }

    public String getBackDLImageUrl() {
        return backDLImageUrl;
    }

    public void setBackDLImageUrl(String backDLImageUrl) {
        this.backDLImageUrl = backDLImageUrl;
    }

    public String getdLIsIdentity() {
        return dLIsIdentity;
    }

    public void setdLIsIdentity(String dLIsIdentity) {
        this.dLIsIdentity = dLIsIdentity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getIdnumberFaceImageUrl() {
        return idnumberFaceImageUrl;
    }

    public void setIdnumberFaceImageUrl(String idnumberFaceImageUrl) {
        this.idnumberFaceImageUrl = idnumberFaceImageUrl;
    }

    public String getIdnumberBackImageUrl() {
        return idnumberBackImageUrl;
    }

    public void setIdnumberBackImageUrl(String idnumberBackImageUrl) {
        this.idnumberBackImageUrl = idnumberBackImageUrl;
    }

    public String getIdnumIsIdentity() {
        return idnumIsIdentity;
    }

    public void setIdnumIsIdentity(String idnumIsIdentity) {
        this.idnumIsIdentity = idnumIsIdentity;
    }
}
