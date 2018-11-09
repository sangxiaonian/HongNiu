package com.hongniu.baselibrary.widget.order.helper;

import android.text.TextUtils;
import android.view.View;

import com.hongniu.baselibrary.widget.order.CommonOrderUtils;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;

import java.util.ArrayList;
import java.util.List;

import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHANGE_RECEIPT;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_GOODS;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_RECEIPT;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_ROUT;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_ENTRY_ARRIVE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_START_CAR;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_UP_RECEIPT;


/**
 * 作者： ${PING} on 2018/8/2.
 * 司机订单
 */
public class DriveOwnerOrder extends OwnerOrder implements OrderDetailItemControl.IOrderItemHelper {


    public DriveOwnerOrder(OrderDetailItemControl.OrderState state) {
        super(state);
    }

    @Override
    public String getOrderState() {
        return CommonOrderUtils.getOrderStateDes(state);
    }

    /**
     * 获取要显示的按钮类型
     */
    @Override
    public List<ButtonInforBean> getButtonInfors() {

        switch (state) {
            case WAITE_PAY://待支付
                break;
            case WAITE_START://待发车
                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(  new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }

                buttonInfors.add( new ButtonInforBean(1,ORDER_START_CAR));//开始发车

                break;
            case IN_TRANSIT://运输中
                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(  new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }
                buttonInfors.add(  new ButtonInforBean(ORDER_CHECK_ROUT));//查看路线
                buttonInfors.add(new ButtonInforBean(1, ORDER_ENTRY_ARRIVE));//确认到达
                break;
            case HAS_ARRIVED://已到达
                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(  new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }
                if (hasReceiptImage) {//回单存在
                    buttonInfors.add(  new ButtonInforBean(ORDER_CHANGE_RECEIPT));//修改回单
                } else {
                    buttonInfors.add( new ButtonInforBean(1,ORDER_UP_RECEIPT));//上传回单

                }
                break;
            case RECEIPT://已收货
                if (hasGoodsImage) {//如果存在货单
                    buttonInfors.add(  new ButtonInforBean(ORDER_CHECK_GOODS));//查看货单
                }
                if (hasReceiptImage) {//回单存在
                    buttonInfors.add( new ButtonInforBean(ORDER_CHECK_RECEIPT));//查看回单
                }
                break;
            case REFUND://退款
            case UNKNOW://未知状态
                break;
            default:
                break;
        }


        return buttonInfors;
    }


}
