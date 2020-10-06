package com.hongniu.freight.entity;

/**
 * 作者：  on 2019/8/24.
 */
public class WayBillBean {


    /**
     * id : 3
     * waybillId : 46
     * icon : null
     * statusDesc : 已签收
     * createTime : 2019-08-14 16:31:31
     * description :  已签收，期待再次为您服务
     */

    private int id;
    private int waybillId;
    private String icon;
    private String statusDesc;
    private String createTime;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(int waybillId) {
        this.waybillId = waybillId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
