package com.hongniu.baselibrary.widget.order.helper;

import android.text.TextUtils;
import android.view.View;

import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.baselibrary.widget.order.OrderUtils;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.ORDER_CHECK_ROUT;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.ORDER_ENTRY_ARRIVE;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.ORDER_START_CAR;

/**
 * 作者： ${PING} on 2018/8/2.
 * 司机订单
 */
public class DriveOwnerOrder implements OrderDetailItemControl.IOrderItemHelper {


    private OrderDetailItemControl.OrderState state;
    private boolean insurance;


    public DriveOwnerOrder(OrderDetailItemControl.OrderState state, boolean insurance) {
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
                break;
            case IN_TRANSIT://运输中
                stateMsg = ORDER_CHECK_ROUT;
                break;
            case HAS_ARRIVED://已到达
                break;
            case RECEIPT://已收货
                break;
            default:
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
                break;
            case WAITE_START://待发车(已买保险)
                stateMsg = ORDER_START_CAR;
                break;
            case IN_TRANSIT://运输中
                stateMsg = ORDER_ENTRY_ARRIVE;
                break;
            case HAS_ARRIVED://已到达

                break;
            case RECEIPT://已收货
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
