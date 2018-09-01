package com.hongniu.baselibrary.widget.order;


/**
 * 作者： ${PING} on 2018/8/2.
 */
public class CommonOrderUtils {
    public static String getOrderState(OrderDetailItemControl.OrderState state) {
        String stateMsg;
        if (state == null) {
            return "无效状态";
        }
        switch (state) {

            case WAITE_PAY://待支付
                stateMsg = "待支付";
                break;

            case WAITE_START://待发车(已买保险)
                stateMsg = "待发车";
                break;
            case IN_TRANSIT://运输中
                stateMsg = "运输中";
                break;
            case HAS_ARRIVED://已到达
                stateMsg = "已到达";
                break;
            case RECEIPT://已收货
                stateMsg = "已收货";
                break;
            case REFUND://退款
            case UNKNOW://未知状态
            default:
                stateMsg = "无效状态";
                break;
        }
        return stateMsg;
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
                role = "货主";
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
            default:
                role = OrderDetailItemControl.RoleState.CARGO_OWNER;
                break;
        }
        return role;
    }


}
