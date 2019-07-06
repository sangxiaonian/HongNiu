package com.hongniu.modulecargoodsmatch.entity;

import java.security.PublicKey;

/**
 * 作者： ${PING} on 2019/5/21.
 * 车货匹配创建货源信息
 */
public class MatchCreatGoodsSourceParams {
   public String startTime	;//true	string	发货日期（字符串，格式YYYY-MM-dd）
   public String startPlaceInfo	;//true	string(128)	开始地点描述
   public String startPlaceX	;//true	string	开始地点经度
   public String startPlaceY	;//true	string	开始地点纬度
   public String destinationInfo	;//true	string(128)	目的地描述
   public String destinationX	;//true	string	目的地经度
   public String destinationY	;//true	string	目的地纬度
   public String departNum	;//true	string(128)	发车编号
   public String goodName	;//true	string(128)	货物名称
   public String goodVolume	;//true	string	货物体积 (方)
   public String goodWeight	;//true	string	货物质量(吨)
   public String freightAmount	;//true	string	运费
   public String carTypeId	;//false	string	车辆类型(默认id为1,小货车) 车辆类型接口见文档4.9)
   public String carLength	;//false	string	车辆长(值由13.1接口指定)
   public String carType	;//false	string	车辆类型(值由13.1接口指定)
   public String remark	;//false	string	备注信息

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartPlaceInfo() {
        return startPlaceInfo;
    }

    public void setStartPlaceInfo(String startPlaceInfo) {
        this.startPlaceInfo = startPlaceInfo;
    }

    public String getStartPlaceX() {
        return startPlaceX;
    }

    public void setStartPlaceX(String startPlaceX) {
        this.startPlaceX = startPlaceX;
    }

    public String getStartPlaceY() {
        return startPlaceY;
    }

    public void setStartPlaceY(String startPlaceY) {
        this.startPlaceY = startPlaceY;
    }

    public String getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(String destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public String getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(String destinationX) {
        this.destinationX = destinationX;
    }

    public String getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(String destinationY) {
        this.destinationY = destinationY;
    }

    public String getDepartNum() {
        return departNum;
    }

    public void setDepartNum(String departNum) {
        this.departNum = departNum;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodVolume() {
        return goodVolume;
    }

    public void setGoodVolume(String goodVolume) {
        this.goodVolume = goodVolume;
    }

    public String getGoodWeight() {
        return goodWeight;
    }

    public void setGoodWeight(String goodWeight) {
        this.goodWeight = goodWeight;
    }

    public String getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(String freightAmount) {
        this.freightAmount = freightAmount;
    }

    public String getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(String carTypeId) {
        this.carTypeId = carTypeId;
    }
}
