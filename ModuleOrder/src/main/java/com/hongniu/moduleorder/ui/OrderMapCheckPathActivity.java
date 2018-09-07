package com.hongniu.moduleorder.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.navi.enums.LaneAction;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceListener;
import com.amap.api.trace.TraceLocation;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.widget.order.OrderDetailItem;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.entity.LocationBean;
import com.hongniu.moduleorder.entity.PathBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.sang.common.event.BusFactory;
import com.sang.common.net.error.NetException;
import com.sang.common.net.rx.BaseObserver;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.thirdlibrary.map.MarkUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 查看轨迹
 */
@Route(path = ArouterParamOrder.activity_map_check_path)
public class OrderMapCheckPathActivity extends BaseActivity implements TraceListener {


    private MapView mapView;

    private OrderDetailItem item;
    private AMap aMap;
    private LBSTraceClient mTraceClient;
    private PolylineOptions lineOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_check_path);
        initView();
        initData();
        initListener();
        mapView.onCreate(savedInstanceState);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void initView() {
        super.initView();
        mapView = findViewById(R.id.map);
        aMap = mapView.getMap();
        item = findViewById(R.id.item_order);
        item.hideButton(true);


    }


    @Override
    protected void initData() {
        super.initData();
        setToolbarTitle("查看轨迹");
        mTraceClient = LBSTraceClient.getInstance(this);
        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
        BitmapDescriptor mRedTexture = BitmapDescriptorFactory
                .fromResource(R.mipmap.map_line);
        textureList.add(mRedTexture);
        List<Integer> textureIndexs = new ArrayList<Integer>();
        textureIndexs.add(0);
          lineOption = new PolylineOptions()

                .setCustomTextureList(textureList)
                .setCustomTextureIndex(textureIndexs)
                .setUseTexture(true)
                .width(DeviceUtils.dip2px(mContext, 5));
    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderEvent.CheckPathEvent event) {
        if (event != null && event.getBean() != null) {
            final OrderDetailBean bean = event.getBean();
            item.setIdentity(event.getRoaleState());
            item.setInfor(bean);
            item.postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawPath(bean);

                }
            }, 200);

        }
        BusFactory.getBus().removeStickyEvent(event);

    }

    private void drawPath(final OrderDetailBean bean) {

        MarkUtils.addMark(aMap,
                BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), com.sang.thirdlibrary.R.mipmap.start))
                , bean.getStartLatitude(), bean.getStartLongitude()
        );
        MarkUtils.addMark(aMap,
                BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), com.sang.thirdlibrary.R.mipmap.end))
                , bean.getDestinationLatitude(), bean.getDestinationLongitude()
        );

        drawPathWithTrace(bean);




    }

    public void moveTo(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 15, 30, 30));
        aMap.moveCamera(mCameraUpdate);
//        aMap.moveCamera(CameraUpdateFactory.scrollBy(0, DeviceUtils.dip2px(mContext, 300)));

    }


    @Override
    public void onRequestFailed(int i, String s) {

        JLog.i(i+">>>>>>>>>>>>>>>");

            onTaskFail(new NetException(i,s),i+"",s+">>"+i);
    }

    @Override
    public void onTraceProcessing(int i, int i1, List<LatLng> list) {

    }

    @Override
    public void onFinished(int i, List<LatLng> list, int i1, int i2) {
            drawPath(list);
            onTaskSuccess();
    }

    /**
     * 绘制轨迹
     * @param linePatch
     */
    private void drawPath(List<LatLng> linePatch){
        JLog.i(linePatch.size()+">>>>");
        lineOption.addAll(linePatch);
        mapView.getMap().addPolyline(lineOption);
   }
    private void drawPathWithTrace(final OrderDetailBean bean){

        HttpOrderFactory.getPath(bean.getId())
                .map(new Function<List<LocationBean>, List<TraceLocation>>() {
                    @Override
                    public List<TraceLocation> apply(List<LocationBean> data) throws Exception {
                        List<TraceLocation> result = new ArrayList<>();
                            if (data != null) {
                                for (LocationBean o : data ) {
                                    if (o.getLatitude() != 0 && o.getLongitude() != 0) {
                                        TraceLocation location=new TraceLocation();
                                        location.setBearing(o.getDirection());
                                        location.setSpeed((float) o.getSpeed());
                                        location.setTime(ConvertUtils.StrToDate(o.getMovingTime(),"yyyy-MM-dd HH:mm:ss").getTime());
                                        location.setLatitude(o.getLatitude());
                                        location.setLongitude(o.getLongitude());
                                        result.add(location);
                                    }
                                }
                            }

                        return result;
                    }
                })
                .map(new Function<List<TraceLocation>, List<TraceLocation>>() {
                    @Override
                    public List<TraceLocation> apply(List<TraceLocation> latLngs) throws Exception {
                        if (!latLngs.isEmpty()) {
                            moveTo(latLngs.get(0).getLatitude(), latLngs.get(0).getLongitude());
                        }
                        return latLngs;
                    }
                })
                .compose(RxUtils.<List<TraceLocation>>getSchedulersObservableTransformer())
                .subscribe(new BaseObserver<List<TraceLocation>>(this) {
                    @Override
                    public void onNext(final List<TraceLocation> result) {
                        super.onNext(result);
                        if (result == null || result.isEmpty()) {
                            showAleart("当前订单暂无位置信息");
                            onTaskSuccess();
                        } else {
                            mTraceClient.queryProcessedTrace(1, result,
                                    LBSTraceClient.TYPE_AMAP, new TraceListener() {
                                        @Override
                                        public void onRequestFailed(int i, String s) {

                                            List<LatLng> list=new ArrayList<>();
                                            for (TraceLocation location : result) {
                                                list.add(new LatLng(location.getLatitude(),location.getLongitude()));
                                            }
                                            drawPath(list);
                                            onTaskSuccess();
                                        }

                                        @Override
                                        public void onTraceProcessing(int i, int i1, List<LatLng> list) {

                                        }

                                        @Override
                                        public void onFinished(int i, List<LatLng> list, int i1, int i2) {
                                            onTaskSuccess();
                                            drawPath(list);
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onComplete() {
//                        super.onComplete();

                    }
                });

    }


   private void drawPathWithNoTrace(final OrderDetailBean bean){
        HttpOrderFactory.getPath(bean.getId())
                .map(new Function<List<LocationBean>, List<LatLng>>() {
                    @Override
                    public List<LatLng> apply(List<LocationBean> data) throws Exception {
                        List<LatLng> result = new ArrayList<>();
                            if (data != null) {
                                for (LocationBean o : data ) {
                                    if (o.getLatitude() != 0 && o.getLongitude() != 0) {
                                        LatLng latLng=new LatLng(o.getLatitude(),o.getLongitude());
                                        result.add(latLng);
                                    }
                                }
                            }

                        return result;
                    }
                })
                .map(new Function<List<LatLng>, List<LatLng>>() {
                    @Override
                    public List<LatLng> apply(List<LatLng> latLngs) throws Exception {
                        if (!latLngs.isEmpty()) {
                            moveTo(latLngs.get(0).latitude, latLngs.get(0).longitude);
                        }
                        return latLngs;
                    }
                })
                .compose(RxUtils.<List<LatLng>>getSchedulersObservableTransformer())
                .subscribe(new BaseObserver<List<LatLng>>(this) {
                    @Override
                    public void onNext(List<LatLng> result) {
                        super.onNext(result);
                        if (result == null || result.isEmpty()) {
                            showAleart("当前订单暂无位置信息");
                        } else {
                            drawPath(result);
                        }
                    }
                });


   }


}
