package com.hongniu.freight.ui;

/**
 * 作者：  on 2020/3/10.
 * 查询保费需要的接口
 */
public class QueryInsurancePriceParams {
  private String  goodPrice;//	false	number	货物价格
  private double  startPlaceLon;//	true	number	开始地经度
  private double  startPlaceLat;//	true	number	开始地纬度
  private double  destinationLon;//	true	number	目的地经度
  private double  destinationLat;//	true	number	目的地纬度

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public double getStartPlaceLon() {
        return startPlaceLon;
    }

    public void setStartPlaceLon(double startPlaceLon) {
        this.startPlaceLon = startPlaceLon;
    }

    public double getStartPlaceLat() {
        return startPlaceLat;
    }

    public void setStartPlaceLat(double startPlaceLat) {
        this.startPlaceLat = startPlaceLat;
    }

    public double getDestinationLon() {
        return destinationLon;
    }

    public void setDestinationLon(double destinationLon) {
        this.destinationLon = destinationLon;
    }

    public double getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(double destinationLat) {
        this.destinationLat = destinationLat;
    }
}
