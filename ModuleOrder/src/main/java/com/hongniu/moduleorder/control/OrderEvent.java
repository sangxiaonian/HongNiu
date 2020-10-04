package com.hongniu.moduleorder.control;

import com.amap.api.services.core.PoiItem;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.entity.QueryReceiveBean;
import com.fy.androidlibrary.event.IBus;

/**
 * 作者： ${PING} on 2018/8/8.
 */
public class OrderEvent {


    public static class SearchPioItem implements IBus.IEvent {
        public PoiItem t;
        public String key;

        public SearchPioItem(PoiItem t) {
            this.t = t;
        }
    }

    /**
     * 删除指定位置图片
     */
    public static class DeletedPic implements IBus.IEvent {
        private int position;
        private String path;

        public int getPosition() {
            return position;
        }

        public String getPath() {
            return path;
        }

        public DeletedPic(int position, String path) {
            this.position = position;
            this.path = path;
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


    /**
     * 修改回单
     */
    public static class UpReceiver implements IBus.IEvent {

        public QueryReceiveBean bean;

        public UpReceiver(QueryReceiveBean bean) {
            this.bean = bean;
        }
    }


}
