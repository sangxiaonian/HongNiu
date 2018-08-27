package com.hongniu.moduleorder.utils;

import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.moduleorder.entity.LocationBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.reactivex.disposables.Disposable;

/**
 * 作者： ${PING} on 2018/8/27.
 * <p>
 * 位置信息上传工具类
 */
public class LoactionUpUtils {

    public Queue<List<LocationBean>> queue = new LinkedList<>();

    private List<LocationBean> temp =new ArrayList<>();



    /**
     * true	long	订单id
     */
    private String orderId;
    /**
     * true	string	车辆id
     */
    private String carId;

    public void setOrderInfor(String orderId, String carId) {
        this.orderId = orderId;
        this.carId = carId;
    }

    private double latitude;
    /**
     * true	double	经度，浮点数，范围为-180~180，负数表示西经
     */
    private double longitude;

    public void add(double latitude, double longitude,long movingTime) {
        LocationBean bean = new LocationBean();
        bean.setOrderId(orderId);
        bean.setCarId(carId);
        bean.setLatitude(latitude);
        bean.setLongitude(longitude);
        bean.setMovingTime(movingTime);
        if (temp.size()<10){
            temp.add(bean);
        }else {
            notifyQueue(temp);
            temp.clear();
            temp.add(bean);
        }


    }

    /**
     * 刷新队列，并将新的数据加入队列
     * @param temp
     */
    private void notifyQueue(List<LocationBean> temp) {
        List<LocationBean> datas=new ArrayList<>(10);
        datas.addAll(temp);
        queue.offer(datas);
        if (!isUp){
            upData();
        }
    }
    public Disposable disposable;

    private boolean isUp;

    //将数据上传
    public void upData(){
        if (queue.size()>0){
            isUp=true;
        }else {
            isUp=false;
            return;
        }
        List<LocationBean> poll = queue.peek();
        HttpOrderFactory.upLoaction(poll).subscribe(new NetObserver<String>(null) {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                isUp=false;
            }

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                disposable=d;
            }

            @Override
            public void doOnSuccess(String data) {
                queue.poll();//去除并删除第一个
                upData();
            }
        });

    }

    /**
     * 摧毁时候清除所有数据
     */
    public void onDestroy(){
        disposable.dispose();
        queue.clear();
    }




}
