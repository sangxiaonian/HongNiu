package com.hongniu.freight.config;

/**
 * 作者：  on 2020/2/7.
 * 角色
 */
public enum RoleOrder {
    //    用户当前认证类型(1公司托运人 2个人托运人 3 公司承运人 4 个人承运人 5 司机)

    UNKNOW(-1, "未知"),//平台
    SHIPPER(1, "托运人"),//托运人
    CARRIER (2, "承运人"),//承运人
    DRIVER(3, "司机"),//司机
    PLATFORM(4, "平台"),//平台
    ;
    private final String name;
    private final int type;

    RoleOrder(int type, String name) {
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
