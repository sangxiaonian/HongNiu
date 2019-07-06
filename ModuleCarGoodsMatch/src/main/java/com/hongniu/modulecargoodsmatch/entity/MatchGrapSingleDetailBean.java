package com.hongniu.modulecargoodsmatch.entity;

/**
 * 作者： ${PING} on 2019/5/19.
 * 接单明细列表
 */
public class MatchGrapSingleDetailBean {

 public String  id	;//true	number	接单id
 public String  goodsSourceId	;//true	string	货源id
 public String  creationTime	;//true	string	创建时间
 public String  carId	;//true	string	车辆id
 public String  carNum	;//true	string	车牌号
 public String  carType	;//true	string	车辆类型
 public String  carTypeName	;//true	string	车辆类型名称
 public String  driverId	;//true	string	司机id
 public String  driverName	;//true	string	司机名
 public String  driverMobile	;//true	string	司机手机号
 public String  robAmount	;//true	string	保证金金额
 public String  payWay	;//true	string	保证金支付方式
 public String  payTime	;//true	string	保证金支付时间
 public String  status	;//true	string	接单状态(0生成1已支付2确认3失效4已完成)
 public String  cancel	;//true	string	取消接单(0可以取消 1不能取消,2已取消)
 public String  cancelTime	;//true	string	取消接单时间
 public String  robNum	;//true	string	接单单号
 public String  payNum	;//true	string	支付单号




}
