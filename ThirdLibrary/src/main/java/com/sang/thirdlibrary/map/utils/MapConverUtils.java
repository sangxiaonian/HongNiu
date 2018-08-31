package com.sang.thirdlibrary.map.utils;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;

/**
 * 作者： ${PING} on 2018/8/31.
 * 地图换算工具
 */
public class MapConverUtils {

    /**
     * 两坐标之间的距离 单位米
     * @param startLatitude
     * @param startLongitude
     * @param endLatitude
     * @param endLongitude
     * @return
     */
    public static float caculeDis(double startLatitude, double startLongitude, double endLatitude,
                           double endLongitude){
        DPoint startLatlng =new DPoint();
        DPoint endLatlng =new DPoint();
        startLatlng.setLatitude(startLatitude);
        startLatlng.setLongitude(startLongitude);
        endLatlng.setLatitude(endLatitude);
        endLatlng.setLongitude(endLongitude);
        return CoordinateConverter.calculateLineDistance(startLatlng,endLatlng);
    }
}
