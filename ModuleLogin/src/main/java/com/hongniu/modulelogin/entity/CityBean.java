package com.hongniu.modulelogin.entity;

import java.util.List;

/**
 * 作者： ${桑小年} on 2018/8/19.
 * 努力，为梦长留
 */
public class CityBean {


    private LoginAreaBean infor;
    List<DistrictBean> districts;

    public CityBean(LoginAreaBean infor) {
        this.infor = infor;
    }

    public List<DistrictBean> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictBean> districts) {
        this.districts = districts;
    }

    public LoginAreaBean getInfor() {
        return infor;
    }

    public void setInfor(LoginAreaBean infor) {
        this.infor = infor;
    }
    @Override
    public String toString() {
        return infor==null?"":(infor.getAreaName()==null?"":infor.getAreaName());
    }
}
