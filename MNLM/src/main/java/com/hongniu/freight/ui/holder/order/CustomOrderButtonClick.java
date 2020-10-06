package com.hongniu.freight.ui.holder.order;

import android.text.TextUtils;
import android.widget.TextView;

import com.hongniu.freight.config.OrderButtonConfig;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.order.helper.control.OrderButtonClickListener;

/**
 * 作者：  on 2020/2/8.
 */
public class CustomOrderButtonClick implements OrderButtonClickListener {
    OrderButtonClickListener listener;
    private String msg;

    public CustomOrderButtonClick(OrderButtonClickListener listener) {
        this.listener = listener;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 当按钮点击的时候，根据按钮上的数据进行点击处理
     *
     * @param msg
     */
    public void performClick(String msg, OrderInfoBean infoBean) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }


        if (TextUtils.equals(msg, OrderButtonConfig.ORDER_CANCEL)) {
            //取消订单
            onOrderCancleClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.PAY)) {
            //继续付款
            onPayClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.PAY_BALANCE)) {
            //支付差额
            onPayBalanceClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.PAY_INSURANCE)) {
            //购买保险
            onPayInsuranceClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.CHECK_INSURANCE)) {
            //查看保单
            onCheckInsuranceClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.QUERY_TRACK)) {
            //查看轨迹
            onCheckTrackClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.ENTRY_RECEIVE)) {
            //确认收货
            onEntryReceiveClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.ENTRY_RECEIVE_ORDER)) {
            //确认接单
            onEntryReceiveOrderClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.EVALUATE)) {
            //评价
            onEvaluateClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.SEND_ORDER)) {
            //立即派车
            onSendOrderClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.FIND_CAR)) {
            //发布找车
            onFindCarClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.RE_SEND_CARD)) {
            //重新派单
            onReSendOrderClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.RECEIVE_ORDER)) {
            //我要接单
            onReceiveOrderClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.START_CAR)) {
            //开始发车
            onStartCarClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.ENTRY_ARRIVE)) {
            //确认到达
            onEntryArriveClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.QUERY_PATH)) {
            //查看路线
            onQueryPathClick(infoBean);
        } else if (TextUtils.equals(msg, OrderButtonConfig.ORDER_MODIFY)) {
            //修改订单
            onOrderModify(infoBean);
        }else if (TextUtils.equals(msg, OrderButtonConfig.UPDATE_RECEIPT)) {
            //上传回单
            onUpLoadReceipts(infoBean);
        }else if (TextUtils.equals(msg, OrderButtonConfig.CHECK_RECEIPT)) {
            //查看回单
            onCheckReceipts(infoBean);
        }
    }

    /**
     * 继续付款
     *
     * @param bean
     */
    @Override
    public void onPayClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onPayClick(bean);
        }
    }

    /**
     * 取消订单
     *
     * @param bean
     */
    @Override
    public void onOrderCancleClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onOrderCancleClick(bean);
        }
    }

    /**
     * 差额支付
     *
     * @param bean
     */
    @Override
    public void onPayBalanceClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onPayBalanceClick(bean);
        }
    }

    /**
     * 购买保险
     *
     * @param bean
     */
    @Override
    public void onPayInsuranceClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onPayInsuranceClick(bean);
        }
    }

    /**
     * 查看保单
     *
     * @param bean
     */
    @Override
    public void onCheckInsuranceClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onCheckInsuranceClick(bean);
        }
    }

    /**
     * 查看轨迹
     *
     * @param bean
     */
    @Override
    public void onCheckTrackClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onCheckTrackClick(bean);
        }
    }

    /**
     * 确认收货
     *
     * @param bean
     */
    @Override
    public void onEntryReceiveClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onEntryReceiveClick(bean);
        }
    }

    /**
     * 确认接单
     *
     * @param bean
     */
    @Override
    public void onEntryReceiveOrderClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onEntryReceiveOrderClick(bean);
        }
    }

    /**
     * 评价
     *
     * @param bean
     */
    @Override
    public void onEvaluateClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onEvaluateClick(bean);
        }
    }

    /**
     * 立即派车
     *
     * @param bean
     */
    @Override
    public void onSendOrderClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onSendOrderClick(bean);
        }
    }

    /**
     * 发布找车
     *
     * @param bean
     */
    @Override
    public void onFindCarClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onFindCarClick(bean);
        }
    }

    /**
     * 重新派单
     *
     * @param bean
     */
    @Override
    public void onReSendOrderClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onReSendOrderClick(bean);
        }
    }

    /**
     * 我要接单
     *
     * @param bean
     */
    @Override
    public void onReceiveOrderClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onReceiveOrderClick(bean);
        }
    }

    /**
     * 开始发车
     *
     * @param bean
     */
    @Override
    public void onStartCarClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onStartCarClick(bean);
        }
    }

    /**
     * 确认到达
     *
     * @param bean
     */
    @Override
    public void onEntryArriveClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onEntryArriveClick(bean);
        }
    }

    /**
     * 查看路线
     *
     * @param bean
     */
    @Override
    public void onQueryPathClick(OrderInfoBean bean) {
        if (listener != null) {
            listener.onQueryPathClick(bean);
        }
    }

    /**
     * 修改订单
     *
     * @param bean
     */
    @Override
    public void onOrderModify(OrderInfoBean bean) {
        if (listener != null) {
            listener.onOrderModify(bean);
        }
    }

    /**
     * 上传回单
     *
     * @param bean
     */
    @Override
    public void onUpLoadReceipts(OrderInfoBean bean) {
        if (listener != null) {
            listener.onUpLoadReceipts(bean);
        }
    }

    /**
     * 查看回单
     *
     * @param bean
     */
    @Override
    public void onCheckReceipts(OrderInfoBean bean) {
        if (listener != null) {
            listener.onCheckReceipts(bean);
        }
    }
}
