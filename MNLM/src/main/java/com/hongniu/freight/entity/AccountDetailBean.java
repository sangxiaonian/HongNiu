package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/5.
 */
public class AccountDetailBean {
    private String accountCode;//	true	string	会员代码
    private String userId;//	true	long	用户ID
    private String userName;//	false	string	用户姓名
    private String userMobile;//	true	string	用户手机号码
    private String totalBalance;//	true	double	账户总余额=（可用余额+锁定金额+在途金额）
    private double availableBalance;//	true	double	账户可用余额（实际可用余额）
    private double lockAmount;//	true	double	账户锁定金额
    private double transitAmount;//	true	double	账户在途金额
    private int state;//	true	int	1正常2锁定
    private boolean setPassWord;//	true	string	是否设置支付密码(true:设置过支付密码，false：未设置支付密码）
    private double totalIntegral;//	true	double	总牛贝
    private double availableIntegral;//	true	double	牛贝总收益
    private double lockIntegral;//	true	double	牛贝昨日收益
    private double tobeCreditedIntegral;//	true	double
    private double tobeCreditedBalance;//	true	double	待入账金额
    private boolean companyPayPermission;//	true	boolean	是否为企业用户(true:有  false:无)
    private double companyAvailableBalance;//	true	double	企业支付可用余额
    private int type;//	true	int	1:个人账户(不能企业支付)  2:(管理员 申请支付) 不需要输入企业支付密码  3:(超级管理员 直接支付) 需要输入企业支付密码
    private boolean subAccStatus;//	true	boolean	开户状态 true:已开子账户 false:未开

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

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public double getLockAmount() {
        return lockAmount;
    }

    public void setLockAmount(double lockAmount) {
        this.lockAmount = lockAmount;
    }

    public double getTransitAmount() {
        return transitAmount;
    }

    public void setTransitAmount(double transitAmount) {
        this.transitAmount = transitAmount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isSetPassWord() {
        return setPassWord;
    }

    public void setSetPassWord(boolean setPassWord) {
        this.setPassWord = setPassWord;
    }

    public double getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(double totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public double getAvailableIntegral() {
        return availableIntegral;
    }

    public void setAvailableIntegral(double availableIntegral) {
        this.availableIntegral = availableIntegral;
    }

    public double getLockIntegral() {
        return lockIntegral;
    }

    public void setLockIntegral(double lockIntegral) {
        this.lockIntegral = lockIntegral;
    }

    public double getTobeCreditedIntegral() {
        return tobeCreditedIntegral;
    }

    public void setTobeCreditedIntegral(double tobeCreditedIntegral) {
        this.tobeCreditedIntegral = tobeCreditedIntegral;
    }

    public double getTobeCreditedBalance() {
        return tobeCreditedBalance;
    }

    public void setTobeCreditedBalance(double tobeCreditedBalance) {
        this.tobeCreditedBalance = tobeCreditedBalance;
    }

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
}
