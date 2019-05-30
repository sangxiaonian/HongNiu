package com.hongniu.baselibrary.widget.order;

import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.widget.order.helper.ButtonInforBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/7.
 * -1 已退款 0 待支付 1订单已支付（暂时无效） 2待发车 3运输中 4已到达 5已收货 20 已取消
 */
public class OrderDetailItemControl {


    /**
     * 订单 当前所处的支付状态
     */
    public enum OrderState {
        REFUND(-1,"已退款"),//退款
        WAITE_PAY(0,"待支付"),//待支付
        HAS_PAY(1,"订单已支付"),//订单已支付
        WAITE_START(2,"待发车"),//待发车
        IN_TRANSIT(3,"运输中"),//运输中
        HAS_ARRIVED(4,"已到达"),//已到达
        RECEIPT(5,"已收货"),//已收货
        PAY_CHECK(6,"支付审核中"),//企业支付，支付申请中
        PAY_REFUSE(7,"申请被拒"),//企业支付，支付被拒绝
        ARRIVED_PAY(8,"收货人已支付"),//到达后收货人已支付
        ARRIVED_WAITE_PAY(9,"收货人待支付"),//到达后收货人待支付

        HAS_CANCLE(20,"已取消"),//已取消
        UNKNOW(999,"未知状态");//未知状态


        private int state;
        private String des;

        OrderState(int state, String des) {
            this.state = state;
            this.des = des;

        }

        public int getState() {
            return state;
        }

        public String getDes() {
            return des;
        }
    }

    /**
     * 当前角色
     */
    public enum RoleState {
        CAR_OWNER,//车主
        CARGO_OWNER,//货主
        DRIVER,//司机
        CARGO_RECEIVE;//收货人


    }


    /**
     * 作者： ${PING} on 2018/8/2.
     * <p>
     * 订单处理的辅助类接口，用来对货主、车主、司机不同角色的不同状态订单进行处理
     */
    public interface IOrderItemHelper {

        /**
         * 是否购买保险
         *
         * @param insurance true 已经购买
         * @return
         */
        IOrderItemHelper setInsurance(boolean insurance);

        /**
         * 是否存在货单
         *
         * @param hasGoodsImage true 存在
         * @return
         */
        IOrderItemHelper setHasGoodsImage(boolean hasGoodsImage);

        /**
         * 是否购存在回单
         *
         * @param hasReceiptImage true 存在
         * @return
         */
        IOrderItemHelper setHasReceiptImage(boolean hasReceiptImage);


        /**
         * 获取订单当前状态
         *
         * @return
         */
        String getOrderState();

        /**
         * 获取要显示的按钮类型
         */
        List<ButtonInforBean> getButtonInfors();

        OrderDetailItemControl.IOrderItemHelper setHasPay(boolean hasPay);
    }


    /**
     * 订单详情条目按钮被点击时候的点击事件
     */
    public interface OnOrderDetailBtClickListener {


        /**
         * "取消订单";
         *
         * @param orderBean
         */
        void onOrderCancle(OrderDetailBean orderBean);


        /**
         * 购买保险
         *
         * @param orderBean
         */
        void onOrderBuyInsurance(OrderDetailBean orderBean);

        /**
         * ORDER_PAY 继续付款
         *
         * @param orderBean
         */
        void onOrderPay(OrderDetailBean orderBean);

        /**
         * ORDER_CHECK_INSURANCE 查看保单
         *
         * @param orderBean
         */
        void onCheckInsruance(OrderDetailBean orderBean);

        /**
         * ORDER_CHECK_PATH 查看轨迹
         *
         * @param orderBean
         */
        void onCheckPath(OrderDetailBean orderBean);

        /**
         * ORDER_ENTRY_ORDER 确认收货
         *
         * @param orderBean
         */
        void onEntryOrder(OrderDetailBean orderBean);

        /**
         * ORDER_START_CAR           ="开始发车";
         *
         * @param orderBean
         */
        void onStartCar(OrderDetailBean orderBean);

        /**
         * ORDER_CHECK_ROUT          ="查看路线";
         *
         * @param orderBean
         */
        void onCheckRout(OrderDetailBean orderBean);

        /**
         * ORDER_CHECK_ROUT          ="货车导航";
         *
         * @param orderBean
         */
        void onTruchGuid(OrderDetailBean orderBean);

        /**
         * ORDER_ENTRY_ARRIVE        ="确认到达";
         *
         * @param orderBean
         */
        void onEntryArrive(OrderDetailBean orderBean);

        /**
         * ORDER_CHECK_RECEIPT        ="查看回单";
         *
         * @param orderBean
         */
        void onCheckReceipt(OrderDetailBean orderBean);


        /**
         * ORDER_UP_RECEIPT        ="上传回单";
         *
         * @param orderBean
         */
        void onUpReceipt(OrderDetailBean orderBean);

        /**
         * ORDER_CHANGE_RECEIPT        ="修改回单";
         *
         * @param orderBean
         */
        void onChangeReceipt(OrderDetailBean orderBean);

        /**
         * ORDER_CHANGE        ="修改订单";
         *
         * @param orderBean
         */
        void onChangeOrder(OrderDetailBean orderBean);

        /**
         * ORDER_CHECK_GOODS        ="查看货单";
         *
         * @param orderBean
         */
        void onCheckGoods(OrderDetailBean orderBean);

        /**
         * ORDER_CHECK_GOODS        ="被拒原因";
         *
         * @param orderBean
         */
        void onPayRefuse(OrderDetailBean orderBean);


        /**
         * ORDER_ENTRY_AND_PAY_ORDER 确认收货并支付订单
         * @param orderBean
         */
        void onEntryAndPay(OrderDetailBean orderBean);
    }


}
