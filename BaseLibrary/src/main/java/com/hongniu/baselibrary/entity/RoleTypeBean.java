package com.hongniu.baselibrary.entity;

/**
 * 作者： ${PING} on 2018/8/23.
 */
public class RoleTypeBean {
    /**
     * true	string	用户的角色Id(1 车主；2司机；3货主)
     */
    private int roleId;
    /**
     * true	string	用户的角色名称
     */
    private String roleName;
    /**
     * false	string	订单id （有司机角色正在运输的订单  时返回数据，其他情况为空）
     */
    private String orderId;
    /**
     * false	string	车辆Id（有司机角色正在运输的订单  时返回数据，其他情况为空）
     */
    private String carId;
    /**
     * false	string	订单状态（有司机角色正在运输的订单  时返回数据，其他情况为空）
     */
    private String status;
    private double startLatitude;
    private double startLongitude;
    private double destinationLatitude;
    private double destinationLongitude;

    public RoleTypeBean() {
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
