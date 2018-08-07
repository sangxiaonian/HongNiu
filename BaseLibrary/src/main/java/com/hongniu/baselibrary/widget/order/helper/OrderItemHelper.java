package com.hongniu.baselibrary.widget.order.helper;

import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;

/**
 * 作者： ${PING} on 2018/8/2.
 */
public class OrderItemHelper implements OrderDetailItemControl.IOrderItemHelper {

    OrderDetailItemControl.IOrderItemHelper helper;

    public OrderItemHelper(OrderDetailItemControl.OrderState state, OrderDetailItemControl.RoleState roleState, boolean insurance) {
        switch (roleState) {
            case CAR_OWNER:
                helper = new CarOwnerOrder(state, insurance);
                break;
            case CARGO_OWNER:
                helper = new CargoOwnerOrder(state, insurance);
                break;
            case DRIVER:
                helper = new DriveOwnerOrder(state, insurance);
                break;
        }
    }

    @Override
    public int getLeftVisibility() {
        return helper.getLeftVisibility();
    }

    @Override
    public int getRightVisibility() {
        return helper.getRightVisibility();
    }

    /**
     * 获取左侧按钮信息
     */
    @Override
    public String getBtLeftInfor() {
        return helper.getBtLeftInfor();
    }

    /**
     * 获取右侧
     *
     * @return
     */
    @Override
    public String getBtRightInfor() {
        return helper.getBtRightInfor();
    }

    /**
     * 获取订单当前状态
     *
     * @return
     */
    @Override
    public String getOrderState() {
        return helper.getOrderState();
    }


}
