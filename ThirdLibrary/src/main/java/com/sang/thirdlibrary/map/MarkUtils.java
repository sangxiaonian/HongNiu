package com.sang.thirdlibrary.map;

import com.amap.api.col.n3.bm;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.PoiItem;

/**
 * 作者： ${桑小年} on 2018/8/28.
 * 努力，为梦长留
 */
public class MarkUtils {

    public static MarkerOptions creatMark(BitmapDescriptor bitmap, String title) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(bitmap);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        return markerOption;
    }

    public static void addMark(AMap aMap, BitmapDescriptor bitmap, double latitude,double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(bitmap);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        aMap.addMarker(markerOption);
    }
}
