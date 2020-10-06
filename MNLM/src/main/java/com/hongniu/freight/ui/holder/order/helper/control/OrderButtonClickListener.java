package com.hongniu.freight.ui.holder.order.helper.control;

import com.hongniu.freight.entity.OrderInfoBean;

/**
 * 作者：  on 2020/2/8.
 */
public interface OrderButtonClickListener {
    /**
     * 继续付款
     */
    void onPayClick(OrderInfoBean bean);

    /**
     * 取消订单
     */
    void onOrderCancleClick(OrderInfoBean bean);

    /**
     * 差额支付
     */
    void onPayBalanceClick(OrderInfoBean bean);

    /**
     * 购买保险
     */
    void onPayInsuranceClick(OrderInfoBean bean);

    /**
     * 查看保单
     */
    void onCheckInsuranceClick(OrderInfoBean bean);

    /**
     * 查看轨迹
     */
    void onCheckTrackClick(OrderInfoBean bean);


    /**
     * 确认收货
     */
    void onEntryReceiveClick(OrderInfoBean bean);

    /**
     * 确认接单
     */
    void onEntryReceiveOrderClick(OrderInfoBean bean);

    /**
     * 评价
     */
    void onEvaluateClick(OrderInfoBean bean);

    /**
     * 立即派车
     */
    void onSendOrderClick(OrderInfoBean bean);

    /**
     * 发布找车
     */
    void onFindCarClick(OrderInfoBean bean);

    /**
     * 重新派单
     */
    void onReSendOrderClick(OrderInfoBean bean);

    /**
     * 我要接单
     */
    void onReceiveOrderClick(OrderInfoBean bean);

    /**
     * 开始发车
     */
    void onStartCarClick(OrderInfoBean bean);

    /**
     * 确认到达
     */
    void onEntryArriveClick(OrderInfoBean bean);

    /**
     * 查看路线
     */
    void onQueryPathClick(OrderInfoBean bean);
    /**
     * 修改订单
     */
    void onOrderModify(OrderInfoBean bean);
    /**
     * 上传回单
     */
    void onUpLoadReceipts(OrderInfoBean bean);
    /**
     * 查看回单
     */
    void onCheckReceipts(OrderInfoBean bean);

}
