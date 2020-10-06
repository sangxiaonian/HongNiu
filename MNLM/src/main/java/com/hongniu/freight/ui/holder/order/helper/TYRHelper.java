package com.hongniu.freight.ui.holder.order.helper;

import com.hongniu.freight.config.OrderButtonConfig;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.config.Status;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 作者：  on 2020/2/8.
 * 托运人数据，托运人有平台和个人两种昂区别
 */
public class TYRHelper extends BaseHelper {

    public TYRHelper( ) {
    }


    /**
     * 根据状态，获取到指定荒唐的按钮
     *
     * @param status
     * @return
     */
    @Override
    public Map<String, Integer> getButtons(int status) {
        return getPersonalMap(status);
    }

    private Map<String, Integer> getPersonalMap(int status) {
        Map<String, Integer> map = new LinkedHashMap<>();
        if (status == Status.WAITE_PAY.getStatus()||status == Status.AUDIT_FAIL.getStatus()) {
//            result = "待支付";
            map.put(OrderButtonConfig.ORDER_CANCEL, 0);
            map.put(OrderButtonConfig.ORDER_MODIFY, 1);
            map.put(OrderButtonConfig.PAY, 1);
        } else if (status == Status.WAITE_RECEIVING_ORDER.getStatus()) {
//            result = "待接单";
            //不显示订单
            map.put(OrderButtonConfig.ORDER_CANCEL, 0);
        } else if (status == Status.WAITE_PAY_BALANCE.getStatus()) {
//            result = "差额支付中";
            map.put(OrderButtonConfig.PAY_BALANCE, 1);
        } else if (status == Status.WAITE_CAR.getStatus()) {
//            result = "待派车";
            //不显示订单
        } else if (status == Status.FIND_CAR.getStatus()) {
//            result = "找车中";
        } else if (status == Status.WAITE_DEPART_NO_INSURANCE.getStatus()) {
            if (isInsurance) {
//            result = "待发车(已买保险)";
                map.put(OrderButtonConfig.CHECK_INSURANCE, 0);
            } else {
//            result = "待发车(未买保险)";
                map.put(OrderButtonConfig.PAY_INSURANCE, 1);

            }
        } else if (status == Status.IN_TRANSIT.getStatus()) {
//            result = "运输中";
            if (isInsurance) {
                map.put(OrderButtonConfig.CHECK_INSURANCE, 0);
            }
            map.put(OrderButtonConfig.QUERY_TRACK, 1);
        } else if (status == Status.ARRIVE.getStatus()) {
//            result = "已到达";
            if (isInsurance) {
                map.put(OrderButtonConfig.CHECK_INSURANCE, 0);
            }

            map.put(OrderButtonConfig.QUERY_TRACK, 1);

            if (hasReceiptImage) {
                map.put(OrderButtonConfig.CHECK_RECEIPT, 1);
            }
            map.put(OrderButtonConfig.ENTRY_RECEIVE, 1);
        } else if (status == Status.RECEIVING.getStatus()) {
//            result = "已收货";
            if (isInsurance) {
                map.put(OrderButtonConfig.CHECK_INSURANCE, 0);
            }
            map.put(OrderButtonConfig.QUERY_TRACK, 1);
            if (hasReceiptImage) {
                map.put(OrderButtonConfig.CHECK_RECEIPT, 1);
            }
            map.put(OrderButtonConfig.EVALUATE, 1);
        } else {
//            result = "未知状态";
        }
        return map;
    }


}
