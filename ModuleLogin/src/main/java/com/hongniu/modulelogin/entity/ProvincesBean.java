package com.hongniu.modulelogin.entity;

import java.util.List;

/**
 * 作者： ${桑小年} on 2018/8/19.
 * 努力，为梦长留
 */
public class ProvincesBean {

    public ProvincesBean(LoginAreaBean infor) {
        this.infor = infor;
    }

    /**
     * areaId : 1
     * areaCode : 110000
     * areaName : 北京市
     * level : 1
     * cityCode : 010
     * center : 116.407394,39.904211
     * parentId : -1
     */



    private LoginAreaBean infor;
    private List<CityBean> citys;

    public LoginAreaBean getInfor() {
        return infor;
    }

    public void setInfor(LoginAreaBean infor) {
        this.infor = infor;
    }

    public List<CityBean> getCitys() {
        return citys;
    }

    public void setCitys(List<CityBean> citys) {
        this.citys = citys;
    }


    @Override
    public String toString() {
        return infor==null?"":(infor.getAreaName()==null?"":infor.getAreaName());
    }
}
