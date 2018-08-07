package com.hongniu.baselibrary.widget.order;

/**
 * 作者： ${PING} on 2018/8/7.
 */
public class OrderDetailItemControl {


    public static final String ORDER_CANCLE = "取消订单";
    public static final String ORDER_PAY = "继续付款";
    public static final String ORDER_BUY_INSURANCE = "购买保险";
    public static final String ORDER_CHECK_INSURANCE = "查看保单";
    public static final String ORDER_CHECK_PATH = "查看轨迹";
    public static final String ORDER_ENTRY_ORDER = "确认收货";
    public static final String ORDER_START_CAR = "开始发车";
    public static final String ORDER_CHECK_ROUT = "查看路线";
    public static final String ORDER_ENTRY_ARRIVE = "确认到达";


    /**
     * 订单 当前所处的支付状态
     */
    public enum OrderState {
        WAITE_PAY,//待支付
        WAITE_START,//待发车
        IN_TRANSIT,//运输中
        HAS_ARRIVED,//已到达
        RECEIPT,//已收货

    }

    /**
     * 当前角色
     */
    public enum RoleState {
        CAR_OWNER,//车主
        CARGO_OWNER,//货主
        DRIVER;//司机

    }

    /**
     * 作者： ${PING} on 2018/8/2.
     * <p>
     * 订单处理的辅助类接口，用来对货主、车主、司机不同角色的不同状态订单进行处理
     */
    public interface IOrderItemHelper {


        int getLeftVisibility();

        int getRightVisibility();


        /**
         * 获取左侧按钮信息
         */
        String getBtLeftInfor();

        /**
         * 获取右侧
         *
         * @return
         */
        String getBtRightInfor();

        /**
         * 获取订单当前状态
         *
         * @return
         */
        String getOrderState();


    }


    /**
     * 订单详情条目按钮被点击时候的点击事件
     */
    public interface OnOrderDetailBtClickListener {


        /**
         * "取消订单";
         */
        void onOrderCancle();


        /**
         * 购买保险
         */
        void onOrderBuyInsurance();

        /**
         * ORDER_PAY 继续付款
         */
        void onOrderPay();

        /**
         * ORDER_CHECK_INSURANCE 查看保单
         */
        void onCheckInsruance();

        /**
         * ORDER_CHECK_PATH 查看轨迹
         */
        void onCheckPath();

        /**
         * ORDER_ENTRY_ORDER 确认收货
         */
        void onEntryOrder();

        /**
         * ORDER_START_CAR           ="开始发车";
         */
        void onStartCar();

        /**
         * ORDER_CHECK_ROUT          ="查看路线";
         */
        void onCheckRout();

        /**
         * ORDER_ENTRY_ARRIVE        ="确认到达";
         */
        void onEntryArrive();


    }


}
