package com.sang.thirdlibrary.map;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import android.util.SparseArray;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapRestrictionInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.sang.common.utils.ToastUtils;

import java.util.HashMap;

/**
 * 作者： ${PING} on 2018/8/8.
 * <p>
 * 算路使用的 Helper
 */
public class MapCalculateHelper extends BaseMapHelper implements AMap.OnMyLocationChangeListener, AMapNaviListener {


    private AMapNavi mAMapNavi;

    /**
     * 是否是多条道路 ，true 多条道路 false 单条道路
     */
    private boolean singleRoud;

    /**
     * 保存当前算好的路线
     */
    private SparseArray<RouteOverLay> routeOverlays = new SparseArray<RouteOverLay>();

    /**
     * 当前用户选中的路线，在下个页面进行导航
     */
    private int routeIndex;
    /**
     * 路线的权值，重合路线情况下，权值高的路线会覆盖权值低的路线
     **/
    private int zindex = 1;
    /**
     * 路线计算成功标志位
     */
    private boolean calculateSuccess = false;
    private boolean chooseRouteSuccess = false;
    private AMapCalcRouteResult aMapCalcRouteResult;

    public void moveToStart() {
        if (!sList.isEmpty()){
            moveTo(sList.get(0).getLatitude(),sList.get(0).getLongitude());
        }
    }

    public RouteOverLay getAMapNaviViewOptions() {


        return routeOverlays.get(routeIndex);
    }


    private static class InnerMpa {
        public static MapCalculateHelper utils = new MapCalculateHelper();
    }

    public static MapCalculateHelper getInstance() {
        return InnerMpa.utils;
    }

    @Override
    public BaseMapHelper initMap(Context context, MapView mMapView) {
        mAMapNavi = AMapNavi.getInstance(context.getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        return super.initMap(context, mMapView);
    }

    @Override
    public void onMyLocationChange(Location location) {
        super.onMyLocationChange(location);

    }

    /**
     * 是否是多条道路 ，true 多条道路 false 单条道路
     */
    public void setSingleRoud(boolean singleRoud) {
        this.singleRoud = singleRoud;
    }

    /**
     * 开始算路
     * @param carNum 车牌号
     */
    public void calculate(String carNum){
        clearRoute();
        /*
         * strategyFlag转换出来的值都对应PathPlanningStrategy常量，用户也可以直接传入PathPlanningStrategy常量进行算路。
         * 如:mAMapNavi.calculateDriveRoute(mStartList, mEndList, mWayPointList,PathPlanningStrategy.DRIVING_DEFAULT);
         */
        int strategyFlag = 0;
        try {
            strategyFlag = mAMapNavi.strategyConvert(false, false, false, false, singleRoud);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strategyFlag >= 0) {
            String carNumber = carNum;
            AMapCarInfo carInfo = new AMapCarInfo();
            if (!TextUtils.isEmpty(carNum)) {
                //设置车牌
                carInfo.setCarNumber(carNumber);
                //设置车牌是否参与限行算路
                carInfo.setRestriction(true);
            }

            mAMapNavi.setCarInfo(carInfo);
            mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategyFlag);
        }


    }

    private void drawRoutes(int routeId, AMapNaviPath path, Context context) {
        calculateSuccess = true;
        aMap.moveCamera(CameraUpdateFactory.changeTilt(0));
        RouteOverLay routeOverLay = new RouteOverLay(aMap, path, context);
        routeOverLay.setTrafficLine(false);
        routeOverLay.addToMap();
        routeOverlays.put(routeId, routeOverLay);
    }

    public void changeRoute() {
        if (!calculateSuccess) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请先算路");
            return;
        }
        /**
         * 计算出来的路径只有一条
         */
        if (routeOverlays.size() == 1) {
            chooseRouteSuccess = true;
            //必须告诉AMapNavi 你最后选择的哪条路
            mAMapNavi.selectRouteId(routeOverlays.keyAt(0));
            return;
        }

        if (routeIndex >= routeOverlays.size()) {
            routeIndex = 0;
        }
        int routeID = routeOverlays.keyAt(routeIndex);
        //突出选择的那条路
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            routeOverlays.get(key).setTransparency(0.4f);
        }
        routeOverlays.get(routeID).setTransparency(1);
        /**把用户选择的那条路的权值弄高，使路线高亮显示的同时，重合路段不会变的透明**/
        routeOverlays.get(routeID).setZindex(zindex++);

        //必须告诉AMapNavi 你最后选择的哪条路
        mAMapNavi.selectRouteId(routeID);
        routeIndex++;
        chooseRouteSuccess = true;

        /**选完路径后判断路线是否是限行路线**/
        AMapRestrictionInfo info = mAMapNavi.getNaviPath().getRestrictionInfo();
        if (!TextUtils.isEmpty(info.getRestrictionTitle())) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show(info.getRestrictionTitle());
        }
    }

    /**
     * 清除当前地图上算好的路线
     */
    private void clearRoute() {
        for (int i = 0; i < routeOverlays.size(); i++) {
            RouteOverLay routeOverlay = routeOverlays.valueAt(i);
            routeOverlay.removeFromMap();
        }
        routeOverlays.clear();
    }


    public MapCalculateHelper() {
        super();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /**
         * 当前页面只是展示地图，activity销毁后不需要再回调导航的状态
         */
        mAMapNavi.removeAMapNaviListener(this);
        mAMapNavi.destroy();
        mAMapNavi=null;
        routeOverlays.clear();
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //清空上次计算的路径列表。
        routeOverlays.clear();
        HashMap<Integer, AMapNaviPath> paths = mAMapNavi.getNaviPaths();
        for (int i = 0; i < ints.length; i++) {
            AMapNaviPath path = paths.get(ints[i]);
            if (path != null) {
                drawRoutes(ints[i], path,mContext);
            }
        }

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        routeOverlays.clear();
        this.aMapCalcRouteResult=aMapCalcRouteResult;
        int[] routeid = aMapCalcRouteResult.getRouteid();
        HashMap<Integer, AMapNaviPath> paths = mAMapNavi.getNaviPaths();
        for (int i = 0; i < routeid.length; i++) {
            AMapNaviPath path = paths.get(routeid[i]);
            if (path != null) {
                drawRoutes(routeid[i], path,mContext);
            }
        }
    }


    @Override
    public void onCalculateRouteFailure(int arg0) {
        calculateSuccess = false;
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("计算路线失败，errorcode＝" + arg0);
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    /**
     * @param i
     * @param s
     * @deprecated
     */
    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }



    /**
     * @deprecated
     */
    @Override
    public void onReCalculateRouteForYaw() {

    }

    /**
     * @deprecated
     */
    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    /**
     * @param aMapNaviInfo
     * @deprecated
     */
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    /**
     * @param aMapLaneInfos
     * @param bytes
     * @param bytes1
     * @deprecated
     */
    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }


    @Override
    public void notifyParallelRoad(int i) {

    }

    /**
     * @param aMapNaviTrafficFacilityInfo
     * @deprecated
     */
    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    /**
     * @param trafficFacilityInfo
     * @deprecated
     */
    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }


    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }

}
