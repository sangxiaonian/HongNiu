package com.hongniu.freight.entity;

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
    private List<LocationBean> list;
    private List<AppPathStationBean> logisticsList;

    public List<AppPathStationBean> getLogisticsList() {
        return logisticsList;
    }

    public void setLogisticsList(List<AppPathStationBean> logisticsList) {
        this.logisticsList = logisticsList;
    }

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

    public List<LocationBean> getList() {
        return list;
    }

    public void setList(List<LocationBean> list) {
        this.list = list;
    }
}
