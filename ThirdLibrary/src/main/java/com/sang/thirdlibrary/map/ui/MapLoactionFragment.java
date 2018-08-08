package com.sang.thirdlibrary.map.ui;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.thirdlibrary.R;
import com.sang.thirdlibrary.map.LoactionMapUtils;
import com.sang.thirdlibrary.utils.XLog;

/**
 * 作者： ${PING} on 2018/8/8.
 * <p>
 * 地图定位App，用来查询并显示地点的的Fragment
 */
public class MapLoactionFragment extends Fragment implements AMap.OnMyLocationChangeListener, PoiSearch.OnPoiSearchListener {


    MapView mMapView = null;
    private EditText etSearch;
    private PoiSearch.Query query;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.map_loaction_fragment, container, false);
        mMapView = inflate.findViewById(R.id.map);
        etSearch = inflate.findViewById(R.id.et_search);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        LoactionMapUtils.getInstance().initMap(mMapView).setOnMyLocationChangeListener(this);
        initView();
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


    private void initView() {
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

    private void searchPio() {
        String trim = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(trim)){
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("请输入搜索地址");
        }else {
            query = new PoiSearch.Query(trim, "", "");
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
            query.setPageSize(10);// 设置每页最多返回多少条poiitem
            query.setPageNum(1);//设置查询页码
            PoiSearch poiSearch = new PoiSearch(getContext(), query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.searchPOIAsyn();
        }

    }


    @Override
    public void onMyLocationChange(Location location) {
        XLog.i(location.toString());

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        JLog.i(poiResult.toString());
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}

