package com.hongniu.moduleorder.entity;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class OrderCreatParamBean {
    /**
     * true	string	发车编号
     */
    private String  departNum	          ;
    /**
     * true	string	开始地点x坐标
     */
    private String  stratPlaceX	      ;
    /**
     * true	string	开始地点y坐标
     */
    private String  stratPlaceY	      ;
    /**
     * true	string	开始地点描述
     */
    private String  stratPlaceInfo	  ;
    /**
     * true	string	目的地x坐标
     */
    private String  destinationX      ;
    /**
     * true	string	目的地y坐标
     */
    private String  destinationY	      ;
    /**
     * true	string	目的地描述
     */
    private String  destinationInfo	  ;
    /**
     * true	string	发货日期（字符串，格式YYYY-MM-dd）
     */
    private String  deliverydateStr	  ;
    /**
     * true	string	货物名称
     */
    private String  goodName	          ;
    /**
     * true	string	货物体积 (方)
     */
    private String  goodvolume	      ;
    /**
     * true	string	货物质量(吨)
     */
    private String  goodweight	      ;
    /**
     * true	string	配送金额
     */
    private String  money             ;
    /**
     * true	string	车牌号
     */
    private String  carnum	          ;
    /**
     * false	string	车辆类型
     */
    private String  carInfo	          ;
    /**
     * true	string	车主姓名
     */
    private String  userName	          ;
    /**
     * true	string	车主手机号
     */
    private String  userPhone	          ;
    /**
     * true	string	司机名称
     */
    private String  drivername	      ;
    /**
     * true	string	司机手机号
     */
    private String  drivermobile	      ;
    /**
     * false	string	支付方式(0微信,1银联,2线下支付)
     */
    private String  payWay	          ;

    public void setDepartNum(String departNum) {
        this.departNum = departNum;
    }

    public void setStratPlaceX(String stratPlaceX) {
        this.stratPlaceX = stratPlaceX;
    }

    public void setStratPlaceY(String stratPlaceY) {
        this.stratPlaceY = stratPlaceY;
    }

    public void setStratPlaceInfo(String stratPlaceInfo) {
        this.stratPlaceInfo = stratPlaceInfo;
    }

    public void setDestinationX(String destinationX) {
        this.destinationX = destinationX;
    }

    public void setDestinationY(String destinationY) {
        this.destinationY = destinationY;
    }

    public void setDestinationInfo(String destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public void setDeliverydateStr(String deliverydateStr) {
        this.deliverydateStr = deliverydateStr;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public void setGoodvolume(String goodvolume) {
        this.goodvolume = goodvolume;
    }

    public void setGoodweight(String goodweight) {
        this.goodweight = goodweight;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public void setDrivermobile(String drivermobile) {
        this.drivermobile = drivermobile;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
}
