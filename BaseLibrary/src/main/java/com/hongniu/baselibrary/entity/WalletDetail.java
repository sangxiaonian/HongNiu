package com.hongniu.baselibrary.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者： ${PING} on 2018/10/25.
 * 钱包首页账户详情
 */
public class WalletDetail implements Parcelable {
    /**
     * true	string	会员代码
     */
    private String accountCode;
    /**
     * true	long	用户ID
     */
    private String userId;
    /**
     * false	string	用户姓名
     */
    private String userName;
    /**
     * true	string	用户手机号码
     */
    private String userMobile;
    /**
     * true	double	账户总余额=（可用余额+锁定金额+在途金额）
     */
    private String totalBalance;
    /**
     * true	double	账户可用余额（实际可用余额）
     */
    private String availableBalance;
    /**
     * true	double	账户锁定金额
     */
    private String lockAmount;
    /**
     * true	double	账户在途金额
     */
    private String transitAmount;
    /**
     * true	int	1正常2锁定
     */
    private int state;
    /**
     * true	string	是否设置支付密码(true:设置过支付密码，false：未设置支付密码）
     */
    private boolean setPassWord;
    /**
     * true	double	总牛贝=可用+锁定
     */
    private String totalIntegral;
    /**
     * true	double	可用牛贝
     */
    private String availableIntegral = "0";
    /**
     * true	double	锁定牛贝
     */
    private String lockIntegral;
    /**
     * true	double	待入账牛贝
     */
    private String tobeCreditedIntegral;
    /**
     * true	double	待入账金额
     */
    private String tobeCreditedBalance;
    /**
     * 是否有企业支付权限(true:有  false:无)
     */
    private boolean companyPayPermission;
    /**
     * 企业支付可用余额
     */
    private double companyAvailableBalance;
    /**
     * 1:不可用企业支付  2:企业支付需要审核  3:企业支付不需要审核
     */
    private int type;
    /**
     * 1开户状态 true:已开子账户 false:未开
     */
    private boolean subAccStatus;


    protected WalletDetail(Parcel in) {
        accountCode = in.readString();
        userId = in.readString();
        userName = in.readString();
        userMobile = in.readString();
        totalBalance = in.readString();
        availableBalance = in.readString();
        lockAmount = in.readString();
        transitAmount = in.readString();
        state = in.readInt();
        setPassWord = in.readByte() != 0;
        totalIntegral = in.readString();
        availableIntegral = in.readString();
        lockIntegral = in.readString();
        tobeCreditedIntegral = in.readString();
        tobeCreditedBalance = in.readString();
        companyPayPermission = in.readByte() != 0;
        companyAvailableBalance = in.readDouble();
        type = in.readInt();
        subAccStatus = in.readByte() != 0;
    }

    public static final Creator<WalletDetail> CREATOR = new Creator<WalletDetail>() {
        @Override
        public WalletDetail createFromParcel(Parcel in) {
            return new WalletDetail(in);
        }

        @Override
        public WalletDetail[] newArray(int size) {
            return new WalletDetail[size];
        }
    };

    public boolean isCompanyPayPermission() {
        return companyPayPermission;
    }

    public void setCompanyPayPermission(boolean companyPayPermission) {
        this.companyPayPermission = companyPayPermission;
    }

    public double getCompanyAvailableBalance() {
        return companyAvailableBalance;
    }

    public void setCompanyAvailableBalance(double companyAvailableBalance) {
        this.companyAvailableBalance = companyAvailableBalance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSubAccStatus() {
        return subAccStatus;
    }

    public void setSubAccStatus(boolean subAccStatus) {
        this.subAccStatus = subAccStatus;
    }


    public String getAccountCode() {
        return accountCode;
    }

    public boolean isSetPassWord() {
        return setPassWord;
    }

    public void setSetPassWord(boolean setPassWord) {
        this.setPassWord = setPassWord;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getLockAmount() {
        return lockAmount;
    }

    public void setLockAmount(String lockAmount) {
        this.lockAmount = lockAmount;
    }

    public String getTransitAmount() {
        return transitAmount;
    }

    public void setTransitAmount(String transitAmount) {
        this.transitAmount = transitAmount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public String getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(String totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public String getAvailableIntegral() {
        return availableIntegral == null ? "0" : availableIntegral;
    }

    public void setAvailableIntegral(String availableIntegral) {
        this.availableIntegral = availableIntegral;
    }

    public String getLockIntegral() {
        return lockIntegral;
    }

    public void setLockIntegral(String lockIntegral) {
        this.lockIntegral = lockIntegral;
    }

    public String getTobeCreditedIntegral() {
        return tobeCreditedIntegral;
    }

    public void setTobeCreditedIntegral(String tobeCreditedIntegral) {
        this.tobeCreditedIntegral = tobeCreditedIntegral;
    }

    public String getTobeCreditedBalance() {
        return tobeCreditedBalance;
    }

    public void setTobeCreditedBalance(String tobeCreditedBalance) {
        this.tobeCreditedBalance = tobeCreditedBalance;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accountCode);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(userMobile);
        dest.writeString(totalBalance);
        dest.writeString(availableBalance);
        dest.writeString(lockAmount);
        dest.writeString(transitAmount);
        dest.writeInt(state);
        dest.writeByte((byte) (setPassWord ? 1 : 0));
        dest.writeString(totalIntegral);
        dest.writeString(availableIntegral);
        dest.writeString(lockIntegral);
        dest.writeString(tobeCreditedIntegral);
        dest.writeString(tobeCreditedBalance);
        dest.writeByte((byte) (companyPayPermission ? 1 : 0));
        dest.writeDouble(companyAvailableBalance);
        dest.writeInt(type);
        dest.writeByte((byte) (subAccStatus ? 1 : 0));
    }
}
