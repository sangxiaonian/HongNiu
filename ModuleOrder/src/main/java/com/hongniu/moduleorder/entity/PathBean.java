package com.hongniu.moduleorder.entity;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/28.
 */
public class PathBean {

    /**
     * startPlace : 上海大学(延长校区)
     * carNum : 沪1234567
     * list : []
     * endPlace : 上海大学宝山校区
     */

    private String startPlace;
    private String carNum;
    private String endPlace;
    private List<Object> list;

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}
