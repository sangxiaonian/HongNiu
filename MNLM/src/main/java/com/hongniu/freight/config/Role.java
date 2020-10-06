package com.hongniu.freight.config;

/**
 * 作者：  on 2020/2/7.
 * 角色
 */
public enum Role {
    //    用户当前认证类型(1公司托运人 2个人托运人 3 公司承运人 4 个人承运人 5 司机)

    UNKNOW(-1, "未知"),//平台
    PLATFORM(0, "平台员工"),//平台
    SHIPPER_COMPANY(1, "公司托运人"),//托运人
    SHIPPER_PERSONAL(2, "个人托运人"),//托运人
    CARRIER_COMPANY(3, "公司承运人"),//承运人
    CARRIER_PERSONAL(4, "个人承运人"),//承运人
    DRIVER(5, "司机"),//司机
    ;
    private final String name;
    private final int type;

    Role(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
