package com.hongniu.moduleorder.utils;

import android.view.View;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.trace.TraceLocation;
import com.sang.common.utils.JLog;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 作者： ${桑小年} on 2018/8/22.
 * 努力，为梦长留
 * <p>
 * 坐标收集更改的集合
 */
public class LoactionCollectionUtils implements INaviInfoCallback {
    private LoactionCellection instance;
    public Queue<LatLng> loaction = new LinkedList<>();
    private String orderNum;//订单号
    private String carID;//车辆ＩＤ

    private AMapNaviLocation lastLoaction;

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }


    public LoactionCollectionUtils() {
        instance = LoactionCellection.getInstance();

    }

    //
    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }


    //当前位置发生改变
    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
          if (lastLoaction != null && lastLoaction.getBearing() != aMapNaviLocation.getBearing()) {//方向更改
            TraceLocation location = new TraceLocation();
            location.setBearing(aMapNaviLocation.getBearing());
            location.setLatitude(aMapNaviLocation.getCoord().getLatitude());
            location.setLongitude(aMapNaviLocation.getCoord().getLongitude());
            location.setTime(aMapNaviLocation.getTime());
            instance.getList().add(location);
            lastLoaction=aMapNaviLocation;
              JLog.i("收集数据：onLocationChange:" + aMapNaviLocation.getCoord().getLatitude() + ">>" + aMapNaviLocation.getCoord().getLongitude() + ">>"
                      + aMapNaviLocation.getSpeed() + ">>>>");

          } else {//初始化
            lastLoaction = aMapNaviLocation;
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
}