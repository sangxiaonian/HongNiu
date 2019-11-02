package com.hongniu.baselibrary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiSearch;
import com.hongniu.baselibrary.R;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.BaseUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.sang.common.net.rx.RxUtils;
import com.sang.thirdlibrary.map.MapUtils;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 作者：  on 2019/11/2.
 */
public class MapPointFrament extends BaseFragment implements MapUtils.OnMapChangeListener {

    private MapView mMapView;
    private AMap aMap;
    //    private Marker marker;
    MapUtils mapUtils;
    private boolean start = true;

    //移动地图的时候是否更新数据
    private boolean upData = true;
    private ViewGroup llMarkDes;
    private TextView tv_title;
    private TextView tv_des;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frament_map_point, null, false);
        mMapView = inflate.findViewById(R.id.map);
        llMarkDes = inflate.findViewById(R.id.ll);
        tv_title = inflate.findViewById(R.id.tv_title);
        tv_des = inflate.findViewById(R.id.tv_des);
        aMap = mMapView.getMap();
        mMapView.onCreate(savedInstanceState);


        return inflate;

    }

    @Override
    protected void initData() {
        super.initData();
        mapUtils = new MapUtils();
        mapUtils.init(getContext(), aMap);
        mapUtils.setMapListener(this);
        aMap.getUiSettings().setScaleControlsEnabled(false);//隐藏定位标签
        llMarkDes.setVisibility(View.GONE);

//        if (start) {
//            marker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.start))));
//        } else {
//            marker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.end))));
//        }

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * 定位改变监听
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void loactionChangeListener(double latitude, double longitude) {
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
//        MarkUtils.moveMark(marker, latitude, longitude);
        llMarkDes.setVisibility(View.GONE);
    }

    /**
     * 地图移动完成
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void onCameraChangeFinish(double latitude, double longitude) {
        queryData(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 1000));
    }

    private void queryData(PoiSearch.SearchBound keSearch) {
        upData = false;
        PoiSearch search = getBoundParams(keSearch);

        HttpAppFactory.searchPio(search)
                .subscribe(new NetObserver<PageBean<PoiItem>>(null) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        llMarkDes.setVisibility(View.VISIBLE);
                        tv_des.setText("加载中...");
                        tv_title.setVisibility(View.GONE);
                    }

                    @Override
                    public void doOnSuccess(PageBean<PoiItem> data) {
                        //查询到所地址
                        if (!BaseUtils.isCollectionsEmpty(data.getList())) {
                            PoiItem poiItem = data.getList().get(0);
                            //移动到第一个的位置
//                            MarkUtils.moveMark(marker, poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
                            llMarkDes.setVisibility(View.VISIBLE);
                            String placeInfor = Utils.dealPioPlace(poiItem);
                            tv_des.setText(placeInfor);
                            tv_title.setText(poiItem.getTitle());
                            tv_title.setVisibility(View.VISIBLE);
                        }

                    }
                });


    }


    //根据当前地理位置进行搜索
    private PoiSearch getBoundParams(PoiSearch.SearchBound searchBound) {
        upData = true;
        PoiSearch.Query query = new PoiSearch.Query("", "", "");
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(Param.PAGE_SIZE);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        query.setCityLimit(true);
        query.requireSubPois(true);
        PoiSearch poiSearch = new PoiSearch(getContext(), query);
        if (searchBound != null) {
            poiSearch.setBound(searchBound);
        }
        return poiSearch;
    }


    public void moveToPoint(PoiItem poiItem) {
        mapUtils.moveTo(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
        llMarkDes.setVisibility(View.VISIBLE);
        String placeInfor = Utils.dealPioPlace(poiItem);
        tv_des.setText(placeInfor);
        tv_title.setText(poiItem.getTitle());
    }

}
