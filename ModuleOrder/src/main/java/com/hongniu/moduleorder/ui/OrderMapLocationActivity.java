package com.hongniu.moduleorder.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiSearch;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.sang.common.event.BusFactory;
import com.sang.common.event.IBus;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.thirdlibrary.map.MapUtils;
import com.sang.thirdlibrary.map.MarkUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.Observable;

/**
 * 定位，选择当前所在点的Activity，
 */
@Route(path = ArouterParamOrder.activity_map_loaction)
public class OrderMapLocationActivity extends RefrushActivity<PoiItem> implements MapUtils.OnMapChangeListener, View.OnClickListener {


    private TextView etSearch;
    private int selectPositio;
    private boolean start;
    private PoiSearch.SearchBound searchBound;
    private MapView mMapView;
    private AMap aMap;
    private Marker marker;
    MapUtils mapUtils;
    private PoiItem searchKey;
    private PeakHolder header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_map_location_ativity);
        setToolbarTitle("位置");
        setToolbarSrcRight("确定");

        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        aMap = mMapView.getMap();
        mMapView.onCreate(savedInstanceState);

        initView();
        initData();
        initListener();
        mapUtils = new MapUtils();
        mapUtils.init(this, aMap);
        mapUtils.setMapListener(this);

    }


    @Override
    protected void initView() {
        super.initView();
        etSearch = findViewById(R.id.et_search);
        refresh.setVisibility(View.GONE);


    }

    @Override
    protected void initData() {
        super.initData();
        start = getIntent().getBooleanExtra(Param.TRAN, false);

        if (start) {
            marker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.start))));
            etSearch.setText("从哪里发货");
        } else {
            marker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.end))));
            etSearch.setText("在哪里收货");
        }
        header = new PeakHolder(mContext, rv, R.layout.order_item_loaction_head) {
            @Override
            public void initView(int position) {
                super.initView(position);
                TextView tvTitle = itemView.findViewById(R.id.tv_title);
                tvTitle.setText(String.format(getString(R.string.map_search_aleart), key));
            }
        };
    }

    @Override
    protected Observable<CommonBean<PageBean<PoiItem>>> getListDatas() {
        PoiSearch.Query  query = new PoiSearch.Query( searchKey==null?"":searchKey.getTitle(), "", "");


//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(Param.PAGE_SIZE);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);//设置查询页码
        query.setCityLimit(true);
        query.requireSubPois(true);
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        if (searchBound != null) {
            poiSearch.setBound(searchBound);
        }
        return HttpOrderFactory.searchPio(poiSearch);
    }


    @Override
    protected XAdapter<PoiItem> getAdapter(List<PoiItem> datas) {
        return new XAdapter<PoiItem>(mContext, datas) {
            @Override
            public BaseHolder<PoiItem> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<PoiItem>(context, parent, R.layout.map_select_item) {
                    @Override
                    public void initView(View itemView, final int position, final PoiItem data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                        TextView tvDes = (TextView) itemView.findViewById(R.id.tv_des);
                        ImageView img = itemView.findViewById(R.id.img);

                        String placeInfor=Utils.dealPioPlace(data);


                        if (selectPositio == position) {
                            img.setVisibility(View.VISIBLE);
                            if (etSearch!=null&&!TextUtils.isEmpty(placeInfor)){
//                                etSearch.setText(placeInfor);
                            }

                        } else {
                            img.setVisibility(View.GONE);
                        }


                        tvDes.setText(placeInfor);
                        tvTitle.setText(data.getTitle());
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectPositio = position;
                                upData = false;
                                adapter.notifyDataSetChanged();
                                MarkUtils.moveMark(marker, data.getLatLonPoint().getLatitude(), data.getLatLonPoint().getLongitude());
                                mapUtils.moveTo(data.getLatLonPoint().getLatitude(), data.getLatLonPoint().getLongitude());


                            }
                        });
                    }
                };
            }
        };
    }

    //移动地图的时候是否更新数据
    private boolean upData = true;

    @Override
    protected void initListener() {
        super.initListener();

        etSearch.setOnClickListener(this);

        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPositio >= 0 && datas.size() > selectPositio) {
                    IBus.IEvent event;
                    PoiItem poiItem = datas.get(selectPositio);

                    if (start) {
                        event = new OrderEvent.StartLoactionEvent(poiItem);
                    } else {
                        event = new OrderEvent.EndLoactionEvent(poiItem);

                    }
                    BusFactory.getBus().post(event);
                    finish();
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请选择地址");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (refresh.getVisibility() == View.VISIBLE) {
            refresh.setVisibility(View.GONE);
        } else {
            super.onBackPressed();

        }
    }


    @Override
    public void onTaskSuccess() {
        super.onTaskSuccess();
        adapter.notifyDataSetChanged();
        refresh.setVisibility(View.VISIBLE);

    }


    /**
     * 定位改变监听
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void loactionChangeListener(double latitude, double longitude) {
        searchBound = new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 1000);
        mapUtils.moveTo(latitude, longitude);
    }

    /**
     * 地图移动变化
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void onCameraChange(double latitude, double longitude) {
        MarkUtils.moveMark(marker, latitude, longitude);
    }

    /**
     * 地图移动完成
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void onCameraChangeFinish(double latitude, double longitude) {
        if (upData) {
            searchBound = new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 1000);
            selectPositio = 0;
            searchKey=null;
            queryData(true);
        } else {
            upData = true;
        }
    }


    @Override
    protected boolean getUseEventBus() {
        return true;
    }
    String key;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(  OrderEvent.SearchPioItem event) {
        if (event != null) {
            this.searchKey = event.t;
            upData=false;
            searchBound=null;
            selectPositio = 0;
            mapUtils.moveTo(searchKey.getLatLonPoint().getLatitude(),searchKey.getLatLonPoint().getLongitude());
            key =event.key;
            if (TextUtils.isEmpty(key)){
                adapter.removeHeard(header);
            }else  {
                adapter.addHeard(header);
            }
            queryData(true, true);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.et_search) {

            Intent intent = new Intent(mContext, OrderMapSearchActivity.class);
            ActivityOptionsCompat optionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this, etSearch, "search");
            startActivity(intent, optionsCompat.toBundle());
        }
    }
}
