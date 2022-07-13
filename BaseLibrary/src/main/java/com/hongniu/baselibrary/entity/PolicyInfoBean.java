package com.hongniu.baselibrary.entity;

import java.util.List;

/**
 * @data 2022/7/13$
 * @Author PING
 * @Description
 */
public class PolicyInfoBean {


    private List<PolicyInfoItemBean> loadingMethods;
    private List<String> policyType;
    private List<PolicyInfoItemBean> goodsTypes;
    private List<PolicyInfoItemBean> packingMethods;
    private List<PolicyInfoItemBean> transportMethods;

    public List<PolicyInfoItemBean> getLoadingMethods() {
        return loadingMethods;
    }

    public void setLoadingMethods(List<PolicyInfoItemBean> loadingMethods) {
        this.loadingMethods = loadingMethods;
    }

    public List<String> getPolicyType() {
        return policyType;
    }

    public void setPolicyType(List<String> policyType) {
        this.policyType = policyType;
    }

    public List<PolicyInfoItemBean> getGoodsTypes() {
        return goodsTypes;
    }

    public void setGoodsTypes(List<PolicyInfoItemBean> goodsTypes) {
        this.goodsTypes = goodsTypes;
    }

    public List<PolicyInfoItemBean> getPackingMethods() {
        return packingMethods;
    }

    public void setPackingMethods(List<PolicyInfoItemBean> packingMethods) {
        this.packingMethods = packingMethods;
    }

    public List<PolicyInfoItemBean> getTransportMethods() {
        return transportMethods;
    }

    public void setTransportMethods(List<PolicyInfoItemBean> transportMethods) {
        this.transportMethods = transportMethods;
    }

    public static class PolicyInfoItemBean {
        private String companyId;
        private String displayName;
        private String id;

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
