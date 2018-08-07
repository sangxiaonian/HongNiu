package com.hongniu.baselibrary.widget.order.helper;

import android.text.TextUtils;
import android.view.View;

import com.hongniu.baselibrary.widget.order.OrderState;
import com.hongniu.baselibrary.widget.order.OrderUtils;
import com.hongniu.baselibrary.widget.order.RoleState;

/**
 * 作者： ${PING} on 2018/8/2.
 * 车主订单
 */
public class CarOwnerOrder implements IOrderItemHelper {


    private OrderState state;
    private RoleState roleState;

    public CarOwnerOrder(OrderState state, RoleState roleState) {
        this.state = state;
        this.roleState = roleState;
    }

    @Override
    public int getLeftVisibility() {
        return TextUtils.isEmpty(getBtLeftInfor()) ? View.VISIBLE : View.GONE;
    }

    @Override
    public int getRightVisibility() {
        return TextUtils.isEmpty(getBtRightInfor()) ? View.VISIBLE : View.GONE;
    }

    /**
     * 获取左侧按钮信息
     */
    @Override
    public String getBtLeftInfor() {
        String stateMsg;
        switch (state) {
            case WAITE_PAY://待支付
            case WAITE_START_NO_INSURANCE://待发车(未购买保险)
                stateMsg = null;
                break;
            case WAITE_START://待发车(已买保险)
                stateMsg = "查看保单";
                break;
            case IN_TRANSIT://运输中
                stateMsg = "查看保单";
                break;
            case HAS_ARRIVED://已到达
                stateMsg = "查看保单";
                break;
            case RECEIPT://已收货
                stateMsg = "查看保单";
                break;
            default:
                stateMsg = null;
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

        String stateMsg = null;
        switch (state) {
            case WAITE_PAY://待支付
                break;
            case WAITE_START_NO_INSURANCE://待发车(未购买保险)
                stateMsg = "购买保险";
                break;
            case WAITE_START://待发车(已买保险)
                break;
            case IN_TRANSIT://运输中
                stateMsg = "查看轨迹";
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
