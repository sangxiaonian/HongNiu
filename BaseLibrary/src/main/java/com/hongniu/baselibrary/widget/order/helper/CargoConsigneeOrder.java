package com.hongniu.baselibrary.widget.order.helper;

import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;

import java.util.List;

import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_BUY_INSURANCE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CANCLE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHANGE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_GOODS;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_INSURANCE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_PATH;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_RECEIPT;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_ENTRY_AND_PAY_ORDER;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_ENTRY_ORDER;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_PAY;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_PAY_REFUSE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_START_CAR;


/**
 * 作者： ${PING} on 2018/8/2.
 * 货主是收货人订单
 */
public class CargoConsigneeOrder extends OwnerOrder implements OrderDetailItemControl.IOrderItemHelper {


    public CargoConsigneeOrder(OrderDetailItemControl.OrderState state) {
        super(state);
    }


    @Override
    public String getOrderState() {
        return state==null?OrderDetailItemControl.OrderState.UNKNOW.getDes():state.getDes();
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
            case WAITE_START://待发车
                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }

                break;
            case IN_TRANSIT://运输中
                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }

                buttonInfors.add(new ButtonInforBean(ORDER_CHECK_PATH));//查看看轨迹
                break;
            case HAS_ARRIVED://已到达
                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }
                if (hasReceiptImage) {//如果存在回单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_RECEIPT));//查看回单
                }

                buttonInfors.add(new ButtonInforBean(1, ORDER_ENTRY_ORDER));//确认收货
                buttonInfors.add(new ButtonInforBean(1, ORDER_ENTRY_AND_PAY_ORDER));//确认收货,并支付订单
                break;
            case RECEIPT://已收货
                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }
                if (hasReceiptImage) {//如果存在回单
                    buttonInfors.add(new ButtonInforBean(ORDER_CHECK_RECEIPT));//查看回单
                }

                break;
            case PAY_REFUSE://企业支付申请被拒绝
            case PAY_CHECK://支付申请中
            case REFUND://退款
            case UNKNOW://未知状态
            default:
                break;
        }
        return buttonInfors;
    }


}
