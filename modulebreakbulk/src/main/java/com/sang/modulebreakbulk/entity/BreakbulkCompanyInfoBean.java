package com.sang.modulebreakbulk.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2019/9/22.
 */
public class BreakbulkCompanyInfoBean implements Parcelable {


    private String ref1;//true	string	logo地址
    private String companyname;//true	string	公司名称
    private String workaddress;//true	string	公司地址
    private String longitude;//true	double	地址经度坐标
    private String latitude;//true	double	地址纬度坐标
    private String contactPhone;//true	string	联系电话
    private String transportLine;//true	string	运输线路
    /**
     * id : 66
     * companycode : CHN000066
     * companyabbreviation : null
     * tel :
     * fax :
     * legalname : 马化腾
     * legalid : null
     * legalmobile : null
     * email : 745673935@qq.com
     * webaddress :
     * industry : 软件和信息技术服务业
     * industrycode : null
     * unifiedcode : 91440300708461136T
     * taxpayerid : null
     * organizationcode : null
     * companytype : null
     * operatingperiodstart : null
     * operatingperiodstend : null
     * companyaddress : null
     * businessscope : null
     * registertime : 1568192246000
     * fileoperating : images/license\2019/09\07156819224505967.jpg
     * filelegal : null
     * registway : 1
     * registoperator : null
     * companyauditstatus : 2
     * auditoperator : 超级管理员
     * auditotime : 1568192246000
     * contact : 方小浩
     * remark : null
     * ref2 : null
     * ref3 : null
     * ref4 : null
     * ref5 : null
     * ref6 : null
     * chargeDirections : 每公里一块钱
     * longitude : 113.95587
     * latitude : 22.53929
     * orderMark : 1
     * sortNum : 2
     */

    private String id;
    private String companycode;
    private String companyabbreviation;
    private String tel;
    private String fax;
    private String legalname;
    private String legalid;
    private String legalmobile;
    private String email;
    private String webaddress;
    private String industry;
    private String industrycode;
    private String unifiedcode;
    private String taxpayerid;
    private String organizationcode;
    private String companytype;
    private String operatingperiodstart;
    private String operatingperiodstend;
    private String companyaddress;
    private String businessscope;
    private long registertime;
    private String fileoperating;
    private String filelegal;
    private String registway;
    private String registoperator;
    private String companyauditstatus;
    private String auditoperator;
    private long auditotime;
    private String contact;
    private String remark;
    private String ref2;
    private String ref3;
    private String ref4;
    private String ref5;
    private String ref6;
    private String chargeDirections;
    private int orderMark;
    private int sortNum;
    private String userId;
    private String rongToken;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRongToken() {
        return rongToken;
    }

    public void setRongToken(String rongToken) {
        this.rongToken = rongToken;
    }

    public String getRef1() {
        return ref1;
    }

    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getWorkaddress() {
        return workaddress;
    }

