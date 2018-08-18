package com.hongniu.baselibrary.widget.order.helper;

import android.text.TextUtils;
import android.view.View;

import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.baselibrary.widget.order.OrderUtils;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.ORDER_BUY_INSURANCE;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.ORDER_CANCLE;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.ORDER_CHECK_INSURANCE;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.ORDER_CHECK_PATH;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.ORDER_ENTRY_ORDER;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.ORDER_PAY;

/**
 * 作者： ${PING} on 2018/8/2.
 * 货主订单
 */
public class CargoOwnerOrder implements OrderDetailItemControl.IOrderItemHelper {


    private OrderDetailItemControl.OrderState state;
    private boolean insurance;//是否购买保险

    public CargoOwnerOrder(OrderDetailItemControl.OrderState state, boolean insurance) {
        this.state = state;
        this.insurance = insurance;
    }

    @Override
    public int getLeftVisibility() {
        return !TextUtils.isEmpty(getBtLeftInfor()) ? View.VISIBLE : View.GONE;
    }

    @Override
    public int getRightVisibility() {
        return !TextUtils.isEmpty(getBtRightInfor()) ? View.VISIBLE : View.GONE;
    }

    /**
     * 获取左侧按钮信息
     */
    @Override
    public String getBtLeftInfor() {
        String stateMsg = "";
        switch (state) {
            case WAITE_PAY://待支付
                stateMsg = ORDER_CANCLE;
                break;
            case WAITE_START://待发车
                if (insurance) {
                    stateMsg = ORDER_CHECK_INSURANCE;
                }
                break;
            case IN_TRANSIT://运输中
                if (insurance) {
                    stateMsg = ORDER_CHECK_INSURANCE;
                }
                break;
            case HAS_ARRIVED://已到达
                if (insurance) {
                    stateMsg = ORDER_CHECK_INSURANCE;
                }
                break;
            case RECEIPT://已收货
                if (insurance) {
                    stateMsg = ORDER_CHECK_INSURANCE;
                }
                break;
            default:
                stateMsg = "";
                break;
        }
        return stateMsg;

    }

    /**
     * 获取右侧
     *
     * @return
     */
    @Override
    public String getBtRightInfor() {

        String stateMsg = "";
        switch (state) {
            case WAITE_PAY://待支付
                stateMsg = ORDER_PAY;
                break;
            case WAITE_START://待发车(已买保险)
                if (!insurance) {
                    stateMsg = ORDER_BUY_INSURANCE;
                }
                break;
            case IN_TRANSIT://运输中
                stateMsg = ORDER_CHECK_PATH;
                break;
            case HAS_ARRIVED://已到达
                stateMsg = ORDER_ENTRY_ORDER;
                break;
            case RECEIPT://已收货
            case REFUND://退款
            case UNKNOW://未知状态
                break;
            default:
                break;
        }
        return stateMsg;
    }


    @Override
    public String getOrderState() {
        return OrderUtils.getOrderState(state);
    }


}
