package com.hongniu.modulelogin.entity;

import java.util.List; /**
 * 作者： ${桑小年} on 2018/8/19.
 * 努力，为梦长留
 */
public class AreaBeans {
    private final List<List<List<DistrictBean>>> district;
    private final List<List<CityBean>> cityBean;
    private final List<ProvincesBean> provinces;

    public AreaBeans(List<ProvincesBean> provinces, List<List<CityBean>> cityBean, List<List<List<DistrictBean>>> district) {
        this.district=district;
        this.cityBean=cityBean;
        this.provinces=provinces;
    }

    public List<List<List<DistrictBean>>> getDistrict() {
        return district;
    }

    public List<List<CityBean>> getCityBean() {
        return cityBean;
    }

    public List<ProvincesBean> getProvinces() {
        return provinces;
    }
}
