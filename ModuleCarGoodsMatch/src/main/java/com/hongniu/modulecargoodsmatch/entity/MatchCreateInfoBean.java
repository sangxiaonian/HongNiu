package com.hongniu.modulecargoodsmatch.entity;

/**
 * 作者：  on 2019/11/3.
 */
public class MatchCreateInfoBean {
   private String carGoodsOrderId	;//true	number	车货匹配订单id
   private String estimateFare	;//true	number	预估运费
   private String distance	;//true	number	距离
   private String startPrice	;//true	number	起步价
   private String exceedMileagePrice	;//true	number	超里程费

    public String getCarGoodsOrderId() {
        return carGoodsOrderId;
    }

    public void setCarGoodsOrderId(String carGoodsOrderId) {
        this.carGoodsOrderId = carGoodsOrderId;
    }

    public String getEstimateFare() {
        return estimateFare;
    }

    public void setEstimateFare(String estimateFare) {
        this.estimateFare = estimateFare;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getExceedMileagePrice() {
        return exceedMileagePrice;
    }

    public void setExceedMileagePrice(String exceedMileagePrice) {
        this.exceedMileagePrice = exceedMileagePrice;
    }
}
