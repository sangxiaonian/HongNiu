package com.hongniu.baselibrary.entity;

/**
 * 作者： ${PING} on 2019/5/29.
 */
public class GrapSingleInforBean {
    public String id;//	true	number	抢单id
    public String goodsSourceId;//	true	string	货源id
    public String carId;//	true	string	车辆id
    public String carNum;//	true	string	车牌号
    public String carType;//	true	string	车辆类型
    public String carTypeName;//	true	string	车辆类型名称
    public String robAmount;//	true	string	意向金金额
    public String creationTime;//	true	string	创建时间
    public String payTime;//	true	string	意向金支付时间
    public String driverId;//	true	string	抢单人id
    public String driverName;//	true	string	抢单人名字
    public String driverMobile;//	true	string	抢单人手机号
    public String payWay;//	true	string	支付方式(0微信,1银联,2线下支付,3支付宝支付,4余额支付)
//    public String payTime;//	true	string	付款时间
    public String robNum;//	true	string	抢单单号
    public String payNum;//	true	string	支付单号
    public int status;//	true	string	抢单状态(0生成1已支付2确认3失效4已完成)
    public String cancel;//	true	string	取消抢单(0可以取消 1不能取消,2已取消)

}
