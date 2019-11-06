package com.hongniu.modulecargoodsmatch.entity;

/**
 * 作者：  on 2019/11/2.
 */
public class MatchCountFareBean {
  private String  distance;//true	number	到达距离 (公里)
  private String  estimateFare;//true	number	预估运费
  private String  mileageFareSumInfo;//true	number	预估运费

    public String getMileageFareSumInfo() {
        return mileageFareSumInfo;
    }

    public void setMileageFareSumInfo(String mileageFareSumInfo) {
        this.mileageFareSumInfo = mileageFareSumInfo;
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
}
