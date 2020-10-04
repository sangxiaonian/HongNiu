package com.hongniu.supply.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.utils.clickevent.ClickEventBean;
import com.hongniu.supply.entity.AppToken;
import com.hongniu.supply.entity.HomeADBean;
import com.hongniu.supply.entity.WayBillBean;
import com.hongniu.supply.entity.WayBillParams;
import com.fy.androidlibrary.net.rx.RxUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpMainFactory {


    /**
     * 查询首页活动信息
     *
     * @return
     */
    public static Observable<CommonBean<List<HomeADBean>>> queryActivity() {
        return MainClient.getInstance().getService()
                .queryActivity()
                .compose(RxUtils.<CommonBean<List<HomeADBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询首页活动信息
     *
     * @return
     */
    public static Observable<CommonBean<List<HomeADBean>>> queryClickEvent() {
        return MainClient.getInstance().getService()
                .queryClickEvent()
                .compose(RxUtils.<CommonBean<List<HomeADBean>>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 查询首页活动信息
     *
     * @param eventParams
     * @return
     */
    public static Observable<CommonBean<String>> upClickEvent(ClickEventBean eventParams) {
        return MainClient.getInstance().getService()
                .upClickEvent(eventParams)
                .compose(RxUtils.<CommonBean<String>>getSchedulersObservableTransformer())
                ;
    }

    /**
     * 根据运单号查询运单信息
     *
     * @param wayBillNumber
     * @return
     */
    public static Observable<CommonBean<List<WayBillBean>>> queryWaybill(String wayBillNumber) {
        WayBillParams array = new WayBillParams();
        array.setWaybillNum(  wayBillNumber);
        return MainClient.getInstance().getService()
                .queryWaybill(array)
                .compose(RxUtils.<CommonBean<List<WayBillBean>>>getSchedulersObservableTransformer())
                ;
    }   /**
     * 根据运单号查询运单信息
     *
     * @param token
     * @return
     */
    public static Observable<CommonBean<Object>> upToken(String token) {
        AppToken array = new AppToken();
        array.setDeviceTokens( token);
        array.setDeviceType("android");
        return MainClient.getInstance().getService()
                .upToken(array)
                .compose(RxUtils.<CommonBean<Object>>getSchedulersObservableTransformer())
                ;
    }


}
