package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/2/27.
 */
public class IdentityParams {
    int type;//用户当前认证类型(1公司托运人 2个人托运人 3公司承运人 4个人承运人 5司机)

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
