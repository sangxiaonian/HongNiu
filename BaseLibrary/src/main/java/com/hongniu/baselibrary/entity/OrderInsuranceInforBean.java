package com.hongniu.baselibrary.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者： ${桑小年} on 2018/12/1.
 * 努力，为梦长留
 * 被保险人信息
 */
public class OrderInsuranceInforBean implements Parcelable{


    /**
     * id : 78
     * username : 再次修改
     * idnumber : 410184199105254454
     * email : gminin@qq.com
     * provinceId : 2
     * province : 北京
     * cityId : 52
     * city : 北京
     * districtId : 500
     * district : 东城区
     * address : 刚洗完澡
     * companyName : null
     * companyCreditCode : null
     * companyOrganization : null
     * imageUrl : null
     * absoluteImageUrl : null
     * cardType : 01
     * insuredType : 1
     * isDefault : true
     * isDel : false
     * isMe : true
     * createTime : 2018-11-29 20:26:04
     * updateTime : 2018-11-29 21:45:44
     */

    private String id;
    private String username;
    private String idnumber;
    private String email;
    private String provinceId;
    private String province;
    private String cityId;
    private String city;
    private String districtId;
    private String district;
    private String address;
    private String companyName;
    private String companyCreditCode;
    private String companyOrganization;
    private String imageUrl;
    private String absoluteImageUrl;
    private String cardType;
    private int insuredType;
    private boolean isDefault;
    private boolean isDel;
    private boolean isMe;
    private String createTime;
    private String updateTime;

    protected OrderInsuranceInforBean(Parcel in) {
        id = in.readString();
        username = in.readString();
        idnumber = in.readString();
        email = in.readString();
        provinceId = in.readString();
        province = in.readString();
        cityId = in.readString();
        city = in.readString();
        districtId = in.readString();
        district = in.readString();
        address = in.readString();
        companyName = in.readString();
        companyCreditCode = in.readString();
        companyOrganization = in.readString();
        imageUrl = in.readString();
        absoluteImageUrl = in.readString();
        cardType = in.readString();
        insuredType = in.readInt();
        isDefault = in.readByte() != 0;
        isDel = in.readByte() != 0;
        isMe = in.readByte() != 0;
        createTime = in.readString();
        updateTime = in.readString();
    }

    public static final Creator<OrderInsuranceInforBean> CREATOR = new Creator<OrderInsuranceInforBean>() {
        @Override
        public OrderInsuranceInforBean createFromParcel(Parcel in) {
            return new OrderInsuranceInforBean(in);
        }

        @Override
        public OrderInsuranceInforBean[] newArray(int size) {
            return new OrderInsuranceInforBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCompanyOrganization() {
        return companyOrganization;
    }

    public void setCompanyOrganization(String companyOrganization) {
        this.companyOrganization = companyOrganization;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAbsoluteImageUrl() {
        return absoluteImageUrl;
    }

    public void setAbsoluteImageUrl(String absoluteImageUrl) {
        this.absoluteImageUrl = absoluteImageUrl;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getInsuredType() {
        return insuredType;
    }

    public void setInsuredType(int insuredType) {
        this.insuredType = insuredType;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean isIsDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }

    public boolean isIsMe() {
        return isMe;
    }

    public void setIsMe(boolean isMe) {
        this.isMe = isMe;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(idnumber);
        dest.writeString(email);
        dest.writeString(provinceId);
        dest.writeString(province);
        dest.writeString(cityId);
        dest.writeString(city);
        dest.writeString(districtId);
        dest.writeString(district);
        dest.writeString(address);
        dest.writeString(companyName);
        dest.writeString(companyCreditCode);
        dest.writeString(companyOrganization);
        dest.writeString(imageUrl);
        dest.writeString(absoluteImageUrl);
        dest.writeString(cardType);
        dest.writeInt(insuredType);
        dest.writeByte((byte) (isDefault ? 1 : 0));
        dest.writeByte((byte) (isDel ? 1 : 0));
        dest.writeByte((byte) (isMe ? 1 : 0));
        dest.writeString(createTime);
        dest.writeString(updateTime);
    }
}
