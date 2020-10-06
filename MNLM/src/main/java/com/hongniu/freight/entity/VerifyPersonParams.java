package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/1.
 */
public class VerifyPersonParams {
    private String name;//	false	string	姓名
    private String email;//	false	string	邮箱
    private String idnumber;//	false	string	身份证
    private String inputQcNum;//	true	从业资格证编号
    private String inputRtpNum;//	true	道路运输许可证编号
    private String affiliationAgreementImageUrl;//	false	string	个人承运人挂靠协议图片url(相对路径)
    private String roadTransportPermitImageUrl;//	false	string	道路运输许可证照片url(相对路径)
    private String qualificationCertificateImageUrl;//	false	司机从业资格证图片url(相对路径)
    private String faceDLImageUrl;//	false	驶证正面照片url(相对路径)
    private String backDLImageUrl;//	false	驶证反面照片url(相对路径)
    private String idnumberFaceImageUrl;//	false	string	身份证正面照片url(相对路径) 传入文件类型参考11.1接口)
    private String idnumberBackImageUrl;//	false	string	身份证背面照片url(相对路径)

    public String getIdnumberBackImageUrl() {
        return idnumberBackImageUrl;
    }

    public void setIdnumberBackImageUrl(String idnumberBackImageUrl) {
        this.idnumberBackImageUrl = idnumberBackImageUrl;
    }

    public String getInputRtpNum() {
        return inputRtpNum;
    }

    public void setInputRtpNum(String inputRtpNum) {
        this.inputRtpNum = inputRtpNum;
    }

    public String getInputQcNum() {
        return inputQcNum;
    }

    public void setInputQcNum(String inputQcNum) {
        this.inputQcNum = inputQcNum;
    }

    public String getIdnumberFaceImageUrl() {
        return idnumberFaceImageUrl;
    }

    public void setIdnumberFaceImageUrl(String idnumberFaceImageUrl) {
        this.idnumberFaceImageUrl = idnumberFaceImageUrl;
    }

    public String getQualificationCertificateImageUrl() {
        return qualificationCertificateImageUrl;
    }

    public void setQualificationCertificateImageUrl(String qualificationCertificateImageUrl) {
        this.qualificationCertificateImageUrl = qualificationCertificateImageUrl;
    }

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

    public String getAffiliationAgreementImageUrl() {
        return affiliationAgreementImageUrl;
    }

    public void setAffiliationAgreementImageUrl(String affiliationAgreementImageUrl) {
        this.affiliationAgreementImageUrl = affiliationAgreementImageUrl;
    }

    public String getRoadTransportPermitImageUrl() {
        return roadTransportPermitImageUrl;
    }

    public void setRoadTransportPermitImageUrl(String roadTransportPermitImageUrl) {
        this.roadTransportPermitImageUrl = roadTransportPermitImageUrl;
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
}
