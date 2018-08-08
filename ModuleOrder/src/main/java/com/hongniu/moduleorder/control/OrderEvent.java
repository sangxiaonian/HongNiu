package com.hongniu.moduleorder.control;

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
}
