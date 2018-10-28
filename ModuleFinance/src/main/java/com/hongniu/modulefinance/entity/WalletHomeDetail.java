package com.hongniu.modulefinance.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者： ${PING} on 2018/10/25.
 * 钱包首页账户详情
 */
public class WalletHomeDetail implements Parcelable {
    /**
     *      true	string	会员代码
      */
    private String accountCode	       ;
    /**
     *      true	long	用户ID
      */
    private String userId	           ;
    /**
     *  false	string	用户姓名
      */
    private String userName	           ;
    /**
     *      true	string	用户手机号码
      */
    private String userMobile	       ;
    /**
     *  true	double	账户总余额=（可用余额+锁定金额+在途金额）
      */
    private String totalBalance	       ;
    /**
     *  true	double	账户可用余额（实际可用余额）
      */
    private String availableBalance	   ;
    /**
     *      true	double	账户锁定金额
      */
    private String lockAmount	       ;
    /**
     *      true	double	账户在途金额
      */
    private String transitAmount	       ;
    /**
     *  true	int	1正常2锁定
      */
    private int state	               ;
    /**
     *          true	string	是否设置支付密码(true:设置过支付密码，false：未设置支付密码）
      */
    private String setPassWord	       ;
    /**
     *  true	double	总牛贝=可用+锁定
      */
    private String totalIntegral	       ;
    /**
     *  true	double	可用牛贝
      */
    private String availableIntegral="0"	   ;
    /**
     *    	true	double	锁定牛贝
      */
    private String lockIntegral         ;
    /**
     * 	true	double	待入账牛贝
      */
    private String tobeCreditedIntegral;
    /**
     * 	    true	double	待入账金额
      */
    private String tobeCreditedBalance;

    protected WalletHomeDetail(Parcel in) {
        accountCode = in.readString();
        userId = in.readString();
        userName = in.readString();
        userMobile = in.readString();
        totalBalance = in.readString();
        availableBalance = in.readString();
        lockAmount = in.readString();
        transitAmount = in.readString();
        state = in.readInt();
        setPassWord = in.readString();
        totalIntegral = in.readString();
        availableIntegral = in.readString();
        lockIntegral = in.readString();
        tobeCreditedIntegral = in.readString();
        tobeCreditedBalance = in.readString();
    }

    public static final Creator<WalletHomeDetail> CREATOR = new Creator<WalletHomeDetail>() {
        @Override
        public WalletHomeDetail createFromParcel(Parcel in) {
            return new WalletHomeDetail(in);
        }

        @Override
        public WalletHomeDetail[] newArray(int size) {
            return new WalletHomeDetail[size];
        }
    };

    public String getAccountCode() {
        return accountCode;
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

    public String getSetPassWord() {
        return setPassWord;
    }

    public void setSetPassWord(String setPassWord) {
        this.setPassWord = setPassWord;
    }

    public String getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(String totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public String getAvailableIntegral() {
        return availableIntegral==null?"0":availableIntegral;
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
        dest.writeString(accountCode);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(userMobile);
        dest.writeString(totalBalance);
        dest.writeString(availableBalance);
        dest.writeString(lockAmount);
        dest.writeString(transitAmount);
        dest.writeInt(state);
        dest.writeString(setPassWord);
        dest.writeString(totalIntegral);
        dest.writeString(availableIntegral);
        dest.writeString(lockIntegral);
        dest.writeString(tobeCreditedIntegral);
        dest.writeString(tobeCreditedBalance);
    }
}
