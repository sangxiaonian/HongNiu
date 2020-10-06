package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/3.
 */
public class CarTypeBean {
    private String id;//	true	string	车辆类型id
    private String carType;//	true	string	车辆类型名称
    private int status;//	true	string	是否可用。1 代表可用 0 不可用

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return carType;
    }
}
