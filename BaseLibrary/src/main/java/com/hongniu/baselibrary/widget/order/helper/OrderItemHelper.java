package com.hongniu.baselibrary.widget.order.helper;

import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/2.
 */
public class OrderItemHelper implements OrderDetailItemControl.IOrderItemHelper {

    OrderDetailItemControl.IOrderItemHelper helper;

    public OrderItemHelper(OrderDetailItemControl.OrderState state, OrderDetailItemControl.RoleState roleState) {
        switch (roleState) {
            case CAR_OWNER:
                helper = new CarOwnerOrder(state);
                break;
            case CARGO_OWNER:
                helper = new CargoOwnerOrder(state);
                break;
            case DRIVER:
                helper = new DriveOwnerOrder(state);
                break;
        }
    }

    /**
     * 是否购买保险
     *
     * @param insurance true 已经购买
     * @return
     */
    @Override
    public OrderDetailItemControl.IOrderItemHelper setInsurance(boolean insurance) {
        helper.setInsurance(insurance);
        return this;
    }

    /**
     * 是否存在货单
     *
     * @param hasGoodsImage true 存在
     * @return
     */
    @Override
    public OrderDetailItemControl.IOrderItemHelper setHasGoodsImage(boolean hasGoodsImage) {
        helper.setHasGoodsImage(hasGoodsImage);
        return this;
    }

    /**
     * 是否购存在回单
     *
     * @param hasReceiptImage true 存在
     * @return
     */
    @Override
    public OrderDetailItemControl.IOrderItemHelper setHasReceiptImage(boolean hasReceiptImage) {
        helper.setHasReceiptImage(hasReceiptImage);
        return this;
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

    /**
     * 获取要显示的按钮类型
     */
    @Override
    public List<ButtonInforBean> getButtonInfors() {
        return helper.getButtonInfors();
    }


}
