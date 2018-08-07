package com.hongniu.baselibrary.widget.order.helper;

import com.hongniu.baselibrary.widget.order.OrderState;
import com.hongniu.baselibrary.widget.order.RoleState;

/**
 * 作者： ${PING} on 2018/8/2.
 */
public class OrderItemHelper implements IOrderItemHelper {

    IOrderItemHelper helper;

    public OrderItemHelper(OrderState state, RoleState roleState) {
        switch (roleState) {
            case CAR_OWNER:
                helper = new CargoOwnerOrder(state, roleState);
                break;
            case CARGO_OWNER:
                helper = new CargoOwnerOrder(state, roleState);
                break;
            case DRIVER:
                helper = new DriveOwnerOrder(state, roleState);
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
