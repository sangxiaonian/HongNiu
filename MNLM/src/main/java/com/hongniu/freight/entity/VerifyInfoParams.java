package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/1.
 */
public class VerifyInfoParams {
   private int userType;//	false	string	用户当前认证类型(1公司托运人 2个人托运人 3公司承运人 4个人承运人 5司机)

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
