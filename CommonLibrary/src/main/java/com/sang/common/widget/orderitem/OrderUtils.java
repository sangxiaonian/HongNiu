package com.sang.common.widget.orderitem;

/**
 * 作者： ${PING} on 2018/8/2.
 */
public class OrderUtils {
    public static String getOrderState(OrderItemView.OrderState state) {
        String stateMsg;
        switch (state) {

            case WAITE_PAY://待支付
                stateMsg = "待支付";
                break;
            case WAITE_START_NO_INSURANCE://待发车(未购买保险)
                stateMsg = "待发车";
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
            default:
                stateMsg = "无效状态";
                break;
        }
        return stateMsg;
    }


    public static String getRoleState(OrderItemView.RoleState roleState) {
        String role;
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
}
