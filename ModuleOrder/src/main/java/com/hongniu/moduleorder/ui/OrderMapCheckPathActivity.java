package com.hongniu.moduleorder.ui;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.PolylineOptions;
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
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.sang.common.event.BusFactory;
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

import io.reactivex.functions.Function;

/**
 * 查看轨迹
 */
@Route(path = ArouterParamOrder.activity_map_check_path)
public class OrderMapCheckPathActivity extends BaseActivity {


    private MapView mapView;

    private OrderDetailItem item;
    private AMap aMap;
    private LBSTraceClient mTraceClient;
    private PolylineOptions lineOption;
    private OrderDetailBean bean;


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
                .width(DeviceUtils.dip2px(mContext, 5))
                .color(Color.parseColor("#43BFA3"));


    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderEvent.CheckPathEvent event) {
        if (event != null && event.getBean() != null) {
            bean = event.getBean();
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
        drawPathWithTrace(bean);
    }


    /**
     * 绘制轨迹
     *
     * @param linePatch
     */
    private void drawPath(List<LatLng> linePatch) {



        lineOption.addAll(linePatch);
        mapView.getMap().addPolyline(lineOption);
    }

    /**
     * 携带轨迹纠偏
     * @param bean
     */
    private void drawPathWithTrace(final OrderDetailBean bean) {

        HttpOrderFactory.getPath(bean.getId())
                .map(new Function<List<LocationBean>, List<TraceLocation>>() {
                    @Override
                    public List<TraceLocation> apply(List<LocationBean> data) throws Exception {
                        if (data != null&&!data.isEmpty()) {
                            MarkUtils.addMark(aMap,
                                    BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_carmap_50))
                                    , data.get(data.size()-1).getLatitude(), data.get(data.size()-1).getLongitude()
                            );
                        }

                        LatLngBounds.Builder builder2 = new LatLngBounds.Builder();
                        builder2.include(new LatLng(bean.getStartLatitude(), bean.getStartLongitude()));
                        List<TraceLocation> result = new ArrayList<>();
                        if (data != null) {
                            for (LocationBean o : data) {
                                if (o.getLatitude() != 0 && o.getLongitude() != 0) {
                                    TraceLocation location = new TraceLocation();
                                    location.setBearing(o.getDirection());
                                    location.setSpeed((float) o.getSpeed());
                                    location.setTime(ConvertUtils.StrToDate(o.getMovingTime(), "yyyy-MM-dd HH:mm:ss").getTime());
                                    location.setLatitude(o.getLatitude());
                                    location.setLongitude(o.getLongitude());
                                    result.add(location);
                                    builder2.include(new LatLng(o.getLatitude(),o.getLongitude()));
                                }
                            }
                        }
                        builder2.include(new LatLng(bean.getDestinationLatitude(), bean.getDestinationLongitude()));
                        LatLngBounds latlngBounds = builder2.build();
                        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, DeviceUtils.dip2px(mContext, 50)));

                        return result;
                    }
                })

                .compose(RxUtils.<List<TraceLocation>>getSchedulersObservableTransformer())
                .subscribe(new BaseObserver<List<TraceLocation>>(this) {
                    @Override
                    public void onNext(final List<TraceLocation> result) {
                        super.onNext(result);
                        if (result == null || result.size()<2) {
                            showAleart("当前订单暂无位置信息");
                            onTaskSuccess();
                        } else {
                            mTraceClient.queryProcessedTrace(1, result,
                                    LBSTraceClient.TYPE_AMAP, new TraceListener() {
                                        @Override
                                        public void onRequestFailed(int i, String s) {

                                            List<LatLng> list = new ArrayList<>();
                                            for (TraceLocation location : result) {
                                                list.add(new LatLng(location.getLatitude(), location.getLongitude()));
                                            }
                                            JLog.e("纠偏失败");
                                            drawPath(list);
                                            onTaskSuccess();
                                        }

                                        @Override
                                        public void onTraceProcessing(int i, int i1, List<LatLng> list) {

                                        }

                                        @Override
                                        public void onFinished(int i, List<LatLng> list, int i1, int i2) {
                                            drawPath(list);
                                            onTaskSuccess();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onComplete() {
//                        super.onComplete();
                        drawMark(bean);

                    }
                });

    }


    private void drawPathWithNoTrace(final OrderDetailBean bean) {
        HttpOrderFactory.getPath(bean.getId())
                .map(new Function<List<LocationBean>, List<LatLng>>() {
                    @Override
                    public List<LatLng> apply(List<LocationBean> data) throws Exception {
                        if (!data.isEmpty()) {
                            MarkUtils.addMark(aMap,
                                    BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_carmap_50))
                                    , data.get(data.size()-1).getLatitude(), data.get(data.size()-1).getLongitude()
                            );

                        }
                        LatLngBounds.Builder builder2 = new LatLngBounds.Builder();
                        builder2.include(new LatLng(bean.getStartLatitude(), bean.getStartLongitude()));
                        List<LatLng> result = new ArrayList<>();
                        if (data != null) {
                            for (LocationBean o : data) {
                                if (o.getLatitude() != 0 && o.getLongitude() != 0) {
                                    LatLng latLng = new LatLng(o.getLatitude(), o.getLongitude());
                                    result.add(latLng);
                                    builder2.include(latLng);

                                }
                            }
                        }
                        LatLngBounds latlngBounds = builder2.build();
                        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, DeviceUtils.dip2px(mContext, 50)));


                        return result;
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

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        drawMark(bean);

                    }
                });


    }

    private void drawMark(OrderDetailBean bean) {

        MarkUtils.addMark(aMap,
                BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.start))
                , bean.getStartLatitude(), bean.getStartLongitude()
        );
        MarkUtils.addMark(aMap,
                BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.end))
                , bean.getDestinationLatitude(), bean.getDestinationLongitude()
        );
    }


}
