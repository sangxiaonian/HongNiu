package com.hongniu.supply.net;

import com.hongniu.baselibrary.entity.CarTypeBean;
import com.hongniu.baselibrary.entity.CommonBean;
import com.sang.common.net.rx.RxUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/8/13.
 * <p>
 * 登录使用的App
 */
public class HttpAppFactory {


    /**
     * 获取车辆类型
     *
     */
    public static Observable<CommonBean<List<CarTypeBean>>> getCarType() {

        return AppClient.getInstance().getService().getCarType().compose(RxUtils.<CommonBean<List<CarTypeBean>>>getSchedulersObservableTransformer());

    }

}
