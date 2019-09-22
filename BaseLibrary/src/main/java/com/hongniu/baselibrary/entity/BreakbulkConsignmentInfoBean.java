package com.hongniu.baselibrary.entity;

/**
 * 作者：  on 2019/9/22.
 * 零担发货发货记录信息
 */
public class BreakbulkConsignmentInfoBean {
   private String  id	;//true	long	货物id
   private String  ltlGoodsNum	;//true	string	零担货物单号
   private String  ownerId	;//true	string	货主id
   private String  ownerName	;//true	string	货主名
   private String  ownerMobile	;//true	string	货主手机号
   private String  sendUserName	;//true	string	发货联系人名称
   private String  sendMobile	;//true	string	发货人手机号码
   private String  startPlaceInfo	;//true	string	发货详细地址
   private String  receiptUserName	;//true	string(128)	收货人姓名
   private String  receiptMobile	;//true	string	收货人手机号码
   private String  destinationInfo	;//true	string(128)	收货详细地址
   private String  goodsName	;//true	string(32)	货物名称
   private String  goodsVolume	;//true	double	货物体积 (方)
   private String  goodsWeight	;//true	double	货物质量(吨)
   private int  isToDoor	;//true	int	是否上门接货 0:否 1:是
   private String  estimateFare	;//true	string	预估运费
   private String  actualFare	;//true	string	实际运费
   private String  balanceFare	;//true	string	差额运费
   private int  estimateFareIsPay	;//true	int	预估运费是否已支付(0未支付1已支付 注:首次支付为预估运费)
   private String  estimateFarePayNum	;//true	string	预估运费支付单号
   private int  actualFareIsPay	;//true	int	差额运费是否已支付(0未支付1已支付2不需要支付 注:支付完预估运费才能支付差额运费)
   private String  actualFarePayNum	;//true	string	差额运费支付单号
   private String  logisticsCompanyId	;//true	int	物流公司id
   private String  logisticsCompanyName	;//true	string	物流公司名称
   private String  waybillNum	;//true	string	关联运单号
   private String  remark	;//true	string	备注
   private String  createTime	;//true	string	创建时间
   private int  isDel	;//true	int	是否删除(0正常,1删除)


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLtlGoodsNum() {
        return ltlGoodsNum;
    }

    public void setLtlGoodsNum(String ltlGoodsNum) {
        this.ltlGoodsNum = ltlGoodsNum;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

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

    public String getActualFare() {
        return actualFare;
    }

    public void setActualFare(String actualFare) {
        this.actualFare = actualFare;
    }

    public String getBalanceFare() {
        return balanceFare;
    }

    public void setBalanceFare(String balanceFare) {
        this.balanceFare = balanceFare;
    }

    public int getEstimateFareIsPay() {
        return estimateFareIsPay;
    }

    public void setEstimateFareIsPay(int estimateFareIsPay) {
        this.estimateFareIsPay = estimateFareIsPay;
    }

    public String getEstimateFarePayNum() {
        return estimateFarePayNum;
    }

    public void setEstimateFarePayNum(String estimateFarePayNum) {
        this.estimateFarePayNum = estimateFarePayNum;
    }

    public int getActualFareIsPay() {
        return actualFareIsPay;
    }

    public void setActualFareIsPay(int actualFareIsPay) {
        this.actualFareIsPay = actualFareIsPay;
    }

    public String getActualFarePayNum() {
        return actualFarePayNum;
    }

    public void setActualFarePayNum(String actualFarePayNum) {
        this.actualFarePayNum = actualFarePayNum;
    }

    public String getLogisticsCompanyId() {
        return logisticsCompanyId;
    }

    public void setLogisticsCompanyId(String logisticsCompanyId) {
        this.logisticsCompanyId = logisticsCompanyId;
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
    }

    public String getWaybillNum() {
        return waybillNum;
    }

    public void setWaybillNum(String waybillNum) {
        this.waybillNum = waybillNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
