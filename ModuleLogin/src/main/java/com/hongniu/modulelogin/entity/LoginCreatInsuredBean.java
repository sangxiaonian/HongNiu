package com.hongniu.modulelogin.entity;

/**
 * 作者： ${桑小年} on 2018/12/1.
 * 努力，为梦长留
 * 洗澡能被保险人信息
 */
public class LoginCreatInsuredBean {

    /**
     *  true	string	个人姓名（身份为个人有此项）
      */
    private String username	       ;
    /**
     *  true	string	个人身份证（身份为个人有此项）
      */
    private String idnumber	       ;
    /**
     *      true	string	企业名称（身份为企业有此项）
      */
    private String companyName	   ;
    /**
     * 	true	string	企业社会信任码或纳税人识别号（身份为企业有此项）
      */
    private String companyCreditCode;
    /**
     *  false	string	证件照url相对路径(企业非必传)
      */
    private String imageUrl	       ;
    /**
     *  true	string	邮箱
      */
    private String email	           ;
    /**
     *      true	int	省份Id
      */
    private String provinceId	   ;
    /**
     *  true	string	省名称
      */
    private String province	       ;
    /**
     *      true	int	市Id
      */
    private String cityId	       ;
    /**
     *  true	string	市名称
      */
    private String city	           ;
    /**
     *      true	int	区Id
      */
    private String districtId	   ;
    /**
     *  true	string	区名称
      */
    private String district	       ;
    /**
     *      true	string	详细地址
      */
    private String address	       ;
    /**
     *      true	int	投保人类型 1个人 2公司
      */
    private int insuredType	   ;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCompanyCreditCode() {
        return companyCreditCode;
    }

    public void setCompanyCreditCode(String companyCreditCode) {
        this.companyCreditCode = companyCreditCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
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

    public int getInsuredType() {
        return insuredType;
    }

    public void setInsuredType(int insuredType) {
        this.insuredType = insuredType;
    }
}
