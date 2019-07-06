package com.hongniu.modulecargoodsmatch.entity;

import java.util.List;

/**
 * 作者： ${PING} on 2019/7/6.
 * 车货匹配预加载的车辆信息
 */
public class MatchCarPreInforBean {

    private List<String> carLength;
    private List<String> carType;

    public List<String> getCarLength() {
        return carLength;
    }

    public void setCarLength(List<String> carLength) {
        this.carLength = carLength;
    }

    public List<String> getCarType() {
        return carType;
    }

    public void setCarType(List<String> carType) {
        this.carType = carType;
    }
}
