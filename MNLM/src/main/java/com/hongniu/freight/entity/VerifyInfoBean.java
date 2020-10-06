package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/1.
 * 身份认证信息
 */
public class VerifyInfoBean {
    private String userId;//	true	string	用户id
    private VerifyCompanyParams companyShipper;//	false	string	公司托运人认证信息(json对象-为空不显示)
    private VerifyCompanyParams companyCarrier;//	false	string	公司承运人认证信息(json对象-为空不显示)
    private VerifyPersonParams personShipper;//	false	string	个人托运人认证信息(json对象-为空不显示)
    private VerifyPersonParams personCarrier;//	false	string	个人承运人认证信息(json对象-为空不显示)
    private VerifyPersonParams driver;//	false	string	司机认证信息(json对象-为空不显示)


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public VerifyCompanyParams getCompanyShipper() {
        return companyShipper;
    }

    public void setCompanyShipper(VerifyCompanyParams companyShipper) {
        this.companyShipper = companyShipper;
    }

    public VerifyCompanyParams getCompanyCarrier() {
        return companyCarrier;
    }

    public void setCompanyCarrier(VerifyCompanyParams companyCarrier) {
        this.companyCarrier = companyCarrier;
    }

    public VerifyPersonParams getPersonShipper() {
        return personShipper;
    }

    public void setPersonShipper(VerifyPersonParams personShipper) {
        this.personShipper = personShipper;
    }

    public VerifyPersonParams getPersonCarrier() {
        return personCarrier;
    }

    public void setPersonCarrier(VerifyPersonParams personCarrier) {
        this.personCarrier = personCarrier;
    }

    public VerifyPersonParams getDriver() {
        return driver;
    }

    public void setDriver(VerifyPersonParams driver) {
        this.driver = driver;
    }
}
