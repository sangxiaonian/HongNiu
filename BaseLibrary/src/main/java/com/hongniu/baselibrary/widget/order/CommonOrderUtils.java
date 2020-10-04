package com.hongniu.baselibrary.widget.order;


import android.text.TextUtils;

import com.fy.androidlibrary.utils.JLog;

/**
 * 作者： ${PING} on 2018/8/2.
 */
public class CommonOrderUtils {
    /***************************订单显示按钮的文案****************************/
    public static final String ORDER_CANCLE = "取消订单";
    public static final String ORDER_PAY = "继续付款";
    public static final String ORDER_PAY_REFUSE = "被拒原因";
    public static final String ORDER_BUY_INSURANCE = "购买保险";
    public static final String ORDER_CHECK_INSURANCE = "查看保单";
    public static final String ORDER_CHECK_PATH = "查看轨迹";
    public static final String ORDER_ENTRY_ORDER = "确认收货";
    public static final String ORDER_ENTRY_AND_PAY_ORDER = "确认收货,并支付订单";
    public static final String ORDER_START_CAR = "开始发车";
    public static final String ORDER_CHECK_ROUT = "查看路线";
    public static final String ORDER_TRUCK_GUIDE = "货车导航";
    public static final String ORDER_ENTRY_ARRIVE = "确认到达";

    public static final String ORDER_CHANGE = "修改订单";
    public static final String ORDER_CHECK_GOODS = "查看货单";
    public static final String ORDER_CHECK_RECEIPT = "查看回单";
    public static final String ORDER_UP_RECEIPT = "上传回单";
    public static final String ORDER_CHANGE_RECEIPT = "修改回单";


    /**
     * 根据当前的状态值获取不同的状态状态,此方法是为了防止订单状态数据更改，因此更改为自己的状态，后期不需要再关心该字段
     *
     * @param status
     * @return
     */
    public static OrderDetailItemControl.OrderState getStatus(int status) {
        OrderDetailItemControl.OrderState[] values = OrderDetailItemControl.OrderState.values();
        OrderDetailItemControl.OrderState statu = null;
        for (OrderDetailItemControl.OrderState value : values) {
            if (value.getState() == status) {
                statu = value;
                break;
            }
        }
        return statu == null ? OrderDetailItemControl.OrderState.UNKNOW : statu;


    }


    /**
     * 根据订单状态获取到订单相应的 状态信息
     *
     * @param state
     * @return
     */
    public static String getOrderStateDes(OrderDetailItemControl.OrderState state) {
        if (state == null) {
            return OrderDetailItemControl.OrderState.UNKNOW.getDes();
        } else {
            return state.getDes();
        }

    }


    public static String getRoleState(OrderDetailItemControl.RoleState roleState) {
        String role;
        if (roleState == null) {
            return "未知";
        }
        switch (roleState) {
            case CAR_OWNER:
                role = "车主";
                break;
            case CARGO_OWNER:
                role = "发货人";
                break;
            case CARGO_RECEIVE:
                role = "收货人";
                break;
            case DRIVER:
                role = "司机";
                break;
            default:
                role = "未知";
                break;
        }
        return role;
    }

    public static OrderDetailItemControl.RoleState getRoleState(int roleType) {
        OrderDetailItemControl.RoleState role;
        switch (roleType) {
            case 1:
                role = OrderDetailItemControl.RoleState.CAR_OWNER;
                break;
            case 2:
                role = OrderDetailItemControl.RoleState.DRIVER;
                break;
            case 3:
                role = OrderDetailItemControl.RoleState.CARGO_OWNER;
                break;
            case 4:
                role = OrderDetailItemControl.RoleState.CARGO_RECEIVE;
                break;
            default:
                role = OrderDetailItemControl.RoleState.CARGO_OWNER;
                break;
        }
        return role;
    }


    /**
     * 判断是否是线上支付
     *
     * @param payType
     * @return true 线上支付
     */
    public static boolean isPayOnLine(String payType) {
        return !TextUtils.isEmpty(payType) && !TextUtils.equals("2", payType);
    }

    /**
     * 根据支付方式获取对应的转换
     *
     * @param payWay
     * @return
     */
    public static String getPayWay(String payWay) {
        if (TextUtils.isEmpty(payWay)) {
            return "";
        } else if (payWay.equals("0")) {
            return "微信";
        } else if (payWay.equals("1")) {
            return "银联";
        } else if (payWay.equals("2")) {
            return "线下支付";
        } else if (payWay.equals("3")) {
            return "支付宝";
        } else if (payWay.equals("4")) {
            return "余额";
        } else if (payWay.equals("5")) {
            return "企业余额";
        } else {
            return "";
        }
    }


}
