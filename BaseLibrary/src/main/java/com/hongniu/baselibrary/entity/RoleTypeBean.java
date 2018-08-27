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
    private double stratPlaceX;
    private double stratPlaceY;
    private double destinationX;
    private double destinationY;

    public RoleTypeBean() {
    }

    public double getStratPlaceX() {
        return stratPlaceX;
    }

    public void setStratPlaceX(double stratPlaceX) {
        this.stratPlaceX = stratPlaceX;
    }

    public double getStratPlaceY() {
        return stratPlaceY;
    }

    public void setStratPlaceY(double stratPlaceY) {
        this.stratPlaceY = stratPlaceY;
    }

    public double getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(double destinationX) {
        this.destinationX = destinationX;
    }

    public double getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(double destinationY) {
        this.destinationY = destinationY;
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
