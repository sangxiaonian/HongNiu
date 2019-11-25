package com.hongniu.baselibrary.entity;

/**
 * 作者： ${PING} on 2018/8/15.
 * 个人信息
 */
public class LoginPersonInfor {


     /**
     * true	string	公司名称
     */
    private String company;
    /**
     * true	string	联系人
     */
    private String contact;
    /**
     * true	string	邮箱
     */
    private String email;
    /**
     * true	string	公司电话
     */
    private String phone;
    /**
     * true	string	省名称
     */
    private String province;
    /**
     * 省份ID
     */
    private String provinceId;

    /**
     * true	string	市名称
     */
    private String city;
    private String cityId;
    /**
     * true	string	区名称
     */
    private String district;
    private String districtId;
    /**
     * true	string	详细地址
     */
    private String address;
    /**
     * true	string	手机号
     */
    private String mobile;
    /**
     * true	string	公司组织机构代码
     */
    private String organization;
    /**
     * true	string	联系人身份证
     */
    private String idnumber;

    private int is_driver_status;//0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败

   private String faceDLImageUrl	;//true	string	正面驾照照片url
   private String backDLImageUrl	;//true	string	背面驾照照片url
   private String faceVImageUrl	;//true	string	正面行驶证照片url
   private String backVImageUrl	;//true	string	背面行驶证照片url
    private int isDriver;//	true	string	是否要认证司机 0否 1是(认证司机时驾驶证和行驶证照片必传 传入文件类型参考10.2接口)


    private String nickname;
    private String gender;
    private String logoPath;
    private String logo;
    private String remarks;
    private boolean subAccStatus;//开户状态

    public int getIsDriver() {
        return isDriver;
    }

    public void setIsDriver(int isDriver) {
        this.isDriver = isDriver;
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

    public String getFaceVImageUrl() {
        return faceVImageUrl;
    }

    public void setFaceVImageUrl(String faceVImageUrl) {
        this.faceVImageUrl = faceVImageUrl;
    }

    public String getBackVImageUrl() {
        return backVImageUrl;
    }

    public void setBackVImageUrl(String backVImageUrl) {
        this.backVImageUrl = backVImageUrl;
    }

    public boolean isSubAccStatus() {
        return subAccStatus;
    }

    public int getIs_driver_status() {
        return is_driver_status;
    }

    public void setIs_driver_status(int is_driver_status) {
        this.is_driver_status = is_driver_status;
    }

    public boolean getSubAccStatus() {
        return subAccStatus;
    }

    public void setSubAccStatus(boolean subAccStatus) {
        this.subAccStatus = subAccStatus;
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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
