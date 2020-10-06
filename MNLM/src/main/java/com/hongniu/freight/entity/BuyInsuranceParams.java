package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/9.
 */
public class BuyInsuranceParams {

  private String  id;//	true	number	订单id
  private String  insuranceUserId;//	true	number	被保险人id
  private String  goodPrice;//	true	number	货物价值

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsuranceUserId() {
        return insuranceUserId;
    }

    public void setInsuranceUserId(String insuranceUserId) {
        this.insuranceUserId = insuranceUserId;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }
}
