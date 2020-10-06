package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/1.
 * 公司承运人数据
 */
public class VerifyCompanyParams {
    private String companyName;//	false	string	企业名称
    private String companyAddress;//	false	string	企业地址
    private String contactName;//	false	string	联系人姓名
    private String contactEmail;//	true	string	联系人邮箱
    private String contactMobile;//	false	string	联系人手机号
    private String businessLicenseImageUrl;//	false	string	营业执照照片url(相对路径) 传入文件类型参考11.1接口)
    private String roadTransportPermitImageUrl;//	false	string	道路运输许可证照片url
    private String idnumber;//	false	string	身份证
    private String name;//	false	string	身份证
    private String idnumberFaceImageUrl;//	false	string	身份证正面照片url(相对路径) 传入文件类型参考11.1接口)
    private String idnumberBackImageUrl;//	false	string	身份证背面照片url(相对路径)
    private String inputRtpNum;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getBusinessLicenseImageUrl() {
        return businessLicenseImageUrl;
    }

    public void setBusinessLicenseImageUrl(String businessLicenseImageUrl) {
        this.businessLicenseImageUrl = businessLicenseImageUrl;
    }

    public String getRoadTransportPermitImageUrl() {
        return roadTransportPermitImageUrl;
    }

    public void setRoadTransportPermitImageUrl(String roadTransportPermitImageUrl) {
        this.roadTransportPermitImageUrl = roadTransportPermitImageUrl;
    }

    public void setInputRtpNum(String inputRtpNum) {
        this.inputRtpNum = inputRtpNum;
    }

    public String getInputRtpNum() {
        return inputRtpNum;
    }
}
