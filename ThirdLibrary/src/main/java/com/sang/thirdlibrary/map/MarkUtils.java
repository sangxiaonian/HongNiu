package com.sang.thirdlibrary.map;

import android.graphics.BitmapFactory;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.sang.thirdlibrary.R;

import java.util.List;

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

    public static void addMark(AMap aMap, BitmapDescriptor bitmap, double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(bitmap);

        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//        markerOption.setFlat(true);//设置marker平贴地图效果
        aMap.addMarker(markerOption);


    }

    public static void moveMark(Marker mark, double latitude, double longitude){
        mark.setPosition(new LatLng(latitude,longitude));

    }



    /**
     * 算斜率
     */
    private static double getSlope(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        double slope = ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
        return slope;

    }
    /**
     * 根据点获取图标转的角度
     */
    public static double getAngle(int startIndex, List<LatLng> points) {
        if ((startIndex + 1) >= points.size()) {
            throw new RuntimeException("index out of bonds");
        }
        LatLng startPoint = points.get(startIndex);
        LatLng endPoint = points.get(startIndex + 1);
        return getSlope(startPoint, endPoint);
    }


}
