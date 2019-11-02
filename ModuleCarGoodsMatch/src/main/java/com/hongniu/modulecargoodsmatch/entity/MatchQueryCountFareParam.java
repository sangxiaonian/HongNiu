package com.hongniu.modulecargoodsmatch.entity;

/**
 * 作者：  on 2019/11/2.
 */
public class MatchQueryCountFareParam {

 private String  startPlaceLon	;//true	number	开始地经度
 private String  startPlaceLat	;//true	number	开始地纬度
 private String  destinationLon	;//true	number	目的地经度
 private String  destinationLat	;//true	number	目的地纬度
 private String  cartypeId	;//true	number	车辆类型id

    public String getStartPlaceLon() {
        return startPlaceLon;
    }

    public void setStartPlaceLon(String startPlaceLon) {
        this.startPlaceLon = startPlaceLon;
    }

    public String getStartPlaceLat() {
        return startPlaceLat;
    }

    public void setStartPlaceLat(String startPlaceLat) {
        this.startPlaceLat = startPlaceLat;
    }

    public String getDestinationLon() {
        return destinationLon;
    }

    public void setDestinationLon(String destinationLon) {
        this.destinationLon = destinationLon;
    }

    public String getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(String destinationLat) {
        this.destinationLat = destinationLat;
    }

    public String getCartypeId() {
        return cartypeId;
    }

    public void setCartypeId(String cartypeId) {
        this.cartypeId = cartypeId;
    }
}
