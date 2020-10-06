package com.hongniu.freight.entity;

/**
 * 作者： ${PING} on 2018/8/27.
 * 车辆位置信息
 */
public class LocationBean {
    /**
     * true	long	订单id
      */
    private String  orderId         	;
    /**
     * true	string	车辆id
      */
    private String  carId     	    ;
    /**
     *     true	datetime	位移时间
      */
    private  String movingTime	        ;
    /**
     * true	double	纬度，浮点数，范围为-90~90，负数表示南纬
      */
    private double	 latitude	    ;
    /**
     * true	double	经度，浮点数，范围为-180~180，负数表示西经
      */
    private double	 longitude	    ;
    /**
     * false	double	高度，单位 m
      */
    private double	 altitude	    ;
    /**
     * false	double	速度，浮点数，单位m/s
      */
    private double	 speed	        ;
    /**
     * false	int 	方向
      */
    private float 	 direction      	;
    /**
     * false	int	    位置的精确度
      */
    private int	     accuracy	    ;
    /**
     * false	int	垂直精度，单位 m（Android 无法获取，返回 0）
      */
    private int	 verticalAccuracy	;
    /**
     * false	int	水平精度，单位 m
      */
    private int	 horizontalAccuracy	;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getMovingTime() {
        return movingTime;
    }

    public void setMovingTime(String movingTime) {
        this.movingTime = movingTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getVerticalAccuracy() {
        return verticalAccuracy;
    }

    public void setVerticalAccuracy(int verticalAccuracy) {
        this.verticalAccuracy = verticalAccuracy;
    }

    public int getHorizontalAccuracy() {
        return horizontalAccuracy;
    }

    public void setHorizontalAccuracy(int horizontalAccuracy) {
        this.horizontalAccuracy = horizontalAccuracy;
    }
}
