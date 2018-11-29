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



    private String nickname;
    private String gender;
    private String logoPath;
    private String remarks;


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
