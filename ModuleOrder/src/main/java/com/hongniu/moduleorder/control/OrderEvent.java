package com.hongniu.moduleorder.control;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.sang.common.event.IBus;

/**
 * 作者： ${PING} on 2018/8/8.
 */
public class OrderEvent {

    public static class StartLoactionEvent implements IBus.IEvent {
        public PoiItem t;

        public StartLoactionEvent(PoiItem t) {
            this.t = t;
        }
    }

    public static class EndLoactionEvent implements IBus.IEvent {
        public PoiItem t;

        public EndLoactionEvent(PoiItem t) {
            this.t = t;
        }
    }

    /**
     * 订单更新列表
     */
    public static class OrderUpdate implements IBus.IEvent {
        public OrderDetailItemControl.RoleState roleState;//角色

        public OrderUpdate(OrderDetailItemControl.RoleState roleState) {
            this.roleState = roleState;
        }
    }




    /**
     * 订单支付
     */
    public static class PayOrder implements IBus.IEvent {
        public String orderID;//订单ID
        public float money;//运费金额
        public boolean insurance;//是否购买保险，true购买
        public String orderNum;//订单号

    }


    /**
     * 开始或者停止用户信息上传的数据
     */
    public static class UpLoactionEvent implements IBus.IEvent {
        public String cardID;
        public String orderID;
        public double destinationLatitude;
        public double destinationLongitude;

        public boolean start;//true 开始，false 停止

    }


    /**
     * 查看轨迹
     */
    public static class CheckPathEvent implements IBus.IEvent {
        OrderDetailBean bean;
        private OrderDetailItemControl.RoleState roaleState;

        public CheckPathEvent(OrderDetailBean bean) {
            this.bean = bean;
        }

        public void setBean(OrderDetailBean bean) {
            this.bean = bean;
        }


        public OrderDetailBean getBean() {
            return bean;
        }

        public void setRoaleState(OrderDetailItemControl.RoleState roaleState) {
            this.roaleState = roaleState;
        }

        public OrderDetailItemControl.RoleState getRoaleState() {
            return roaleState;
        }
    }


}
