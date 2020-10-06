package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/2/23.
 * 个人资料
 */
public class PersonInfor  {
    private String id;//true	主键
    private String company;//true	公司名称
    private String contact;//true	联系人
    private String email;//true	邮箱
    private String phone;//true	公司电话
    private String province;//true	省名称
    private String city;//true	市名称
    private String district;//true	区名称
    private String address;//true	详细地址
    private String mobile;//true	手机号
    private String organization;//true	公司组织机构代码
    private String idnumber;//true	联系人身份证
    private String rongToken;//true	融云token
    private String logo;//true	相对路径
    private String logoPath;//true	绝对路径用于显示
    private boolean subAccStatus;//true	开户状态 true:已开子账户 false:未开
    private String totalBalance;//true	用户账户总余额
    protected int isStaff;//true	平台员工身份 1:是 0:否
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
    protected int isRealname	;//true	int	是否实名 0未实名 1已实名;//true

    public int getIsRealname() {
        return isRealname;
    }

    public void setIsRealname(int isRealname) {
        this.isRealname = isRealname;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(int isStaff) {
        this.isStaff = isStaff;
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

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getRongToken() {
        return rongToken;
    }

    public void setRongToken(String rongToken) {
        this.rongToken = rongToken;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public boolean isSubAccStatus() {
        return subAccStatus;
    }

    public void setSubAccStatus(boolean subAccStatus) {
        this.subAccStatus = subAccStatus;
    }

}
