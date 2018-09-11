package com.hongniu.moduleorder.utils;

import android.view.View;

import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.moduleorder.control.OrderEvent;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.JLog;

/**
 * 作者： ${桑小年} on 2018/8/22.
 * 努力，为梦长留
 * <p>
 * 坐标收集更改的集合
 */
public class LoactionCollectionUtils implements INaviInfoCallback {

    private OrderDetailBean orderInfor;


    public LoactionCollectionUtils() {

    }

    //
    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }


    private float currentBear;//当前方向

    //当前位置发生改变
    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        float bearing = aMapNaviLocation.getBearing();
        if (currentBear != bearing) {
            currentBear = bearing;
            Event.UpLoaction upLoaction = new Event.UpLoaction(aMapNaviLocation.getCoord().getLatitude(), aMapNaviLocation.getCoord().getLongitude());
            upLoaction.bearing = aMapNaviLocation.getBearing();
            upLoaction.movingTime = aMapNaviLocation.getTime();
            upLoaction.speed = aMapNaviLocation.getSpeed();
            BusFactory.getBus().postSticky(upLoaction);
        }
    }

    //到达目的地
    @Override
    public void onArriveDestination(boolean b) {
        JLog.i("onArriveDestination:" + b);
    }

    //开始导航
    @Override
    public void onStartNavi(int i) {
        JLog.i("onStartNavi:" + i);
        if (orderInfor != null) {
            OrderEvent.UpLoactionEvent upLoactionEvent = new OrderEvent.UpLoactionEvent();
            upLoactionEvent.start = true;
            upLoactionEvent.orderID = orderInfor.getId();
            upLoactionEvent.cardID = orderInfor.getCarId();
            upLoactionEvent.destinationLatitude = orderInfor.getDestinationLatitude();
            upLoactionEvent.destinationLongitude = orderInfor.getDestinationLongitude();
            BusFactory.getBus().post(upLoactionEvent);
        }
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    //退出页面
    @Override
    public void onExitPage(int i) {
        JLog.i("onExitPage:" + i);
    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    public void setOrderInfor(OrderDetailBean orderInfor) {
        this.orderInfor = orderInfor;
    }
}
