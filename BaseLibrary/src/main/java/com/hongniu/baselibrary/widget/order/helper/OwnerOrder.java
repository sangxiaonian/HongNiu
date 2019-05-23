package com.hongniu.baselibrary.widget.order.helper;

import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/10/23.
 */
public abstract class OwnerOrder   implements OrderDetailItemControl.IOrderItemHelper{
    protected OrderDetailItemControl.OrderState state;
    protected boolean insurance;//是否购买保险
    protected List<ButtonInforBean> buttonInfors;
    /**
     * 是否有货单
     */
    protected boolean hasGoodsImage;
    /**
     * 是否有回单
     */
    protected boolean hasReceiptImage;
    protected boolean hasPay;//设置是否已经支付，目前仅仅在发货人里面使用


    public OwnerOrder(OrderDetailItemControl.OrderState state) {
        this.state = state;
        buttonInfors=new ArrayList<>();
    }

    /**
     * 是否购买保险
     *
     * @param insurance true 已经购买
     * @return
     */
    @Override
    public OrderDetailItemControl.IOrderItemHelper setInsurance(boolean insurance) {
        this.insurance=insurance;
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
        this.hasGoodsImage=hasGoodsImage;
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
        this.hasReceiptImage=hasReceiptImage;
        return this;
    }

    @Override
    public OrderDetailItemControl.IOrderItemHelper setHasPay(boolean hasPay) {
        this.hasPay=hasPay;
        return this;
    }
}
