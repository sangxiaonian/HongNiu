package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/2/23.
 */
public class LoginInfo {

    private boolean isStaff;//true	平台员工身份 1:是 0:否
    private String id;//true	用户id
    private String mobile;//true	用户手机号
    private String openid;//false	微信的openid
    private String token;//false	本次登录的token，有有效期。
    private int type;//false	用户类型。无实际作用。
    private int state;//false	状态
    private String logo;//false	微信登录时用户图像的地址
    private String contact;//false	用户的联系方式
    private String nickname;//false	用户的微信昵称
    private int gender;//false	性别,1男,2女,0未知
    private String remark;//false	备注
    private String rongToken;//true	融云token
    private String logoPath;//true	绝对路径用于显示
    protected int isDriver;//true	司机身份1:是 0:否
    protected int isPersonCarrier;//true	个人承运人身份 1:是 0:否
    protected int isCompanyCarrier;//true	公司承运人身份 1:是 0:否
    protected int isCompanyShipper;//true	公司托运人身份 1:是 0:否
    protected int isPersonShipper;//true	个人托运人身份 1:是 0:否
    protected int isStaffStatus;//true	平台员工身份状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
    protected int isDriverStatus;//true	认证司机状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
    protected int isPersonShipperStatus;//true	认证个人托运人状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
    protected int isPersonCarrierStatus;//true	认证个人承运人状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
    protected int isCompanyShipperStatus;//true	认证公司托运人状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
    protected int isCompanyCarrierStatus;//true	认证公司承运人状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
    protected int isRealname;//true	int	是否实名 0未实名 1已实名

    public int getIsRealname() {
        return isRealname;
    }

    public void setIsRealname(int isRealname) {
        this.isRealname = isRealname;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    public int getIsDriver() {
        return isDriver;
    }

    public void setIsDriver(int isDriver) {
        this.isDriver = isDriver;
    }

    public int getIsPersonCarrier() {
        return isPersonCarrier;
    }

    public void setIsPersonCarrier(int isPersonCarrier) {
        this.isPersonCarrier = isPersonCarrier;
    }

    public int getIsCompanyCarrier() {
        return isCompanyCarrier;
    }

    public void setIsCompanyCarrier(int isCompanyCarrier) {
        this.isCompanyCarrier = isCompanyCarrier;
    }

    public int getIsCompanyShipper() {
        return isCompanyShipper;
    }

    public void setIsCompanyShipper(int isCompanyShipper) {
        this.isCompanyShipper = isCompanyShipper;
    }

    public int getIsPersonShipper() {
        return isPersonShipper;
    }

    public void setIsPersonShipper(int isPersonShipper) {
        this.isPersonShipper = isPersonShipper;
    }

    public int getIsStaffStatus() {
        return isStaffStatus;
    }

    public void setIsStaffStatus(int isStaffStatus) {
        this.isStaffStatus = isStaffStatus;
    }

    public int getIsDriverStatus() {
        return isDriverStatus;
    }

    public void setIsDriverStatus(int isDriverStatus) {
        this.isDriverStatus = isDriverStatus;
    }

    public int getIsPersonShipperStatus() {
        return isPersonShipperStatus;
    }

    public void setIsPersonShipperStatus(int isPersonShipperStatus) {
        this.isPersonShipperStatus = isPersonShipperStatus;
    }

    public int getIsPersonCarrierStatus() {
        return isPersonCarrierStatus;
    }

    public void setIsPersonCarrierStatus(int isPersonCarrierStatus) {
        this.isPersonCarrierStatus = isPersonCarrierStatus;
    }

    public int getIsCompanyShipperStatus() {
        return isCompanyShipperStatus;
    }

    public void setIsCompanyShipperStatus(int isCompanyShipperStatus) {
        this.isCompanyShipperStatus = isCompanyShipperStatus;
    }

    public int getIsCompanyCarrierStatus() {
        return isCompanyCarrierStatus;
    }

    public void setIsCompanyCarrierStatus(int isCompanyCarrierStatus) {
        this.isCompanyCarrierStatus = isCompanyCarrierStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRongToken() {
        return rongToken;
    }

    public void setRongToken(String rongToken) {
        this.rongToken = rongToken;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }


}
