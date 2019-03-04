package com.hongniu.baselibrary.widget.order;


import android.text.TextUtils;

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
    public static final String ORDER_START_CAR = "开始发车";
    public static final String ORDER_CHECK_ROUT = "查看路线";
    public static final String ORDER_TRUCK_GUIDE = "货车导航";
    public static final String ORDER_ENTRY_ARRIVE = "确认到达";

    public static final String ORDER_CHANGE = "修改订单";
    public static final String ORDER_CHECK_GOODS = "查看货单";
    public static final String ORDER_CHECK_RECEIPT = "查看回单";
    public static final String ORDER_UP_RECEIPT = "上传回单";
    public static final String ORDER_CHANGE_RECEIPT = "修改回单";


    /*********************不同订单状态的文案***************************/
    public static final String STATUS_DES_REFUND = "已退款";//已退款
    public static final String STATUS_DES_WAITE_PAY = "待支付";//待支付
    public static final String STATUS_DES_HAS_PAY = "订单已支付";//订单已支付（暂时不使用）
    public static final String STATUS_DES_WAITE_START = "待发车";//待发车
    public static final String STATUS_DES_IN_TRANSIT = "运输中";//运输中
    public static final String STATUS_DES_HAS_ARRIVED = "已到达";//已到达
    public static final String STATUS_DES_RECEIPT = "已收货";//已收货
    public static final String STATUS_DES_HAS_CANCLE = "已取消";//已取消
    public static final String STATUS_DES_PAY_CHECK = "支付审核中";//支付审核中
    public static final String STATUS_DES_PAY_REFUSE = "申请被拒";//企业支付申请被拒绝

    public static final String STATUS_DES_UNKNOW = "无效状态";//已取消


    /***************************不同的订单状态*******************************************/
    public static final int STATUS_REFUND = -1;//已退款
    public static final int STATUS_WAITE_PAY = 0;//待支付
    public static final int STATUS_HAS_PAY = 1;//订单已支付（暂时不使用）
    public static final int STATUS_WAITE_START = 2;//待发车
    public static final int STATUS_IN_TRANSIT = 3;//运输中
    public static final int STATUS_HAS_ARRIVED = 4;//已到达
    public static final int STATUS_RECEIPT = 5;//已收货
    public static final int STATUS_PAY_CHECK = 6;//支付审核中
    public static final int STATUS_PAY_REFUSE = 7;//申请被拒
    public static final int STATUS_HAS_CANCLE = 20;//已取消


    /**
     * 根据当前的状态值获取不同的状态状态,此方法是为了防止订单状态数据更改，因此更改为自己的状态，后期不需要再关心该字段
     *
     * @param status
     * @return
     */
    public static OrderDetailItemControl.OrderState getStatus(int status) {
        OrderDetailItemControl.OrderState statu;
        switch (status) {
            case STATUS_REFUND://  =-1;//已退款
                statu = OrderDetailItemControl.OrderState.REFUND;
                break;
            case STATUS_WAITE_PAY://  =0;//待支付
                statu = OrderDetailItemControl.OrderState.WAITE_PAY;
                break;
            case STATUS_HAS_PAY://  =1;//订单已支付（暂时不使用）
                statu = OrderDetailItemControl.OrderState.HAS_PAY;
                break;
            case STATUS_WAITE_START://      =2;//待发车
                statu = OrderDetailItemControl.OrderState.WAITE_START;
                break;
            case STATUS_IN_TRANSIT://  =3;//运输中
                statu = OrderDetailItemControl.OrderState.IN_TRANSIT;
                break;
            case STATUS_HAS_ARRIVED://  =4;//已到达
                statu = OrderDetailItemControl.OrderState.HAS_ARRIVED;
                break;
            case STATUS_RECEIPT://  =5;//已收货
                statu = OrderDetailItemControl.OrderState.RECEIPT;
                break;
            case STATUS_PAY_CHECK://  =6;//支付审核中
                statu = OrderDetailItemControl.OrderState.PAY_CHECK;
                break;
            case STATUS_PAY_REFUSE://  =7;//支付申请北拒绝
                statu = OrderDetailItemControl.OrderState.PAY_REFUSE;
                break;
            case STATUS_HAS_CANCLE://  =20;//已取消
                statu = OrderDetailItemControl.OrderState.HAS_CANCLE;
                break;
            default:
                statu = OrderDetailItemControl.OrderState.UNKNOW;
                break;
        }
        return statu;
    }


    /**
     * 根据订单状态获取到订单相应的 状态信息
     *
     * @param state
     * @return
     */
    public static String getOrderStateDes(OrderDetailItemControl.OrderState state) {
        String stateMsg;
        if (state == null) {
            return STATUS_DES_UNKNOW;
        }
        switch (state) {

            case REFUND://退款
                stateMsg = STATUS_DES_REFUND;
                break;
            case WAITE_PAY://待支付
                stateMsg = STATUS_DES_WAITE_PAY;
                break;

            case HAS_PAY://订单已支付
                stateMsg = STATUS_DES_HAS_PAY;
                break;
            case WAITE_START://待发车
                stateMsg = STATUS_DES_WAITE_START;
                break;
            case IN_TRANSIT://运输中
                stateMsg = STATUS_DES_IN_TRANSIT;
                break;
            case HAS_ARRIVED://已收货
                stateMsg = STATUS_DES_HAS_ARRIVED;
                break;
            case RECEIPT://已收货
                stateMsg = STATUS_DES_RECEIPT;
                break;
            case PAY_CHECK://企业支付申请中
                stateMsg = STATUS_DES_PAY_CHECK;
                break;
            case PAY_REFUSE://企业支付申请被拒绝
                stateMsg = STATUS_DES_PAY_REFUSE;
                break;
            case HAS_CANCLE://已取消
                stateMsg = STATUS_DES_HAS_CANCLE;
                break;
            case UNKNOW://未知状态
            default:
                stateMsg = STATUS_DES_UNKNOW;
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
        } else {
            return "";
        }
    }


}
