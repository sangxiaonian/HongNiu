package com.hongniu.moduleorder.ui;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.sang.common.event.BusFactory;
import com.sang.common.event.IBus;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.thirdlibrary.map.LoactionMapUtils;

import java.util.ArrayList;

/**
 * 定位，选择当前所在点的Activity
 */
@Route(path = ArouterParamOrder.activity_map_loaction)
public class OrderMapLocationActivity extends BaseActivity implements AMap.OnMyLocationChangeListener, PoiSearch.OnPoiSearchListener {


    MapView mMapView = null;
    private EditText etSearch;
    private PoiSearch.Query query;
    private ArrayList<PoiItem> poiItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private int selectPositio = -1;
    private XAdapter<PoiItem> adapter;
    private boolean start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_map_location_ativity);
        setToolbarTitle("位置");
        setToolbarSrcRight("确定");
        initView();
        mMapView.onCreate(savedInstanceState);
        initData();
        initListener();
    }


    @Override
    protected void initView() {
        super.initView();
        mMapView = findViewById(R.id.map);
        etSearch = findViewById(R.id.et_search);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();
        start = getIntent().getBooleanExtra(Param.TRAN, false);

        if (start) {
            etSearch.setHint("从哪里发货");
        } else {
            etSearch.setHint("在哪里收货");
        }

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new XAdapter<PoiItem>(mContext, poiItems) {
            @Override
            public BaseHolder<PoiItem> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<PoiItem>(context, parent, R.layout.map_select_item) {
                    @Override
                    public void initView(View itemView, final int position, final PoiItem data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                        TextView tvDes = (TextView) itemView.findViewById(R.id.tv_des);
                        ImageView img = itemView.findViewById(R.id.img);
                        if (selectPositio == position) {
                            img.setVisibility(View.VISIBLE);
                        } else {
                            img.setVisibility(View.GONE);
                        }

                        tvDes.setText(data.getSnippet());
                        tvTitle.setText(data.getTitle());
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (selectPositio != position) {
                                    selectPositio = position;
                                    adapter.notifyDataSetChanged();
                                    LoactionMapUtils.getInstance().moveTo(data);
                                    LoactionMapUtils.getInstance().addMark(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                            .decodeResource(getResources(), R.drawable.vector_drawable_start)), data);

                                }

                            }
                        });
                    }
                };
            }
        };
        recyclerView.setAdapter(adapter);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchPio();
                }
                return false;
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        LoactionMapUtils.getInstance().initMap(mMapView).setOnMyLocationChangeListener(this);
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPositio > 0 && poiItems.size() > selectPositio) {
                    IBus.IEvent event;
                    if (start) {
                        event = new OrderEvent.StartLoactionEvent(poiItems.get(selectPositio));
                    } else {
                        event = new OrderEvent.EndLoactionEvent(poiItems.get(selectPositio));

                    }
                    BusFactory.getBus().post(event);
                    finish();
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请选择地址");
                }
            }
        });
    }

    private void searchPio() {
        String trim = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请输入搜索地址");
        } else {
            query = new PoiSearch.Query(trim, "", "");
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
            query.setPageSize(10);// 设置每页最多返回多少条poiitem
            query.setPageNum(1);//设置查询页码
            PoiSearch poiSearch = new PoiSearch(mContext, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.searchPOIAsyn();
        }

    }

    @Override
    public void onBackPressed() {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMyLocationChange(Location location) {
        JLog.i(location.toString());

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            poiItems.clear();
            poiItems.addAll(poiResult.getPois());
            adapter.notifyDataSetChanged();

            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("搜索失败");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
