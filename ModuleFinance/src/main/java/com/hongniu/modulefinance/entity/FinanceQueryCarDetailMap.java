package com.hongniu.modulefinance.entity;

import com.google.gson.annotations.SerializedName;
import com.hongniu.baselibrary.entity.OrderDetailBean;

import java.util.List;
import java.util.Map;

/**
 * 作者： ${桑小年} on 2018/10/28.
 * 努力，为梦长留
 */
public class FinanceQueryCarDetailMap {


    private Map<String, List<OrderDetailBean>> carDetails;

    public Map<String, List<OrderDetailBean>> getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(Map<String, List<OrderDetailBean>> carDetails) {
        this.carDetails = carDetails;
    }
}
