package com.hongniu.baselibrary.entity;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class CarTypeBean {
    private String id;//true string 车辆类型id

    private String carType;//true string 车辆类型名称

    private String status;//是否可用。1 代表可用 0 不可用

    @Override
    public String toString() {
        return carType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
