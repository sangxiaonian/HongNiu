package com.hongniu.moduleorder.ui;

import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.control.OrderMapListener;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.ui.fragment.OrderMapPathFragment;
import com.sang.common.event.BusFactory;
import com.sang.common.event.IBus;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 定位，选择当前所在点的Activity，
 */
@Route(path = ArouterParamOrder.activity_map_loaction)
public class OrderMapLocationActivity extends RefrushActivity<PoiItem> implements  AMap.OnMyLocationChangeListener {


    private EditText etSearch;
    private int selectPositio = -1;
    private boolean start;
    private OrderMapListener helper;
    private PoiSearch.SearchBound searchBound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_map_location_ativity);
        setToolbarTitle("位置");
        setToolbarSrcRight("确定");
        OrderMapPathFragment orderMapPathFragment = new OrderMapPathFragment();
        helper = orderMapPathFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.content, orderMapPathFragment).commit();
        initView();
        initData();
        initListener();


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
            etSearch.setHint("从哪里发货");
        } else {
            etSearch.setHint("在哪里收货");
        }
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchBound=null;
                    queryData(true, true);
                }
                return false;
            }
        });
    }

    @Override
    protected Observable<CommonBean<PageBean<PoiItem>>> getListDatas() {
        PoiSearch.Query query = new PoiSearch.Query(etSearch.getText().toString().trim(), "", "");
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(Param.PAGE_SIZE);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        if (searchBound!=null) {
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

                                    if (helper != null) {
                                        if (start) {
                                            helper.setStartMarker(data.getLatLonPoint().getLatitude(), data.getLatLonPoint().getLongitude(), data.getTitle());
                                        } else {
                                            helper.setEndtMarker(data.getLatLonPoint().getLatitude(), data.getLatLonPoint().getLongitude(), data.getTitle());

                                        }
                                    }
                                }

                            }
                        });
                    }
                };
            }
        };
    }


    @Override
    protected void initListener() {
        super.initListener();
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPositio >= 0 && datas.size() > selectPositio) {
                    IBus.IEvent event;
                    PoiItem poiItem = datas.get(selectPositio);
                    JLog.i(poiItem.getLatLonPoint() + ">>>>" + poiItem.getEnter() + ">>>" + poiItem.getExit());
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



    @Override
    public void onMyLocationChange(Location location) {
       searchBound = new PoiSearch.SearchBound(new LatLonPoint(location.getLatitude(),
                location.getLongitude()), 1000);
        queryData(true);
    }
}
