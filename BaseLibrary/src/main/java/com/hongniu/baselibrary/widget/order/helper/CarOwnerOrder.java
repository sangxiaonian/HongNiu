package com.hongniu.baselibrary.widget.order.helper;

import android.text.TextUtils;
import android.view.View;

import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.baselibrary.widget.order.CommonOrderUtils;

import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_BUY_INSURANCE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_INSURANCE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_PATH;


/**
 * 作者： ${PING} on 2018/8/2.
 * 车主订单
 */
public class CarOwnerOrder implements OrderDetailItemControl.IOrderItemHelper {


    private OrderDetailItemControl.OrderState state;
    private boolean insurance;//是否购买保险

    public CarOwnerOrder(OrderDetailItemControl.OrderState state, boolean insurance) {
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
                break;
            case WAITE_START://待发车(已买保险)
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
            case REFUND://退款
            case UNKNOW://未知状态

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
        return CommonOrderUtils.getOrderStateDes(state);
    }


}
