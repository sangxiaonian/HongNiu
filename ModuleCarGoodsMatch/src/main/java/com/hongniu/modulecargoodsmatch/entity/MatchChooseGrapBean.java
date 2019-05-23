package com.hongniu.modulecargoodsmatch.entity;

/**
 * 作者： ${PING} on 2019/5/23.
 */
public class MatchChooseGrapBean {
  public String id;//	true	number	货源id
  public String gsNum;//	true	string	货源单号
  public String startTime;//	true	string	发车时间
  public String startPlaceInfo;//	true	string(128)	开始地点描述
  public String startPlaceX;//	true	string	开始地点经度
  public String startPlaceY;//	true	string	开始地点纬度
  public String destinationInfo;//	true	string(128)	目的地描述
  public String destinationX;//	true	string	目的地经度
  public String destinationY;//	true	string	目的地纬度
  public String departNum;//	true	string(128)	发车编号
  public String goodName;//	true	string(128)	货物名称
  public String goodVolume;//	true	string	货物体积 (方)
  public String goodWeight;//	true	string	货物质量(吨)
  public String freightAmount;//	true	string	运费
  public String carTypeId;//	false	string	车辆类型id
  public String carType;//	true	string	车辆类型名称
  public String userId;//	true	string	创建用户id
  public String userName;//	true	string	创建用户名
  public String userMobile;//	true	string	创建用户手机号
  public String status;//	true	string	货源状态(0生成1已预定2已失效 3完成)
  public String goodsDetail;//	true	string	货物详细信息

}
