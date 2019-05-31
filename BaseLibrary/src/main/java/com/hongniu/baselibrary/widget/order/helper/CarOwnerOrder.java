package com.hongniu.baselibrary.widget.order.helper;

import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;

import java.util.List;

import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_BUY_INSURANCE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_GOODS;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_INSURANCE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_PATH;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_RECEIPT;


/**
 * 作者： ${PING} on 2018/8/2.
 * 车主订单
 *
 * 2019/5/31 去除查看货单入口 setHasGoodsImage 值强制更改为false
 * */
public class CarOwnerOrder extends OwnerOrder {


    public CarOwnerOrder(OrderDetailItemControl.OrderState state) {
        super(state);
    }

    @Override
    public String getOrderState() {
        return state == null ? OrderDetailItemControl.OrderState.UNKNOW.getDes() : state.getDes();
    }
    @Override
    public OrderDetailItemControl.IOrderItemHelper setHasGoodsImage(boolean hasGoodsImage) {
        this. hasGoodsImage=false;
        return this;
    }
    /**
     * 获取要显示的按钮类型
     */
    @Override
    public List<ButtonInforBean> getButtonInfors() {
        buttonInfors.clear();

        switch (state) {
            case WAITE_PAY://待支付
                break;
            case WAITE_START://待发车(已买保险)

                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }
                if (!insurance) {//未购买保险
                    buttonInfors.add(new ButtonInforBean(1, ORDER_BUY_INSURANCE));//购买保险
                } else {//如果已经购买了保险
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_INSURANCE));//查看保单
                }

                break;
            case IN_TRANSIT://运输中
                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }
                if (insurance) {//如果已经购买保险
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_INSURANCE));//查看保单

                }
                buttonInfors.add(new ButtonInforBean(ORDER_CHECK_PATH));//查看看轨迹

                break;
            case HAS_ARRIVED://已到达
            case WAITE_CHECK://到付待审核

                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }

                if (hasReceiptImage) {//如果存在回单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_RECEIPT));//查看回单
                }

                if (insurance) {//如果已经购买保险
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_INSURANCE));//查看保单
                }
                break;
            case RECEIPT://已收货
                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }
                if (hasReceiptImage) {//如果存在回单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_RECEIPT));//查看回单
                }
                if (insurance) {//如果已经购买保险
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_INSURANCE));//查看保单
                }
                break;
            case REFUND://退款
            case UNKNOW://未知状态

        }

        return buttonInfors;
    }


}
