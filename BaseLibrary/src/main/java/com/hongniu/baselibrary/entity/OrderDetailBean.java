package com.hongniu.baselibrary.entity;

/**
 * 作者： ${PING} on 2018/8/7.
 * 订单详情相关数据
 */
public class OrderDetailBean  {

    /**
     * 主键
     */
    private String id;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     *  true string 订单状态 -1退款 2待发货 3配送中 4到货 5已收货
     */
    private String status;

    /**
     * true string 出发地x坐标
     */
    private String  stratPlaceX ;

    /**
     * true string 出发地y坐标
     */
    private String  stratPlaceY ;

    /**
     * true string 出发地描述
     */
    private String stratPlaceInfo ;

    /**
     *  true string 目的地描述
     */
    private String  destinationInfo;

    /**
     *  true string 目的地x坐标
     */
    private String  destinationX;
    /**
     * true string 目的地y坐标
     */
    private String  destinationY ;

    /**
     * true string 创建日期
     */
    private String  creationDate ;

    /**
     * true string 发货日期
     */
    private String  deliverydate ;

    /**
     *  true string 完成日期
     */
    private String  endTime;

    /**
     * true string 下单人id
     */
    private String userId ;
    /**
     * true string 车主的姓名(非下单人)
     */
    private String  userName ;

    /**
     * true string 车主的电话(非下单人)
     */
    private String  userPhone ;

    /**
     * true string 司机的id
     */
    private String   userIdSend ;
    /**
     * true string 司机的姓名
     */
    private String   drivername ;

    /**
     * true string 司机的手机号
     */
    private String  drivermobile ;

    /**
     * true string 车辆id
     */
    private String  carId ;
    /**
     * true string 车辆类型
     */
    private String  carInfo;

    /**
     * true string 车牌号
     */
    private String carnum ;

    /**
     * true number 运费，单位元
     */
    private String  money ;

    /**
     * true string 货物名称
     */
    private String  goodName ;
    /**
     * true number 货物体积，单位方
     */
    private String   goodvolume ;

    /**
     * true number 货物质量，单位吨
     */
    private String  goodweight ;
    /**
     * true number 货物价值，单位元
     */
    private String  goodPrice ;

    /**
     * true boolean 是否购买保险，true=是
     */
    private String   insurance ;

    /**
     * true boolean 是否付运费，true=是
     */
    private String   hasFreight ;

    /**
     * true string 发车编号
     */
    private String  departNum ;


}
