package com.hongniu.baselibrary.widget.order;


import com.hongniu.baselibrary.entity.OrderDetailBean;

import java.util.Date;
import java.util.Random;

/**
 * 作者： ${PING} on 2018/8/2.
 */
public class OrderUtils {
    public static String getOrderState(OrderDetailItemControl.OrderState state) {
        String stateMsg;
        if (state==null){
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


    public static String getRoleState( OrderDetailItemControl.RoleState roleState) {
        String role;
        if (roleState==null){
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


    public static OrderDetailBean creatBean(int status, boolean insurance){
        OrderDetailBean orderBean = new OrderDetailBean();
        orderBean.setOrderNum("80080018000");//订单号
        orderBean.setDeliverydate(new Date().getTime());//发货日期
        orderBean.setStratPlaceInfo("上海虹桥机场国际物流中心");//发车地点
        orderBean.setDestinationInfo("青岛市国际物流中心");//目的地
        orderBean.setMoney("1200");//运费
        orderBean.setDepartNum("21554812154");//发车编号
        orderBean.setCarnum("沪A666666");//车牌号
        orderBean.setUserName(insurance?"有保险车主":"无保险车主");//车主
        orderBean.setInsurance(insurance);
        orderBean.setUserPhone("15515846532");//车主手机
        orderBean.setGoodName("医疗器材");//货物名称
        orderBean.setDrivername("杨先生");//司机
        orderBean.setDrivermobile("16575545654");//司机手机号
        orderBean.setStatus(status);
        return orderBean;

    }


}
