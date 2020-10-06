package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/1.
 * 道路运输许可证认证信息(为空则无数据)
 */
public class VerifyRtpIdentityBean {
  private String  roadTransportPermitImageUrl;//	false	string	道路运输许可证照片url(相对路径)
  private String  rTPIsIdentity;//	false	string	道路运输许可证是否认证 0否 1是

   private String  affiliationAgreementImageUrl;//	false	string	个人承运人挂靠协议图片url(相对路径)
   private String  aAIsIdentity;//	false	string	个人承运人挂靠协议是否认证 0否 1是
    private String  qualificationCertificateImageUrl;//	false	string	司机从业资格证图片url
    private String  qCIsIdentity;//	false	string	司机从业资格证是否认证 0否 1是


    public String getAffiliationAgreementImageUrl() {
        return affiliationAgreementImageUrl;
    }

    public void setAffiliationAgreementImageUrl(String affiliationAgreementImageUrl) {
        this.affiliationAgreementImageUrl = affiliationAgreementImageUrl;
    }

    public String getaAIsIdentity() {
        return aAIsIdentity;
    }

    public void setaAIsIdentity(String aAIsIdentity) {
        this.aAIsIdentity = aAIsIdentity;
    }

    public String getQualificationCertificateImageUrl() {
        return qualificationCertificateImageUrl;
    }

    public void setQualificationCertificateImageUrl(String qualificationCertificateImageUrl) {
        this.qualificationCertificateImageUrl = qualificationCertificateImageUrl;
    }

    public String getqCIsIdentity() {
        return qCIsIdentity;
    }

    public void setqCIsIdentity(String qCIsIdentity) {
        this.qCIsIdentity = qCIsIdentity;
    }

    public String getRoadTransportPermitImageUrl() {
        return roadTransportPermitImageUrl;
    }

    public void setRoadTransportPermitImageUrl(String roadTransportPermitImageUrl) {
        this.roadTransportPermitImageUrl = roadTransportPermitImageUrl;
    }

    public String getrTPIsIdentity() {
        return rTPIsIdentity;
    }

    public void setrTPIsIdentity(String rTPIsIdentity) {
        this.rTPIsIdentity = rTPIsIdentity;
    }
}
