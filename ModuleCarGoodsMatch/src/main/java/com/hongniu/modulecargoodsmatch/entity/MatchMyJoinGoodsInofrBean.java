package com.hongniu.modulecargoodsmatch.entity;

/**
 * 作者： ${PING} on 2019/5/21.
 */
public class MatchMyJoinGoodsInofrBean {
   public String id;//	true	number	接单id
   public String goodsSourceId;//	true	string	货源id
   public String carId;//	true	string	车辆id
   public String carNum;//	true	string	车牌号
   public String carType;//	true	string	车辆类型
   public String carTypeName;//	true	string	车辆类型名称
   public String robAmount;//	true	string	保证金金额
   public String creationTime;//	true	string	创建时间
   public String payTime;//	true	string	保证金支付时间
   public String goodsUserId;//	true	string	货源下单人id
   public String goodsUserName;//	true	string	货源下单人名字
   public String goodsUserMobile;//	true	string	货源下单人手机号
   public String startTime;//	true	string	货源发货时间
   public String startPlaceInfo;//	true	string	货源发货地址
   public String destinationInfo;//	true	string	货源收货地址
   public String goodsSourceDetail;//	true	string	货物名称
   public String status;//	true	string	接单状态(0生成1已支付2确认3失效4已完成)
   public String cancel;//	true	string	取消接单(0可以取消 1不能取消,2已取消)

}
