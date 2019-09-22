package com.sang.modulebreakbulk.entity;

/**
 * 作者：  on 2019/9/22.
 * 零担发货发货记录信息
 */
public class BreakbulkConsignmentCreateParams {
   private String sendUserName	;//true	string	发货联系人名称
   private String sendMobile	;//true	string	发货人手机号码
   private String startPlaceInfo	;//true	string	发货详细地址
   private String receiptUserName	;//true	string(128)	收货人姓名
   private String receiptCompanyName	;//true	string	收货公司名称
   private String receiptMobile	;//true	string	收货人手机号码
   private String destinationInfo	;//true	string(128)	收货详细地址
   private String goodsName	;//true	string(32)	货物名称
   private String goodsVolume	;//true	double	货物体积 (方)
   private String goodsWeight	;//true	double	货物质量(吨)
   private int isToDoor	;//true	int	是否上门接货 0:否 1:是
   private String estimateFare	;//true	string	预估运费
   private String remark	;//false	string	备注
   private String logisticsCompanyId	;//true	int	物流公司id

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }


    public String getSendMobile() {
        return sendMobile;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile;
    }

    public String getStartPlaceInfo() {
        return startPlaceInfo;
    }

    public void setStartPlaceInfo(String startPlaceInfo) {
        this.startPlaceInfo = startPlaceInfo;
    }

    public String getReceiptUserName() {
        return receiptUserName;
    }

    public void setReceiptUserName(String receiptUserName) {
        this.receiptUserName = receiptUserName;
    }

    public String getReceiptCompanyName() {
        return receiptCompanyName;
    }

    public void setReceiptCompanyName(String receiptCompanyName) {
        this.receiptCompanyName = receiptCompanyName;
    }

    public String getReceiptMobile() {
        return receiptMobile;
    }

    public void setReceiptMobile(String receiptMobile) {
        this.receiptMobile = receiptMobile;
    }

    public String getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(String destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsVolume() {
        return goodsVolume;
    }

    public void setGoodsVolume(String goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public String getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(String goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public int getIsToDoor() {
        return isToDoor;
    }

    public void setIsToDoor(int isToDoor) {
        this.isToDoor = isToDoor;
    }

    public String getEstimateFare() {
        return estimateFare;
    }

    public void setEstimateFare(String estimateFare) {
        this.estimateFare = estimateFare;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLogisticsCompanyId() {
        return logisticsCompanyId;
    }

    public void setLogisticsCompanyId(String logisticsCompanyId) {
        this.logisticsCompanyId = logisticsCompanyId;
    }
}
