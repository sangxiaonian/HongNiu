package com.hongniu.moduleorder.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.amap.api.maps.model.LatLng;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.moduleorder.entity.LocationBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.JLog;
import com.sang.thirdlibrary.map.utils.MapConverUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 作者： ${PING} on 2018/8/27.
 * <p>
 * 位置信息上传工具类
 */
public class LoactionUpUtils {


    private List<LocationBean> temp = new ArrayList<>();
    private int tempSize = 10;//每次批量上传的坐标个数
    private int minDis = 10;//记录两次坐标之间的最小距离

    LoactionInfor loactionInfor=new LoactionInfor();

    public void setOrderInfor(String orderId, String carId, double destinationLatitude, double destinationLongitude) {
        loactionInfor = new LoactionInfor();
        loactionInfor.cardID = carId;
        loactionInfor.orderId = orderId;
        loactionInfor.latitude = destinationLatitude;
        loactionInfor.longitude = destinationLongitude;
    }


    private LatLng lastLoaction = new LatLng(0, 0);


    public void add(double latitude, double longitude, long movingTime, float speed, float bearing) {


        if (loactionInfor == null || TextUtils.isEmpty(loactionInfor.cardID) || TextUtils.isEmpty(loactionInfor.orderId)) {
            return;
        }

        //计算当前距离目的地的距离
        float dis = MapConverUtils.caculeDis(loactionInfor.latitude, loactionInfor.longitude, latitude, longitude);
        float v = MapConverUtils.caculeDis(lastLoaction.latitude, lastLoaction.longitude, latitude, longitude);

        if (dis < 100) {
            LocationBean bean = getLocationBean(latitude, longitude, movingTime, speed, bearing);
            if (v > minDis) {
                temp.add(bean);
            }
            notifyQueue(temp);
            lastLoaction = new LatLng(latitude, longitude);
            temp.clear();
        } else if (v < minDis) {
            JLog.d("上次位置：" + lastLoaction.latitude +
                    "\n此次位置：" + latitude
                    + "\n此次记录距离：" + v
                    + "\n速度：" + speed
                    + "\n方向：" + bearing
                    + "\n位置改变：" + (lastLoaction.latitude == latitude)
            );
        } else {
            LocationBean bean = getLocationBean(latitude, longitude, movingTime, speed, bearing);
            if (temp.size() < tempSize) {
                temp.add(bean);
            } else {
                notifyQueue(temp);
                temp.clear();
                temp.add(bean);
            }
            lastLoaction = new LatLng(latitude, longitude);

        }
    }

    @NonNull
    private LocationBean getLocationBean(double latitude, double longitude, long movingTime, float speed, float bearing) {
        LocationBean bean = new LocationBean();
        bean.setOrderId(loactionInfor.orderId);
        bean.setCarId(loactionInfor.cardID);
        bean.setLatitude(latitude);
        bean.setLongitude(longitude);
        bean.setSpeed(speed);
        bean.setDirection(bearing);
        bean.setMovingTime(ConvertUtils.formatTime(movingTime, "yyyy-MM-dd HH:mm:ss"));
        return bean;
    }


    /**
     * 刷新队列，并将新的数据加入队列
     *
     * @param temp
     */
    private void notifyQueue(List<LocationBean> temp) {
        if (temp == null || temp.isEmpty()) {
            return;
        }
        List<LocationBean> datas = new ArrayList<>(10);
        datas.addAll(temp);
        upData(datas);
    }

    public Disposable disposable;


    //将数据上传
    public void upData(final List<LocationBean> datas) {
        if (datas == null || datas.isEmpty()) {
            return;
        }
        HttpOrderFactory
                .upLoaction(datas)
                .map(new Function<CommonBean<String>, CommonBean<String>>() {
                    @Override
                    public CommonBean<String> apply(CommonBean<String> stringCommonBean) throws Exception {
                        return stringCommonBean;
                    }
                })
                .compose(RxUtils.<CommonBean<String>>retry(3, 1000))
                .subscribe(new NetObserver<String>(null) {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        disposable = d;
                    }

                    @Override
                    public void doOnSuccess(String data) {
                    }
                });
    }


    /**
     * 摧毁时候清除所有数据
     */
    public void onDestroy() {
        JLog.i("停止记录位置信息");
        upData(temp);
        loactionInfor = null;
    }


    public static class LoactionInfor {
        /**
         * true	long	车辆ID
         */
        public String cardID;
        /**
         * true	long	订单id
         */
        public String orderId;
        /**
         * 终点位置
         */
        public double latitude;
        public double longitude;


        public String getCardID() {
            return cardID;
        }

        public void setCardID(String cardID) {
            this.cardID = cardID;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }


}
