package com.sang.thirdlibrary.map;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.sang.common.utils.ToastUtils;
import com.sang.thirdlibrary.map.utils.ErrorInfo;
import com.sang.thirdlibrary.map.voice.TTSController;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/9.
 */
public class NavigationUtils implements AMapNaviListener, AMapNaviViewListener {


    //起点坐标
    protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    //终点坐标
    protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
    private final TTSController mTtsManager;
    private final Context context;
    protected List<NaviLatLng> mWayPointList;//途经地点
    private AMapNavi mAMapNavi;

    private int type = NaviType.GPS;//导航模式


    private MapControl.OnMapInitListener listener;

    public void setListener(MapControl.OnMapInitListener listener) {
        this.listener = listener;
    }

    public NavigationUtils(Context context) {
        this.context=context;
        mTtsManager = TTSController.getInstance(context.getApplicationContext());
        mTtsManager.init();
        mAMapNavi = AMapNavi.getInstance(context.getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.addAMapNaviListener(mTtsManager);
        mAMapNavi.setUseInnerVoice(true);
    }

    public void setDebug() {

        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(175);
        type = NaviType.EMULATOR;
    }


    public void setWayPointList(List<NaviLatLng> mWayPointList) {
        this.mWayPointList = mWayPointList;
    }

    /**
     * 设置当前计算起点坐标
     *
     * @param latitude
     * @param longitude
     */
    public void setStartPoint(double latitude, double longitude) {
        NaviLatLng latLng = new NaviLatLng(latitude, longitude);
        sList.clear();
        sList.add(latLng);
    }

    /**
     * 添加当前计算起点坐标
     *
     * @param latitude
     * @param longitude
     */
    public void addStartPoint(double latitude, double longitude) {
        NaviLatLng latLng = new NaviLatLng(latitude, longitude);
        sList.add(latLng);
    }


    /**
     * 设置当前计算终点坐标
     *
     * @param latitude
     * @param longitude
     */
    public void setEndtPoint(double latitude, double longitude) {
        NaviLatLng latLng = new NaviLatLng(latitude, longitude);
        eList.clear();
        eList.add(latLng);
    }

    /**
     * 添加当前计算终点坐标
     *
     * @param latitude
     * @param longitude
     */
    public void addEdntPoint(double latitude, double longitude) {
        NaviLatLng latLng = new NaviLatLng(latitude, longitude);
        eList.add(latLng);
    }


    public void onResume() {
    }

    public void onPause() {

//        仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
        mTtsManager.stopSpeaking();
//
//        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
//        mAMapNavi.stopNavi();
    }

    public void onDestroy() {
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
        mTtsManager.destroy();
    }


    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //多路径算路成功回调
        if (listener != null) {
            listener.onCalculateRouteSuccess();
        }
    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        //单一路径算路成功回调
        if (listener != null) {
            listener.onCalculateRouteSuccess();
        }
    }

    public void startNavi(){
        mAMapNavi.startNavi(type);

    }


    //地图初始化完成，此处开始设置路径计算策略
    @Override
    public void onInitNaviSuccess() {
        /**
         * 方法:
         *   int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute);
         * 参数:
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         * 说明:
         *      以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         * 注意:
         *      不走高速与高速优先不能同时为true
         *      高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
        if (listener != null) {
            listener.onInitSuccess();
        }
    }

    @Override
    public void onInitNaviFailure() {
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("导航初始化失败");
    }


    @Override
    public void onStartNavi(int type) {
        //开始导航回调
    }

    @Override
    public void onTrafficStatusUpdate() {
        //
    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
        //当前位置回调
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {
        //结束模拟导航
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
        Log.e("dm", "--------------------------------------------");
        Log.i("dm", "路线计算失败：错误码=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
        Log.i("dm", "错误码详细链接见：http://lbs.amap.com/api/android-navi-sdk/guide/tools/errorcode/");
        Log.e("dm", "--------------------------------------------");
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("errorInfo：" + errorInfo + ",Message：" + ErrorInfo.getError(errorInfo));
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航后重新计算路线回调
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
        //到达途径点
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        //GPS开关状态回调
    }

    @Override
    public void onNaviSetting() {
        //底部导航设置点击回调
    }

    @Override
    public void onNaviMapMode(int isLock) {
        //地图的模式，锁屏或锁车
    }

    @Override
    public void onNaviCancel() {
        if (context!=null&&context instanceof Activity) {
            ((Activity) context).finish();
        }
    }


    @Override
    public void onNaviTurnClick() {
        //转弯view的点击回调
    }

    @Override
    public void onNextRoadClick() {
        //下一个道路View点击回调
    }


    @Override
    public void onScanViewButtonClick() {
        //全览按钮点击回调
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
        //过时
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        //已过时
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        //已过时
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
    }

    @Override
    public void hideCross() {
        //隐藏转弯回调
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {
        //显示车道信息

    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
    }


    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            Log.d("wlx", "当前在主辅路过渡");
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("当前在主辅路过渡");

            return;
        }
        if (i == 1) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("当前在主路");

            Log.d("wlx", "当前在主路");
            return;
        }
        if (i == 2) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("当前在辅路");
            Log.d("wlx", "当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //更新交通设施信息
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
    }

    @Override
    public void onPlayRing(int i) {

    }


    @Override
    public void onLockMap(boolean isLock) {
        //锁地图状态发生变化时回调
    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx", "导航页面加载成功");
        Log.d("wlx", "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public void onNaviViewShowMode(int i) {

    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }


    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }


    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }
}
