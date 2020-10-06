package com.hongniu.freight.ui.holder.order.helper;

import com.hongniu.freight.config.OrderButtonConfig;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.ui.holder.order.helper.control.HelperControl;

import java.util.Map;
import java.util.TreeMap;

/**
 * 作者：  on 2020/2/8.
 */
public class CYRHelper extends BaseHelper {




    /**
     * 根据状态，获取到指定荒唐的按钮
     *
     * @param status
     * @return
     */
    @Override
    public Map<String, Integer> getButtons(int status) {
        Map<String,Integer> map=new TreeMap<>();
        if (status == Status.WAITE_PAY.getStatus()) {
//            result = "待支付";
            //不显示订单
        } else if (status == Status.WAITE_RECEIVING_ORDER.getStatus()) {
//            result = "待接单";
            //不显示订单
        } else if (status == Status.WAITE_PAY_BALANCE.getStatus()) {
//            result = "差额支付中";
            //不显示订单
        } else if (status == Status.WAITE_CAR.getStatus()) {
//            result = "待派车";
            //不显示订单
        } else if (status == Status.FIND_CAR.getStatus()) {
//            result = "找车中";
            map.put(OrderButtonConfig.RECEIVE_ORDER,1);
        } else if (status == Status.WAITE_DEPART_NO_INSURANCE.getStatus()) {
//            result = "待发车(未买保险)";
//            不显示订单
        } else if (status == Status.IN_TRANSIT.getStatus()) {
//            result = "运输中";
            map.put(OrderButtonConfig.QUERY_TRACK,1);
        } else if (status == Status.ARRIVE.getStatus()) {
//            result = "已到达";
            map.put(OrderButtonConfig.QUERY_TRACK, 1);
            if (hasReceiptImage) {
                map.put(OrderButtonConfig.CHECK_RECEIPT, 1);
            }
        } else if (status == Status.RECEIVING.getStatus()) {
//            result = "已收货";
            map.put(OrderButtonConfig.QUERY_TRACK, 1);
            if (hasReceiptImage) {
                map.put(OrderButtonConfig.CHECK_RECEIPT, 1);
            }
            map.put(OrderButtonConfig.EVALUATE,1);
        } else {
//            result = "未知状态";
        }
        return map;
    }
}
