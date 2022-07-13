package com.hongniu.baselibrary.entity;

/**
 * @data 2022/7/13$
 * @Author PING
 * @Description
 */
public class PolicyCaculParam {
    private String id;//true	number	订单id
    private String goodPrice;//true	number	货物价值，单位元
    private String goodTypes;//true	string	货物类型
    private String loadingMethods;//true	string	装载方式
    private String transportMethods;//true	string	运输方式
    private String packingMethods;//true	string	打包方式
    private String policyType;//true	string	险种类型
    private String policyPrice;//true	string	险种类型

    public String getPolicyPrice() {
        return policyPrice;
    }

    public void setPolicyPrice(String policyPrice) {
        this.policyPrice = policyPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodTypes() {
        return goodTypes;
    }

    public void setGoodTypes(String goodTypes) {
        this.goodTypes = goodTypes;
    }

    public String getLoadingMethods() {
        return loadingMethods;
    }

    public void setLoadingMethods(String loadingMethods) {
        this.loadingMethods = loadingMethods;
    }

    public String getTransportMethods() {
        return transportMethods;
    }

    public void setTransportMethods(String transportMethods) {
        this.transportMethods = transportMethods;
    }

    public String getPackingMethods() {
        return packingMethods;
    }

    public void setPackingMethods(String packingMethods) {
        this.packingMethods = packingMethods;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }
}
