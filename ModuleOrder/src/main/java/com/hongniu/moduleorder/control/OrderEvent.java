package com.hongniu.moduleorder.control;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.PoiItem;
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
     * 订单更新
     */
    public static class OrderUpdate implements IBus.IEvent {

    }

    public static class MapNavigationEvent implements IBus.IEvent {
        LatLng start;

        LatLng end;

        public void setStart(double latitude, double longitude) {
            start = new LatLng(latitude, longitude);
        }

        public void setEnd(double latitude, double longitude) {
            end = new LatLng(latitude, longitude);
        }

        public LatLng getStart() {
            return start;
        }

        public LatLng getEnd() {
            return end;
        }
    }


    /**
     * 订单支付
     */
    public static class PayOrder implements IBus.IEvent {
        public String orderID;//订单号
        public float money;//运费金额
        public boolean insurance;//是否购买保险，true购买

    }
}