    public void setWorkaddress(String workaddress) {
        this.workaddress = workaddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getTransportLine() {
        return transportLine;
    }

    public void setTransportLine(String transportLine) {
        this.transportLine = transportLine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getCompanyabbreviation() {
        return companyabbreviation;
    }

    public void setCompanyabbreviation(String companyabbreviation) {
        this.companyabbreviation = companyabbreviation;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLegalname() {
        return legalname;
    }

    public void setLegalname(String legalname) {
        this.legalname = legalname;
    }

    public String getLegalid() {
        return legalid;
    }

    public void setLegalid(String legalid) {
        this.legalid = legalid;
    }

    public String getLegalmobile() {
        return legalmobile;
    }

    public void setLegalmobile(String legalmobile) {
        this.legalmobile = legalmobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebaddress() {
        return webaddress;
    }

    public void setWebaddress(String webaddress) {
        this.webaddress = webaddress;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustrycode() {
        return industrycode;
    }

    public void setIndustrycode(String industrycode) {
        this.industrycode = industrycode;
    }

    public String getUnifiedcode() {
        return unifiedcode;
    }

    public void setUnifiedcode(String unifiedcode) {
        this.unifiedcode = unifiedcode;
    }

    public String getTaxpayerid() {
        return taxpayerid;
    }

    public void setTaxpayerid(String taxpayerid) {
        this.taxpayerid = taxpayerid;
    }

    public String getOrganizationcode() {
        return organizationcode;
    }

    public void setOrganizationcode(String organizationcode) {
        this.organizationcode = organizationcode;
    }

    public String getCompanytype() {
        return companytype;
    }

    public void setCompanytype(String companytype) {
        this.companytype = companytype;
    }

    public String getOperatingperiodstart() {
        return operatingperiodstart;
    }

    public void setOperatingperiodstart(String operatingperiodstart) {
        this.operatingperiodstart = operatingperiodstart;
    }

    public String getOperatingperiodstend() {
        return operatingperiodstend;
    }

    public void setOperatingperiodstend(String operatingperiodstend) {
        this.operatingperiodstend = operatingperiodstend;
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress;
    }

    public String getBusinessscope() {
        return businessscope;
    }

    public void setBusinessscope(String businessscope) {
        this.businessscope = businessscope;
    }

    public long getRegistertime() {
        return registertime;
    }

    public void setRegistertime(long registertime) {
        this.registertime = registertime;
    }

    public String getFileoperating() {
        return fileoperating;
    }

    public void setFileoperating(String fileoperating) {
        this.fileoperating = fileoperating;
    }

    public String getFilelegal() {
        return filelegal;
    }

    public void setFilelegal(String filelegal) {
        this.filelegal = filelegal;
    }

    public String getRegistway() {
        return registway;
    }

    public void setRegistway(String registway) {
        this.registway = registway;
    }

    public String getRegistoperator() {
        return registoperator;
    }

    public void setRegistoperator(String registoperator) {
        this.registoperator = registoperator;
    }

    public String getCompanyauditstatus() {
        return companyauditstatus;
    }

    public void setCompanyauditstatus(String companyauditstatus) {
        this.companyauditstatus = companyauditstatus;
    }

    public String getAuditoperator() {
        return auditoperator;
    }

    public void setAuditoperator(String auditoperator) {
        this.auditoperator = auditoperator;
    }

    public long getAuditotime() {
        return auditotime;
    }

    public void setAuditotime(long auditotime) {
        this.auditotime = auditotime;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRef2() {
        return ref2;
    }

    public void setRef2(String ref2) {
        this.ref2 = ref2;
    }

    public String getRef3() {
        return ref3;
    }

    public void setRef3(String ref3) {
        this.ref3 = ref3;
    }

    public String getRef4() {
        return ref4;
    }

    public void setRef4(String ref4) {
        this.ref4 = ref4;
    }

    public String getRef5() {
        return ref5;
    }

    public void setRef5(String ref5) {
        this.ref5 = ref5;
    }

    public String getRef6() {
        return ref6;
    }

    public void setRef6(String ref6) {
        this.ref6 = ref6;
    }

    public String getChargeDirections() {
        return chargeDirections;
    }

    public void setChargeDirections(String chargeDirections) {
        this.chargeDirections = chargeDirections;
    }


    public int getOrderMark() {
        return orderMark;
    }

    public void setOrderMark(int orderMark) {
        this.orderMark = orderMark;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }


    public BreakbulkCompanyInfoBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ref1);
        dest.writeString(this.companyname);
        dest.writeString(this.workaddress);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeString(this.contactPhone);
        dest.writeString(this.transportLine);
        dest.writeString(this.id);
        dest.writeString(this.companycode);
        dest.writeString(this.companyabbreviation);
        dest.writeString(this.tel);
        dest.writeString(this.fax);
        dest.writeString(this.legalname);
        dest.writeString(this.legalid);
        dest.writeString(this.legalmobile);
        dest.writeString(this.email);
        dest.writeString(this.webaddress);
        dest.writeString(this.industry);
        dest.writeString(this.industrycode);
        dest.writeString(this.unifiedcode);
        dest.writeString(this.taxpayerid);
        dest.writeString(this.organizationcode);
        dest.writeString(this.companytype);
        dest.writeString(this.operatingperiodstart);
        dest.writeString(this.operatingperiodstend);
        dest.writeString(this.companyaddress);
        dest.writeString(this.businessscope);
        dest.writeLong(this.registertime);
        dest.writeString(this.fileoperating);
        dest.writeString(this.filelegal);
        dest.writeString(this.registway);
        dest.writeString(this.registoperator);
        dest.writeString(this.companyauditstatus);
        dest.writeString(this.auditoperator);
        dest.writeLong(this.auditotime);
        dest.writeString(this.contact);
        dest.writeString(this.remark);
        dest.writeString(this.ref2);
        dest.writeString(this.ref3);
        dest.writeString(this.ref4);
        dest.writeString(this.ref5);
        dest.writeString(this.ref6);
        dest.writeString(this.chargeDirections);
        dest.writeInt(this.orderMark);
        dest.writeInt(this.sortNum);
        dest.writeString(this.userId);
        dest.writeString(this.rongToken);
    }

    protected BreakbulkCompanyInfoBean(Parcel in) {
        this.ref1 = in.readString();
        this.companyname = in.readString();
        this.workaddress = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.contactPhone = in.readString();
        this.transportLine = in.readString();
        this.id = in.readString();
        this.companycode = in.readString();
        this.companyabbreviation = in.readString();
        this.tel = in.readString();
        this.fax = in.readString();
        this.legalname = in.readString();
        this.legalid = in.readString();
        this.legalmobile = in.readString();
        this.email = in.readString();
        this.webaddress = in.readString();
        this.industry = in.readString();
        this.industrycode = in.readString();
        this.unifiedcode = in.readString();
        this.taxpayerid = in.readString();
        this.organizationcode = in.readString();
        this.companytype = in.readString();
        this.operatingperiodstart = in.readString();
        this.operatingperiodstend = in.readString();
        this.companyaddress = in.readString();
        this.businessscope = in.readString();
        this.registertime = in.readLong();
        this.fileoperating = in.readString();
        this.filelegal = in.readString();
        this.registway = in.readString();
        this.registoperator = in.readString();
        this.companyauditstatus = in.readString();
        this.auditoperator = in.readString();
        this.auditotime = in.readLong();
        this.contact = in.readString();
        this.remark = in.readString();
        this.ref2 = in.readString();
        this.ref3 = in.readString();
        this.ref4 = in.readString();
        this.ref5 = in.readString();
        this.ref6 = in.readString();
        this.chargeDirections = in.readString();
        this.orderMark = in.readInt();
        this.sortNum = in.readInt();
        this.userId = in.readString();
        this.rongToken = in.readString();
    }

    public static final Creator<BreakbulkCompanyInfoBean> CREATOR = new Creator<BreakbulkCompanyInfoBean>() {
        @Override
        public BreakbulkCompanyInfoBean createFromParcel(Parcel source) {
            return new BreakbulkCompanyInfoBean(source);
        }

        @Override
        public BreakbulkCompanyInfoBean[] newArray(int size) {
            return new BreakbulkCompanyInfoBean[size];
        }
    };
}
